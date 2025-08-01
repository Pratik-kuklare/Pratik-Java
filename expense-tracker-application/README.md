# Expense Tracker Application

A comprehensive expense tracking application built with Java Spring Boot, Hibernate, REST API, and MySQL.

## Features

- **Add Transactions**: Record expense transactions with description, amount, date, and category
- **Categorize Expenses**: Organize expenses into customizable categories
- **Generate Reports**: Create detailed expense reports with category breakdowns and date ranges
- **REST API**: Full RESTful API for all operations
- **Data Validation**: Input validation and error handling
- **Sample Data**: Pre-loaded categories and sample transactions

## Technologies Used

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA (Hibernate)
- MySQL 8.0
- Maven
- Bean Validation

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+

## Setup Instructions

### 1. Database Setup

Create a MySQL database:
```sql
CREATE DATABASE expense_tracker;
```

### 2. Configuration

Update `src/main/resources/application.properties` with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/expense_tracker
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8081`

## API Endpoints

### Categories

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/categories` | Get all categories |
| GET | `/api/categories/{id}` | Get category by ID |
| POST | `/api/categories` | Create new category |
| PUT | `/api/categories/{id}` | Update category |
| DELETE | `/api/categories/{id}` | Delete category |

#### Category JSON Structure
```json
{
  "id": 1,
  "name": "Food",
  "description": "Food and dining expenses"
}
```

### Transactions

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/transactions` | Get all transactions |
| GET | `/api/transactions/{id}` | Get transaction by ID |
| GET | `/api/transactions/date-range?startDate=2024-01-01&endDate=2024-01-31` | Get transactions by date range |
| GET | `/api/transactions/category/{categoryId}` | Get transactions by category |
| POST | `/api/transactions` | Create new transaction |
| PUT | `/api/transactions/{id}` | Update transaction |
| DELETE | `/api/transactions/{id}` | Delete transaction |

#### Transaction JSON Structure
```json
{
  "id": 1,
  "description": "Lunch at restaurant",
  "amount": 25.50,
  "transactionDate": "2024-01-15",
  "categoryId": 1,
  "categoryName": "Food"
}
```

### Reports

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/reports/expense?startDate=2024-01-01&endDate=2024-01-31` | Get expense report for date range |
| GET | `/api/reports/monthly/{year}/{month}` | Get monthly report |
| GET | `/api/reports/yearly/{year}` | Get yearly report |

#### Report JSON Structure
```json
{
  "startDate": "2024-01-01",
  "endDate": "2024-01-31",
  "totalAmount": 174.25,
  "transactionCount": 4,
  "categoryBreakdown": {
    "Food": 111.25,
    "Transportation": 45.00,
    "Entertainment": 18.00
  },
  "transactions": [...]
}
```

## Sample API Calls

### Create a Category
```bash
curl -X POST http://localhost:8081/api/categories \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Food",
    "description": "Food and dining expenses"
  }'
```

### Create a Transaction
```bash
curl -X POST http://localhost:8081/api/transactions \
  -H "Content-Type: application/json" \
  -d '{
    "description": "Lunch at restaurant",
    "amount": 25.50,
    "transactionDate": "2024-01-15",
    "categoryId": 1
  }'
```

### Get Monthly Report
```bash
curl http://localhost:8081/api/reports/monthly/2024/1
```

## Database Schema

The application creates the following tables:

- `categories`: Stores expense categories
- `transactions`: Stores individual expense transactions

## Error Handling

The application includes comprehensive error handling:
- Validation errors return detailed field-specific messages
- Runtime exceptions return appropriate HTTP status codes
- Global exception handler ensures consistent error responses

## Sample Data

The application automatically creates sample categories and transactions on first run:
- Categories: Food, Transportation, Entertainment, Utilities, Healthcare, Shopping
- Sample transactions for demonstration

## Development

To extend the application:
1. Add new entities in the `entity` package
2. Create corresponding repositories in the `repository` package
3. Implement business logic in the `service` package
4. Create REST endpoints in the `controller` package
5. Add DTOs for API requests/responses in the `dto` package