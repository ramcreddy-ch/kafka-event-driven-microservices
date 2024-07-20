# Kafka Event-Driven Microservices

A Spring Boot microservices architecture demonstrating event-driven communication using Apache Kafka.

## Architecture

```
                 ┌───────────────┐
                 │  Order Svc    │
                 │ (Producer)    │
                 └───────┬───────┘
                         │ OrderPlacedEvent
                         ▼
                 ┌───────────────┐
                 │    Kafka      │
                 └───────┬───────┘
          ┌──────────────┴───────────────┐
          │                              │
          ▼                              ▼
┌──────────────────┐           ┌──────────────────┐
│  Inventory Svc   │           │ Notification Svc │
│   (Consumer)     │           │    (Consumer)    │
└──────────────────┘           └──────────────────┘
```

## Services

1. **Order Service** (`8081`): Creates orders and publishes `OrderPlacedEvent`.
2. **Inventory Service** (`8082`): Listens for order events and reserves inventory.
3. **Notification Service** (`8083`): Listens for order events and sends email notifications.

## Prerequisites

- Java 17
- Maven 3.8+
- Docker & Docker Compose

## Build & Run

### 1. Build the project

```bash
mvn clean package -DskipTests
```

### 2. Run Infrastructure (Kafka)

```bash
docker-compose up -d zookeeper kafka
```

### 3. Run Services (Local)

Run each service in a separate terminal:

```bash
# Order Service
java -jar order-service/target/order-service-*.jar

# Inventory Service
java -jar inventory-service/target/inventory-service-*.jar

# Notification Service
java -jar notification-service/target/notification-service-*.jar
```

Or run everything via Docker:

```bash
docker-compose up --build
```

## Usage

### Place an Order

```bash
curl -X POST http://localhost:8081/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "productId": "prod-123",
    "quantity": 2,
    "price": 99.99,
    "customerEmail": "user@example.com"
  }'
```

Check the logs for Inventory and Notification services to see the event processing.

## Author

Ramchandra Chintala
