# Finance Dashboard System

A backend REST API for managing organizational financial records with role-based access control. Built with Java 21, Spring Boot 3, Spring Security 6, and MySQL.

![Java](https://img.shields.io/badge/Java-21-orange) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green) ![MySQL](https://img.shields.io/badge/MySQL-8-blue) ![Docker](https://img.shields.io/badge/Docker-Compose-purple)

---

## Overview

The Finance Dashboard System provides a secure backend for tracking organizational income and expenses. Admins manage users and financial records, Analysts view records and insights, and Viewers access dashboard summaries only.

Key design goals:
- Role-based access control enforced at the API level via Spring Security
- JWT authentication stored in HttpOnly cookies to prevent XSS token theft
- Dashboard aggregation computed at the database level — not in Java memory
- No self-registration — all users created by ADMIN only

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3 |
| Security | Spring Security 6 + JWT (JJWT 0.11.5) |
| Database | MySQL 8 + Spring Data JPA / Hibernate |
| Build | Maven |
| Containerization | Docker + Docker Compose |
| Validation | Jakarta Bean Validation |
| Token storage | HttpOnly cookie |

---

## Role Model

| Permission | ADMIN | ANALYST | VIEWER |
|---|:---:|:---:|:---:|
| View dashboard summary | ✅ | ✅ | ✅ |
| View financial records | ✅ | ✅ | ❌ |
| Create financial records | ✅ | ❌ | ❌ |
| Update financial records | ✅ | ❌ | ❌ |
| Delete financial records | ✅ | ❌ | ❌ |
| Create users | ✅ | ❌ | ❌ |
| Manage user roles | ✅ | ❌ | ❌ |
| Activate/deactivate users | ✅ | ❌ | ❌ |

> ADMIN cannot assign the ADMIN role via API — enforced in the service layer.

---

## API Endpoints

### Auth (public — no authentication required)

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/login` | Authenticate user. Sets `authToken` HttpOnly cookie. Returns username + role. |
| POST | `/api/auth/logout` | Clear `authToken` cookie. |

**Login request body:**
```json
{
    "username": "admin",
    "password": "Admin@123"
}
```

**Login response:**
```json
{
    "username": "admin",
    "role": "ADMIN",
    "token": "eyJhbGc..."
}
```

---

### User Management (ADMIN only)

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/users` | Create a new user (ANALYST or VIEWER role only) |
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| PUT | `/api/users/{id}` | Update user details |
| PUT | `/api/users/{id}/role` | Change user role |
| PUT | `/api/users/{id}/status` | Activate or deactivate user |
| DELETE | `/api/users/{id}` | Delete user |

**Create user request body:**
```json
{
    "username": "analyst1",
    "email": "analyst1@example.com",
    "password": "Password@123",
    "role": "ANALYST"
}
```

---

### Financial Records

| Method | Endpoint | Roles | Description |
|---|---|---|---|
| GET | `/api/records` | ADMIN, ANALYST | Get records with optional filters and pagination |
| GET | `/api/records/{id}` | ADMIN, ANALYST | Get single record by ID |
| POST | `/api/records` | ADMIN | Create a financial record |
| PUT | `/api/records/{id}` | ADMIN | Update a financial record |
| DELETE | `/api/records/{id}` | ADMIN | Delete a financial record |

**GET /api/records query parameters (all optional):**

| Parameter | Type | Default | Description |
|---|---|---|---|
| `type` | TransactionType | — | Filter by INCOME or EXPENSE |
| `category` | TransactionCategory | — | Filter by category |
| `from` | LocalDate (yyyy-MM-dd) | — | Date range start |
| `to` | LocalDate (yyyy-MM-dd) | — | Date range end |
| `page` | int | 0 | Page number (0-indexed) |
| `size` | int | 10 | Records per page |
| `sortBy` | String | date | Field to sort by |
| `sortDir` | String | desc | Sort direction (asc/desc) |

**Create record request body:**
```json
{
    "amount": 50000.00,
    "type": "INCOME",
    "category": "OTHERS",
    "date": "2024-03-15",
    "description": "March salary deposit"
}
```

---

### Dashboard (all authenticated roles)

| Method | Endpoint | Roles | Description |
|---|---|---|---|
| GET | `/api/dashboard/summary` | ADMIN, ANALYST, VIEWER | Full dashboard summary |

**Dashboard response:**
```json
{
    "totalIncome": 120000.00,
    "totalExpense": 45000.00,
    "netBalance": 75000.00,
    "categoryBreakdown": [
        { "category": "HOUSING", "total": 15000.00 },
        { "category": "UTILITIES", "total": 3000.00 }
    ],
    "monthlyTrends": [
        { "month": "January", "totalIncome": 50000.00, "totalExpense": 23000.00 },
        { "month": "February", "totalIncome": 70000.00, "totalExpense": 22000.00 }
    ],
    "recentActivity": [...]
}
```

---

## Data Models

### User entity

| Field | Type | Notes |
|---|---|---|
| id | Long | Auto-generated primary key |
| username | String | Unique, not null |
| email | String | Unique, not null |
| password | String | BCrypt hashed, never returned in responses |
| role | Role enum | ADMIN, ANALYST, VIEWER |
| status | UserStatus enum | ACTIVE (default), INACTIVE |
| createdAt | LocalDateTime | Set automatically by Hibernate |
| updatedAt | LocalDateTime | Updated automatically by Hibernate |

### FinancialRecord entity

| Field | Type | Notes |
|---|---|---|
| id | Long | Auto-generated primary key |
| amount | BigDecimal | Positive value, not null |
| type | TransactionType enum | INCOME or EXPENSE |
| category | TransactionCategory enum | See enum values below |
| date | LocalDate | Transaction date, not null, not future |
| description | String | Optional, max 500 chars |
| createdBy | User | FK to users table, set from SecurityContext |
| createdAt | LocalDateTime | Set automatically |

### Enums

**TransactionType:** `INCOME`, `EXPENSE`

**TransactionCategory:** `ENTERTAINMENT`, `FOOD`, `HEALTHCARE`, `HOUSING`, `OTHERS`, `SAVINGS`, `SHOPPING`, `TRANSPORTATION`, `UTILITIES`

**Role:** `ADMIN`, `ANALYST`, `VIEWER`

**UserStatus:** `ACTIVE`, `INACTIVE`

---

## Local Development Setup

### Prerequisites

- Java 21
- Maven
- MySQL 8 running locally

### Steps

**1. Clone the repository**
```bash
git clone https://github.com/yourusername/finance-dashboard-system.git
cd finance-dashboard-system
```

**2. Create the database**
```sql
CREATE DATABASE finance_db;
```

**3. Create `application-local.properties` in `src/main/resources/`**
```properties
DB_URL=jdbc:mysql://localhost:3306/finance_db
DB_USERNAME=your_username
DB_PASSWORD=your_password
JWT_SECRET_KEY=your-secret-key-minimum-32-characters
```

**4. Set the active profile in your IDE**

Add this environment variable to your run configuration:
```
SPRING_PROFILES_ACTIVE=local
```

**5. Run the application**
```bash
./mvnw spring-boot:run
```

Hibernate auto-creates all tables on startup. A default admin account is 
created automatically via `DataSeeder` — a `CommandLineRunner` bean that 
checks if the admin username exists and creates it if not. No manual SQL 
insert required.

### Default Admin Credentials

```
Username: admin
Password: Admin@123
```

---

## Docker Setup

### Prerequisites

- Docker Desktop installed and running

### Steps

**1. Clone the repository**
```bash
git clone https://github.com/yourusername/finance-dashboard-system.git
cd finance-dashboard-system
```

**2. Create your environment file**
```bash
cp .env.example .env
```

**3. Fill in your values in `.env`**
```
DB_USERNAME=your_username
DB_PASSWORD=your_password
MYSQL_ROOT_PASSWORD=your_root_password
JWT_SECRET_KEY=your-secret-key-minimum-32-characters
```

> `JWT_SECRET_KEY` must be at least 32 characters for HMAC-SHA256 signing.

**4. Build and run**
```bash
docker-compose up --build
```

**5. API is available at**
```
http://localhost:8080
```

Wait for this log line before making requests:

Started FinanceDashboardApplication

The default admin account is seeded automatically on first startup by 
`DataSeeder`. No manual database setup required.

### Docker commands

```bash
# Run in background
docker-compose up --build -d

# View logs
docker-compose logs -f app

# Stop containers
docker-compose down

# Stop and remove all data (wipes database)
docker-compose down -v
```

---

## Architecture & Design Decisions

### No self-registration
All users are created by ADMIN only via `POST /api/users`. This prevents unauthorized access to financial data. A default admin account is seeded on first startup via `DataSeeder` implementing `CommandLineRunner`.

### JWT in HttpOnly cookie
The JWT token is stored in an HttpOnly cookie rather than the Authorization header or localStorage. HttpOnly cookies cannot be accessed by JavaScript — XSS attacks cannot steal the token. The cookie is set on login and cleared on logout.

### `createdBy` from SecurityContext
When creating a financial record, the `createdBy` field is set from the authenticated user in `SecurityContextHolder` — never from the request body. The client cannot forge the creator identity.

### BigDecimal for financial amounts
All monetary amounts use `BigDecimal` not `double` or `float`. Floating point arithmetic introduces rounding errors which are unacceptable for financial data.

### DB-level aggregation for dashboard
Dashboard totals, category breakdowns, and monthly trends are computed via JPQL aggregation queries (`SUM`, `GROUP BY`, `MONTH()`) — not by fetching all records into Java memory and iterating. This scales correctly regardless of record volume.

### ADMIN cannot assign ADMIN role
Enforced in `UserService.createUser()` and `UserService.changeRole()`. A compromised admin account cannot escalate privileges by creating another admin.

### HttpMethod-level security in SecurityConfig
Access control for financial records is enforced at the HTTP method level in `SecurityConfig` using `requestMatchers(HttpMethod.GET, ...)` etc. This makes the access policy explicit and centralized rather than scattered across method annotations.

---

## Optional Features Implemented

- ✅ JWT authentication via HttpOnly cookies
- ✅ Pagination on `GET /api/records` (page, size, sortBy, sortDir)
- ✅ Filtering on `GET /api/records` (type, category, date range)
- ✅ Docker + Docker Compose deployment
- ✅ Global exception handling with structured JSON error responses
- ✅ Input validation with Jakarta Bean Validation

## Optional Features Not Implemented

- Soft delete (hard delete used)
- Rate limiting
- Unit / integration tests
- Full-text search
- Swagger / OpenAPI documentation

---

## Error Responses

All error responses follow this structure:

```json
{
    "error": "User not found with id: 5"
}
```

| Status | Scenario |
|---|---|
| 400 | Validation failure — missing or invalid fields |
| 401 | Invalid or missing JWT token, wrong credentials, inactive user |
| 403 | Authenticated but insufficient role |
| 404 | Resource not found |
| 409 | Duplicate username or email |
| 500 | Unexpected server error |

---

## Project Structure

```
src/main/java/com/syed/fds/
├── config/
│   └── SecurityConfig.java
├── controller/
│   ├── AuthController.java
│   ├── UserController.java
│   ├── FinancialRecordController.java
│   └── DashboardController.java
├── service/
│   ├── AuthService.java
│   ├── UserService.java
│   ├── FinancialRecordService.java
│   └── DashboardService.java
├── repository/
│   ├── UserRepository.java
│   └── FinancialRecordRepository.java
├── entity/
│   ├── User.java
│   └── FinancialRecord.java
├── dto/
│   ├── request/
│   └── response/
├── security/
│   ├── JwtUtil.java
│   ├── JwtAuthFilter.java
│   └── CustomUserDetailsService.java
├── enums/
│   ├── Role.java
│   ├── UserStatus.java
│   ├── TransactionType.java
│   └── TransactionCategory.java
└── exception/
    ├── ResourceNotFoundException.java
    └── GlobalExceptionHandler.java
```
