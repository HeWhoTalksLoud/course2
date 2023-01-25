package services;


import exceptions.TaskNotFoundException;
import tasks.Task;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class TaskService {
    private Map<Integer, Task> taskMap;
    private List<Task> removedTasks;

    public TaskService() {
        this.taskMap = new HashMap<>();
        this.removedTasks = new LinkedList<>();
    }

    public void add(Task task) {
        taskMap.put(task.getId(), task);
    }

    public Task remove(int id) throws TaskNotFoundException {
        Task task = taskMap.get(id);
        if (task.equals(null)) {
            throw new TaskNotFoundException("Задача для удаления не найдена");
        }
        taskMap.remove(task.getId());
        removedTasks.add(task);
        return task;
    }

    public List<Task> getAllByDate(LocalDate date) {
        List<Task> tasks = new LinkedList<>();

        tasks = taskMap.values()
            .stream()
            .filter(task -> task.appearsIn(date))
            .collect(Collectors.toList());

        return tasks;
    }
}
