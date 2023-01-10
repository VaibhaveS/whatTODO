package com.example.done.service;

import com.example.done.model.TodoItem;
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

    public List<TodoItem> findAll() {
        return todoRepo.findAll();
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
        return todoRepo.save(TodoItem);
    }


    public TodoItem update(TodoItem TodoItem){
        return todoRepo.save(TodoItem);
    }

    public void deleteById(Long id){
        todoRepo.deleteById(id);
    }

    public TodoItem addTodoToUser(Long id, Integer userId){
        TodoItem todoItem = todoRepo.findTodoItemById(id);
        todoItem.setUserId(userId);
        return todoRepo.save(todoItem);
    }

    public List<TodoItem> findByUserId(Integer userId){
        return todoRepo.findByUserId(userId);
    }
}
