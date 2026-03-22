# Expense Sharing Backend (Splitwise-like)

A production-ready backend system for expense sharing, similar to Splitwise. Built with **Java 17**, **Spring Boot**, and deployed via **Docker + Jenkins CI/CD**.

![Architecture](https://github.com/user-attachments/assets/63cf8cf8-0677-44cf-9b2c-c43c2d44c201)

---

## Features

- Create users and groups
- Add shared expenses with three split strategies:
  - **Equal** — split evenly among all members
  - **Exact** — specify exact amount per user
  - **Percentage** — split by percentage share
- Track who owes whom across a group
- **Greedy balance simplification** — minimizes the number of transactions needed to settle a group
- Mark individual expenses as settled

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.2 |
| ORM | Spring Data JPA / Hibernate |
| Database | H2 In-Memory |
| Build | Maven |
| Containerization | Docker (multi-stage build) |
| CI/CD | Jenkins |
| Testing | JUnit 5 + Mockito |

---

## Architecture

```
Controller Layer  →  REST API endpoints
Service Layer     →  Business logic, split calculation, balance simplification
Repository Layer  →  JPA database access
Database          →  H2 in-memory (auto-reset on restart)
```

---

## API Reference

| Method | Endpoint | Description |
|---|---|---|
| POST | `/users` | Create a user |
| GET | `/users` | List all users |
| POST | `/groups` | Create a group |
| GET | `/groups/{groupId}` | Get group by ID |
| POST | `/expenses` | Add an expense |
| GET | `/expenses/group/{groupId}` | List expenses in a group |
| PATCH | `/expenses/{id}/settle` | Mark expense as settled |
| GET | `/balances/{groupId}` | Get simplified settlement payments |

---

## Getting Started

### Prerequisites
- Java 17
- Maven 3.x

### Run Locally
```bash
git clone https://github.com/Adarsh0503/SplitwiseLocal.git
cd SplitwiseLocal/demo
mvn spring-boot:run
```

App runs on: **http://localhost:8081**
H2 Console: **http://localhost:8081/h2-console**

### Run with Docker
```bash
docker build -t splitwise-backend .
docker run -p 8081:8081 splitwise-backend
```

### Run with Docker Compose
```bash
docker-compose up
```

---

## Testing the API (Postman)

### 1. Create Users
**POST** `/users`
```json
{ "name": "Alice", "email": "alice@gmail.com" }
```
Repeat for Bob and Charlie.

![Create User](https://github.com/user-attachments/assets/e03ac76d-7d18-415f-bb47-38e9c3c4f27a)

---

### 2. Create a Group
**POST** `/groups`
```json
{
  "name": "Goa Trip",
  "members": [{ "id": 1 }, { "id": 2 }, { "id": 3 }]
}
```

![Create Group](https://github.com/user-attachments/assets/684ca635-3172-4588-8041-7c505ace8921)

---

### 3. Add an Expense
**POST** `/expenses`
```json
{
  "groupId": 1,
  "paidByUserId": 1,
  "totalAmount": 300,
  "splitType": "EQUAL",
  "splits": { "1": 0, "2": 0, "3": 0 },
  "description": "Dinner"
}
```

![Add Expense](https://github.com/user-attachments/assets/faadb045-48b5-4076-af9f-b9c147551571)

---

### 4. Get Simplified Balances
**GET** `/balances/1`

Returns the minimum set of payments needed to settle the group:
```json
[
  { "fromUserId": 2, "toUserId": 1, "amount": 100.00 },
  { "fromUserId": 3, "toUserId": 1, "amount": 100.00 }
]
```

![Balances](https://github.com/user-attachments/assets/a3fef7b4-53ef-4ec7-97f4-92c5d7a1683b)

---

### 5. Settle an Expense
**PATCH** `/expenses/1/settle`

Returns the expense with `"settled": true`. Settled expenses are excluded from future balance calculations.

---

## Design Highlights

### Balance Simplification Algorithm
Uses a **greedy approach with two priority queues** — a max-heap for creditors and a min-heap for debtors. Always matches the largest creditor with the largest debtor, minimizing total transactions. Runs in **O(n log n)**.

### Split Validation
- `EXACT` splits must sum exactly to the total amount
- `PERCENTAGE` splits must sum to 100
- The payer must be a member of the group

### Error Handling
All errors return a consistent JSON structure:
```json
{
  "timestamp": "2025-01-01T10:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Group not found with id: 5"
}
```

---

## CI/CD Pipeline (Jenkins)

The `Jenkinsfile` defines the following stages:

```
Checkout → Build → Test → Code Quality → Package → Docker Build → Docker Push → Deploy
```

---

## Future Enhancements

- Authentication & authorization (JWT/OAuth2)
- Persistent database (PostgreSQL)
- Activity logs & audit history
- Frontend integration

---

## Author

**Adarsh Gaurav** — [GitHub](https://github.com/adarsh0503) · [LinkedIn](https://www.linkedin.com/in/adarsh-gaurav-3595b6219/)