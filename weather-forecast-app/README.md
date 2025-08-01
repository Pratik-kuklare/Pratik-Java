# Weather Forecast Application

A Spring Boot web application that provides real-time weather information for any city using the OpenWeatherMap API.

## Features

- 🌤️ Real-time weather data for any city worldwide
- 🌡️ Temperature display in Celsius
- 💨 Weather conditions and descriptions
- 💧 Humidity information
- 🎯 Simple and clean web interface
- ⚡ Fast and responsive design

## Technologies Used

- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Web**
- **Thymeleaf** (Template Engine)
- **Maven** (Build Tool)
- **OpenWeatherMap API**

## Prerequisites

Before running this application, make sure you have:

- Java 17 or higher installed
- Maven 3.6+ installed
- Internet connection (for API calls)
- OpenWeatherMap API key (already configured)

## Project Structure

```
weather-forecast-app/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/weather/
│       │       ├── WeatherForecastAppApplication.java    # Main application class
│       │       ├── controller/
│       │       │   └── WeatherController.java           # Web controller
│       │       ├── service/
│       │       │   └── WeatherService.java              # Weather API service
│       │       └── model/                               # Data models
│       └── resources/
│           ├── application.properties                   # Configuration
│           └── templates/
│               └── weather.html                         # Web template
├── pom.xml                                             # Maven configuration
└── README.md                                           # This file
```

## Installation & Setup

### 1. Clone or Download the Project
```bash
# If using Git
git clone <repository-url>
cd weather-forecast-app

# Or download and extract the project folder
```

### 2. Open in VS Code
1. Open VS Code
2. Go to **File → Open Folder**
3. Select the `weather-forecast-app` folder

### 3. Install Required VS Code Extensions
- Extension Pack for Java
- Spring Boot Extension Pack

## Running the Application

### Method 1: Using Maven Command
```bash
mvn spring-boot:run
```

### Method 2: Using VS Code
1. Open `WeatherForecastAppApplication.java`
2. Click the "Run" button above the `main` method
3. Or use the Spring Boot Dashboard in VS Code

### Method 3: Using Terminal in VS Code
1. Open terminal in VS Code (`Ctrl + ` ` `)
2. Run: `mvn spring-boot:run`

## Accessing the Application

1. Once the application starts, you'll see:
   ```
   Tomcat started on port 8080 (http) with context path ''
   ```

2. Open your web browser and navigate to:
   ```
   http://localhost:8080
   ```

3. Enter any city name and click "Get Weather" to see the weather information.

## Usage Examples

Try entering these cities:
- New York
- London
- Tokyo
- Mumbai
- Paris
- Sydney

## API Configuration

The application uses OpenWeatherMap API. The configuration is in `src/main/resources/application.properties`:

```properties
weather.api.key=dd709d7bb3b9b1307523f122735648ba
weather.api.url=https://api.openweathermap.org/data/2.5/forecast
spring.thymeleaf.cache=false
```

## Troubleshooting

### Common Issues:

1. **Port 8080 already in use**
   - Stop other applications using port 8080
   - Or change the port in `application.properties`:
     ```properties
     server.port=8081
     ```

2. **City not found error**
   - Check spelling of the city name
   - Try using city name with country code (e.g., "London,UK")

3. **API errors**
   - Check internet connection
   - Verify API key is valid

### Build Issues:
```bash
# Clean and rebuild
mvn clean compile

# Skip tests if needed
mvn spring-boot:run -DskipTests
```

## Development

### Making Changes:
1. **HTML Templates**: Edit `src/main/resources/templates/weather.html`
2. **Styling**: Add CSS in the HTML template or create separate CSS files
3. **Business Logic**: Modify files in `src/main/java/com/example/weather/`

### Hot Reload:
The application supports hot reload for template changes when `spring.thymeleaf.cache=false` is set.

## Building for Production

```bash
# Create executable JAR
mvn clean package

# Run the JAR
java -jar target/weather-forecast-app-0.0.1-SNAPSHOT.jar
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is open source and available under the [MIT License](LICENSE).

## Contact

For questions or support, please contact the development team.

---

**Happy Weather Checking! 🌤️**