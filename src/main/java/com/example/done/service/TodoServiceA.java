package com.example.done.service;

import com.example.done.model.TodoItem;
import com.example.done.model.TodoItem;
import com.example.done.queue.MessagingRabbitmqApplication;
import com.example.done.queue.Receiver;
import com.example.done.repo.TodoRepo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Qualifier("serviceA")
public class TodoServiceA extends TodoService{


    public TodoServiceA(Receiver receiver, RabbitTemplate rabbitTemplate) {
        super(receiver, rabbitTemplate);
    }

    public TodoItem save(TodoItem TodoItem) throws Exception {
        sendMessage();
        System.out.println("Service A!!");
        return todoRepo.save(TodoItem);
    }

}
