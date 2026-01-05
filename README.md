# Internet Banking Microservices Backend

A backend system for an Internet Banking application, built using Spring Boot microservices architecture.

## Architecture Overview

This project is being developed step-by-step to understand how different services find each other, share configuration from one place, and communicate with one another.

## Current Status:-

### Completed Microservices

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

### Work in Progress

Upcoming additions include:

4. **API Gateway** - Port 8082 (Planned)
   - Single entry point for all services

5. **User Service** - Port 8083 (Planned)
   - User registration and authentication
   - User profile management

6. **Fund Transfer Service** - Port 8084 (Planned)
   - Inter-account fund transfers
   - Transfer history and tracking

   ## Project Structure
   ```
   internet-banking-microservices/
   ├── service-registry/
   │   ├── src/
   │   └── pom.xml
   ├── config-server/
   │   ├── src/
   │   │   └── main/
   │   │       └── resources/
   │   │           └── config/
   │   │               ├── service-registry.properties
   │   │               └── user-service.properties
   │   └── pom.xml
   ├── core-banking-service/
   │   ├── src/
   │   │   └── main/
   │   │       └── java/com/banking/core_banking_service/
   │   │           ├── entity/
   │   │           ├── repository/
   │   │           ├── service/
   │   │           └── controller/
   │   └── pom.xml
   └── README.md
   ```

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

## Author

- **Sambita Khuntia**
- **Email**: somyasambita11@gmail.com

---

**Note**: This project is under active development. More microservices and features will be added progressively.