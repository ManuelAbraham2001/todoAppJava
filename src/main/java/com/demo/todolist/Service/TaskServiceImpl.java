package com.demo.todolist.Service;

import com.demo.todolist.Entity.Task;
import com.demo.todolist.Repository.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{

    @Autowired
    TaskRepo repo;

    @Override
    public List<Task> getTasks() {
        return (List<Task>) repo.findAll();
    }

    @Override
    public Task getTaskById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public Task createTask(Task task) {
        return repo.save(task);
    }

    @Override
    public void updateTask(Task task) {
        repo.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        repo.deleteById(id);
    }
}
