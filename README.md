# Kitnet - Real Estate Platform

Welcome to Kitnet, a comprehensive real estate platform designed to connect property owners, real estate agents, and tenants/buyers. This project aims to simplify the process of listing, searching, and managing properties, with a focus on small cities.

---

## Table of Contents

- [About Kitnet](#about-kitnet)
- [Features](#features)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Project Structure](#project-structure)
  - [Environment Variables (.env)](#environment-variables-env)
  - [Running with Docker Compose](#running-with-docker-compose)
- [Accessing Services](#accessing-services)
- [Development Workflow (Hot-Reloading)](#development-workflow-hot-reloading)
- [Future Plans](#future-plans)
- [Monetization Strategy](#monetization-strategy)

---

## About Kitnet

Kitnet is a platform for renting and selling properties, designed with a special focus on the dynamics of small cities. It offers a robust API and a modern web interface to provide a seamless experience for all users involved in the real estate market, without directly handling rental or sales money.

---

## Features

- **User Management**: Dedicated profiles for Tenants, Owners, Real Estate Agents, and Admins.
- **Authentication**: Secure login with Email/Password, Google, and Apple ID (via Firebase Auth).
- **Property Listings**: Detailed property advertisements with customizable options for owners and agents.
- **Flexible Monetization**:
  - Premium subscriptions for Real Estate Agents.
  - Property Boosting to increase visibility.
  - Future plans for affiliate links and coupons.
- **Verification System**: Robust document and account verification for enhanced trust.
- **Communication Tools**: In-app messaging and visit scheduling.
- **Gamification**: Badges and achievements for active and reputable users.

---

## Architecture

Kitnet is built as a multi-service application, orchestrated with Docker Compose for local development.

-   **Frontend**: Next.js (Node.js) application.
-   **Backend**: Spring Boot (Java) RESTful API.
-   **Database**: MySQL.
-   **Temporary CDN**: Nginx serving static files (for local development, will migrate to cloud storage like AWS S3/CloudFront).

---

## Getting Started

Follow these instructions to set up and run the Kitnet project locally using Docker Compose.

### Prerequisites

Make sure you have the following installed on your machine:

-   [Docker Desktop](https://www.docker.com/products/docker-desktop) (includes Docker Engine and Docker Compose) or [Docker Engine](https://docs.docker.com/engine/install/) and [Docker Compose](https://docs.docker.com/compose/install/) separately.

### Project Structure

```ts

kitnet-project/
├── backend/                  # Spring Boot Java application
│   ├── src/                  # Java source code
│   ├── Dockerfile            # Dockerfile for backend build
│   └── ...
├── frontend/                 # Next.js application
│   ├── public/               # Static assets
│   ├── Dockerfile            # Dockerfile for frontend build
│   └── ...
├── db/                       # Database initialization scripts (optional)
│   └── init.sql
├── cdn-temp/                 # Temporary Nginx CDN for local static files
│   ├── files/                # Directory for static files (e.g., uploaded images)
│   ├── nginx.conf            # Nginx configuration
│   └── Dockerfile
├── docker-compose.yml        # Orchestrates all services
└── .env                      # Environment variables for Docker Compose and services

```

### Environment Variables (`.env`)

Create a `.env` file in the **root directory** of your project (`kitnet-project/`). This file contains sensitive information and configuration parameters for your services.

```env
# .env (in the root directory of your project)

# Application Configurations
SPRING_APPLICATION_NAME=kitnet-backend
APP_BASE_URL=http://localhost:8081
APP_TOKEN_EXPIRY_MINUTES=15
APP_EMAIL_FROM=kitnet.rent@gmail.com

# Email Server (SMTP) Configurations
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your.email@gmail.com
MAIL_PASSWORD=your_app_password # IMPORTANT: Use an App Password for Gmail
MAIL_PROPERTIES_MAIL_SMTP_AUTH=true
MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE=true
MAIL_PROPERTIES_MAIL_SMTP_SSL_TRUST=smtp.gmail.com

# Database Configurations (HIGHLY SENSITIVE!)
DATABASE_NAME=kitnet # Database name for MySQL container
DATABASE_USERNAME=root
DATABASE_PASSWORD=root
# DATABASE_URL is built dynamically in docker-compose.yml based on service name
DATABASE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configurations
JPA_HIBERNATE_DDL_AUTO=update # 'update' for development, 'none' or 'validate' for production
JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.MySQL8Dialect
JPA_SHOW_SQL=true # Display SQL queries in the console (disable in production)
JPA_FORMAT_SQL=true # Format SQL queries (disable in production)

# Server Configurations
SERVER_PORT=8081

# Spring Security Configurations (In-memory User - for development ONLY)
SECURITY_USER_NAME=user
SECURITY_USER_PASSWORD=password

# JWT Configurations (HIGHLY SENSITIVE!)
JWT_SECRET=a_very_long_and_random_secret_key_for_jwt_signing_do_not_use_in_production_generate_a_new_one
JWT_EXPIRATION=86400000 # JWT token expiration time in milliseconds (24 hours)

# Firebase Admin SDK Configuration
# Path to your Firebase service account JSON file (relative to backend/src/main/resources/)
FIREBASE_SERVICE_ACCOUNT_PATH=kitnetapi-firebase-adminsdk-your-project-id-xxxxxxxxxx.json 

# Frontend Variables
FRONTEND_PORT=3000 # Port for the Next.js frontend on your host machine

# Temporary CDN Variables
CDN_TEMP_PORT=8080 # Port for the temporary Nginx CDN on your host machine

```

Important: Replace placeholder values like your.email@gmail.com, your_app_password, and kitnetapi-firebase-adminsdk-your-project-id-xxxxxxxxxx.json with your actual credentials. For Gmail, you MUST use an App Password for external applications.

Running with Docker Compose
Create your Firebase Service Account file: Download your Firebase service account JSON file from Firebase Console (Project settings > Service accounts > Generate new private key) and place it in backend/src/main/resources/. Update the FIREBASE_SERVICE_ACCOUNT_PATH in your .env accordingly.
Configure Frontend package.json for Development: Update the scripts in frontend/package.json to allow Next.js to bind to 0.0.0.0 within the container.

```JSON

{
  "scripts": {
    "dev": "next dev --hostname 0.0.0.0 --turbopack",
    "start": "next start --hostname 0.0.0.0",
    // ...
  }
}

```
Navigate to the root directory: Open your terminal and go to the kitnet-project/ directory where your docker-compose.yml and .env files are located.
Start the services:
```Bash

docker-compose up --build

```
The --build flag ensures that Docker images are rebuilt, picking up any changes in your Dockerfiles or code. For subsequent runs where you only changed application code (and not Dockerfiles or dependencies), you can omit --build for faster startup (docker-compose up).
Accessing Services
Once all services are up and running:

Frontend (Next.js): Access your application in your web browser at http://localhost:${FRONTEND_PORT} (e.g., http://localhost:3000).
Backend (Spring Boot API): The API is accessible at http://localhost:${SERVER_PORT}/api (e.g., http://localhost:8081/api).
Temporary CDN (Nginx): Static files (if any are manually placed in cdn-temp/files/ for testing) can be accessed at http://localhost:${CDN_TEMP_PORT} (e.g., http://localhost:8080).
MySQL Database: You can connect to the MySQL database from your host machine using localhost:3306 with the credentials defined in your .env.
Development Workflow (Hot-Reloading)
Hot-reloading is configured for a smoother development experience:

Frontend (Next.js): When you save changes to your frontend code, the frontend container will automatically detect them and recompile, reflecting updates in your browser immediately.
Backend (Spring Boot Java): When you save changes to your Java code in your IDE, the IDE will automatically compile them. Spring Boot DevTools, running inside the backend container, will detect these compiled changes via mounted volumes and restart the application automatically. You'll see "Restarting main application..." messages in the backend container logs.
Future Plans
Kitnet is a university project with ambitious future plans, including:

Migrating the temporary CDN to a cloud-native solution (e.g., AWS S3 + CloudFront).
Implementing advanced monetization strategies like affiliate links, coupons, and tiered boosting options for properties and user profiles.
Expanding communication and negotiation features.
Monetization Strategy
Kitnet's monetization focuses on value-added services without touching rental or sales transaction money:

Premium Subscriptions: For real estate agents, offering enhanced features and visibility.
Property Boosting: Paid promotion to increase property listing visibility in search results.
Third-Party Service Integration: Partnering with providers for services like cleaning, photography, or legal assistance, earning commissions through referrals.
Data Insights: Potentially offering anonymized market data and insights.