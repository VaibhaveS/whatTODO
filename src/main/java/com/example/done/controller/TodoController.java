package com.example.done.controller;

import com.example.done.model.TodoItem;
import com.example.done.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/todo")
public class TodoController {


    @Autowired
    private TodoService todoService;

    @GetMapping
    public List<TodoItem> findAll(){
        return todoService.findAll();
    }

    @PostMapping
    public TodoItem save(@RequestBody TodoItem todoItem) throws Exception {
        return todoService.save(todoItem);
    }

    @PutMapping
    public TodoItem update(@RequestBody TodoItem todoItem) throws Exception {
        return todoService.save(todoItem);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id){
        todoService.deleteById(id);
    }

    @PutMapping(value = "/{id}/{userId}" )
    public TodoItem addTodoToUser(@PathVariable Long id, @PathVariable Integer userId, @RequestBody TodoItem todoItem) throws Exception {
        return todoService.save(todoItem);
    }

    @GetMapping(value = "/{userId}")
    public List<TodoItem> findAllForUser(@PathVariable Integer userId){
        return todoService.findByUserId(userId);
    }


}

