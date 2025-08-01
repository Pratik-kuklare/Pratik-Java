<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error - Election System</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f4f4f4; }
        .container { max-width: 600px; margin: 50px auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        .error-code { font-size: 48px; color: #dc3545; text-align: center; margin-bottom: 20px; }
        .error-message { background-color: #f8d7da; color: #721c24; padding: 15px; border-radius: 5px; margin-bottom: 20px; }
        .btn { display: inline-block; padding: 10px 20px; background-color: #007bff; color: white; text-decoration: none; border-radius: 5px; }
        .btn:hover { background-color: #0056b3; }
    </style>
</head>
<body>
    <div class="container">
        <div class="error-code">Error ${statusCode}</div>
        <h2>Something went wrong!</h2>
        
        <div class="error-message">
            <strong>Error Details:</strong><br>
            ${errorMessage}
        </div>
        
        <p>Please try the following:</p>
        <ul>
            <li>Check if the database is running</li>
            <li>Verify your database credentials</li>
            <li>Make sure all required fields are filled</li>
            <li>Contact the administrator if the problem persists</li>
        </ul>
        
        <div style="text-align: center; margin-top: 30px;">
            <a href="/" class="btn">← Back to Home</a>
        </div>
    </div>
</body>
</html>