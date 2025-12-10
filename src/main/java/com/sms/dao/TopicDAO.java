package com.sms.dao;

import com.sms.model.Topic;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TopicDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/studyscheduler";
    private String jdbcUsername = "root";
    private String jdbcPassword = "743426";

    private static final String INSERT_SQL = "INSERT INTO topics(subject, topic_name, completed, estimated_hours, due_date) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM topics";
    private static final String SELECT_BY_ID = "SELECT * FROM topics WHERE id=?";
    private static final String UPDATE_SQL = "UPDATE topics SET subject=?, topic_name=?, completed=?, estimated_hours=?, due_date=? WHERE id=?";
    private static final String DELETE_SQL = "DELETE FROM topics WHERE id=?";
    private static final String MARK_SQL = "UPDATE topics SET completed=? WHERE id=?";
    private static final String SELECT_PENDING = "SELECT * FROM topics WHERE completed=false";

    protected Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (Exception e) { e.printStackTrace(); }
        return con;
    }

    public void insertTopic(Topic t) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {

            ps.setString(1, t.getSubject());
            ps.setString(2, t.getTopicName());
            ps.setBoolean(3, t.isCompleted());
            ps.setInt(4, t.getEstimatedHours());
            ps.setDate(5, t.getDueDate() != null ? Date.valueOf(t.getDueDate()) : null);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<Topic> getAllTopics() {
        List<Topic> list = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Topic(
                        rs.getInt("id"),
                        rs.getString("subject"),
                        rs.getString("topic_name"),
                        rs.getBoolean("completed"),
                        rs.getInt("estimated_hours"),
                        rs.getDate("due_date") != null ? rs.getDate("due_date").toLocalDate() : null
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public Topic getTopic(int id) {
        Topic t = null;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                t = new Topic(
                        rs.getInt("id"),
                        rs.getString("subject"),
                        rs.getString("topic_name"),
                        rs.getBoolean("completed"),
                        rs.getInt("estimated_hours"),
                        rs.getDate("due_date") != null ? rs.getDate("due_date").toLocalDate() : null
                );
            }
        } catch (Exception e) { e.printStackTrace(); }
        return t;
    }

    public void updateTopic(Topic t) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_SQL)) {

            ps.setString(1, t.getSubject());
            ps.setString(2, t.getTopicName());
            ps.setBoolean(3, t.isCompleted());
            ps.setInt(4, t.getEstimatedHours());
            ps.setDate(5, t.getDueDate() != null ? Date.valueOf(t.getDueDate()) : null);
            ps.setInt(6, t.getId());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void deleteTopic(int id) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_SQL)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void markCompleted(int id, boolean completed) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(MARK_SQL)) {
            ps.setBoolean(1, completed);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<Topic> getPendingTopics() {
        List<Topic> list = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_PENDING)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Topic(
                        rs.getInt("id"),
                        rs.getString("subject"),
                        rs.getString("topic_name"),
                        rs.getBoolean("completed"),
                        rs.getInt("estimated_hours"),
                        rs.getDate("due_date") != null ? rs.getDate("due_date").toLocalDate() : null
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
