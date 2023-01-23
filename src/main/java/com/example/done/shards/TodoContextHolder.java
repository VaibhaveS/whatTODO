package com.example.done.shards;


public class TodoContextHolder {

    private static final ThreadLocal<TodoType> contextHolder =
            new ThreadLocal<TodoType>();

    public static void setTodoType(TodoType todoType) {
        contextHolder.set(todoType);
    }

    public static TodoType getTodoType() {
        return (TodoType) contextHolder.get();
    }

    public static void clearCustomerType() {
        contextHolder.remove();
    }
}