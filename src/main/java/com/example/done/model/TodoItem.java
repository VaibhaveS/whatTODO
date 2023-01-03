package com.example.done.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class TodoItem {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private boolean isDone;

    private Integer userId;

    public TodoItem() {}
    public TodoItem(Long id, String name, boolean isDone, Integer userId) {
        this.id = id;
        this.name = name;
        this.isDone = isDone;
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
