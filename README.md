# 멀티모듈 기반 이커머스 프로젝트

## 기술 스택

-   Kotlin 2.0 _(Java21)_
-   Spring Boot 3.4.1
-   MySQL8.4.4
-   Redis Cluster
-   Kafka

## 프로젝트 구조

### 패키지 구조

```
project-root/
└── module/
    ├── app/
    │   └── # core 모듈의 구현, 스프링 설정 등
    │   └── Application.kt (SpringBootApplication 진입점)
    │
    ├── core/
    │   └── # 공통 의존 모듈
    │
    ├── common/
    │   └── # 공통 사용 모듈 (레디스 클라이언트, 카프카, MySQL 등의 외부 시스템 클라이언트 구현체)
    │
    ├── domain/
    │   └─── domain-name
    │        ├─── domain-interface
    │        │    └── 도메인 인터페이스 (RestAPI, Consumer, Subscriber 등...)
    │        │
    │        ├─── domain-service
    │        │    └── 도메인 로직 (Service, Facade, UseCase, DTO 등...)
    │        │
    │        ├─── domain-persistence
    │        │    └── 도메인 데이터 영속 (JPA, Jooq, QueryDSL, Cache, AWS S3 등...)
    │        │
    │        └─── domain-external-client
    │             └── 외부 시스템 연동 (Publisher, Producer, 외부 API 등...)
    │
    └── test/
        ├─── configuration
        │    └── 공통 테스트 설정 (Testcontainers - Redis, Kafka, MySQL, H2 등...)
        └─── support
             └── 기타 테스트 공통 유틸 등
```

### 모듈 구조

#### 레이어 단위 구조

```mermaid
flowchart LR
    subgraph Domain Layer
        subgraph UseCase
            direction LR
            Service
            Facade
            Batch
            Scheduler
        end

        subgraph Domain
            direction LR
            Member
            Product
            Order
            Pay
            ETC...
        end
    end

    subgraph Interface Layer
        EventListener
        RestController
    end

    subgraph Infra[Infrastructure Layer]
        subgraph Client
            KafkaClient
            RedisClient
            DatabaseClient
        end
    end

    SpringBootApplication -->|Route| RestController

    RestController -->|Request/Response| UseCase
    EventListener -->|Invoke| UseCase

    UseCase -->|Operate| Domain

    Infra -.->|DIP| UseCase
```

#### 외부시스템 연동 구조

```mermaid
flowchart LR
    subgraph Infrastructure
        subgraph Client
            KafkaClient
            HttpClient

            subgraph RedisClient
                direction TB
                CacheClient
                EventClient
            end

            subgraph DatabaseClient
                direction TB
                QueryDSL
                JPA
                Jooq
                QueryDSL -->|Support| JPA
            end
        end
    end

    subgraph Event Bus
        Partition[[Kafka Event Topic]]
        Channel[[Redis Event Channel]]
    end

    Client -.->|DIP| UseCase((UseCase))
    HttpClient <-->|Request/Response| API>External RestAPI]
    EventClient -->|Event Publish|Channel
    KafkaClient -->|Event Produce|Partition

    subgraph Storage
        Cache[(Redis KV Store)]
        Database[(MySQL Database)]
    end

    CacheClient <-->|CacheAside
    WriteAround
    WriteThrough
    etc|Cache

    DatabaseClient <-->|Query/Command|Database
```

#### Core/Common 모듈 활용 및 구현 구조

> ```Core``` 모듈은 다른 의존성을 가지지 않으며 순수한 언어 단위의 **인터페이스**  
> 각각의 유즈케이스들은 직접적인 의존이 아닌 인터페이스를 활용한 간접적인 의존

```mermaid
flowchart LR
    subgraph Common
        Redis
        Kafka
        etc[ETC ...]
    end

    subgraph Core
        DistributedLock
        EventProducer
        Exception
        Logger
        ETC[ETC ...]
        Redis -->|Implement| DistributedLock
        Kafka -->|Implement| EventProducer

    end
    UseCase -.->|Dependent| Core
    SpringBootApplication -->|Implement| Core
    SpringBootApplication -->|Configure| Common
```

### Event 처리 구조

```mermaid
flowchart TB
    subgraph Domain Layer
        UseCase -->|도메인 이벤트 발생| DomainEvent
    end

    subgraph Core
        EventProducer
    end

    subgraph Infrastructure Layer
        KafkaClient
        RedisClient
    end

    subgraph Event Bus
        Partition[[Kafka Event Topic]]
        Channel[[Redis Event Channel]]
    end

    subgraph Interface Layer
        EventListener
    end

    DomainEvent -->|Publish| EventProducer
    EventProducer -->|Send| KafkaClient & RedisClient

    KafkaClient -->|Produce| Partition
    RedisClient -->|Publish| Channel

    Partition -->|Consume| EventListener
    Channel -->|Subscribe| EventListener

    EventListener -->|Invoke| UseCase
```


## Domain Model Diagram

```mermaid
classDiagram
    class Member {
        +id: UUID
        +name: String
        +email: String
        +password: String
        +cash: Int
    }

    class Address {
        +id: UUID
        +memberId: UUID
        +address: String
        +default: Boolean
        changeByMemberId(memberId: UUID) AddressDTO
    }

    class Product {
        +id: UUID
        +name: String
        +price: Int
        +capacity: Int
        +productStatus: ProductStatus
        isOrderble() Boolean
    }

    class ProductStatus {
        <<ENUM>>
        ENABLE
        UNABLE
    }

    class Cart {
        +id: UUID
        +memberId: UUID
        +items: List~CartItem~
    }

    class CartItem {
        +productId: UUID
        +amount: Int
    }

    class Order {
        +id: UUID
        +memberId: UUID
        +addressId: UUID
        +orderItems: List~OrderItem~
        +status: OrderStatus
        +address: AddressDTO
    }

    class OrderItem {
        +productId: UUID
        +quantity: Int
        +price: Int
    }

    class OrderStatus {
        <<enum>>
        PENDING
        PAID
        SHIPPING
        DELIVERED
        CANCELLED
        REFUNDED
    }

    Member "1" --> "0..*" Cart: owns
    Member "1" --> "0..*" Order: places
    Member "1" --> "1..*" Address: references
    Order --> OrderStatus
    Product --> ProductStatus
    Order "1" --> "1..*" OrderItem: contains
    Order "1" --> "1" Address: references
    OrderItem "1" --> "1" Product: references
    Cart "1" --> "1..*" CartItem: contains
    CartItem "1" --> "1" Product: references
```
