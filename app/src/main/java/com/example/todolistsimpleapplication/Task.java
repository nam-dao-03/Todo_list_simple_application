package com.example.todolistsimpleapplication;

import java.io.Serializable;

public class Task implements Serializable {
    private int task_id;
    private String name;
    private String description;

    public Task(int task_id, String name, String description) {
        this.description = description;
        this.name = name;
        this.task_id = task_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }
}
