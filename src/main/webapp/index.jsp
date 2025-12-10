<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Study/Revision Scheduler</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #1e1e1e; /* dark background */
            color: #ffffff; /* light text for contrast */
            text-align: center;
            padding-top: 100px;
        }
        a {
            text-decoration: none;
            padding: 10px 20px;
            background-color: #333333; /* darker button */
            color: white;
            border-radius: 5px;
        }
        a:hover {
            background-color: #555555;
        }
    </style>
</head>
<body>
    <h1>Welcome to Study/Revision Scheduler</h1>
    <p>Click below to view and manage your study topics.</p>
    <a href="${pageContext.request.contextPath}/SchedulerServlet">Go to Topics</a>
</body>
</html>
