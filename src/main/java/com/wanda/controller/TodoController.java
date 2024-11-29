package com.wanda.controller;

import com.wanda.entity.Task;
import com.wanda.service.TaskService;
import com.wanda.utils.exceptions.response.SuccessResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todo")
public class TodoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    private TaskService taskService;

    public TodoController(TaskService taskService){
        this.taskService = taskService;
    }



    @PostMapping
    public ResponseEntity createTask(@RequestBody Task payloadTask){
        logger.info(payloadTask.toString());

        var task = this.taskService.createTask(payloadTask.getTitle());

        var sucess = new SuccessResponse(
                true,
                "Successfully Created the Task",
                task
        );

        return new ResponseEntity<>(sucess, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getAllTasks(Model model){
       var tasks = this.taskService.getAllTask();

        var success = new SuccessResponse<Task>(
                true,
                "Successfully fetched all tasks",
                tasks
        );

        return new ResponseEntity<>(success, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getTaskById(@PathVariable String id){
        var task = this.taskService.getTask(id);

        var success = new SuccessResponse<Task>(
                true,
                "Successfully fetched the task",
                task
        );

        return new ResponseEntity<>(success, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity updateTask(@RequestParam String id,
                                     @RequestBody Task t){
        var task = this.taskService.updateTask(id, t.getTitle(), t.getCompleted());

        var success = new SuccessResponse<Task>(
                true,
                "Successfully fetched the task",
                task
        );

        return new ResponseEntity<>(success, HttpStatus.OK);


    }

    @DeleteMapping
    public ResponseEntity deleteTask(@RequestParam String id){
        var task = this.taskService.deleteTask(id);

        var success = new SuccessResponse<Task>(
                true,
                "Successfully deleted the task",
                task
        );

        return new ResponseEntity<>(success, HttpStatus.OK);
    }

}
