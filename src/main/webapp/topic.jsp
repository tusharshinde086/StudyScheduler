<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sms.model.Topic"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%
    Topic topic = (Topic) request.getAttribute("topic");
    boolean isEdit = (topic != null);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= isEdit ? "Edit Topic" : "Add New Topic" %></title>
    <style>
        body { 
            font-family: Arial, sans-serif; 
            background-color: #1e1e1e; /* dark background */
            color: #ffffff; /* light text */
            text-align: center; 
            padding-top: 50px; 
        }
        form { 
            display: inline-block; 
            margin-top: 50px; 
            text-align: left; 
        }
        label { 
            display: block; 
            margin: 10px 0 5px; 
        }
        input { 
            padding: 5px; 
            width: 300px; 
            border: 1px solid #555; 
            background-color: #333; 
            color: #fff; 
        }
        input:focus { 
            outline: none; 
            border-color: #777; 
        }
        button { 
            margin-top: 10px; 
            padding: 5px 10px; 
            background-color: #555; 
            color: white; 
            border: none; 
            border-radius: 5px; 
            cursor: pointer; 
        }
        button:hover { 
            background-color: #777; 
        }
        a { 
            margin-left: 10px; 
            text-decoration: none; 
            color: #4CAF50; 
        }
        a:hover {
            color: #80e080;
        }
    </style>
</head>
<body>
    <h1><%= isEdit ? "Edit Topic" : "Add New Topic" %></h1>
    <form action="SchedulerServlet" method="post">
        <% if (isEdit) { %>
            <input type="hidden" name="id" value="<%= topic.getId() %>">
        <% } %>

        <label>Subject:</label>
        <input type="text" name="subject" value="<%= isEdit ? topic.getSubject() : "" %>" required>

        <label>Topic Name:</label>
        <input type="text" name="topic_name" value="<%= isEdit ? topic.getTopicName() : "" %>" required>

        <label>Estimated Hours:</label>
        <input type="number" name="estimated_hours" value="<%= isEdit ? topic.getEstimatedHours() : 1 %>" min="1" required>

        <label>Due Date:</label>
        <input type="date" name="due_date" value="<%= isEdit && topic.getDueDate() != null ? topic.getDueDate().format(formatter) : "" %>">

        <br>
        <button type="submit"><%= isEdit ? "Update Topic" : "Add Topic" %></button>
        <a href="SchedulerServlet">Cancel</a>
    </form>
</body>
</html>
