package com.taskmanager.kafka;

public class TaskAssignedEvent {

    private Long taskId;
    private Long assigneeId;

    public TaskAssignedEvent() {
    }

    public TaskAssignedEvent(Long taskId, Long assigneeId) {
        this.taskId = taskId;
        this.assigneeId = assigneeId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public Long getAssigneeId() {
        return assigneeId;
    }
}