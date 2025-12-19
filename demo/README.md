# Expense Sharing Application (Splitwise-like Backend)

## Overview
This project is a backend system for an expense-sharing application similar to Splitwise.  
It allows users to create groups, add shared expenses, track balances, and settle dues with simplified transactions.

The focus of this project is **clean system design**, **correct business logic**, and **balance simplification**, not UI or authentication.

---

## Features
- Create users and groups  
- Add expenses within a group  
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
- Postman (API testing)  

---

## High-Level Architecture
- **Controller Layer** → REST APIs  
- **Service Layer** → Business logic (expense handling & balance simplification)  
- **Repository Layer** → Database access using JPA  
- **Database** → H2 in-memory database  

---

## Core Design
- Expenses are stored per group  
- Each expense is split using Equal / Exact / Percentage logic  
- Net balances are calculated per user  
- Balances are simplified using a greedy settlement algorithm to minimize transactions  

---

## Sample APIs
- `POST /users` – Create user  
- `POST /groups` – Create group  
- `POST /expenses` – Add expense  
- `GET /balances/{groupId}` – Get simplified balances  

---

## How to Run
```bash
mvn spring-boot:run

## Application runs on:

http://localhost:8081


## How to Test

## Use Postman to:

Create users

Create a group with users

Add expenses

Fetch balances for a group

Future Enhancements

Authentication & authorization

Persistent database (MySQL/PostgreSQL)

Activity logs

Frontend integration