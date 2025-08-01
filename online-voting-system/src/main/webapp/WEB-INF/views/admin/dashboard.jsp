<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard - Election System</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f4f4f4; }
        .container { max-width: 1200px; margin: 0 auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; padding-bottom: 20px; border-bottom: 1px solid #eee; }
        .nav { margin-bottom: 30px; }
        .nav a { display: inline-block; padding: 10px 20px; margin-right: 10px; background-color: #007bff; color: white; text-decoration: none; border-radius: 5px; }
        .nav a:hover { background-color: #0056b3; }
        .stats { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 20px; margin-bottom: 30px; }
        .stat-card { background-color: #f8f9fa; padding: 20px; border-radius: 10px; text-align: center; }
        .stat-number { font-size: 36px; font-weight: bold; color: #007bff; }
        .stat-label { color: #666; margin-top: 5px; }
        .section { margin-bottom: 40px; }
        .section h3 { border-bottom: 2px solid #007bff; padding-bottom: 10px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #f8f9fa; font-weight: bold; }
        .btn { padding: 8px 16px; border: none; border-radius: 4px; cursor: pointer; text-decoration: none; display: inline-block; }
        .btn-primary { background-color: #007bff; color: white; }
        .btn-success { background-color: #28a745; color: white; }
        .btn-danger { background-color: #dc3545; color: white; }
        .btn:hover { opacity: 0.8; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>🛠️ Admin Dashboard</h1>
            <div>
                Welcome, Admin | <a href="/logout" style="color: #dc3545;">Logout</a>
            </div>
        </div>
        
        <div class="nav">
            <a href="/admin/dashboard">Dashboard</a>
            <a href="/admin/candidates">Manage Candidates</a>
            <a href="/admin/results">View Results</a>
        </div>
        
        <div class="stats">
            <div class="stat-card">
                <div class="stat-number">${candidates.size()}</div>
                <div class="stat-label">Total Candidates</div>
            </div>
            <div class="stat-card">
                <div class="stat-number">${users.size()}</div>
                <div class="stat-label">Registered Voters</div>
            </div>
            <div class="stat-card">
                <div class="stat-number">${totalVotes}</div>
                <div class="stat-label">Votes Cast</div>
            </div>
            <div class="stat-card">
                <div class="stat-number">
                    <c:set var="votedUsers" value="0" />
                    <c:forEach var="user" items="${users}">
                        <c:if test="${user.hasVoted}">
                            <c:set var="votedUsers" value="${votedUsers + 1}" />
                        </c:if>
                    </c:forEach>
                    ${users.size() > 0 ? String.format("%.1f", (votedUsers * 100.0 / users.size())) : 0}%
                </div>
                <div class="stat-label">Turnout Rate</div>
            </div>
        </div>
        
        <div class="section">
            <h3>Recent Candidates</h3>
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Party</th>
                        <th>Votes</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="candidate" items="${candidates}" end="4">
                        <tr>
                            <td>${candidate.name}</td>
                            <td>${candidate.party}</td>
                            <td>${candidate.voteCount}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${candidate.active}">
                                        <span style="color: #28a745;">Active</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: #dc3545;">Inactive</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <a href="/admin/candidates/edit/${candidate.id}" class="btn btn-primary">Edit</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div style="margin-top: 15px;">
                <a href="/admin/candidates" class="btn btn-success">Manage All Candidates</a>
            </div>
        </div>
        
        <div class="section">
            <h3>Recent Voter Registrations</h3>
            <table>
                <thead>
                    <tr>
                        <th>Full Name</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Registration Date</th>
                        <th>Voted</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${users}" end="4">
                        <c:if test="${user.role == 'VOTER'}">
                            <tr>
                                <td>${user.fullName}</td>
                                <td>${user.username}</td>
                                <td>${user.email}</td>
                                <td>${user.registrationDate.toLocalDate()}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${user.hasVoted}">
                                            <span style="color: #28a745;">✓ Yes</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span style="color: #dc3545;">✗ No</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>