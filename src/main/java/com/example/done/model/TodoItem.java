package com.example.done.model;




import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "todo_item")
public class TodoItem {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private boolean is_done;
    private Integer user_id;

    public TodoItem() {}
    public TodoItem(Long id, String name, boolean isDone, Integer userId) {
        this.id = id;
        this.name = name;
        this.is_done = isDone;
        this.user_id = userId;
    }

    public Integer getUserId() {
        return user_id;
    }

    public void setUserId(Integer userId) {
        this.user_id = userId;
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
        return is_done;
    }

    public void setDone(boolean done) {
        is_done = done;
    }
}
