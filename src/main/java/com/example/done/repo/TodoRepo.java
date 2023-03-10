package com.example.done.repo;

import com.example.done.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TodoRepo extends JpaRepository<TodoItem, Long >, JpaSpecificationExecutor<TodoItem> {

    @Query(value = "select * from todo_item where user_id = :user_id", nativeQuery = true)
    List<TodoItem> findByUser_id(Integer user_id);

    TodoItem findTodoItemById(Long id);

    TodoItem findTodoItemByName(String name);

}
