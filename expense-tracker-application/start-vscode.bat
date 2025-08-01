@echo off
echo Starting Expense Tracker in VS Code...
echo.

REM Check if VS Code is installed
where code >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: VS Code is not installed or not in PATH
    echo Please install VS Code and add it to your PATH
    pause
    exit /b 1
)

REM Open project in VS Code
echo Opening project in VS Code...
code .

echo.
echo VS Code opened successfully!
echo.
echo Next steps:
echo 1. Install recommended Java extensions when prompted
echo 2. Wait for Java extension to load the project
echo 3. Configure your database connection in application.properties
echo 4. Press Ctrl+Shift+P and run "Java: Run Java" or use F5
echo.
echo The application will be available at: http://localhost:8081
echo.
pause