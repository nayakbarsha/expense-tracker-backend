# 🫰🏻Expense Tracker – Backend (Spring Boot)
This repository contains the backend service for the Expense Tracker application.
It exposes REST APIs for managing expenses.

## 📝 Note
This backend exposes public CRUD APIs (authentication not implemented yet).
It is intended to be consumed by a frontend application.
This backend must be running locally to use the Expense Tracker frontend.

## 🔗 Frontend Repository
### Live UI (Backend required)
👉🏻 https://expense-tracker-frontend-pi-eight.vercel.app/

## 🛠 Tech Stack

* Java 21

* Spring Boot

* Spring Web (REST APIs)

* Spring Data JPA

* PostgreSQL / MySQL

* Maven

## ⚙️ How to Run Locally
1️⃣ Clone the repository
git clone 
```
https://github.com/nayakbarsha/ExpenseTracker-Backend.git
cd ExpenseTracker-Backend
```

2️⃣ Create database
~~~
CREATE DATABASE expense_tracker;

~~~

3️⃣ Configure application.properties
```
spring.datasource.url=jdbc:postgresql://localhost:5432/expense_tracker
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

spring.jpa.hibernate.ddl-auto=update
server.port=8080
```

4️⃣ Run the application
```
mvn spring-boot:run

```


## 🏁 🟢 🚀 Backend will start at:

http://localhost:8080

## ✅ Current Features

* Add, view, update, delete expenses

* APIs are publicly accessible (temporary)

* No authentication yet

## 🚧 Planned Enhancements

+ Login & signup

+ Secured expense access

+ JWT-based authentication

+ Backend deployment

## 👩‍💻 Author

Barsha Nayak
