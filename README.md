# Test Solver

## Project Description
The Test Solver project allows students to take tests, participate in multiple tests, and track their performance. The application features a service where users can create, update, delete, and manage students and tests, as well as track student participation in tests.

The application is built using Spring Boot and Java 21, with an H2 in-memory database for storing data temporarily. It is fully documented with RESTful web services and unit tests, ensuring smooth operations and maintainability.

## Features
- Student Management: Add, update, delete, and view student details (name, surname, student number).
- Test Management: Create tests with 10 questions, update, delete, and view tests.
- Student Test Participation: Track students’ test participation and calculate test performance based on answers.
- REST API: Exposed RESTful APIs for student and test management.
- Validation: Includes validation rules for input, ensuring a test contains exactly 10 questions and limits on student participation.
- Exception Handling: Handles not found entities and invalid requests.
- Unit Tests: Includes tests to ensure the functionality of services.
- Integration Tests: Tests the integration of various components, such as database interactions and service layers, to ensure end-to-end functionality.
- Swagger API Documentation: API documentation using Springdoc OpenAPI.

## API Documentation
For a detailed view of the available endpoints and how to interact with the API, check the Swagger UI provided with the application. The API exposes all the necessary endpoints for student and test management, as well as test participation.

You can access Swagger at /swagger-ui/index.html.

## Prerequisites
1. **Clone the Project**: 
   ```bash
   git clone https://github.com/HasanHaydarY/test-solver.git

2. **Install Dependencies: Use Maven to install dependencies.**
   ```bash
    mvn install
3. **Start the Application**: To run the application:
   ```bash
   mvn spring-boot:run
4. **H2 Console**: Access the H2 database console at http://localhost:8080/h2-console. Login credentials:
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **User Name:** `sa`
- **Password:** `password`

## Database Schema
- Student Table: Stores student information.
- Test Table: Stores test details, including the list of questions.
- Student Test Participation Table: Tracks which student took which test and their performance.

## Unit Testing
The project includes unit tests for the services to ensure the integrity and reliability of the business logic. The tests cover:

- Creating students and tests.
- Retrieving all tests or student data.
- Test participation logic.

## Integration Testing
The project also includes integration tests to ensure end-to-end functionality across multiple components. These tests involve:

- Verifying database interactions using the H2 in-memory database.
- Testing the API endpoints to ensure they integrate well with the service and repository layers.
- Ensuring that student creation, test creation, test participation, and result retrieval are all functioning as expected.

## More Information
For detailed examples of API requests and responses, please visit the [Wiki](https://github.com/HasanHaydarY/test-solver/wiki).
