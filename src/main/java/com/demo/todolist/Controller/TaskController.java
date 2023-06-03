package com.demo.todolist.Controller;

import com.demo.todolist.Entity.Task;
import com.demo.todolist.Service.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TaskController {
    @Autowired
    TaskServiceImpl impl;

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public ResponseEntity<?> getTasks(){
        List<Task> tasks = impl.getTasks();
        return ResponseEntity.ok(tasks);
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getTaskById(@PathVariable Long id){
        Task findTask = impl.getTaskById(id);
        return ResponseEntity.ok(findTask);
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.POST)
    public ResponseEntity<?> createTask(@RequestBody Task task){
        Task newTask = impl.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> editTask(@PathVariable Long id){
        Task task = impl.getTaskById(id);
        task.setComplete(true);
        impl.updateTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTask(@PathVariable Long id){
        impl.deleteTask(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity<?> test(){
        return ResponseEntity.ok().body("hola");
    }

}
