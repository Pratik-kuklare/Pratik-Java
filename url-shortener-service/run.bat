@echo off
echo ========================================
echo    URL Shortener Service Launcher
echo ========================================
echo.

echo Checking if port 8080 is available...
netstat -ano | findstr :8080 > nul
if %errorlevel% == 0 (
    echo Port 8080 is in use. Attempting to free it...
    for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8080') do (
        echo Killing process %%a
        taskkill /f /pid %%a > nul 2>&1
    )
    timeout /t 2 > nul
)

echo Starting URL Shortener Service...
echo.
echo Web Interface: http://localhost:8080/
echo API Endpoint: http://localhost:8080/api/shorten
echo.
echo Press Ctrl+C to stop the application
echo ========================================

mvn spring-boot:run