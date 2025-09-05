# 💰 SpendWise – Smart Personal Finance Manager

[![Java](https://img.shields.io/badge/Java-17-red?style=flat-square&logo=openjdk)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green?style=flat-square&logo=springboot)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18-blue?style=flat-square&logo=react)](https://reactjs.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat-square&logo=mysql)](https://www.mysql.com/)
[![Cloudinary](https://img.shields.io/badge/Cloudinary-Image%20Hosting-orange?style=flat-square&logo=cloudinary)](https://cloudinary.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow?style=flat-square)](LICENSE)

SpendWise is a **full-stack personal finance management app** designed to help you  
**track expenses, monitor incomes, manage categories, and analyze spending habits**.

Built using:

- ⚡ **Spring Boot (Java)** backend for speed & scalability
- 🎨 **React.js** frontend for a smooth user experience
- ☁️ **Cloudinary** for secure image hosting
- 📧 **Brevo SMTP** for email notifications
- 🗃️ **My SQL** for local database storage and,
- ⛁ **PostgreSQL** for production database storage

---

# 💰 Income & Expense Tracker

A **fully functional income and expense management app** built with **React.js** (frontend) and **Spring Boot** (backend).  
The app allows users to **track income, expenses, categories, and profile details**, with rich features like charts, notifications, email reminders, and secure authentication.

---

## 🚀 Features

✅ Register and Login with JWT Authentication  
✅ Manage **Income & Expenses** with input validation  
✅ Upload profile picture to **Cloudinary**  
✅ Category selection with **emoji picker**  
✅ Real-time feedback using **React Hot Toast**  
✅ Analyze financial data using **React Charts**  
✅ Filter transactions by category  
✅ Download and Email transactions  
✅ Loading spinners with **Lucide React Icons**

---

## 🖼️ App Pages

- `/` → **Root**
- `/health` → Health check page
- `/batman` → Fun/error placeholder page
- `/error` → Error page
- `/login` → User login
- `/signup` → User signup
- `/dashboard` → Home dashboard
- `/income` → Income management
- `/expense` → Expense management
- `/filter` → Filter data by category/date
- `/category` → Category management
- `/update-profile` → Update profile (with Cloudinary image upload)

---

## 🛠️ Tech Stack

### Frontend

- ⚛️ **React.js**
- 🎨 **Tailwind CSS**
- 🔔 **React Hot Toast**
- 🎭 **Lucide React Icons**
- 😃 **Emoji Picker**
- 📊 **React Charts**
- 🌐 **Axios**

### Backend

- ☕ **Spring Boot**
- 🗄️ **Spring Data JPA**
- 🛡️ **Spring Security + JWT**
- 🐬 **MySQL Database**

---

## 🔐 Authentication

- User passwords are securely hashed.
- JWT tokens are used for protected routes.

---

## 📸 Screenshots

### 🔑 Login Page

![Login Page](public/screenshots/login.png)

### 📝 Signup Page

![Signup Page](public/screenshots/signup.png)

### 📊 Dashboard (Home)

![Dashboard](public/screenshots/dashboard.png)

### 💰 Income Page

![Income Page](public/screenshots/income.png)

### 🔍 Filter Page

![Filter Page](public/screenshots/filters.png)

### 💸 Expense Page

![Expense Page](public/screenshots/expense.png)

### 📂 Category Page

![Category Page](public/screenshots/category.png)

### 👤 Update Profile Page

![Update Profile Page](public/screenshots/update.png)

### ⚠️ Error Page

## ![Error Page](public/screenshots/error.png)

### 🙋🏼‍♂️ User Avatar

![User Avatar](public/screenshots/user-avatar.png)

### 💸 Add Income

![Add Income](public/screenshots/add-income.png)

## 📧 Contact

👤 **Nakul Attrey**  
📩 nakulattrey@gmail.com
🌐 [Portfolio](https://bio.site/nakulattreydev)

---

## ✨ Features

### 👤 **User Management**

- Secure **JWT authentication**
- Profile picture upload (**Cloudinary integration**)
- Email verification & account activation
- Password encryption using **Spring Security**

### 💸 **Expense Tracking**

- Add, edit, delete expenses
- Categorize expenses (Food, Travel, etc.)
- View monthly totals
- Latest 5 expenses widget

### 💰 **Income Tracking**

- Add, edit, delete incomes
- Categorize incomes (Salary, Investments, etc.)
- View monthly totals
- Latest 5 incomes widget

### 🗂 **Categories**

- Create categories for **income** & **expense**
- Filter categories by type
- Edit category details

### 📊 **Analytics & Insights**

- Dashboard with total income, expenses & balance
- Filter transactions by **date, keyword, type**
- Sort transactions by date or custom fields

---

## 🛠 Tech Stack

| Layer             | Technologies                                                   |
| ----------------- | -------------------------------------------------------------- |
| **Frontend**      | React.js, Tailwind CSS, Axios                                  |
| **Backend**       | Java 17, Spring Boot 3, Spring Security (JWT), Spring Data JPA |
| **Database**      | MySQL 8.0, PostgreSQL                                          |
| **Image Hosting** | Cloudinary                                                     |
| **Email Service** | Brevo SMTP                                                     |
| **Others**        | Lombok, Maven, Postman                                         |

---

## 📌 API Overview

| Method   | Endpoint               | Description            |
| -------- | ---------------------- | ---------------------- |
| **POST** | `/register`            | Register new account   |
| **POST** | `/login`               | Authenticate & get JWT |
| **POST** | `/expenses/addExpense` | Add new expense        |
| **GET**  | `/dashboard`           | Get dashboard stats    |
| **POST** | `/filter`              | Filter transactions    |

Full API route list available in **SpendWise Routes** documentation.

---

## 📷 Screenshots

---

## 🚀 Installation & Setup

```bash
# Clone the repository
git clone https://github.com/your-username/spendwise.git

# Backend setup
cd spendwise/backend
mvn clean install
mvn spring-boot:run

# Frontend setup
cd ../frontend
npm install
npm run dev



### Real-Life Analogy:

You = Customer
Waiter = Controller
Kitchen = Service
Cookbook = Util
Chefs = Entity
Inventory Manager = Repository
Security Guard = Security
Restaurant Manager = Config
Packaging Guy = DTO

### Folder Responsibilities

| Folder       | Role in Code                                        | Real-Life Role     | Simple                                                                                          |
| ------------ | --------------------------------------------------- | ------------------ | ----------------------------------------------------------------------------------------------- |
| `controller` | Handles HTTP requests (e.g., `/add-expense`)        | Waiter             | Talks to the user (browser/app), takes input, and sends it to the kitchen.                      |
| `service`    | Business logic                                      | Kitchen            | Prepares the actual food (logic) based on the order.                                            |
| `repository` | Talks to the database                               | Inventory Manager  | Gets or saves ingredients (data) from storage (DB).                                             |
| `entity`     | Data model (table blueprint)                        | Chef               | Represents the dish being cooked — what ingredients go in (fields like amount, date, category). |
| `dto`        | Custom objects for sending/receiving data           | Packaging guy      | Packages the food nicely before giving it to the customer.                                      |
| `util`       | Helper code (e.g., date formatter, token generator) | Cookbook           | Reusable tools or helpers used by chefs and kitchen.                                            |
| `security`   | Manages login/auth                                  | Security guard     | Controls who gets in, who can order, and protects the restaurant.                               |
| `config`     | Configuration files                                 | Restaurant manager | Sets up how things should work — timings, rules, menus.                                         |

### Flow

[React UI]
↓
@Controller ← Waiter
↓
@Service ← Kitchen
↓
@Repository ← DB guy
↓
Database
↑
DTO/Entity ↔ Util used anywhere
Security used globally
Config runs during setup



```
