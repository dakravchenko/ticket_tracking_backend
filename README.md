# Ticket Tracking System Backend

A REST API backend for a ticket tracking system, built with Java and Spring Boot.

**Live API Documentation:** [Swagger UI](https://ticket-tracking-system-q92t.onrender.com/swagger-ui/index.html)


The application provides functionality for managing users, projects, tickets, ticket assignments, authentication, and file uploads.

## Tech Stack

- Java 21
- Spring Boot
- Spring Security
- PostgreSQL
- Jdbi
- Flyway
- JWT
- Jakarta Validation
- OpenAPI / Swagger
- Maven

## Features

### Authentication
- User registration
- User login
- JWT-based authentication
- Secured API endpoints

### Users
- Create users
- Retrieve users
- Update users
- Delete users

### Projects
- Create projects
- Retrieve projects
- Project summaries

### Tickets
- Create tickets
- Retrieve tickets
- Update tickets
- Assign users to tickets
- Remove users from tickets

### Files
- Upload files to tickets
- Retrieve ticket files
- Mark file processing as completed or failed
- Delete files

## API Documentation

The API is documented using OpenAPI/Swagger.

When running the application locally, Swagger UI is available at:

`http://localhost:8080/swagger-ui/index.html`

## Database

The application uses PostgreSQL as its relational database.

Database schema changes are managed using Flyway migrations.

## Running Locally

### Prerequisites

- Java 21
- Maven
- PostgreSQL

### Configuration

Create the required environment variables for the database and application configuration.

### Run the application

```bash
./mvnw spring-boot:run
