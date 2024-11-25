# SportsUnity Backend Test

This project is a RESTful API implementation for managing a To-Do list within a SaaS for companies. It includes three types of users: Standard Users, Company-Admin Users, and Super Users, each with different levels of access to tasks.

## Features

- **User Roles**:
  - **Standard Users**: Can access only their tasks. All users belong to a company.
  - **Company-Admin Users**: Can access their tasks and those of other employees in the same company.
  - **Super Users**: Have access to all tasks across the system.

- **RESTful API**:
  - Built using Java and the Spring framework.
  - Lightweight, in-memory datastore for simplicity.
  - Demonstrates high-quality code and tests.

- **Standalone Execution**:
  - Does not require any pre-installed container or server.

## Requirements

1. **Language**: Java
2. **Frameworks/Libraries**: Spring (e.g., Spring Boot for REST API development)
3. **Datastore**: In-memory datastore (e.g., H2 or similar)
4. **Execution**: Standalone program
5. **Tests**: Comprehensive tests to verify API functionality

## Setup and Usage

### Prerequisites
- Java 11 or higher
- Maven or Gradle (for dependency management)

### Steps to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/Devopsi10/SportsUnity.git
   cd SportsUnity
