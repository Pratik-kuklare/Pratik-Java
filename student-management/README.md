# Student Management System

A comprehensive web application for managing student records and performance built with Spring Boot, MySQL, and Thymeleaf.

## Features

### Student Management (CRUD)
- Add new students with complete details
- View student information
- Edit student records
- Delete students
- Search students by name
- Filter students by class

### Marks Management
- Add marks for different subjects and exam types
- Edit existing marks
- Delete marks
- View marks by student
- Automatic grade calculation (A+, A, B, C, D, F)
- Percentage calculation

### Report Generation
- Individual student performance reports
- Class-wise student reports
- Print-friendly report formats
- Overall average calculation

### Dashboard
- Quick overview of total students, marks, and classes
- Quick action buttons for common tasks
- Statistics display

## Technologies Used

- **Backend**: Java 17, Spring Boot 3.2.0
- **Database**: MySQL 8.0
- **Frontend**: Thymeleaf, Bootstrap 5.1.3, Font Awesome 6.0
- **Build Tool**: Maven
- **ORM**: Spring Data JPA (Hibernate)

## Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

## Setup Instructions

### 1. Database Setup
1. Install MySQL and start the service
2. Create a database named `student_management`:
   ```sql
   CREATE DATABASE student_management;
   ```
3. Update database credentials in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

### 2. Application Setup
1. Clone or download the project
2. Navigate to the project directory
3. Build the project:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```
5. Access the application at: `http://localhost:8080`

## Application Structure

```
src/main/java/com/studentmanagement/
├── entity/
│   ├── Student.java          # Student entity
│   └── Mark.java            # Mark entity
├── repository/
│   ├── StudentRepository.java
│   └── MarkRepository.java
├── service/
│   ├── StudentService.java
│   └── MarkService.java
├── controller/
│   ├── HomeController.java
│   ├── StudentController.java
│   ├── MarkController.java
│   └── ReportController.java
└── StudentManagementApplication.java

src/main/resources/
├── templates/
│   ├── index.html           # Dashboard
│   ├── students/            # Student templates
│   ├── marks/              # Marks templates
│   └── reports/            # Report templates
└── application.properties   # Configuration
```

## Usage Guide

### Adding Students
1. Navigate to Students → Add New Student
2. Fill in all required fields (marked with *)
3. Click "Save Student"

### Managing Marks
1. Go to a student's detail page
2. Click "Add Marks" or navigate to Marks → Add New Mark
3. Select student, subject, exam type, and enter marks
4. System automatically calculates percentage and grade

### Generating Reports
1. Navigate to Reports
2. Choose between Individual Student Report or Class Report
3. Select the appropriate student or class
4. Click "Generate Report"
5. Use the print button for hard copies

## Grading System

- A+: 90-100%
- A: 80-89%
- B: 70-79%
- C: 60-69%
- D: 50-59%
- F: Below 50%

## API Endpoints

### Students
- `GET /students` - List all students
- `GET /students/new` - Show add student form
- `POST /students` - Create new student
- `GET /students/{id}` - View student details
- `GET /students/{id}/edit` - Show edit student form
- `POST /students/{id}` - Update student
- `POST /students/{id}/delete` - Delete student

### Marks
- `GET /marks` - List all marks
- `GET /marks/student/{studentId}` - List marks for a student
- `GET /marks/new` - Show add mark form
- `POST /marks` - Create new mark
- `GET /marks/{id}/edit` - Show edit mark form
- `POST /marks/{id}` - Update mark
- `POST /marks/{id}/delete` - Delete mark

### Reports
- `GET /reports` - Reports dashboard
- `GET /reports/student/{studentId}` - Individual student report
- `GET /reports/class?studentClass={class}` - Class report

## Configuration

### Database Configuration
Update `application.properties` for your database setup:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_management
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Server Configuration
Change server port if needed:
```properties
server.port=8080
```

## Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Ensure MySQL is running
   - Check database credentials
   - Verify database exists

2. **Port Already in Use**
   - Change server port in application.properties
   - Or stop the process using port 8080

3. **Build Errors**
   - Ensure Java 17 is installed
   - Check Maven installation
   - Run `mvn clean install`

## Future Enhancements

- User authentication and authorization
- Email notifications
- Advanced reporting with charts
- Export reports to PDF/Excel
- Attendance management
- Fee management
- Parent portal

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is open source and available under the MIT License.