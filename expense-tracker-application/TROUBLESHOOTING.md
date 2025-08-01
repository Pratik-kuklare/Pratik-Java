# 🔧 Troubleshooting Guide

## Common Issues and Solutions

### 1. Port 8081 Already in Use

**Error Message:**
```
Web server failed to start. Port 8081 was already in use.
```

**Solutions:**

#### Option A: Use the provided script
```bash
# Double-click this file or run in terminal
run-in-vscode.bat
```

#### Option B: Manual port cleanup
```bash
# Find process using port 8081
netstat -ano | findstr :8081

# Kill the process (replace PID with actual process ID)
taskkill /PID <PID_NUMBER> /F

# Then run the application
mvn spring-boot:run
```

#### Option C: Use different port
Add this to `application.properties`:
```properties
server.port=8082
```

### 2. Maven Command Typos

**Common Typos:**
- `mvn complie` ❌ → `mvn compile` ✅
- `mvn runn` ❌ → `mvn spring-boot:run` ✅
- `mvn clean instal` ❌ → `mvn clean install` ✅

**Correct Maven Commands:**
```bash
mvn clean compile          # Clean and compile
mvn spring-boot:run        # Run the application
mvn clean package         # Build JAR file
mvn clean install         # Install to local repository
```

### 3. Database Connection Issues

**Error Message:**
```
Could not create connection to database server
```

**Solutions:**
1. **Check MySQL is running:**
   ```bash
   # Windows - check if MySQL service is running
   sc query MySQL80
   ```

2. **Verify database exists:**
   ```sql
   CREATE DATABASE expense_tracker;
   ```

3. **Check credentials in `application.properties`:**
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/expense_tracker
   spring.datasource.username=root
   spring.datasource.password=your_actual_password
   ```

### 4. VS Code Java Extension Issues

**Problem:** VS Code doesn't recognize Java project

**Solutions:**
1. **Install Java Extension Pack:**
   - Open VS Code
   - Go to Extensions (Ctrl+Shift+X)
   - Search "Extension Pack for Java"
   - Install it

2. **Reload Java Projects:**
   - Press `Ctrl+Shift+P`
   - Type "Java: Reload Projects"
   - Select it

3. **Check Java Version:**
   ```bash
   java -version
   # Should show Java 17 or higher
   ```

### 5. Frontend Not Loading

**Problem:** Categories not showing, "Loading..." persists

**Solutions:**
1. **Check browser console (F12):**
   - Look for JavaScript errors
   - Check Network tab for failed API calls

2. **Verify backend is running:**
   ```bash
   curl http://localhost:8081/api/categories
   ```

3. **Clear browser cache:**
   - Press `Ctrl+F5` for hard refresh
   - Or clear browser cache manually

### 6. Build Failures

**Error Message:**
```
BUILD FAILURE
```

**Solutions:**
1. **Clean and rebuild:**
   ```bash
   mvn clean compile
   ```

2. **Check Java version:**
   ```bash
   java -version
   mvn -version
   ```

3. **Update dependencies:**
   ```bash
   mvn clean install -U
   ```

## 🚀 Quick Start Commands

### For VS Code:
```bash
# Method 1: Use provided script
run-in-vscode.bat

# Method 2: VS Code tasks
# Press Ctrl+Shift+P → "Tasks: Run Task" → "Kill Port and Run App"

# Method 3: Manual
code .
# Then press F5 or Ctrl+F5
```

### For Command Line:
```bash
# Kill existing processes and run
for /f "tokens=5" %a in ('netstat -ano | findstr :8081') do taskkill /PID %a /F
mvn spring-boot:run
```

## 🔍 Debugging Steps

1. **Check if Java is installed:**
   ```bash
   java -version
   ```

2. **Check if Maven is installed:**
   ```bash
   mvn -version
   ```

3. **Check if MySQL is running:**
   ```bash
   sc query MySQL80
   ```

4. **Test database connection:**
   ```bash
   mysql -u root -p
   USE expense_tracker;
   SHOW TABLES;
   ```

5. **Check port availability:**
   ```bash
   netstat -ano | findstr :8081
   ```

## 📞 Still Having Issues?

If you're still experiencing problems:

1. **Check the full error log** in VS Code terminal
2. **Verify all prerequisites** are installed (Java 17+, Maven, MySQL)
3. **Try running** `mvn clean install` first
4. **Check firewall/antivirus** isn't blocking port 8081
5. **Restart your computer** if all else fails

## ✅ Success Indicators

You'll know everything is working when you see:
- ✅ "Started ExpenseTrackerApplication" in the console
- ✅ "Tomcat started on port(s): 8081" message
- ✅ Categories dropdown populated with options
- ✅ Recent transactions showing data (not "Loading...")
- ✅ Stats showing ₹0.00 (not $0.00)

## 🎯 Test Your Setup

Visit these URLs to verify everything works:
- http://localhost:8081 - Main application
- http://localhost:8081/api/categories - API test
- http://localhost:8081/debug.html - Debug page