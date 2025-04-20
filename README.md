# 멀티모듈 기반 이커머스 프로젝트

## 기술 스택

- Kotlin 2.0 _(Java21)_
- Spring Boot 3.4.1
- MySQL8.4.4
- Redis Cluster
- Kafka

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

### 모듈 의존 흐름

```mermaid
stateDiagram
    [*] --> SpringBootApplication: Inbound
    note right of SpringBootApplication
        메인 실행 모듈
    end note

    state INTERFACE {
        RestController
        EventListener
    }
    SpringBootApplication --> RestController: Route
    Partition --> EventListener: Consume
    Channel --> EventListener: Subscribe

    state SERVICE {
        state Domain {
            Member
            Order
            Product
            Pay
            ETC
        }
        state UseCase {
            Service
            Facade
            Event
            Batch
            Scheduler
        }
        UseCase --> Domain: Operate
    }

    EventListener --> SERVICE: Invoke
    RestController --> SERVICE: Request/Response
    UseCase --> CORE: Reference

    state INFRASTRUCTURE {
        state Client {
            Lettuce
            Lettuce --> Redis: Publish Event
            KafkaClient
            KafkaClient --> Kafka: Produce Event

            state RDBMS {
                JPA
                QueryDSL
                QueryDSL --> JPA: Support
                JDBC
                Jooq
            }

            RDBMS --> Database: Query/Command
        }

        state Storage {
            Cache
            Database
            Cache --> Database: Read Through
            Cache --> Redis: Use
            Database --> Cache: Write Through/Around
            Database --> MySQL: Use
        }
    }

    state COMMON {
        MySQL
        Kafka
        Redis

        state EVENTBUS {
            Partition
            Channel
        }
    }

    Client --> SERVICE: DIP
    Kafka --> Partition
    Redis --> Channel

    state CORE {
        DistributedLock
        Exception
        Logger
        Security
        Web
    }
    note left of CORE
        인터페이스를 통해 메인 모듈에의해서 간접 의존
        UseCase 말고도 여러곳에서 사용
    end note
    SpringBootApplication --> CORE: Implement
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