package com.example.done.service;

import com.example.done.shards.TodoContextHolder;

import com.example.done.model.TodoItem;
import com.example.done.queue.MessagingRabbitmqApplication;
import com.example.done.queue.Receiver;
import com.example.done.repo.TodoRepo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TodoService {
    @Autowired
    protected TodoRepo todoRepo;
    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;


    public void setDB(Long TodoId) {
        System.out.println("setting context");
        if(TodoId%3 == 0) {
            System.out.println("shard 1");
            TodoContextHolder.setTodoType("shard_1");
        } else if (TodoId%3 == 1){
            System.out.println("shard 2");
            TodoContextHolder.setTodoType("shard_2");
        } else {
            System.out.println("shard 3");
            TodoContextHolder.setTodoType("shard_3");
        }
    }

    public List<TodoItem> findAll() {
        TodoContextHolder.setTodoType("shard_1");
        List<TodoItem> items = todoRepo.findAll();
        TodoContextHolder.setTodoType("shard_2");
        items.addAll(todoRepo.findAll());
        TodoContextHolder.setTodoType("shard_3");
        items.addAll(todoRepo.findAll());
        return items;
    }

    public TodoService(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String... args) throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(MessagingRabbitmqApplication.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
    }


    public TodoItem save(TodoItem TodoItem) throws Exception {
        sendMessage();
        System.out.println("resolving....");
        setDB(TodoItem.getId());
        return todoRepo.save(TodoItem);
    }


    public TodoItem update(TodoItem TodoItem){
        setDB(TodoItem.getId());
        return todoRepo.save(TodoItem);
    }

    public void deleteById(Long id){
        setDB(id);
        todoRepo.deleteById(id);
    }

    public TodoItem addTodoToUser(Long id, Integer userId){
        setDB(id);
        TodoItem todoItem = todoRepo.findTodoItemById(id);
        todoItem.setUserId(userId);
        return todoRepo.save(todoItem);
    }

    public List<TodoItem> findByUserId(Integer userId){
        TodoContextHolder.setTodoType("shard_1");
        List<TodoItem> items = todoRepo.findByUser_id(userId);
        TodoContextHolder.setTodoType("shard_2");
        items.addAll(todoRepo.findByUser_id(userId));
        TodoContextHolder.setTodoType("shard_3");
        items.addAll(todoRepo.findByUser_id(userId));
        return items;
    }

    public void addShard(String url) {
        System.out.println("Added a new shard..");
    }

}