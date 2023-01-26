package com.example.done.shards;


public class TodoContextHolder {

    private static final ThreadLocal<String> contextHolder =
            new ThreadLocal<String>();

    public static void setTodoType(String todoType) {
        contextHolder.set(todoType);
    }

    public static String getTodoType() {
        return (String) contextHolder.get();
    }

    public static void clearCustomerType() {
        contextHolder.remove();
    }
}