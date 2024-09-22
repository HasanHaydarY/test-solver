# Test Solver

## Project Description
This project provides a service for students to solve tests. Students can participate in tests, view the questions they answered, and track their test performance. The project is developed using Spring Boot with an H2 in-memory database.

## Features
- Student information: name, surname, student number.
- Test information: test name, questions, and answers.
- A student can participate in multiple tests.
- A test can have multiple students.
- View student test participation and performance.
- H2 in-memory database support.
- RESTful service architecture.
- Validation and error handling.
- Application documentation.

## Prerequisites
1. **Clone the Project**: 
   ```bash
   
   git clone https://github.com/HasanHaydarY/test-solver.git

2. **Install Dependencies: Use Maven to install dependencies.**
   ```bash
    mvn install
4. **Start the Application**: To run the application:
   ```bash
   mvn spring-boot:run
6. **H2 Console**: Access the H2 database console at http://localhost:8080/h2-console. Login credentials:
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **User Name:** `sa`
- **Password:** `password`
7. **API Usage**: You can manage students and tests using the provided APIs. Use Postman or curl for sample requests and responses.
