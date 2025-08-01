<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Candidates - Election System</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f4f4f4; }
        .container { max-width: 1200px; margin: 0 auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; }
        .nav { margin-bottom: 30px; }
        .nav a { display: inline-block; padding: 10px 20px; margin-right: 10px; background-color: #007bff; color: white; text-decoration: none; border-radius: 5px; }
        .nav a:hover { background-color: #0056b3; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #f8f9fa; font-weight: bold; }
        .btn { padding: 8px 16px; border: none; border-radius: 4px; cursor: pointer; text-decoration: none; display: inline-block; margin-right: 5px; }
        .btn-primary { background-color: #007bff; color: white; }
        .btn-success { background-color: #28a745; color: white; }
        .btn-danger { background-color: #dc3545; color: white; }
        .btn:hover { opacity: 0.8; }
        .alert { padding: 15px; margin-bottom: 20px; border-radius: 5px; }
        .alert-success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>👥 Manage Candidates</h1>
            <a href="/admin/candidates/add" class="btn btn-success">+ Add New Candidate</a>
        </div>
        
        <div class="nav">
            <a href="/admin/dashboard">← Dashboard</a>
            <a href="/admin/candidates">Candidates</a>
            <a href="/admin/results">Results</a>
            <a href="/logout">Logout</a>
        </div>
        
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Party</th>
                    <th>Description</th>
                    <th>Votes</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="candidate" items="${candidates}">
                    <tr>
                        <td>${candidate.id}</td>
                        <td>${candidate.name}</td>
                        <td>${candidate.party}</td>
                        <td>${candidate.description}</td>
                        <td><strong>${candidate.voteCount}</strong></td>
                        <td>
                            <c:choose>
                                <c:when test="${candidate.active}">
                                    <span style="color: #28a745; font-weight: bold;">Active</span>
                                </c:when>
                                <c:otherwise>
                                    <span style="color: #dc3545; font-weight: bold;">Inactive</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a href="/admin/candidates/edit/${candidate.id}" class="btn btn-primary">Edit</a>
                            <form method="post" action="/admin/candidates/delete/${candidate.id}" 
                                  style="display: inline;" 
                                  onsubmit="return confirm('Are you sure you want to delete this candidate?')">
                                <button type="submit" class="btn btn-danger">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
        <c:if test="${empty candidates}">
            <div style="text-align: center; padding: 50px; color: #666;">
                <h3>No candidates found</h3>
                <p>There are currently no candidates in the system.</p>
                <a href="/admin/candidates/add" class="btn btn-success">Add First Candidate</a>
            </div>
        </c:if>
    </div>
</body>
</html>