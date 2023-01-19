package com.example.done.shard;


public class TodoContextHolder {

    private static final ThreadLocal<TodoType> contextHolder =
            new ThreadLocal<TodoType>();

    public static void setCustomerType(TodoType customerType) {
        //Assert.notNull(customerType, "customerType cannot be null");
        contextHolder.set(customerType);
    }

    public static TodoType getCustomerType() {
        return (TodoType) contextHolder.get();
    }

    public static void clearCustomerType() {
        contextHolder.remove();
    }
}