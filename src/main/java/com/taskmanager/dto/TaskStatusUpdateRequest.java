package com.taskmanager.dto;

import com.taskmanager.entity.TaskStatus;



public record TaskStatusUpdateRequest(TaskStatus taskStatus)  {
}
