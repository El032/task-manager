package com.taskmanager.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private User assignee;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    protected Task() {}

    public Task(String name, String description) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank");
        }
        this.name = name;
        if(description == null || description.isBlank()) {
            throw new IllegalArgumentException("description cannot be null or blank ");
        }
        this.description = description;
        this.status = TaskStatus.NEW;
    }


    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    public User getAssignee() {
        return assignee;
    }
    public TaskStatus getStatus() {
        return status;
    }


    public void assignUser(User assignee) {
        if(assignee == null) {
            throw new IllegalArgumentException("user cannot be null");
        }
        this.assignee = assignee;
    }
    public void updateStatus(TaskStatus status) {
        if(status == null) {
            throw new IllegalArgumentException("status cannot be null");
        }
        this.status = status;
    }


}
