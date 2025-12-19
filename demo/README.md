# Expense Sharing Application (Splitwise-like Backend)

## Overview
This project is a backend system for an expense-sharing application similar to Splitwise.

It allows users to create groups, add shared expenses, track balances, and generate simplified settlements.

The focus of this project is **clean system design**, **correct business logic**, and **efficient balance simplification**, not UI or authentication.

---

## Features
- Create users and groups
- Add shared expenses within a group
- Supported split types:
  - Equal split
  - Exact amount split
  - Percentage split
- Track who owes whom
- Generate simplified settlements with minimum transactions
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
- Expenses are stored per group
- Each expense is split using Equal / Exact / Percentage logic
- Net balance is calculated per user
- Balances are simplified using a greedy settlement algorithm to minimize transactions

---

## API Endpoints (Sample)

| Method | Endpoint | Description |
|------|---------|-------------|
| POST | `/users` | Create a user |
| POST | `/groups` | Create a group |
| POST | `/expenses` | Add an expense |
| GET  | `/balances/{groupId}` | Get simplified balances |

---

## Setup & Installation

### Prerequisites
- Java 17 installed
- Maven installed
- Git installed

### Clone the Repository
```bash
git clone https://github.com/Adarsh0503/SplitwiseLocal.git
cd SplitwiseLocal/demo
