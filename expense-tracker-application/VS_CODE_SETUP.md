# Expense Tracker - VS Code Setup Guide

## 📁 Project Structure
```
expense-tracker-application/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── expensetracker/
│       └── resources/
│           ├── static/
│           │   ├── index.html
│           │   ├── style.css
│           │   ├── script.js
│           │   └── debug.html
│           └── application.properties
├── pom.xml
├── README.md
├── start-app.bat
└── VS_CODE_SETUP.md (this file)
```

## 🛠️ Prerequisites

### 1. Install Required Software
- **Java 17 or higher** (JDK)
- **Maven 3.6+**
- **MySQL 8.0+**
- **VS Code**
- **Git** (optional)

### 2. VS Code Extensions (Recommended)
Install these extensions in VS Code:
- **Extension Pack for Java** (Microsoft)
- **Spring Boot Extension Pack** (VMware)
- **MySQL** (cweijan)
- **Thunder Client** (for API testing)
- **Live Server** (for frontend development)

## 🗄️ Database Setup

### 1. Create MySQL Database
```sql
CREATE DATABASE expense_tracker;
USE expense_tracker;

-- Categories table
CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

-- Transactions table
CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    transaction_date DATE NOT NULL,
    category_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Insert sample categories
INSERT INTO categories (name, description) VALUES
('Food', 'Food and dining expenses'),
('Transportation', 'Travel and transportation costs'),
('Entertainment', 'Movies, games, and entertainment'),
('Shopping', 'Clothing, electronics, and shopping'),
('Bills', 'Utilities, rent, and recurring bills'),
('Healthcare', 'Medical expenses and healthcare'),
('Education', 'Books, courses, and educational expenses');

-- Insert sample transactions
INSERT INTO transactions (description, amount, transaction_date, category_id) VALUES
('Lunch at restaurant', 25.50, '2025-07-22', 1),
('Weekly groceries', 85.75, '2025-07-20', 1),
('Bus fare', 12.00, '2025-07-21', 2),
('Movie tickets', 30.00, '2025-07-19', 3),
('Monthly internet bill', 45.99, '2025-07-18', 5);
```

### 2. Update Database Configuration
Check `src/main/resources/application.properties`:
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/expense_tracker
spring.datasource.username=root
spring.datasource.password=your_password_here
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Server Configuration
server.port=8081

# Logging
logging.level.com.expensetracker=DEBUG
```

## 🚀 Running in VS Code

### Method 1: Using VS Code Java Extension
1. **Open Project**: File → Open Folder → Select `expense-tracker-application`
2. **Wait for Java Extension**: Let VS Code detect the Maven project
3. **Run Application**: 
   - Press `Ctrl+Shift+P`
   - Type "Java: Run Java"
   - Select your main class `ExpenseTrackerApplication`

### Method 2: Using Integrated Terminal
1. **Open Terminal**: Terminal → New Terminal
2. **Run Maven Command**:
   ```bash
   mvn spring-boot:run
   ```

### Method 3: Using VS Code Tasks
1. **Create `.vscode/tasks.json`**:
   ```json
   {
       "version": "2.0.0",
       "tasks": [
           {
               "label": "Run Spring Boot App",
               "type": "shell",
               "command": "mvn",
               "args": ["spring-boot:run"],
               "group": {
                   "kind": "build",
                   "isDefault": true
               },
               "presentation": {
                   "echo": true,
                   "reveal": "always",
                   "focus": false,
                   "panel": "shared"
               },
               "problemMatcher": []
           }
       ]
   }
   ```
2. **Run Task**: `Ctrl+Shift+P` → "Tasks: Run Task" → "Run Spring Boot App"

## 🌐 Accessing the Application

Once the application starts successfully:
- **Main Application**: http://localhost:8081
- **API Endpoints**: http://localhost:8081/api/categories, http://localhost:8081/api/transactions
- **Debug Page**: http://localhost:8081/debug.html

## 🔧 Development Workflow

### 1. Backend Development
- Java files are in `src/main/java/com/expensetracker/`
- Changes require application restart
- Use VS Code debugger for debugging

### 2. Frontend Development
- HTML/CSS/JS files are in `src/main/resources/static/`
- Changes are hot-reloaded (no restart needed)
- Use browser developer tools (F12) for debugging

### 3. Database Changes
- Update entity classes in Java
- Spring Boot will auto-update database schema
- Check console logs for SQL statements

## 🐛 Troubleshooting

### Common Issues:

1. **Port 8081 already in use**:
   ```bash
   # Windows
   netstat -ano | findstr :8081
   taskkill /PID <PID_NUMBER> /F
   ```

2. **Database connection failed**:
   - Check MySQL is running
   - Verify credentials in `application.properties`
   - Ensure database `expense_tracker` exists

3. **Maven dependencies not resolved**:
   - `Ctrl+Shift+P` → "Java: Reload Projects"
   - Or run: `mvn clean install`

4. **JavaScript not loading**:
   - Hard refresh browser: `Ctrl+F5`
   - Check browser console for errors
   - Verify files are in `src/main/resources/static/`

## 📝 Key Features

### ✅ Working Features:
- ✅ Add/Delete Categories
- ✅ Add/Delete Transactions
- ✅ View Recent Transactions
- ✅ Quick Expense Entry
- ✅ Monthly Reports
- ✅ Category-wise Expense Filtering
- ✅ Rupee Currency Display (₹)
- ✅ REST API Endpoints

### 🔧 API Endpoints:
- `GET /api/categories` - List all categories
- `POST /api/categories` - Create new category
- `DELETE /api/categories/{id}` - Delete category
- `GET /api/transactions` - List all transactions
- `POST /api/transactions` - Create new transaction
- `DELETE /api/transactions/{id}` - Delete transaction
- `GET /api/reports/monthly/{year}/{month}` - Monthly report

## 🎯 Next Steps

1. **Open in VS Code**: File → Open Folder → Select project directory
2. **Install recommended extensions**
3. **Configure database connection**
4. **Run the application**
5. **Start developing!**

## 📞 Support

If you encounter issues:
1. Check the console logs in VS Code terminal
2. Verify database connection
3. Check browser developer tools (F12)
4. Ensure all prerequisites are installed

Happy coding! 🚀