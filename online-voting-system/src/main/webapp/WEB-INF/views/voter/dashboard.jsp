<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Voter Dashboard - Election System</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f4f4f4; }
        .container { max-width: 1000px; margin: 0 auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; padding-bottom: 20px; border-bottom: 1px solid #eee; }
        .nav { margin-bottom: 30px; }
        .nav a { display: inline-block; padding: 10px 20px; margin-right: 10px; background-color: #007bff; color: white; text-decoration: none; border-radius: 5px; }
        .nav a:hover { background-color: #0056b3; }
        .candidates { display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 20px; }
        .candidate { border: 1px solid #ddd; border-radius: 10px; padding: 20px; text-align: center; }
        .candidate h3 { color: #333; margin-bottom: 10px; }
        .candidate p { color: #666; margin-bottom: 15px; }
        .btn { padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; font-size: 14px; }
        .btn-success { background-color: #28a745; color: white; }
        .btn-success:hover { background-color: #218838; }
        .btn-secondary { background-color: #6c757d; color: white; cursor: not-allowed; }
        .alert { padding: 15px; margin-bottom: 20px; border-radius: 5px; }
        .alert-success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .alert-info { background-color: #d1ecf1; color: #0c5460; border: 1px solid #bee5eb; }
        .vote-status { padding: 15px; background-color: #f8f9fa; border-radius: 5px; margin-bottom: 20px; text-align: center; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>🗳️ Voter Dashboard</h1>
            <div>
                Welcome, ${user.fullName} | <a href="/logout" style="color: #dc3545;">Logout</a>
            </div>
        </div>
        
        <div class="nav">
            <a href="/voter/dashboard">Vote</a>
            <a href="/voter/results">View Results</a>
        </div>
        
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        
        <div class="vote-status">
            <c:choose>
                <c:when test="${hasVoted}">
                    <h3 style="color: #28a745;">✅ You have successfully cast your vote!</h3>
                    <p>Thank you for participating in the election. Your vote has been recorded securely.</p>
                </c:when>
                <c:otherwise>
                    <h3 style="color: #007bff;">📊 Cast Your Vote</h3>
                    <p>Please select your preferred candidate below and cast your vote.</p>
                </c:otherwise>
            </c:choose>
        </div>
        
        <div class="candidates">
            <c:forEach var="candidate" items="${candidates}">
                <div class="candidate">
                    <h3>${candidate.name}</h3>
                    <p><strong>Party:</strong> ${candidate.party}</p>
                    <p>${candidate.description}</p>
                    <p><strong>Current Votes:</strong> ${candidate.voteCount}</p>
                    
                    <c:choose>
                        <c:when test="${hasVoted}">
                            <button class="btn btn-secondary" disabled>Already Voted</button>
                        </c:when>
                        <c:otherwise>
                            <form method="post" action="/voter/vote" style="display: inline;">
                                <input type="hidden" name="candidateId" value="${candidate.id}">
                                <button type="submit" class="btn btn-success" 
                                        onclick="return confirm('Are you sure you want to vote for ${candidate.name}? This action cannot be undone.')">
                                    Vote for ${candidate.name}
                                </button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:forEach>
        </div>
        
        <c:if test="${empty candidates}">
            <div class="alert alert-info">
                <h4>No candidates available</h4>
                <p>There are currently no active candidates in the election.</p>
            </div>
        </c:if>
    </div>
</body>
</html>