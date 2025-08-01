# E-Commerce Platform

A basic e-commerce platform built with Spring Boot, MySQL, and Thymeleaf featuring product catalog, shopping cart, and payment simulation.

## Features

- **Product Catalog**: Browse products by category, search functionality
- **Shopping Cart**: Add/remove items, update quantities, session-based cart
- **Payment Simulation**: Simulated checkout process with order confirmation
- **Responsive Design**: Bootstrap-based UI that works on all devices
- **Order Management**: View order details and status tracking

## Technologies Used

- **Backend**: Java 17, Spring Boot 3.2.0
- **Database**: MySQL 8.0
- **Frontend**: Thymeleaf, Bootstrap 5, Font Awesome
- **Build Tool**: Maven

## Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

## Setup Instructions

### 1. Database Setup

Create a MySQL database:

```sql
CREATE DATABASE ecommerce_db;
CREATE USER 'ecommerce_user'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON ecommerce_db.* TO 'ecommerce_user'@'localhost';
FLUSH PRIVILEGES;
```

### 2. Application Configuration

Update `src/main/resources/application.properties` with your database credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
spring.datasource.username=ecommerce_user
spring.datasource.password=password
```

### 3. Build and Run

```bash
# Clone the repository
git clone <repository-url>
cd ecommerce-platform

# Build the application
mvn clean compile

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Project Structure

```
src/main/java/com/example/ecommerce/
├── entity/          # JPA entities (Product, Order, CartItem, OrderItem)
├── repository/      # Data access layer
├── service/         # Business logic layer
├── controller/      # Web controllers
├── config/          # Configuration classes
└── EcommerceApplication.java

src/main/resources/
├── templates/       # Thymeleaf templates
├── static/          # Static resources (CSS, JS, images)
└── application.properties
```

## API Endpoints

### Web Pages
- `GET /` - Home page with product catalog
- `GET /product?id={id}` - Product detail page
- `GET /cart` - Shopping cart page
- `GET /order/checkout` - Checkout page
- `GET /order/confirmation` - Order confirmation page
- `GET /order/{id}` - Order detail page

### Cart Operations
- `POST /cart/add` - Add product to cart
- `POST /cart/update` - Update cart item quantity
- `POST /cart/remove` - Remove item from cart
- `POST /cart/clear` - Clear entire cart

### Order Operations
- `POST /order/place` - Place an order

## Sample Data

The application automatically initializes with sample products across different categories:
- Electronics (iPhone, MacBook, Headphones)
- Clothing (Jackets, T-shirts, Shoes)
- Home & Garden (Coffee Maker, Plants, Pillows)
- Books (Programming, Mystery novels)
- Sports (Yoga Mat, Dumbbells)

## Features in Detail

### Product Catalog
- Browse all products or filter by category
- Search products by name or description
- View product details with images and descriptions
- Stock availability checking

### Shopping Cart
- Session-based cart (no login required)
- Add products with quantity selection
- Update quantities or remove items
- Real-time cart total calculation
- Cart item count in navigation

### Checkout Process
1. Review cart items and total
2. Enter shipping information
3. Simulated payment processing
4. Order confirmation with order ID
5. Order status tracking

### Payment Simulation
- No real payment processing
- Uses dummy credit card information
- Always succeeds for demonstration purposes
- In production, integrate with payment gateways like Stripe or PayPal

## Customization

### Adding New Products
Products are automatically loaded from `DataInitializer.java`. To add more products, modify this file or create an admin interface.

### Styling
The application uses Bootstrap 5. Customize the appearance by:
- Modifying the Thymeleaf templates in `src/main/resources/templates/`
- Adding custom CSS in `src/main/resources/static/css/`

### Database Schema
The application uses JPA with automatic schema generation. Tables are created automatically:
- `products` - Product information
- `cart_items` - Shopping cart items
- `orders` - Order information
- `order_items` - Items within orders

## Production Considerations

For production deployment, consider:

1. **Security**: Add authentication and authorization
2. **Payment Integration**: Integrate real payment gateways
3. **Image Storage**: Use cloud storage for product images
4. **Caching**: Implement Redis for session and data caching
5. **Monitoring**: Add logging and monitoring tools
6. **Database**: Use connection pooling and optimize queries
7. **SSL**: Enable HTTPS for secure transactions

## Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Verify MySQL is running
   - Check database credentials in `application.properties`
   - Ensure database exists

2. **Port Already in Use**
   - Change server port in `application.properties`: `server.port=8081`

3. **Maven Build Issues**
   - Ensure Java 17 is installed and configured
   - Run `mvn clean install` to resolve dependencies

## License

This project is for educational purposes and is provided as-is.