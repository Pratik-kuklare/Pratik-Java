
# Library Management System

A comprehensive library management system built with Java Swing, JDBC, and MySQL for tracking book lending, availability, and user management.

## 🚀 Features

### 📚 Book Management
- Add new books with title, author, ISBN, category, and copy count
- Search books by title, author, or ISBN
- Track total and available copies in real-time
- View complete book inventory

### 👥 User Management
- Add users with different types (Student, Faculty, Staff)
- Store user details: name, email, phone, address
- Search users by name or email
- Manage user profiles

### 📖 Issue/Return System
- Issue books to users with configurable due dates (1-90 days)
- Return books with automatic status updates
- Track book status: Issued, Returned, Overdue
- Real-time availability updates

### 💰 Late Fee Calculation
- Automatic late fee calculation at $2.00 per day
- Overdue status tracking
- Fee calculation on book return
- Late fee reporting

### 🔍 Advanced Features
- Search functionality across all modules
- Filter by book availability
- View overdue books
- Complete transaction history
- User-friendly tabbed interface

## 🛠️ Technical Stack

- **Frontend**: Java Swing GUI
- **Backend**: JDBC with DAO Pattern
- **Database**: MySQL 8.0+
- **Architecture**: MVC Pattern
- **Error Handling**: Graceful database connection management

## 📋 Requirements

- **Java**: JDK 8 or higher
- **MySQL**: Version 8.0 or higher
- **MySQL Connector**: mysql-connector-j-9.3.0.jar (included)
- **Operating System**: Windows, macOS, or Linux

## 🚀 Installation & Setup

### 1. Database Setup

#### Create Database
Run the following SQL script in MySQL Workbench or command line:

```sql
CREATE DATABASE IF NOT EXISTS library_management;
USE library_management;
```

#### Run Schema Script
Execute the complete SQL script from `database/schema.sql`:

```bash
mysql -u root -p < database/schema.sql
```

Or copy and paste the entire content of `database/schema.sql` into MySQL Workbench.

### 2. Configure Database Connection

Edit `src/config/DatabaseConfig.java` and update your MySQL credentials:

```java
private static final String URL = "jdbc:mysql://localhost:3306/library_management";
private static final String USERNAME = "root";
private static final String PASSWORD = "your_mysql_password"; // Update this
```

### 3. Compile the Application

```bash
javac -cp "mysql-connector-j-9.3.0.jar" *.java src/models/*.java src/dao/*.java src/config/*.java
```

### 4. Run the Application

```bash
java -cp ".;src;mysql-connector-j-9.3.0.jar" LibraryManagementSystem
```

**For Linux/macOS:**
```bash
java -cp ".:src:mysql-connector-j-9.3.0.jar" LibraryManagementSystem
```

## 📁 Project Structure

```
library-management/
├── src/
│   ├── config/
│   │   └── DatabaseConfig.java          # Database connection configuration
│   ├── dao/
│   │   ├── BookDAO.java                 # Book data access operations
│   │   ├── UserDAO.java                 # User data access operations
│   │   └── BookIssueDAO.java            # Issue/Return operations
│   ├── models/
│   │   ├── Book.java                    # Book entity model
│   │   ├── User.java                    # User entity model
│   │   └── BookIssue.java               # Book issue entity model
│   ├── BookManagementPanel.java         # Book management GUI
│   ├── UserManagementPanel.java         # User management GUI
│   └── IssueReturnPanel.java            # Issue/Return GUI
├── database/
│   └── schema.sql                       # Database schema and sample data
├── LibraryManagementSystem.java         # Main application class
├── mysql-connector-j-9.3.0.jar         # MySQL JDBC driver
└── README.md                            # This file
```

## 🗄️ Database Schema

### Tables

#### `books`
- `book_id` (Primary Key)
- `title`, `author`, `isbn`
- `category`, `total_copies`, `available_copies`
- `created_at`

#### `users`
- `user_id` (Primary Key)
- `name`, `email`, `phone`, `address`
- `user_type` (STUDENT, FACULTY, STAFF)
- `created_at`

#### `book_issues`
- `issue_id` (Primary Key)
- `book_id`, `user_id` (Foreign Keys)
- `issue_date`, `due_date`, `return_date`
- `late_fee`, `status`

## 🎯 Usage Guide

### Adding Books
1. Navigate to the **Books** tab
2. Fill in book details (Title and Author are required)
3. Set number of copies
4. Click **Add Book**

### Managing Users
1. Go to the **Users** tab
2. Enter user information (Name and Email required)
3. Select user type
4. Click **Add User**

### Issuing Books
1. Open the **Issue/Return** tab
2. Select a book from the dropdown
3. Choose a user
4. Set due days (default: 14 days)
5. Click **Issue Book**

### Returning Books
1. In the **Issue/Return** tab
2. Select an issued book from the table
3. Click **Return Selected Book**
4. Late fees are automatically calculated

### Viewing Reports
- **Active Issues**: Default view shows all currently issued books
- **Overdue Books**: Click "Show Overdue" for late returns
- **All Issues**: Click "Show All Issues" for complete history

## 🔧 Troubleshooting

### Common Issues

#### "MySQL JDBC Driver not found"
- Ensure `mysql-connector-j-9.3.0.jar` is in the project root
- Check the classpath in run command

#### "Access denied for user"
- Verify MySQL username and password in `DatabaseConfig.java`
- Ensure MySQL server is running

#### "Unknown database 'library_management'"
- Run the database schema script first
- Check if database was created successfully

#### Application won't start
- Verify Java version (JDK 8+)
- Check all files are compiled
- Ensure proper classpath

### Error Messages
The application includes user-friendly error messages that guide you through:
- Database connection issues
- Missing required fields
- Invalid input validation
- System errors

## 🎨 Features in Detail

### Book Management
- **Real-time Inventory**: Available copies update automatically
- **ISBN Validation**: Unique ISBN constraint
- **Category Organization**: Books organized by categories
- **Search Capability**: Find books quickly

### User Management
- **User Types**: Different privileges for Students, Faculty, Staff
- **Contact Management**: Complete user profiles
- **Search Users**: Quick user lookup

### Issue/Return System
- **Flexible Due Dates**: 1-90 day lending periods
- **Status Tracking**: Real-time status updates
- **Late Fee System**: Automatic calculation and tracking
- **Transaction History**: Complete audit trail

## 🔒 Security Features

- Input validation on all forms
- SQL injection prevention through prepared statements
- Error handling without exposing system details
- Database connection pooling

## 🚀 Future Enhancements

- User authentication and authorization
- Email notifications for due dates
- Barcode scanning integration
- Advanced reporting and analytics
- Book reservation system
- Multi-library support

## 📞 Support

For issues or questions:
1. Check the troubleshooting section
2. Verify database connection
3. Ensure all requirements are met
4. Check error messages for guidance

## 📄 License

This project is created for educational purposes. Feel free to modify and distribute as needed.

---

**Happy Library Management!** 📚✨
jajavjava -cjava -cp ".;src;mysql-connector-j-9.3.0.jar" LibraryManagemejava -cp ".;src;mysql-connector-j-9.3.0.jar" LibraryManagementSystjava -cp ".;src;mysql-connector-j-9.3.0.jar" LibraryManagementSystem