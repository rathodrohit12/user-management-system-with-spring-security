# UserManagementSystem
A User Management System built with Spring Boot, featuring a layered architecture with Controller, Service, Repository, and Entity layers. It uses Thymeleaf for dynamic views and Bootstrap for a responsive UI. The system supports full CRUD operations for user management with a focus on scalability and maintainability.


## Features
- **User Registration**: Users can sign up with an email and password (passwords are hashed for security).
- **User Login**: Users can log in with email and password authentication.
- **Session Management**: After logging in, users are redirected to a profile page and remain logged in until they log out.
- **Form Validation**: Validates input for both registration and login forms (e.g., password length, valid email format).
- **Error Handling**: Clear error messages are shown for failed login or registration attempts (e.g., incorrect credentials, existing user).
- **Secure Authentication**: Passwords are securely hashed and stored using BCrypt.
- **View All Users**: Admin can view a list of all registered users with their details.
- **Update User Information**: Admin can update the details of a user, including username, email, and password.
- **Delete User**: Admin can delete a user from the system.


## Project Folder Structure
```
└── usermanagementsystem
    ├── config              # Configuration classes (e.g., security, data source)
    ├── controller          # Controllers for routing and business logic
    ├── entity              # Entity classes for ORM (e.g., JPA)
    ├── dto                 # Data transfer objects for data transfer between layers
    ├── repository          # JPA repositories for database operations
    └── service             # Business logic services

```

## Prerequisites
- **Java 8 or higher**
- **Maven 3.5+ (for building the project)**
- **IDE like IntelliJ IDEA or Eclipse (optional but recommended)**


## Installation
To get started with this project, follow these steps:

1. **Clone the repository**:

```bash
git clone https://github.com/rathodrohit12/UserManagementSystem.git
cd UserManagementSystem
```
2. **Build the project**:
```bash
mvn clean install
```
3. **Run the Spring Boot application**:

```bash
mvn spring-boot:run
```
The application will start on http://localhost:8080.


## Screenshots
Here are some screenshots that demonstrate the user interface or other important features of the project.

1. **Home Page**:
Description of the home page.

2. **Login Page**:
Description of the login page.

You can upload screenshots and replace the file paths accordingly.

## Videos
You can include videos for a better demonstration of the application:

1. **Application Walkthrough**:

Watch the video A video explaining the core features and how the application works.




