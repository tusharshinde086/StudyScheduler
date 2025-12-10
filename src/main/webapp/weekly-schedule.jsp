<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.sms.model.Topic"%>
<%@ page import="java.util.List,java.util.Map"%>
<%
    Map<String, List<Topic>> schedule = (Map<String, List<Topic>>) request.getAttribute("schedule");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Weekly Schedule</title>
<style>
body { 
    font-family: Arial; 
    background-color: #1e1e1e; /* dark background */
    color: #ffffff; /* light text */
    text-align: center; 
}
table { 
    margin: auto; 
    border-collapse: collapse; 
    width: 80%; 
}
th, td { 
    padding: 10px; 
    border: 1px solid #555; 
    vertical-align: top; 
    text-align: left;
}
th { 
    background-color: #333; 
}
ul { 
    list-style: none; 
    padding: 0; 
}
a { 
    margin: 0 5px; 
    text-decoration: none; 
    padding: 5px 10px; 
    background-color: #4CAF50; 
    color: white; 
    border-radius: 5px; 
}
a:hover { 
    background-color: #45a049; 
}
</style>
</head>
<body>
<h1>Weekly Study Schedule</h1>
<a href="SchedulerServlet">Back to Topics</a><br><br>
<table>
<tr>
<% if(schedule != null) { for(String day : schedule.keySet()) { %>
<th><%= day %></th>
<% } } %>
</tr>
<tr>
<% if(schedule != null) { for(List<Topic> dayTopics : schedule.values()) { %>
<td>
<ul>
<% for(Topic t : dayTopics) { %>
<li><b><%= t.getSubject() %>:</b> <%= t.getTopicName() %> (<%= t.getEstimatedHours() %> hrs)</li>
<% } %>
</ul>
</td>
<% } } %>
</tr>
</table>
</body>
</html>
