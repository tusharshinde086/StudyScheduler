<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.sms.model.Topic"%>
<%@ page import="java.util.List"%>
<%
    List<Topic> topics = (List<Topic>) request.getAttribute("topics");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Study/Revision Topics</title>
    <style>
        body { 
            font-family: Arial; 
            background-color: #1e1e1e; /* dark background */
            color: #ffffff; /* light text */
            text-align: center; 
            padding-top: 50px; 
        }
        table { 
            margin: auto; 
            border-collapse: collapse; 
            width: 85%; 
        }
        th, td { 
            padding: 10px; 
            border: 1px solid #444; /* darker border */
            text-align: center; 
        }
        th { 
            background-color: #333; 
        }
        a { 
            margin: 0 5px; 
            text-decoration: none; 
            padding: 5px 10px; 
            background-color: #555; 
            color: white; 
            border-radius: 5px; 
        }
        a:hover { 
            background-color: #777; 
        }
    </style>
</head>
<body>
    <h1>Study/Revision Topics</h1>
    <a href="SchedulerServlet?action=ADD">Add New Topic</a>
    <a href="SchedulerServlet?action=SCHEDULE">Generate Weekly Schedule</a>
    <br><br>
    <table>
        <tr>
            <th>ID</th>
            <th>Subject</th>
            <th>Topic</th>
            <th>Hours</th>
            <th>Due Date</th>
            <th>Completed</th>
            <th>Actions</th>
        </tr>
        <%
            if (topics != null && !topics.isEmpty()) {
                for (Topic t : topics) {
        %>
        <tr>
            <td><%= t.getId() %></td>
            <td><%= t.getSubject() %></td>
            <td><%= t.getTopicName() %></td>
            <td><%= t.getEstimatedHours() %></td>
            <td><%= t.getDueDate() != null ? t.getDueDate() : "-" %></td>
            <td><%= t.isCompleted() ? "Yes" : "No" %></td>
            <td>
                <a href="SchedulerServlet?action=EDIT&id=<%= t.getId() %>">Edit</a>
                <a href="SchedulerServlet?action=DELETE&id=<%= t.getId() %>" onclick="return confirm('Are you sure?');">Delete</a>
                <a href="SchedulerServlet?action=MARK&id=<%= t.getId() %>&completed=<%= !t.isCompleted() %>">
                    Mark <%= t.isCompleted() ? "Not Completed" : "Completed" %>
                </a>
            </td>
        </tr>
        <%      }
            } else { %>
        <tr>
            <td colspan="7">No topics found. Click "Add New Topic" to create one.</td>
        </tr>
        <% } %>
    </table>
</body>
</html>
