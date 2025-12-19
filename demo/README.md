# Expense Sharing Application (Splitwise-like Backend)

## Overview
This project is a backend system for an expense-sharing application similar to Splitwise.  
It allows users to create groups, add shared expenses, track balances, and generate simplified settlements.

The focus is on **clean system design**, **correct business logic**, and **balance simplification**, not UI or authentication.

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
- **Repository Layer** – Data access using JPA
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

## How to Run the Project

### Prerequisites
- Java 17 installed
- Maven installed

### Steps
```bash
cd demo
mvn spring-boot:run
