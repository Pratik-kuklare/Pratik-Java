# 📚 Online Bookstore Management System

A comprehensive e-commerce platform for managing an online bookstore built with **Spring Boot**, **MySQL**, and **Thymeleaf**.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-green)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![Maven](https://img.shields.io/badge/Maven-3.6+-red)

## 🌟 Features

### 👤 User Management
- **User Registration & Login** with secure authentication
- **Role-based Access Control** (Customer/Admin)
- **Password Encryption** using BCrypt
- **Session Management** for secure user sessions

### 📖 Book Management
- **Browse Books** with pagination and search
- **Category Filtering** for easy navigation
- **Book Details** with comprehensive information
- **Stock Management** with real-time inventory

### 🛒 Shopping Cart
- **Add/Remove Items** with quantity management
- **Session-based Cart** for guest users
- **Persistent Cart** for registered users
- **Real-time Price Calculation** with tax and shipping

### 📦 Order Management
- **Complete Checkout Process** with shipping details
- **Multiple Payment Methods** (Credit Card, PayPal, Bank Transfer)
- **Order Tracking** with status updates
- **Order History** with detailed information

### 💳 Payment Integration
- **Secure Payment Processing** with validation
- **Multiple Payment Options** for user convenience
- **Transaction Tracking** with unique IDs
- **Payment Status Management**

### 🔐 Security Features
- **Spring Security Integration** for authentication
- **CSRF Protection** for secure forms
- **Role-based Authorization** for different user types
- **Secure Password Handling** with encryption

## 🛠️ Technologies Used

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 17+ | Backend Development |
| **Spring Boot** | 3.2.0 | Application Framework |
| **Spring Security** | 6.2.0 | Authentication & Authorization |
| **Spring Data JPA** | 3.2.0 | Database Operations |
| **MySQL** | 8.0+ | Database Management |
| **Thymeleaf** | 3.1.2 | Template Engine |
| **Bootstrap** | 5.1.3 | Frontend Framework |
| **Font Awesome** | 6.0.0 | Icons |
| **Maven** | 3.6+ | Build Tool |

## 📋 Prerequisites

Before running this application, make sure you have:

- ☑️ **Java 17** or higher installed
- ☑️ **MySQL 8.0** or higher installed and running
- ☑️ **Maven 3.6** or higher installed
- ☑️ **Git** (optional, for cloning)

## 🚀 Quick Start

### 1. Database Setup

```sql
-- Connect to MySQL
mysql -u root -p

-- Create the database
CREATE DATABASE bookstore_db;

-- Verify creation
SHOW DATABASES;

-- Exit MySQL
EXIT;
```

### 2. Clone & Configure

```bash
# Clone the repository (or extract the project)
git clone <repository-url>
cd online-bookstore-management

# Update database credentials in src/main/resources/application.properties
# Change the password to match your MySQL root password
spring.datasource.password=your_mysql_password
```

### 3. Run the Application

**Option 1: Using Maven**
```bash
mvn clean spring-boot:run
```

**Option 2: Using the provided script**
```bash
# Windows
run.bat

# The application will start on http://localhost:8080
```

### 4. Access the Application

- 🏠 **Homepage**: http://localhost:8080
- 🔐 **Login**: http://localhost:8080/login
- 📝 **Register**: http://localhost:8080/register
- 📚 **Books**: http://localhost:8080/books

## 👥 Default User Accounts

The application automatically creates these users on startup:

### 🔑 Admin Account
```
Username: admin
Password: admin123
Role: ADMIN
```

### 🔑 Test User (Pratik)
```
Username: Pratik
Password: 9028574242
Role: ADMIN
```

## 📁 Project Structure

```
online-bookstore-management/
├── 📄 pom.xml                          # Maven configuration
├── 📄 README.md                        # Project documentation
├── 📄 run.bat                          # Quick start script
├── 📁 src/main/
│   ├── 📁 java/com/bookstore/
│   │   ├── 📁 config/                  # Configuration classes
│   │   │   ├── SecurityConfig.java     # Spring Security setup
│   │   │   └── DataInitializer.java    # Sample data loader
│   │   ├── 📁 controller/              # REST Controllers
│   │   │   ├── HomeController.java     # Homepage & book browsing
│   │   │   ├── AuthController.java     # Authentication
│   │   │   ├── CartController.java     # Shopping cart
│   │   │   ├── OrderController.java    # Order management
│   │   │   └── TestController.java     # Development endpoints
│   │   ├── 📁 entity/                  # JPA Entities
│   │   │   ├── User.java              # User account
│   │   │   ├── Book.java              # Book catalog
│   │   │   ├── Cart.java              # Shopping cart
│   │   │   ├── CartItem.java          # Cart items
│   │   │   ├── Order.java             # Customer orders
│   │   │   ├── OrderItem.java         # Order line items
│   │   │   └── Payment.java           # Payment records
│   │   ├── 📁 repository/              # Data Access Layer
│   │   │   ├── UserRepository.java     # User data operations
│   │   │   ├── BookRepository.java     # Book data operations
│   │   │   ├── CartRepository.java     # Cart data operations
│   │   │   └── OrderRepository.java    # Order data operations
│   │   ├── 📁 service/                 # Business Logic
│   │   │   ├── UserService.java        # User management
│   │   │   ├── BookService.java        # Book management
│   │   │   ├── CartService.java        # Cart operations
│   │   │   ├── OrderService.java       # Order processing
│   │   │   ├── PaymentService.java     # Payment handling
│   │   │   └── CustomUserDetailsService.java # Security
│   │   └── BookstoreApplication.java   # Main application class
│   └── 📁 resources/
│       ├── 📄 application.properties   # App configuration
│       └── 📁 templates/               # Thymeleaf templates
│           ├── index.html              # Homepage
│           ├── books.html              # Book listing
│           ├── layout.html             # Base layout
│           ├── 📁 auth/                # Authentication pages
│           │   ├── login.html          # Login form
│           │   └── register.html       # Registration form
│           ├── 📁 cart/                # Shopping cart pages
│           │   └── view.html           # Cart view
│           └── 📁 orders/              # Order pages
│               ├── list.html           # Order history
│               ├── detail.html         # Order details
│               └── checkout.html       # Checkout process
└── 📁 database/
    └── init.sql                        # Database setup script
```

## 🗄️ Database Schema

The application automatically creates these tables using JPA/Hibernate:

| Table | Description |
|-------|-------------|
| `users` | User accounts and profiles |
| `books` | Book catalog with inventory |
| `carts` | User shopping carts |
| `cart_items` | Items in shopping carts |
| `orders` | Customer orders |
| `order_items` | Line items in orders |
| `payments` | Payment transaction records |

## 🔗 API Endpoints

### Public Endpoints
- `GET /` - Homepage with featured books
- `GET /books` - Browse all books with search/filter
- `GET /login` - User login page
- `GET /register` - User registration page

### Authenticated Endpoints
- `GET /cart` - View shopping cart
- `POST /cart/add` - Add item to cart
- `POST /cart/update` - Update cart item quantity
- `POST /cart/remove` - Remove item from cart
- `GET /orders` - View order history
- `GET /orders/{id}` - View specific order details
- `POST /orders/place` - Place a new order

### Admin Endpoints
- `GET /admin/**` - Admin panel (requires ADMIN role)

### Development Endpoints (Remove in Production)
- `GET /test/create-pratik-user` - Create Pratik user
- `GET /test/reset-pratik-password` - Reset Pratik password
- `GET /test/create-test-user` - Create test user

## 🎨 User Interface

The application features a modern, responsive design with:

- 📱 **Mobile-First Design** using Bootstrap 5
- 🎯 **Intuitive Navigation** with clear menu structure
- 🛒 **Interactive Shopping Cart** with real-time updates
- 📊 **Order Tracking** with visual status indicators
- 🔒 **Secure Checkout** with multiple payment options
- 📈 **Admin Dashboard** for management tasks

## 🔧 Configuration

### Database Configuration
Update `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/bookstore_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Server Configuration
server.port=8080
```

### Security Configuration
The application uses Spring Security with:
- BCrypt password encoding
- Session-based authentication
- Role-based authorization
- CSRF protection (disabled for API endpoints)

## 🧪 Testing

### Manual Testing
1. **User Registration**: Create new accounts
2. **User Login**: Test authentication
3. **Book Browsing**: Search and filter books
4. **Cart Operations**: Add, update, remove items
5. **Order Placement**: Complete checkout process
6. **Order Tracking**: View order history and details

### Test Data
The application automatically creates:
- Sample books in different categories
- Admin and test user accounts
- Initial cart and order data

## 🚀 Deployment

### Local Development
```bash
mvn spring-boot:run
```

### Production Deployment
1. Update `application.properties` for production database
2. Build the application: `mvn clean package`
3. Run the JAR file: `java -jar target/online-bookstore-0.0.1-SNAPSHOT.jar`

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/new-feature`
3. Make your changes and commit: `git commit -m 'Add new feature'`
4. Push to the branch: `git push origin feature/new-feature`
5. Submit a pull request

## 📝 License

This project is created for educational purposes and is free to use and modify.

## 📞 Support

For questions or issues:
1. Check the application logs for error details
2. Verify database connection and credentials
3. Ensure all prerequisites are installed
4. Review the project documentation

## 🎯 Future Enhancements

- [ ] Email notifications for orders
- [ ] Advanced search with filters
- [ ] Product reviews and ratings
- [ ] Inventory management dashboard
- [ ] Real payment gateway integration
- [ ] Mobile app development
- [ ] Multi-language support

---

**Happy Coding! 🚀**

*Built with ❤️ using Spring Boot and modern web technologies*