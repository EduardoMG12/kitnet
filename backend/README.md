# Kitnet Backend - Spring Boot API

This repository contains the backend application for the Kitnet platform, developed using Spring Boot (Java 21). It provides a RESTful API for user management, property listings, authentication, and various business logic operations.

---

## Table of Contents

- [Overview](#overview)
- [Technologies Used](#technologies-used)
- [Local Development Setup](#local-development-setup)
  - [Prerequisites](#prerequisites)
  - [Environment Variables](#environment-variables)
  - [Running Locally (without Docker)](#running-locally-without-docker)
  - [Running with Docker Compose](#running-running-with-docker-compose)
- [API Endpoints](#api-endpoints)
- [Database Schema](#database-schema)
- [Authentication & Authorization](#authentication--authorization)
- [Hot-Reloading](#hot-reloading)
- [Code Structure](#code-structure)

---

## Overview

The Kitnet backend serves as the core logic engine for the platform. It manages user accounts (Tenants, Owners, Real Estate Agents, Admins), handles property data, facilitates communication, and implements the monetization logic.

---

## Technologies Used

-   **Java 21**
-   **Spring Boot 3.x**: Web, Data JPA, Security, Mail, Thymeleaf, DevTools
-   **Gradle**: Build Automation
-   **MySQL**: Relational Database
-   **Lombok**: Boilerplate code reduction
-   **JWT (JSON Web Tokens)**: For API authentication
-   **Firebase Admin SDK**: For Google/Apple social login integration
-   **Dotenv-java**: For loading environment variables from `.env` files

---

## Local Development Setup

### Prerequisites

-   Java 21 JDK
-   Gradle
-   MySQL (or use the Docker Compose setup)
-   An IDE (e.g., IntelliJ IDEA) configured for Spring Boot/Gradle development.

### Environment Variables

The backend uses environment variables for sensitive data and configuration. These are loaded from the `.env` file in the **root directory of the project** (`kitnet-project/.env`) via `io.github.cdimascio.dotenv.Dotenv`.

**Important variables for backend:**

-   `SPRING_APPLICATION_NAME`
-   `APP_BASE_URL`, `APP_TOKEN_EXPIRY_MINUTES`, `APP_EMAIL_FROM`
-   `MAIL_HOST`, `MAIL_PORT`, `MAIL_USERNAME`, `MAIL_PASSWORD`, `MAIL_PROPERTIES_MAIL_SMTP_AUTH`, `MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE`, `MAIL_PROPERTIES_MAIL_SMTP_SSL_TRUST`
-   `DATABASE_URL`, `DATABASE_USERNAME`, `DATABASE_PASSWORD`, `DATABASE_DRIVER_CLASS_NAME`
-   `JPA_HIBERNATE_DDL_AUTO`, `JPA_PROPERTIES_HIBERNATE_DIALECT`, `JPA_SHOW_SQL`, `JPA_FORMAT_SQL`
-   `SERVER_PORT`
-   `SECURITY_USER_NAME`, `SECURITY_USER_PASSWORD`
-   `JWT_SECRET`, `JWT_EXPIRATION`
-   `FIREBASE_SERVICE_ACCOUNT_PATH`

### Running Locally (without Docker)

1.  **Database Setup:** Ensure your local MySQL database is running and accessible with the credentials specified in your `.env`.
2.  **Firebase Service Account:** Download your Firebase service account JSON file and place it in `backend/src/main/resources/`. Update `FIREBASE_SERVICE_ACCOUNT_PATH` in your `.env`.
3.  **Run Application:**
    -   **From IDE (IntelliJ IDEA):** Open the `KitnetApplication.java` file and click the "Run" button.
    -   **From Terminal:** Navigate to the `backend/` directory and run:
        ```bash
        ./gradlew bootRun
        ```
    The application will start on `http://localhost:8081` (or the port defined in `SERVER_PORT`).

### Running with Docker Compose

For a full local development environment including MySQL and a temporary CDN, use Docker Compose from the project root. Refer to the [global README.md](../README.md) for detailed instructions on how to set up and run with Docker Compose.

---

## API Endpoints

(This section would typically list your main API endpoints here, e.g.)

-   `POST /api/auth/register-simple`
-   `POST /api/auth/login`
-   `POST /api/auth/firebase-login`
-   `PUT /api/auth/complete-register`
-   `POST /api/users/verify/email/initiate`
-   `GET /api/users/verify/email/confirm?token={token}`
-   `POST /api/users/verify/password/request`
-   `POST /api/users/verify/password/reset?token={token}`
-   ... (and so on for properties, etc.)

---

## Database Schema

The database schema is managed by JPA/Hibernate. The current schema can be visualized using tools like dbdiagram.io with the provided schema definition.

(You might link to an image of your dbdiagram.io here, or include a simplified schema definition).

---

## Authentication & Authorization

-   **Authentication**: Uses JWT for session management. Supports email/password, Google, and Apple ID (via Firebase Admin SDK).
-   **Authorization**: Role-based access control (RBAC) managed by Spring Security, utilizing roles like `LESSEE`, `LESSOR`, `REAL_ESTATE_AGENT`, `ADMIN`, `MODERATOR`, `SUPPORT`.

---

## Hot-Reloading

Hot-reloading is enabled for faster development:

-   **Mechanism**: Uses Spring Boot DevTools in combination with mounted volumes in Docker.
-   **How it works**: When you save changes to your Java code in the IDE, the IDE compiles the changes. DevTools running inside the Docker container detects these compiled `.class` changes and automatically restarts the application within the container.
-   **Verification**: Look for "Restarting main application..." messages in the `backend` container logs after saving code changes.

---

## Code Structure

-   `com.kitnet.kitnet.controller`: REST API endpoints.
-   `com.kitnet.kitnet.service`: Business logic and orchestration.
-   `com.kitnet.kitnet.repository`: Spring Data JPA repositories for data access.
-   `com.kitnet.kitnet.model`: JPA entities (database models).
-   `com.kitnet.kitnet.dto`: Data Transfer Objects for API requests/responses.
-   `com.kitnet.kitnet.exception`: Custom exception classes.
-   `com.kitnet.kitnet.config`: Spring configuration classes (Security, Database, Initializers).
-   `com.kitnet.kitnet.filter`: JWT request filter.
-   `com.kitnet.kitnet.util`: Utility classes (e.g., JwtUtil).

---