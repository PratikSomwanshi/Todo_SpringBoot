package com.wanda.service;

import com.wanda.entity.Task;
import com.wanda.repository.TaskRepository;
import com.wanda.utils.exceptions.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public Task createTask(String title) {

        if(title.isEmpty()) throw new CustomException("title is empty", HttpStatus.BAD_REQUEST, "TODO_CREATED");

        Task task = new Task();
        task.setTitle(title);
        task.setCompleted(false);

        this.taskRepository.save(task);

        return task;
    }

    public List<Task> getAllTask() {
        var tasks = this.taskRepository.findAll();


        return tasks;
    }

    public Task getTask(String id) {

        var task = this.taskRepository.findById(id);

        if(task.isPresent()) return task.get();

        throw new CustomException("Task not found", HttpStatus.NOT_FOUND, "TASK_NOT_FOUND");
    }

    public Task updateTask(String id, String title, Boolean completed) {
        var task = this.taskRepository.findById(id);

        if(task.isEmpty()) throw new CustomException("Task not found", HttpStatus.NOT_FOUND, "TASK_NOT_FOUND");

        if(title != null) task.get().setTitle(title);

        if(completed != null) task.get().setCompleted(completed);


        return this.taskRepository.save(task.get());

    }

    public Task deleteTask(String id) {
        var task = this.getTask(id);

        this.taskRepository.delete(task);

        return task;
    }
}
