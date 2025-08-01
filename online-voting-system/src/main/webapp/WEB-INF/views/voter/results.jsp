<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Election Results - Election System</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f4f4f4; }
        .container { max-width: 1000px; margin: 0 auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        .header { text-align: center; margin-bottom: 30px; }
        .nav { margin-bottom: 30px; }
        .nav a { display: inline-block; padding: 10px 20px; margin-right: 10px; background-color: #007bff; color: white; text-decoration: none; border-radius: 5px; }
        .nav a:hover { background-color: #0056b3; }
        .results { display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 20px; }
        .result-card { border: 1px solid #ddd; border-radius: 10px; padding: 20px; text-align: center; }
        .result-card h3 { color: #333; margin-bottom: 10px; }
        .vote-bar { background-color: #e9ecef; height: 30px; border-radius: 15px; margin: 15px 0; overflow: hidden; }
        .vote-fill { background-color: #007bff; height: 100%; transition: width 0.3s ease; }
        .vote-count { font-size: 24px; font-weight: bold; color: #007bff; }
        .vote-percentage { font-size: 18px; color: #666; }
        .total-votes { background-color: #f8f9fa; padding: 20px; border-radius: 10px; text-align: center; margin-bottom: 30px; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>📊 Election Results</h1>
            <p>Real-time election results</p>
        </div>
        
        <div class="nav">
            <a href="/voter/dashboard">← Back to Dashboard</a>
            <a href="/logout">Logout</a>
        </div>
        
        <div class="total-votes">
            <h2>Total Votes Cast: ${totalVotes}</h2>
        </div>
        
        <div class="results">
            <c:forEach var="candidate" items="${candidates}">
                <c:set var="percentage" value="${totalVotes > 0 ? (candidate.voteCount * 100.0 / totalVotes) : 0}" />
                <div class="result-card">
                    <h3>${candidate.name}</h3>
                    <p><strong>Party:</strong> ${candidate.party}</p>
                    
                    <div class="vote-count">${candidate.voteCount} votes</div>
                    <div class="vote-percentage">${percentage}%</div>
                    
                    <div class="vote-bar">
                        <div class="vote-fill" style="width: ${percentage}%"></div>
                    </div>
                </div>
            </c:forEach>
        </div>
        
        <c:if test="${empty candidates}">
            <div style="text-align: center; padding: 50px; color: #666;">
                <h3>No candidates available</h3>
                <p>There are currently no candidates in the election.</p>
            </div>
        </c:if>
        
        <div style="text-align: center; margin-top: 30px; padding-top: 20px; border-top: 1px solid #eee; color: #666;">
            <p>Results are updated in real-time as votes are cast.</p>
        </div>
    </div>
</body>
</html>