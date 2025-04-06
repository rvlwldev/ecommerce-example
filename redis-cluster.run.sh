#!/bin/bash
set -e  # 오류 발생 시 즉시 종료

echo "Removing Redis cluster containers..."
docker-compose -f docker-compose-redis-cluster-master.yml down
docker-compose -f docker-compose-redis-cluster-slave.yml down

while docker ps | grep -q "redis-"; do
  echo "Waiting for Redis containers to stop..."
  sleep 0.5
done

echo "Removing any remaining Redis containers..."
docker ps -aq --filter network=redis-cluster-network | xargs -r docker rm -f

echo "Force removing network..."
if docker network ls --format '{{.Name}}' | grep -Fxq "redis-cluster-network"; then
  docker network rm redis-cluster-network
fi

while docker network ls --format '{{.Name}}' | grep -Fxq "redis-cluster-network"; do
  echo "Waiting for redis-cluster-network to be removed..."
  sleep 0.5
done

echo "Pruning unused networks..."
docker network prune -f

echo "Cleaning up Redis cluster state..."
rm -rf ./volume/redis/*

echo "Waiting before starting Redis Cluster..."
until ! docker network ls | grep -q redis-cluster-network; do
  sleep 0.5
done

echo "Starting Redis Cluster..."
docker-compose -f docker-compose-redis-cluster-master.yml up -d
docker-compose -f docker-compose-redis-cluster-slave.yml up -d

echo "Waiting for Redis to initialize..."
until docker exec redis-master-001 redis-cli ping | grep -q PONG; do
  echo "Waiting for redis-master-001..."
  sleep 0.5
done

echo "Creating Redis Cluster..."
docker exec -it redis-master-001 redis-cli --cluster create \
    192.168.6.1:6379 \
    192.168.6.2:6379 \
    192.168.6.3:6379 \
    --cluster-replicas 0 --cluster-yes

echo "Adding Redis Slaves..."
docker exec -it redis-master-001 redis-cli --cluster add-node 192.168.6.101:6379 192.168.6.1:6379 --cluster-slave
docker exec -it redis-master-001 redis-cli --cluster add-node 192.168.6.102:6379 192.168.6.1:6379 --cluster-slave
docker exec -it redis-master-001 redis-cli --cluster add-node 192.168.6.103:6379 192.168.6.2:6379 --cluster-slave
docker exec -it redis-master-001 redis-cli --cluster add-node 192.168.6.104:6379 192.168.6.2:6379 --cluster-slave
docker exec -it redis-master-001 redis-cli --cluster add-node 192.168.6.105:6379 192.168.6.3:6379 --cluster-slave
docker exec -it redis-master-001 redis-cli --cluster add-node 192.168.6.106:6379 192.168.6.3:6379 --cluster-slave

echo "Checking Redis Cluster Nodes..."
docker exec -it redis-master-001 redis-cli cluster nodes

echo "Redis Cluster setup done!"
