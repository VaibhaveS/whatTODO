package com.example.done.batch;


import com.example.done.model.TodoItem;
import org.springframework.batch.item.ItemProcessor;

public class TodoProcessor implements ItemProcessor<TodoItem, TodoItem> {

    @Override
    public TodoItem process(TodoItem todoItem) throws Exception {
        return todoItem;
    }
}