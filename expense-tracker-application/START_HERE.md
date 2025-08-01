# 🚀 START HERE - Quick Setup Guide

## ⚡ Fastest Way to Run Your Expense Tracker

### Step 1: Open in VS Code
```bash
# Double-click this file to open project in VS Code
start-vscode.bat
```

### Step 2: Run the Application
```bash
# Double-click this file to start the app (handles port conflicts automatically)
run-in-vscode.bat
```

**OR** in VS Code:
- Press `Ctrl+Shift+P`
- Type "Tasks: Run Task"
- Select "Kill Port and Run App"

### Step 3: Access Your Application
- **Main App**: http://localhost:8081
- **API Test**: http://localhost:8081/debug.html

## 🔧 If You Get Errors:

### Port 8081 Already in Use:
```bash
# The run-in-vscode.bat script handles this automatically
# Or manually run:
netstat -ano | findstr :8081
taskkill /PID <PID_NUMBER> /F
```

### Maven Command Typos:
- ❌ `mvn complie` 
- ✅ `mvn compile`
- ✅ `mvn spring-boot:run`

### Database Not Connected:
1. Start MySQL service
2. Create database: `CREATE DATABASE expense_tracker;`
3. Update `src/main/resources/application.properties` with your MySQL password

## 📋 Prerequisites Checklist:
- ✅ Java 17+ installed
- ✅ Maven installed  
- ✅ MySQL running
- ✅ VS Code with Java extensions

## 🎯 Success Indicators:
When everything works, you'll see:
- ✅ Categories dropdown populated (Food, Transportation, etc.)
- ✅ Recent transactions (not "Loading...")
- ✅ Rupee currency (₹) instead of dollar ($)
- ✅ "Started ExpenseTrackerApplication" in console

## 📞 Need Help?
Check `TROUBLESHOOTING.md` for detailed solutions to common issues.

---
**Ready to code? Your expense tracker is waiting! 🎊**