package com.francisco.simpletask.data;

import com.francisco.simpletask.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    private static final List<Task> tasks = new ArrayList<>();
    private static int currentId = 1;

    public static List<Task> getTasks() {
        return tasks;
    }

    public static void addTask(String title, String description) {
        tasks.add(new Task(currentId++, title, description));
    }

    public static void addSubtask(Task parentTask, String title) {
        parentTask.addSubtask(new Task(currentId++, title));
    }

    public static Task getTaskById(int taskId) {
        for (Task task : tasks) {
            if (task.getId() == taskId) {
                return task;
            }
        }
        return null;
    }

    public static void updateTask(Task task, String title, String description) {
        task.setTitle(title);
        task.setDescription(description);
    }

    public static Task getSubtaskById(int taskId, int subtaskId) {
        Task task = getTaskById(taskId);
        if (task != null) {
            for (Task subtask : task.getSubtasks()) {
                if (subtask.getId() == subtaskId) {
                    return subtask;
                }
            }
        }
        return null;
    }

    public static void removeTask(Task task) {
        tasks.remove(task);
    }

    public static void removeSubtask(Task parentTask, Task subtask) {
        parentTask.getSubtasks().remove(subtask);
    }
}