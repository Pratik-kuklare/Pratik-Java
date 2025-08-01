<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Candidate - Election System</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f4f4f4; }
        .container { max-width: 600px; margin: 20px auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input[type="text"], textarea, select { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px; box-sizing: border-box; }
        textarea { height: 100px; resize: vertical; }
        .btn { padding: 12px 24px; border: none; border-radius: 5px; cursor: pointer; font-size: 16px; margin-right: 10px; }
        .btn-success { background-color: #28a745; color: white; }
        .btn-secondary { background-color: #6c757d; color: white; text-decoration: none; display: inline-block; }
        .btn:hover { opacity: 0.8; }
        .nav { margin-bottom: 30px; }
        .nav a { display: inline-block; padding: 10px 20px; margin-right: 10px; background-color: #007bff; color: white; text-decoration: none; border-radius: 5px; }
        .nav a:hover { background-color: #0056b3; }
        .alert { padding: 10px; margin-bottom: 20px; border-radius: 5px; }
        .alert-success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .alert-danger { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
    </style>
</head>
<body>
    <div class="container">
        <h2>✏️ Edit Candidate</h2>
        
        <div class="nav">
            <a href="/admin/dashboard">← Dashboard</a>
            <a href="/admin/candidates">← Back to Candidates</a>
        </div>
        
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        
        <form method="post" action="/admin/candidates/edit">
            <input type="hidden" name="id" value="${candidate.id}">
            
            <div class="form-group">
                <label for="name">Candidate Name:</label>
                <input type="text" id="name" name="name" value="${candidate.name}" required>
            </div>
            
            <div class="form-group">
                <label for="party">Political Party:</label>
                <input type="text" id="party" name="party" value="${candidate.party}" required>
            </div>
            
            <div class="form-group">
                <label for="description">Description/Biography:</label>
                <textarea id="description" name="description" required>${candidate.description}</textarea>
            </div>
            
            <div class="form-group">
                <label for="photoUrl">Photo URL (optional):</label>
                <input type="text" id="photoUrl" name="photoUrl" value="${candidate.photoUrl}" placeholder="https://example.com/photo.jpg">
            </div>
            
            <div class="form-group">
                <label for="active">Status:</label>
                <select id="active" name="active">
                    <option value="true" ${candidate.active ? 'selected' : ''}>Active</option>
                    <option value="false" ${!candidate.active ? 'selected' : ''}>Inactive</option>
                </select>
            </div>
            
            <div class="form-group">
                <label>Current Vote Count:</label>
                <p style="font-size: 18px; font-weight: bold; color: #007bff;">${candidate.voteCount} votes</p>
                <small style="color: #666;">Vote count cannot be modified for security reasons.</small>
            </div>
            
            <div style="margin-top: 30px;">
                <button type="submit" class="btn btn-success">Update Candidate</button>
                <a href="/admin/candidates" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>