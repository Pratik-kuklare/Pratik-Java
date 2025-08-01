# Secure Online Election System

A comprehensive web-based election system built with Java Spring Security, MySQL, and JSP for conducting secure online elections with Indian political candidates.

## 🗳️ Features

### Security Features
- **Spring Security Integration** with role-based access control
- **BCrypt Password Encryption** for secure authentication
- **Session Management** with secure login/logout
- **Vote Integrity Protection** with database triggers
- **IP Address Logging** for audit trails
- **One-vote-per-user** enforcement

### Voter Features
- **Secure Registration** with email and national ID validation
- **Anonymous Voting** system
- **Real-time Results** viewing
- **Vote Confirmation** system
- **Responsive Interface** for easy voting

### Admin Features
- **Complete Dashboard** with election statistics
- **Candidate Management** (Add, Edit, Delete)
- **Real-time Vote Monitoring**
- **User Management** and oversight
- **Detailed Results Analysis**

## 🇮🇳 Indian Political Candidates

The system includes 5 major Indian political figures:

1. **Rahul Gandhi** - Indian National Congress
   - Focus: Social justice, employment generation, youth empowerment

2. **Amit Shah** - Bharatiya Janata Party
   - Focus: National security, Digital India, Make in India initiatives

3. **Arvind Kejriwal** - Aam Aadmi Party
   - Focus: Anti-corruption, education reforms, transparent governance

4. **Mamata Banerjee** - All India Trinamool Congress
   - Focus: Federal rights, women empowerment, regional development

5. **Uddhav Thackeray** - Shiv Sena (UBT)
   - Focus: Regional identity, urban development, environmental conservation

## 🛠️ Technology Stack

- **Backend**: Java 11, Spring MVC, Spring Security
- **Database**: MySQL 8.0
- **Frontend**: JSP, HTML5, CSS3, JavaScript
- **Security**: BCrypt encryption, CSRF protection
- **Build Tool**: Maven 3.6+
- **Server**: Apache Tomcat (Embedded)

## 📋 Prerequisites

- Java 11 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher
- Web browser (Chrome, Firefox, Edge)

## 🚀 Installation & Setup

### 1. Database Setup
```bash
# Connect to MySQL
mysql -u root -p

# Run the complete database setup
source complete-database-setup.sql
```

### 2. Configure Application
Update database credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.username=root
spring.datasource.password=your_password
```

### 3. Build and Run
```bash
# Build the project
mvn clean compile

# Run the application
mvn spring-boot:run
```

### 4. Access the System
- **Application URL**: `http://localhost:8081`
- **Login Page**: `http://localhost:8081/login`
- **Registration**: `http://localhost:8081/register`

## 👤 Default Credentials

### Admin Account
- **Username**: `admin`
- **Password**: `admin123`
- **Access**: Full system administration

### Voter Accounts
- **Username**: `voter` | **Password**: `voter123`
- **Username**: `testvoter` | **Password**: `test123`
- **Access**: Voting and results viewing

## 🗳️ How to Vote

### For Existing Users:
1. Go to `http://localhost:8081/login`
2. Login with voter credentials
3. Select your preferred candidate
4. Click "Vote for [Candidate Name]"
5. Confirm your choice
6. Vote recorded successfully!

### For New Users:
1. Go to `http://localhost:8081/register`
2. Fill registration form with unique details
3. Login with your new credentials
4. Follow voting steps above

## 👨‍💼 Admin Functions

### Dashboard
- View election statistics
- Monitor voter turnout
- Track candidate performance
- System overview

### Candidate Management
- Add new candidates
- Edit candidate information
- Activate/deactivate candidates
- Delete candidates

### Results & Analytics
- Real-time vote counting
- Percentage calculations
- Leading candidate identification
- Detailed election reports

## 🔒 Security Features

### Database Security
- **Encrypted Passwords**: BCrypt hashing
- **SQL Injection Prevention**: Prepared statements
- **Vote Tampering Protection**: Database triggers
- **Audit Trails**: Complete logging system

### Application Security
- **Role-based Access**: Admin vs Voter permissions
- **Session Management**: Secure login sessions
- **CSRF Protection**: Cross-site request forgery prevention
- **Input Validation**: Form data sanitization

## 📊 Database Schema

### Users Table
- User authentication and profiles
- Role-based access control (ADMIN/VOTER)
- Vote status tracking
- Registration timestamps

### Candidates Table
- Candidate information and profiles
- Party affiliations
- Vote count tracking
- Active/inactive status

### Votes Table
- Secure vote recording
- User-candidate relationships
- Timestamp and IP logging
- Tamper-proof design

## 🌐 API Endpoints

### Public Endpoints
- `GET /` - Home page
- `GET /login` - Login page
- `POST /login` - User authentication
- `GET /register` - Registration page
- `POST /register` - User registration

### Voter Endpoints
- `GET /voter/dashboard` - Voting interface
- `POST /voter/vote` - Cast vote
- `GET /voter/results` - View results

### Admin Endpoints
- `GET /admin/dashboard` - Admin dashboard
- `GET /admin/candidates` - Manage candidates
- `POST /admin/candidates/add` - Add candidate
- `GET /admin/results` - Detailed results

## 🔧 Configuration

### Application Properties
```properties
# Server Configuration
server.port=8081

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/election_db
spring.datasource.username=root
spring.datasource.password=your_password

# JSP Configuration
spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view.suffix=.jsp
```

### Security Configuration
- Custom authentication with fallback
- Session-based security
- Role-based page access
- CSRF protection disabled for API endpoints

## 🚨 Troubleshooting

### Common Issues

**Port 8080 Already in Use:**
- Application runs on port 8081 by default
- Change port in `application.properties` if needed

**Database Connection Failed:**
- Verify MySQL is running
- Check database credentials
- Ensure `election_db` database exists

**Login Issues:**
- Clear browser cache/cookies
- Try incognito/private mode
- Verify user credentials in database

**JSP Not Loading:**
- Check JSP files in `/WEB-INF/views/`
- Verify view resolver configuration
- Restart application

## 📈 Future Enhancements

- [ ] Email verification for registration
- [ ] Two-factor authentication
- [ ] Mobile responsive design
- [ ] REST API for mobile apps
- [ ] Advanced analytics dashboard
- [ ] Multi-language support (Hindi, regional languages)
- [ ] Blockchain integration for enhanced security
- [ ] SMS notifications
- [ ] Export results to PDF/Excel

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-feature`)
3. Commit changes (`git commit -am 'Add new feature'`)
4. Push to branch (`git push origin feature/new-feature`)
5. Create Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For support and questions:
- Create an issue in the repository
- Check the troubleshooting section
- Review the documentation

## 🎯 Project Status

**Current Version**: 1.0.0
**Status**: Production Ready
**Last Updated**: July 2025

---

**Built with ❤️ for secure and transparent elections in India**