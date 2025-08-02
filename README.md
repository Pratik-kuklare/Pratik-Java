# 💰 Expense Tracker

A comprehensive full-stack expense tracking application built with modern web technologies. Track your daily expenses, categorize them, and get insights into your spending patterns.

![Expense Tracker](https://img.shields.io/badge/Status-Active-brightgreen)
![Angular](https://img.shields.io/badge/Angular-17+-red)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)

## 🚀 Technologies Used

### Frontend
- **Angular 17+** - Modern web framework with standalone components
- **Angular Material** - UI component library for consistent design
- **TypeScript** - Type-safe JavaScript development
- **SCSS** - Enhanced CSS with variables and mixins
- **RxJS** - Reactive programming for HTTP requests

### Backend
- **Spring Boot 3.2** - Java-based backend framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Database abstraction layer
- **JWT (JSON Web Tokens)** - Secure authentication
- **BCrypt** - Password hashing
- **Maven** - Dependency management and build tool

### Database
- **MySQL 8.0** - Relational database management system
- **Hibernate** - ORM for database operations

## 📁 Project Structure

```
expense-tracker/
├── backend/                          # Spring Boot Application
│   ├── src/main/java/com/expensetracker/
│   │   ├── config/                   # Security and configuration
│   │   ├── controller/               # REST API endpoints
│   │   ├── dto/                      # Data Transfer Objects
│   │   ├── model/                    # JPA Entity classes
│   │   ├── repository/               # Database repositories
│   │   ├── service/                  # Business logic
│   │   └── ExpenseTrackerApplication.java
│   ├── src/main/resources/
│   │   └── application.properties    # Database and app configuration
│   └── pom.xml                       # Maven dependencies
├── frontend/                         # Angular Application
│   ├── src/app/
│   │   ├── components/               # Angular components
│   │   ├── models/                   # TypeScript interfaces
│   │   ├── services/                 # HTTP services
│   │   ├── app.component.ts          # Root component
│   │   ├── app.routes.ts             # Routing configuration
│   │   └── app.config.ts             # App configuration
│   ├── src/assets/                   # Static assets
│   ├── angular.json                  # Angular CLI configuration
│   ├── package.json                  # Node.js dependencies
│   └── tsconfig.json                 # TypeScript configuration
├── database/
│   └── schema.sql                    # Database schema and sample data
└── README.md                         # Project documentation
```

## 🛠️ Prerequisites

Before running this application, make sure you have the following installed:

- **Node.js** (v18 or higher) - [Download](https://nodejs.org/)
- **Java** (JDK 17 or higher) - [Download](https://adoptium.net/)
- **MySQL** (v8.0 or higher) - [Download](https://dev.mysql.com/downloads/)
- **Maven** (v3.6 or higher) - [Download](https://maven.apache.org/download.cgi)
- **Angular CLI** - Install globally: `npm install -g @angular/cli`

## 🚀 Installation & Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd expense-tracker
```

### 2. Database Setup
1. **Start MySQL server**
2. **Create database and tables:**
   ```sql
   mysql -u root -p < database/schema.sql
   ```
3. **Verify database creation:**
   ```sql
   USE expense_tracker_db;
   SHOW TABLES;
   SELECT * FROM users;
   ```

### 3. Backend Setup
1. **Navigate to backend directory:**
   ```bash
   cd backend
   ```

2. **Update database credentials** in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=your_mysql_username
   spring.datasource.password=your_mysql_password
   ```

3. **Install dependencies and run:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Verify backend is running:**
   - Server should start on `http://localhost:8080`
   - Check logs for "Started ExpenseTrackerApplication"

### 4. Frontend Setup
1. **Open new terminal and navigate to frontend:**
   ```bash
   cd frontend
   ```

2. **Install dependencies:**
   ```bash
   npm install
   ```

3. **Start development server:**
   ```bash
   ng serve
   ```

4. **Access the application:**
   - Open browser and go to `http://localhost:4200`

## 🔐 Authentication

### Default Login Credentials
- **Username:** `demo_user`
- **Password:** `password123`
- **Email:** `demo@example.com`

### Admin Account
- **Username:** `admin`
- **Password:** `password123`
- **Email:** `admin@example.com`

### API Endpoints

#### Authentication
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "demo_user",
  "password": "password123"
}
```

#### Register New User
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "newuser",
  "email": "user@example.com",
  "password": "newpassword"
}
```

## 📊 Features

### ✅ Implemented Features
- **User Authentication** - Secure login/register with JWT tokens
- **Expense Management** - Add, edit, delete, and view expenses
- **Category Management** - Organize expenses by categories
- **Responsive Design** - Works on desktop and mobile devices
- **Material Design UI** - Clean and modern interface
- **Real-time Updates** - Instant UI updates after operations
- **Data Validation** - Form validation on both frontend and backend
- **Secure API** - Protected endpoints with JWT authentication

### 🚧 Planned Features
- **Expense Filtering** - Filter by date range, category, amount
- **Search Functionality** - Search expenses by description
- **Dashboard Charts** - Visual representation of spending patterns
- **Monthly/Yearly Reports** - Detailed expense reports
- **Export Data** - Export expenses to CSV/PDF
- **Budget Management** - Set and track budgets
- **Recurring Expenses** - Manage recurring transactions
- **Multi-currency Support** - Support for different currencies

## 🌐 API Documentation

### Base URL
```
http://localhost:8080/api
```

### Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/auth/login` | User login | No |
| POST | `/auth/register` | User registration | No |
| GET | `/expenses/user/{userId}` | Get user expenses | Yes |
| POST | `/expenses` | Create new expense | Yes |
| PUT | `/expenses/{id}` | Update expense | Yes |
| DELETE | `/expenses/{id}` | Delete expense | Yes |
| GET | `/categories/user/{userId}` | Get user categories | Yes |
| POST | `/categories` | Create new category | Yes |

### Sample API Calls

#### Get User Expenses
```bash
curl -X GET "http://localhost:8080/api/expenses/user/1" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

#### Create New Expense
```bash
curl -X POST "http://localhost:8080/api/expenses" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "description": "Coffee",
    "amount": 5.50,
    "expenseDate": "2025-08-02",
    "categoryId": 1,
    "userId": 1
  }'
```

## 🗄️ Database Schema

### Tables

#### Users
```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### Categories
```sql
CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

#### Expenses
```sql
CREATE TABLE expenses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    expense_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);
```

## 🧪 Testing

### Backend Testing
```bash
cd backend
mvn test
```

### Frontend Testing
```bash
cd frontend
ng test
```

### API Testing with PowerShell
```powershell
# Test login
$body = @{
    username = "demo_user"
    password = "password123"
} | ConvertTo-Json

$response = Invoke-RestMethod -Uri "http://localhost:8080/api/auth/login" -Method POST -Body $body -ContentType "application/json"
$token = $response.token

# Test get expenses
$headers = @{ Authorization = "Bearer $token" }
Invoke-RestMethod -Uri "http://localhost:8080/api/expenses/user/1" -Headers $headers
```

## 🚀 Deployment

### Development
- **Frontend:** `ng serve` (http://localhost:4200)
- **Backend:** `mvn spring-boot:run` (http://localhost:8080)

### Production Build

#### Frontend
```bash
cd frontend
ng build --configuration production
```

#### Backend
```bash
cd backend
mvn clean package
java -jar target/expense-tracker-backend-1.0.0.jar
```

## 🛠️ Troubleshooting

### Common Issues

#### Port Already in Use
```bash
# Kill process using port 8080
netstat -ano | findstr :8080
taskkill /PID <process_id> /F
```

#### Database Connection Issues
1. Verify MySQL is running
2. Check credentials in `application.properties`
3. Ensure database `expense_tracker_db` exists

#### Angular Build Errors
```bash
# Clear node modules and reinstall
rm -rf node_modules package-lock.json
npm install
```

#### CORS Issues
- Backend is configured for `http://localhost:4200`
- If using different port, update `application.properties`

## 📝 Development Guidelines

### Code Style
- **Java:** Follow Spring Boot conventions
- **TypeScript:** Use Angular style guide
- **Database:** Use snake_case for table/column names

### Git Workflow
1. Create feature branch: `git checkout -b feature/expense-filtering`
2. Make changes and commit: `git commit -m "Add expense filtering"`
3. Push and create pull request

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- **Your Name** - *Initial work* - [YourGitHub](https://github.com/yourusername)

## 🙏 Acknowledgments

- Angular team for the amazing framework
- Spring Boot community for excellent documentation
- Material Design for beautiful UI components
- MySQL for reliable database management

## 📞 Support

If you have any questions or issues, please:
1. Check the troubleshooting section
2. Search existing issues on GitHub
3. Create a new issue with detailed description

---

**Happy Expense Tracking! 💰📊**