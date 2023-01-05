package com.example.done.service;


import com.example.done.model.TodoItem;
import com.example.done.queue.MessagingRabbitmqApplication;
import com.example.done.queue.Receiver;
import com.example.done.repo.TodoRepo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TodoService implements CommandLineRunner {


    @Autowired
    private TodoRepo todoRepo;


    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public List<TodoItem> findAll() {
        return todoRepo.findAll();
    }

    public TodoService(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(MessagingRabbitmqApplication.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }


    public TodoItem save(TodoItem todoItem) throws Exception {
        run();
        return todoRepo.save(todoItem);
    }


    public TodoItem update(TodoItem todoItem){
        return todoRepo.save(todoItem);
    }

    public void deleteById(Long id){
        todoRepo.deleteById(id);
    }

    public TodoItem addTodoToUser(Long id, Integer userId, TodoItem todoItem){
        todoItem.setUserId(userId);
        return todoRepo.save(todoItem);
    }

    public List<TodoItem> findByUserId(Integer userId){
        return todoRepo.findByUserId(userId);
    }

}
