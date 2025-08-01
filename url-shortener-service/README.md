# URL Shortener

A Spring Boot application that shortens long URLs and provides analytics tracking.

## Features

- Generate unique short URLs with customizable expiration
- Track clicks with detailed analytics
- REST API for programmatic access
- Web dashboard for easy URL management
- MySQL database for persistence
- Click tracking with IP, user agent, and referrer information

## Tech Stack

- Java 17
- Spring Boot 3.2.0
- MySQL 8.0
- Spring Data JPA
- Maven

## Setup Instructions

### Prerequisites

1. Java 17 or higher
2. Maven 3.6+
3. MySQL 8.0+

### Database Setup

1. Install MySQL and create a database:
```sql
CREATE DATABASE url_shortener;
```

2. Update database credentials in `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    username: your_username
    password: your_password
```

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Shorten URL
```http
POST /api/shorten
Content-Type: application/json

{
  "url": "https://example.com/very/long/url",
  "expirationDays": 30
}
```

### Redirect to Original URL
```http
GET /{shortCode}
```

### Get Analytics
```http
GET /api/analytics/{shortCode}
```

## Usage Examples

### Using cURL

**Shorten a URL:**
```bash
curl -X POST http://localhost:8080/api/shorten \
  -H "Content-Type: application/json" \
  -d '{"url": "https://example.com", "expirationDays": 30}'
```

**Get Analytics:**
```bash
curl http://localhost:8080/api/analytics/abc123
```

### Using the Web Interface

1. Open `http://localhost:8080` in your browser
2. Enter a URL to shorten
3. Use the generated short URL
4. Check analytics using the short code

## Configuration

Key configuration options in `application.yml`:

- `app.base-url`: Base URL for short links
- `app.short-code-length`: Length of generated short codes
- Database connection settings
- Server port

## Database Schema

The application creates two main tables:

- `urls`: Stores original URLs, short codes, and metadata
- `click_events`: Tracks individual clicks with analytics data

## Security Features

- Input validation for URLs
- SQL injection protection via JPA
- Secure random short code generation
- IP address tracking for analytics

## Performance Considerations

- Database indexes on short codes for fast lookups
- Efficient click counting with batch updates
- Configurable expiration to manage storage

## Future Enhancements

- Custom short codes
- Bulk URL shortening
- Advanced analytics (geolocation, device types)
- Rate limiting
- User authentication and URL management
- QR code generation