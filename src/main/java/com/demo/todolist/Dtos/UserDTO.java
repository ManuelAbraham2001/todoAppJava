package com.demo.todolist.Dtos;

import com.demo.todolist.Entity.Task;

import java.util.List;

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private List<Task> taskList;

    public UserDTO(Long id, String username, String email, List<Task> taskList) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.taskList = taskList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }
}
