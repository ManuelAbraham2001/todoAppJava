package com.demo.todolist.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tarea")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarea")
    private Long id;
    @Column(name = "titulo")
    private String title;
    @Column(name = "estado")
    private Boolean isComplete;
    @Column(name = "usuario_id")
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Task(){};

    public Task(Long id, String title, Boolean isComplete, Long userId) {
        this.id = id;
        this.title = title;
        this.isComplete = isComplete;
        this.userId = userId;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
