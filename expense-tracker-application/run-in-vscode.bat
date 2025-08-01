@echo off
echo ========================================
echo    Expense Tracker - VS Code Setup
echo ========================================
echo.

REM Kill any existing processes on port 8081
echo 1. Checking for existing processes on port 8081...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8081') do (
    echo    Killing process %%a...
    taskkill /PID %%a /F >nul 2>&1
)
echo    Port 8081 is now free.
echo.

REM Clean and compile the project
echo 2. Cleaning and compiling the project...
call mvn clean compile
if %ERRORLEVEL% NEQ 0 (
    echo    ERROR: Compilation failed!
    echo    Please check the error messages above.
    pause
    exit /b 1
)
echo    Compilation successful!
echo.

REM Run the application
echo 3. Starting the Spring Boot application...
echo    The application will be available at: http://localhost:8081
echo    Press Ctrl+C to stop the application
echo.
call mvn spring-boot:run

pause