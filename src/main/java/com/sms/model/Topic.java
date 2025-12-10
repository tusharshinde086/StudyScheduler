package com.sms.model;

import java.time.LocalDate;

public class Topic {
    private int id;
    private String subject;
    private String topicName;
    private boolean completed;
    private int estimatedHours;
    private LocalDate dueDate;

    public Topic() {}

    public Topic(int id, String subject, String topicName, boolean completed, int estimatedHours, LocalDate dueDate) {
        this.id = id;
        this.subject = subject;
        this.topicName = topicName;
        this.completed = completed;
        this.estimatedHours = estimatedHours;
        this.dueDate = dueDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getTopicName() { return topicName; }
    public void setTopicName(String topicName) { this.topicName = topicName; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public int getEstimatedHours() { return estimatedHours; }
    public void setEstimatedHours(int estimatedHours) { this.estimatedHours = estimatedHours; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
}
