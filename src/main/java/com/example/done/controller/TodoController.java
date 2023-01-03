package com.example.done.controller;


import com.example.done.Signature;
import com.example.done.model.TodoItem;
import com.example.done.repo.TodoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/todo")
public class TodoController {

    @Autowired
    private TodoRepo todoRepo;

    @GetMapping
    public List<TodoItem> findAll(){
        return todoRepo.findAll();
    }

    @PostMapping
    public TodoItem save(@RequestBody TodoItem todoItem){
        return todoRepo.save(todoItem);
    }

    @PutMapping
    public TodoItem update(@RequestBody TodoItem todoItem){
        return todoRepo.save(todoItem);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id){
        todoRepo.deleteById(id);
    }

    @PutMapping(value = "/{id}/{userId}" )
    public TodoItem addTodoToUser(@PathVariable Long id, @PathVariable Integer userId, @RequestBody TodoItem todoItem){
        todoItem.setUserId(userId);
        return todoRepo.save(todoItem);
    }

    @GetMapping(value = "/{userId}")
    public List<TodoItem> findAllForUser(@PathVariable Integer userId){
        return todoRepo.findByUserId(userId);
    }


}

