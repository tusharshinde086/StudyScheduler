package com.sms.controller;

import com.sms.dao.TopicDAO;
import com.sms.model.Topic;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@WebServlet("/SchedulerServlet")
public class SchedulerServlet extends HttpServlet {

    private TopicDAO dao;

    @Override
    public void init() {
        dao = new TopicDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "LIST";

        switch (action) {
            case "LIST":
                // Show all topics
                req.setAttribute("topics", dao.getAllTopics());
                req.getRequestDispatcher("list-topics.jsp").forward(req, res);
                break;

            case "ADD":
                // Forward to topic.jsp for adding
                req.getRequestDispatcher("topic.jsp").forward(req, res);
                break;

            case "EDIT":
                // Forward to topic.jsp for editing
                try {
                    int editId = Integer.parseInt(req.getParameter("id"));
                    Topic topic = dao.getTopic(editId);
                    if (topic != null) {
                        req.setAttribute("topic", topic);
                        req.getRequestDispatcher("topic.jsp").forward(req, res);
                    } else {
                        res.sendRedirect("SchedulerServlet");
                    }
                } catch (Exception e) {
                    res.sendRedirect("SchedulerServlet");
                }
                break;

            case "DELETE":
                try {
                    int deleteId = Integer.parseInt(req.getParameter("id"));
                    dao.deleteTopic(deleteId);
                } catch (Exception e) { }
                res.sendRedirect("SchedulerServlet");
                break;

            case "MARK":
                try {
                    int markId = Integer.parseInt(req.getParameter("id"));
                    boolean completed = Boolean.parseBoolean(req.getParameter("completed"));
                    dao.markCompleted(markId, completed);
                } catch (Exception e) { }
                res.sendRedirect("SchedulerServlet");
                break;

            case "SCHEDULE":
                int dailyHours = 4; // default hours per day
                List<Topic> pendingTopics = dao.getPendingTopics();
                Map<String, List<Topic>> schedule = generateWeeklySchedule(pendingTopics, dailyHours);
                req.setAttribute("schedule", schedule);
                req.getRequestDispatcher("weekly-schedule.jsp").forward(req, res);
                break;

            default:
                res.sendRedirect("SchedulerServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String id = req.getParameter("id");
        String subject = req.getParameter("subject");
        String topicName = req.getParameter("topic_name");
        int estimatedHours = 1;

        try {
            estimatedHours = Integer.parseInt(req.getParameter("estimated_hours"));
        } catch (NumberFormatException e) { }

        LocalDate dueDate = null;
        String dueDateStr = req.getParameter("due_date");
        if (dueDateStr != null && !dueDateStr.isEmpty()) {
            dueDate = LocalDate.parse(dueDateStr);
        }

        Topic topic = new Topic(
                (id != null && !id.isEmpty()) ? Integer.parseInt(id) : 0,
                subject,
                topicName,
                false,
                estimatedHours,
                dueDate
        );

        if (id == null || id.isEmpty()) {
            dao.insertTopic(topic);
        } else {
            dao.updateTopic(topic);
        }

        res.sendRedirect("SchedulerServlet");
    }

    private Map<String, List<Topic>> generateWeeklySchedule(List<Topic> topics, int dailyHours) {
        Map<String, List<Topic>> schedule = new LinkedHashMap<>();
        DayOfWeek[] days = DayOfWeek.values();
        int dayIndex = 0;
        int hoursLeft = dailyHours;
        List<Topic> dayTopics = new ArrayList<>();

        for (Topic t : topics) {
            if (t.getEstimatedHours() <= hoursLeft) {
                dayTopics.add(t);
                hoursLeft -= t.getEstimatedHours();
            } else {
                schedule.put(days[dayIndex].toString(), new ArrayList<>(dayTopics));
                dayIndex = (dayIndex + 1) % 7;
                dayTopics.clear();
                dayTopics.add(t);
                hoursLeft = dailyHours - t.getEstimatedHours();
            }
        }

        if (!dayTopics.isEmpty()) {
            schedule.put(days[dayIndex].toString(), new ArrayList<>(dayTopics));
        }

        return schedule;
    }
}
