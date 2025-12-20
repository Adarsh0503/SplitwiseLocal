# Expense Sharing Application (Splitwise-like Backend)

## Overview
This project is a backend system for an expense-sharing application similar to Splitwise. It allows users to create groups, add shared expenses, track balances, and generate simplified settlements.

The focus of this project is on **clean system design**, **correct business logic**, and **balance simplification**, not UI or authentication.

---

## Features
- Create users and groups
- Add shared expenses within a group
- Supported split types:
  - Equal split
  - Exact amount split
  - Percentage split
- Track who owes whom
- Simplify balances to minimize transactions
- Mark expenses as settled

---

## Tech Stack
- Java 17
- Spring Boot
- Spring Data JPA (Hibernate)
- H2 In-Memory Database
- Maven
- Postman (for API testing)

---

## High-Level Architecture
- **Controller Layer** – REST APIs
- **Service Layer** – Business logic (expense handling & balance simplification)
- **Repository Layer** – Database access using JPA
- **Database** – H2 in-memory database

---

## Core Design
- Expenses belong to a group
- Each expense is split using Equal / Exact / Percentage logic
- Net balances are calculated per user
- Balances are simplified using a greedy settlement algorithm to reduce transactions

---

## API Endpoints (Sample)

| Method | Endpoint | Description |
|------|---------|-------------|
| POST | `/users` | Create a user |
| POST | `/groups` | Create a group |
| POST | `/expenses` | Add an expense |
| GET  | `/balances/{groupId}` | Get simplified balances |

---

## Clone the Repository
```bash
git clone [https://github.com/Adarsh0503/SplitwiseLocal.git]

cd SplitwiseLocal/demo
```

---

## Run the Application
```bash
mvn spring-boot:run
```

The application will start on: http://localhost:8081

---
# Testing the Application (Using Postman)

### 1️⃣ Create Users 
**POST** `/users`

**JSON**
```json
{ 
  "name": "Alice", 
  "email": "alice@gmail.com" 
}
```
Create at least 3 users: Alice, Bob, and Charlie.

### 2️⃣ Create a Group
**POST** `/groups`

**JSON**
```json

{
  "name": "Goa Trip",
  "members": [
    { "id": 1 },
    { "id": 2 },
    { "id": 3 }
  ]
}
```

### 3️⃣ Add an Expense
**POST** `/expenses`

**JSON**
```json
{
  "groupId": 1,
  "paidByUserId": 1,
  "totalAmount": 300,
  "splitType": "EQUAL",
  "splits": {
    "1": 0,
    "2": 0,
    "3": 0
  },
  "description": "Dinner"
}
```



### 4️⃣ Get Simplified Balances
**GET** `/balances/1`


---

# Database
Uses H2 in-memory database

Data resets automatically on application restart

No external database setup required

---

# Future Enhancements
Authentication & authorization

Persistent database (MySQL/PostgreSQL)

Activity logs & audit history

Frontend integration

---

