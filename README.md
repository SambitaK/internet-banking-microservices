# Internet Banking Microservices Backend

A backend system for an Internet Banking application, built using Spring Boot microservices architecture.

## Overview

This project is being developed step-by-step to understand how different services find each other, share configuration from one place, and communicate with one another.

## Reference

This project references the architecture from [Internet Banking Concept Microservices](https://github.com/JavatoDev-com/internet-banking-concept-microservices).

### Key Differences from Reference
While the reference project uses Keycloak, RabbitMQ, Docker, and Kubernetes, this implementation focuses on core microservices patterns using a simplified tech stack.

## Completed Microservices

1. **Service Registry (Eureka Server)** - Port 8081
   - Eureka Server for service discovery and registration.

2. **Config Server** - Port 8090
   - Centralized configuration management
   - Native profile (filesystem-based configs)
   - Serves configurations to all microservices

3. **Core Banking Service** - Port 8092
   - Account management (Create, Read)
   - Transaction operations (Deposit, Withdraw)
   - MySQL database integration
   - Business logic validation

4. **User Service** - Port 8083
   - User registration and management
   - MySQL database integration
   - **OpenFeign client integration**
   - **Inter-service communication with Core Banking Service**
   - Automatic bank account creation during user registration
   - Duplicate username/email validation

5. **API Gateway** - Port 8082
   - Single entry point for all microservices
   - Reactive routing with Spring Cloud Gateway (WebFlux)
   - Automatic service discovery via Eureka
   - Routes:
      - `/user-service/**` → User Service (8083)
      - `/core-banking-service/**` → Core Banking Service (8092)
   - Runs on Netty (reactive server)

6. **Fund Transfer Service** - Port 8084
   - Inter-account fund transfers
   - Transfer history and tracking
   - Transfer status tracking (SUCCESS/FAILED)
   - MySQL database: `fund_transfer_db`
   - Account validation before transfer
   - Unique transaction reference numbers
   

## Technology Stack

- **Java**: 21
- **Spring Boot**: 3.2.4
- **Spring Cloud**: 2023.0.0
- **Database**: MySQL 8.0
- **Service Discovery**: Netflix Eureka
- **Build Tool**: Maven
- **Architecture**: Microservices

## Core Dependencies
- Spring Boot Starter Web
- Spring Cloud Config Server
- Spring Cloud Netflix Eureka (Server & Client)
- Spring Data JPA
- MySQL Connector
- Spring Cloud OpenFeign
- Spring Cloud Gateway

## API Endpoints

### Core Banking Service (Port 8092)

#### Create Account
```http
POST http://localhost:8092/api/accounts
```

#### Get All Accounts
```http
GET http://localhost:8092/api/accounts
```

#### Get Account by Number
```http
GET http://localhost:8092/api/accounts/{accountNumber}
```

#### Deposit Money
```http
POST http://localhost:8092/api/accounts/{accountNumber}/deposit
```

#### Withdraw Money
```http
POST http://localhost:8092/api/accounts/{accountNumber}/withdraw
```

### User Service (Port 8083)

#### Register User (Creates user + bank account automatically)
```http
POST http://localhost:8083/api/users/register
```

#### Get All Users
```http
GET http://localhost:8083/api/users
```

#### Get User by ID
```http
GET http://localhost:8083/api/users/{id}
```

### API Gateway (Port 8082) - Unified Entry Point

**All services are accessible through the API Gateway:**

#### Register User via Gateway
```http
POST http://localhost:8082/user-service/api/users/register
```

#### Get All Accounts via Gateway
```http
GET http://localhost:8082/core-banking-service/api/accounts
```

#### Deposit via Gateway
```http
POST http://localhost:8082/core-banking-service/api/accounts/{accountNumber}/deposit
```

### Fund Transfer Service (Direct: 8084 | Via Gateway: 8082)

#### Initiate Fund Transfer
```http
POST http://localhost:8082/fund-transfer-service/api/transfers
```

#### Get All Transfers
```http
GET http://localhost:8082/fund-transfer-service/api/transfers
```

#### Get Transfer by Reference Number
```http
GET http://localhost:8082/fund-transfer-service/api/transfers/reference/TXN-xxxxx
```

#### Get Transfers by Account Number
```http
GET http://localhost:8082/fund-transfer-service/api/transfers/account/ACCNO.
```


## Author

- **Sambita Khuntia**
- **Email**: somyasambita11@gmail.com

---
