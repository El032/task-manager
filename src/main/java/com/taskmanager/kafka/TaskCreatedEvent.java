package com.taskmanager.kafka;

public class TaskCreatedEvent {

    private Long taskId;
    private String name;
    private String description;
    private Long assigneeId;

    public TaskCreatedEvent() {
    }

    public TaskCreatedEvent(Long taskId, String name, String description, Long assigneeId) {
        this.taskId = taskId;
        this.name = name;
        this.description = description;
        this.assigneeId = assigneeId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getAssigneeId() {
        return assigneeId;
    }
}