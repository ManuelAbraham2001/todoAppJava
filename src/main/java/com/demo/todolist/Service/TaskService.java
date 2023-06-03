package com.demo.todolist.Service;

import com.demo.todolist.Entity.Task;

import java.util.List;

public interface TaskService {
    List<Task> getTasks();
    Task getTaskById(Long id);
    Task createTask(Task task);
    void updateTask(Task task);
    void deleteTask(Long id);

}
