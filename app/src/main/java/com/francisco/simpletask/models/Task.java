package com.francisco.simpletask.models;

import java.util.ArrayList;
import java.util.List;

public class Task {
    private static int idCounter = 0;
    private int id;
    private String title;
    private String description;
    private List<Task> subtasks;
    private boolean completed;

    public Task(String title, String description) {
        this.id = idCounter++;
        this.title = title;
        this.description = description;
        this.subtasks = new ArrayList<>();
        this.completed = false;
    }

    public Task(int id, String title) {
        this.id = id;
        this.title = title;
        this.completed = false;
        this.subtasks = new ArrayList<>();
    }

    public Task(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.subtasks = new ArrayList<>();
        this.completed = false;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public List<Task> getSubtasks() {
        return subtasks;
    }

    public void addSubtask(Task subtask) {
        subtasks.add(subtask);
    }
}