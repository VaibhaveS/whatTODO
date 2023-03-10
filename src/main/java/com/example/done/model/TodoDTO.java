package com.example.done.model;


import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
public class TodoDTO {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private boolean is_done;
    private Integer user_id;

}
