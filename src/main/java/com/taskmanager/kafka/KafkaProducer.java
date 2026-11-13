package com.taskmanager.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private static final String TOPIC = "task-created";
    private static final String TASK_ASSIGNED_TOPIC = "task-assigned";

    private final KafkaTemplate<String, TaskCreatedEvent> kafkaTemplate;
    private final KafkaTemplate<String, TaskAssignedEvent> taskAssignedTemplate;

    public KafkaProducer(KafkaTemplate<String, TaskCreatedEvent> kafkaTemplate,
                         KafkaTemplate<String, TaskAssignedEvent> taskAssignedTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.taskAssignedTemplate = taskAssignedTemplate;
    }

    public void sendTaskCreatedEvent(TaskCreatedEvent event) {
        kafkaTemplate.send(
                TOPIC,
                event.getTaskId().toString(),
                event);
    }

    public void sendTaskAssignedEvent(TaskAssignedEvent event) {
        taskAssignedTemplate.send(
                TASK_ASSIGNED_TOPIC,
                event.getTaskId().toString(),
                event
        );
    }
}