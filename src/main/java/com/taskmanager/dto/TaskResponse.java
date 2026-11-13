package com.taskmanager.dto;

import com.taskmanager.entity.TaskStatus;

public record TaskResponse(
        Long id,
        String name,
        String description,
        TaskStatus taskStatus,
        UserResponse assignee) {
}
