package com.taskmanager.dto;

public record TaskCreateRequest(
        String name,
        String description) {}
