# Internet Banking Microservices Backend

A backend system for an Internet Banking application, built using Spring Boot microservices architecture.

## Overview

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

4. **User Service** - Port 8083
   - User registration and management
   - MySQL database integration
   - **OpenFeign client integration**
   - **Inter-service communication with Core Banking Service**
   - Automatic bank account creation during user registration
   - Duplicate username/email validation


### Work in Progress

Upcoming additions include:

5. **API Gateway** - Port 8082
   - Single entry point for all services

6. **Fund Transfer Service** - Port 8084
   - Inter-account fund transfers
   - Transfer history and tracking
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

## Author

- **Sambita Khuntia**
- **Email**: somyasambita11@gmail.com

---

**Note**: This project is under active development. More microservices and features will be added progressively.