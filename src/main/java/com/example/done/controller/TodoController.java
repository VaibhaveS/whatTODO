package com.example.done.controller;

import com.example.done.model.TodoDTO;
import com.example.done.model.TodoItem;
import com.example.done.service.TodoService;
import org.modelmapper.ModelMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/todo")
public class TodoController {


    static int turn = 0;

    @Autowired
    @Qualifier("serviceA")
    private TodoService todoService;

    @Autowired
    @Qualifier("serviceB")
    private TodoService todoServiceB;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;

    @GetMapping
    public List<TodoDTO> findAll(){
        List<TodoItem> getResponse = todoService.findAll();
        return Arrays.asList(modelMapper.map(getResponse, TodoDTO[].class));
    }

    @PostMapping
    public TodoDTO save(@RequestBody TodoDTO todoDTO) throws Exception {

        turn++;
        TodoItem todoItem = modelMapper.map(todoDTO, TodoItem.class);
        if(turn%2 == 0) {
            TodoItem postResponse = todoService.save(todoItem);
            return modelMapper.map(postResponse, TodoDTO.class);
        }

        TodoItem postResponse = todoServiceB.save(todoItem);
        return modelMapper.map(postResponse, TodoDTO.class);

    }

    @PutMapping
    public TodoDTO update(@RequestBody TodoDTO todoDTO) throws Exception {
        TodoItem todoItem = modelMapper.map(todoDTO, TodoItem.class);
        TodoItem putResponse = todoService.save(todoItem);
        return modelMapper.map(putResponse, TodoDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id){
        todoService.deleteById(id);
    }

    @PutMapping(value = "/{id}/{userId}" )
    public TodoDTO addTodoToUser(@PathVariable Long id, @PathVariable Integer userId) throws Exception {
        TodoItem putResponse = todoService.addTodoToUser(id, userId);
        return modelMapper.map(putResponse, TodoDTO.class);
    }

    @GetMapping(value = "/{userId}")
    public List<TodoDTO> findAllForUser(@PathVariable Integer userId){
        List<TodoItem> getResponse = todoService.findByUserId(userId);
        return Arrays.asList(modelMapper.map(getResponse, TodoDTO[].class));
    }

    @PostMapping("/batch")
    public void importCsvToDBJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

