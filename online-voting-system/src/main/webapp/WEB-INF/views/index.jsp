<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Secure Election System</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f4f4f4; }
        .container { max-width: 800px; margin: 0 auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        .header { text-align: center; margin-bottom: 30px; }
        .btn { display: inline-block; padding: 12px 24px; margin: 10px; text-decoration: none; border-radius: 5px; font-weight: bold; }
        .btn-primary { background-color: #007bff; color: white; }
        .btn-success { background-color: #28a745; color: white; }
        .btn:hover { opacity: 0.8; }
        .features { display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 20px; margin-top: 30px; }
        .feature { padding: 20px; background: #f8f9fa; border-radius: 5px; text-align: center; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>🗳️ Secure Election System</h1>
            <p>A secure and transparent platform for conducting online elections</p>
        </div>
        
        <div style="text-align: center;">
            <a href="/login" class="btn btn-primary">Login</a>
            <a href="/register" class="btn btn-success">Register to Vote</a>
        </div>
        
        <div class="features">
            <div class="feature">
                <h3>🔐 Secure Voting</h3>
                <p>Advanced security measures to protect your vote and personal information</p>
            </div>
            <div class="feature">
                <h3>👥 Voter Registration</h3>
                <p>Easy and secure voter registration with identity verification</p>
            </div>
            <div class="feature">
                <h3>📊 Real-time Results</h3>
                <p>View election results in real-time with transparent vote counting</p>
            </div>
            <div class="feature">
                <h3>🛡️ Privacy Protection</h3>
                <p>Your vote is anonymous and your privacy is fully protected</p>
            </div>
        </div>
    </div>
</body>
</html>