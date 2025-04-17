# 멀티모듈 기반 이커머스 프로젝트

## Tech Stack

- Kotlin 2.0 _(Java21)_
- Spring Boot 3.4.1
- MySQL8.4.4
- Redis Cluster
- Kafka

## Multi Module Architecture

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
    │   ├─── domain-interface
    │   │    └── 도메인 인터페이스 (RestAPI, Consumer, Subscriber 등...)
    │   │
    │   ├─── domain-service
    │   │    └── 도메인 로직 (Service, Facade, UseCase, DTO 등...)
    │   │
    │   ├─── domain-persistence
    │   │    └── 도메인 데이터 영속 (JPA, Jooq, QueryDSL, Cache, AWS S3 등...)
    │   │
    │   └─── domain-external-client
    │        └── 외부 시스템 연동 (Publisher, Producer, 외부 API 등...)
    │
    └── test/
        ├─── configuration
        │    └── 공통 테스트 설정 (Redis, Kafka, MySQL, H2 등...)
        ├─── integration
        │    └── 도메인별 통합 테스트
        └─── support
             └── 기타 테스트 공통 유틸 등
```

### Domain Dependency Flow

```mermaid
stateDiagram
    [*] --> SpringBootApplication
    SpringBootApplication --> RestController: Route
    note right of SpringBootApplication
        메인 실행 모듈
    end note

    state EVENTBUS {
        Partition
        Channel
    }

    state INTERFACE {
        RestController
        EventListener
    }

    Partition --> EventListener
    Channel --> EventListener

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
        [*] --> UseCase
        UseCase --> Domain: Operate
    }

    EventListener --> SERVICE: Invoke
    RestController --> SERVICE: Request/Response
    SERVICE --> CORE: Reference

    state INFRASTRUCTURE {
        state Client {
            Hibernate
            Lettuce
            QueryDSL
            ETC
        }
        state Storage {
            Cache --> Database: Read Through
            Database --> Cache: Write Through/Around
            Cache --> Redis: Use
            Database --> MySQL: Use
        }

        Client --> Storage: Query/Command
        Client --> Kafka: Produce Event
        Client --> Redis: Publish Event

        state COMMON {
            MySQL
            Kafka
            Redis
        }
    }
    Client --> SERVICE: DIP
    Kafka --> Partition
    Redis --> Channel

    state CORE {
    %% ¬       state Interface {
        Exception
        Logger
        EventPublisher
        Config
        Security
    %%        }
    }

    SpringBootApplication --> CORE: Implement

```

### Domain Model Diagram

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