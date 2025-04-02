#!/bin/bash
set -e  # 오류 발생 시 즉시 종료

echo "Stopping existing containers..."
docker-compose -f docker-compose.master.yml down --remove-orphans
docker-compose -f docker-compose.slave.yml down --remove-orphans

echo "Waiting for containers to stop completely..."
sleep 3

echo "Removing any remaining Redis containers..."
docker ps -aq --filter network=redis_cluster | xargs -r docker rm -f

echo "Force removing network..."
docker network rm redis_cluster || true

echo "Waiting before pruning networks..."
sleep 3

echo "Pruning unused networks..."
docker network prune -f

echo "Waiting before starting Redis Cluster..."
sleep 3

echo "Starting Redis Cluster..."
docker-compose -f docker-compose.master.yml up -d
docker-compose -f docker-compose.slave.yml up -d

echo "Waiting for Redis to initialize..."
sleep 5

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
