package com.taskmanager.service;

import com.taskmanager.entity.Task;
import com.taskmanager.entity.TaskStatus;
import com.taskmanager.entity.User;
import com.taskmanager.kafka.KafkaProducer;
import com.taskmanager.kafka.TaskAssignedEvent;
import com.taskmanager.kafka.TaskCreatedEvent;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;


@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final KafkaProducer kafkaProducer;

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository,
                       KafkaProducer kafkaProducer) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.kafkaProducer = kafkaProducer;
    }
    public Task createTask(String name, String description){
        if(name == null || name.isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is null or blank");
        }
        if(description == null || description.isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Description is null or blank");
        }
        Task task = new Task(name,description);

        Task savedTask = taskRepository.save(task);

        TaskCreatedEvent event = new TaskCreatedEvent(
                savedTask.getId(),
                name,
                description,
                null
        );
        kafkaProducer.sendTaskCreatedEvent(event);


        return savedTask;
    }
    public Task updateStatusTask(Long taskId,TaskStatus newTaskStatus){
        if (newTaskStatus == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New task status is null");
        }

        Task task = taskRepository.findById(taskId).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"task not found"));


        task.updateStatus(newTaskStatus);

        return taskRepository.save(task);
    }

    public Task getTaskById(Long taskId){
        return taskRepository.findById(taskId).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"task not found"));
    }

    public Page<Task> getAllTasks(Pageable pageable){
        return taskRepository.findAll(pageable);
    }

    public Task assignTask(Long taskId, Long userId) {
        if(userId == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId is null");
        }
        Task task = taskRepository.findById(taskId).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"task not found"));

        User user = userRepository.findById(userId).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));

        task.assignUser(user);

        Task savedTask = taskRepository.save(task);

        TaskAssignedEvent event = new TaskAssignedEvent(
                savedTask.getId(),
                user.getId()
        );

        kafkaProducer.sendTaskAssignedEvent(event);
        return savedTask;
    }
}
