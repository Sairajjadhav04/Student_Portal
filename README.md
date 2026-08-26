# Student Portal Backend MVP

A backend MVP for a **Student Portal** developed using **Java, Spring Boot, Spring Security, JWT, Maven, and PostgreSQL**.

The system provides separate modules for students, faculty, and administrators along with academic management features and an integrated online quiz system.

---

## 📌 Backend Modules

The backend currently includes the following modules:

### 1. JWT Authentication

* User registration
* User login
* JWT token generation
* JWT-based authentication for protected endpoints

### 2. Student Module

* Student-related operations
* Access to academic information
* Access to available quizzes
* Quiz attempts and submission

### 3. Faculty Module

* Faculty-related operations
* Assignment creation
* Academic management
* Quiz creation and management
* Quiz reports

### 4. Admin Module

* Administrative operations
* View all quizzes
* System-level management

### 5. Subject Management

* Subject-related management
* Subject information for academic operations

### 6. Assignment Creation

* Faculty can create assignments
* Assignment information can be managed through the backend

### 7. Attendance

* Attendance management
* Student attendance information

### 8. Marks

* Marks management
* Student marks information

### 9. Results

* Result management
* Student result information

### 10. Online Quiz

The online quiz module provides role-specific functionality.

#### Student

* View available quizzes
* Start quizzes
* Submit quizzes
* Manage/view quiz attempts

#### Faculty

* Create quizzes
* Publish quizzes
* Close quizzes
* View quiz reports

#### Admin

* View all quizzes

---

#  Technology Stack

| Technology      | Purpose                         |
| --------------- | ------------------------------- |
| Java 21+        | Backend programming             |
| Spring Boot     | Backend framework               |
| Spring Security | Security and authentication     |
| JWT             | Token-based authentication      |
| Spring Data JPA | Database interaction            |
| Hibernate       | ORM                             |
| PostgreSQL      | Database                        |
| Maven           | Build and dependency management |
| IntelliJ IDEA   | Development environment         |

---

#  Requirements

Before running the project, make sure you have:

* **Java 21+**
* **Maven**
* **PostgreSQL 8+**
* **IntelliJ IDEA**

---

# 🗄️ Database Setup

Create a PostgreSQL database named:

```sql
CREATE DATABASE student_portal;
```

Make sure PostgreSQL is running before starting the backend.

---

# ⚙️ Configuration

Open the following file:

```text
src/main/resources/application.properties
```

Configure your PostgreSQL database details.

In particular, update:

```properties
spring.datasource.password=YOUR_POSTGRESQL_PASSWORD
```

Use your own local PostgreSQL password.

> **Important:** Do not commit your actual database password or other sensitive credentials to GitHub.

---

# ▶️ Running the Application

## 1. Clone the Repository

```bash
git clone https://github.com/Sairajjadhav04/Student_Portal.git
```

Navigate to the project directory:

```bash
cd Student_Portal
```

---

## 2. Build the Project

Run:

```bash
mvn clean install
```

If you are using the Maven Wrapper on Windows:

```powershell
.\mvnw.cmd clean install
```

---

## 3. Start the Backend

Run:

```bash
mvn spring-boot:run
```

Or using the Maven Wrapper:

```powershell
.\mvnw.cmd spring-boot:run
```

---

## 4. Backend URL

After the application starts successfully:

```text
http://localhost:8081
```

---

# 🔐 Authentication

The application uses **JWT authentication**.

## Register

Endpoint:

```http
POST /api/auth/register
```

Use this endpoint to register a new user.

---

## Login

Endpoint:

```http
POST /api/auth/login
```

After successful login, the server returns a JWT token.

Use the returned token to access protected endpoints.

### Authorization Header

```http
Authorization: Bearer YOUR_TOKEN
```

Replace `YOUR_TOKEN` with the JWT received from the login response.

---

# 🔄 Authentication Flow

```text
                User
                  │
                  ▼
              Register
                  │
                  ▼
                Login
                  │
                  ▼
        Validate Credentials
                  │
                  ▼
            Generate JWT
                  │
                  ▼
          Return JWT Token
                  │
                  ▼
       Authorization: Bearer
                  │
                  ▼
        Protected Endpoints
```

---

# 🧠 Online Quiz Flow

## Student Flow

```text
View Available Quizzes
          ↓
      Start Quiz
          ↓
    Answer Questions
          ↓
     Submit Quiz
          ↓
    Record Attempt
```

## Faculty Flow

```text
   Create Quiz
       ↓
  Publish Quiz
       ↓
Students Attempt Quiz
       ↓
    Close Quiz
       ↓
  View Reports
```

## Admin Flow

```text
     Admin
       ↓
View All Quizzes
```

---

# 🏗️ Project Architecture

The backend follows a typical layered architecture:

```text
             Client / Frontend
                     │
                     ▼
              REST Controllers
                     │
                     ▼
                Service Layer
                     │
                     ▼
              Repository Layer
                     │
                     ▼
             Spring Data JPA
                     │
                     ▼
                 Hibernate
                     │
                     ▼
                PostgreSQL
```

Authentication is handled through **Spring Security and JWT**.

---

# 📚 Main Functional Areas

```text
Student Portal Backend
│
├── JWT Authentication
│
├── Student Module
│
├── Faculty Module
│
├── Admin Module
│
├── Subject Management
│
├── Assignment Creation
│
├── Attendance
│
├── Marks
│
├── Results
│
└── Online Quiz
    │
    ├── Student
    │   ├── Available Quizzes
    │   ├── Start
    │   ├── Submit
    │   └── Attempts
    │
    ├── Faculty
    │   ├── Create
    │   ├── Publish
    │   ├── Close
    │   └── Reports
    │
    └── Admin
        └── View All Quizzes
```

---

# 🔒 Security

The current MVP uses:

* Spring Security
* JWT authentication
* Protected endpoints
* Bearer token authorization

The authorization implementation is intentionally kept simple for the MVP.

---

# 🚧 Future Improvements

For a production-ready version, the following improvements are recommended:

* Method-level role restrictions
* DTO-based responses
* Refresh tokens
* Audit logs
* File upload validation
* Pagination
* Stronger quiz timing controls
* Anti-cheating mechanisms

These features are **future improvements and are not represented as currently implemented functionality**.

---

# 📌 Project Status

**Status: Backend MVP**

The current MVP provides the core backend functionality for:

* Authentication
* Student module
* Faculty module
* Admin module
* Subject management
* Assignment creation
* Attendance
* Marks
* Results
* Online Quiz

---

#  Project Information

**Project:** Student Portal Backend MVP

**Type:** Full Stack Java Backend

**Backend:** Spring Boot

**Database:** PostgreSQL

**Authentication:** JWT

**Build Tool:** Maven

**Java Version:** 21+

**Development Environment:** IntelliJ IDEA

---

#  License

This project is developed for **educational and academic purposes**.
