package com.example.done.controller;


import com.example.done.model.TodoItem;
import java.util.concurrent.TimeUnit;

import com.example.done.queue.Receiver;
import com.example.done.repo.TodoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.example.done.queue.MessagingRabbitmqApplication;
import org.springframework.boot.CommandLineRunner;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/todo")
public class TodoController implements CommandLineRunner {

    @Autowired
    private TodoRepo todoRepo;

    @GetMapping
    public List<TodoItem> findAll(){
        return todoRepo.findAll();
    }

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public TodoController(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(MessagingRabbitmqApplication.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }


    @PostMapping
    public TodoItem save(@RequestBody TodoItem todoItem) throws Exception {
        run();
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

