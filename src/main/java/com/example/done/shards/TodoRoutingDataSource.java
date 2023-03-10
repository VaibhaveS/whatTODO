package com.example.done.shards;


import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class TodoRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return TodoContextHolder.getTodoType();
    }
}