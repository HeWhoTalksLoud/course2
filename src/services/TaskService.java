package services;


import exceptions.IncorrectArgumentException;
import exceptions.TaskNotFoundException;
import tasks.Task;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

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

    public Task updateTitle(int id, String newTitle) throws TaskNotFoundException,
            IncorrectArgumentException {
        Task task = taskMap.get(id);
        if (task.equals(null)) {
            throw new TaskNotFoundException("Задача для изменения заголовка не найдена");
        }
        task.setTitle(newTitle);
        return task;
    }

    public Task updateDescription(int id, String newDescription) throws TaskNotFoundException,
            IncorrectArgumentException {
        Task task = taskMap.get(id);
        if (task.equals(null)) {
            throw new TaskNotFoundException("Задача для изменения заголовка не найдена");
        }
        task.setDescription(newDescription);
        return task;
    }

    public Map<LocalDate, List<Task>> getAllGroupedByDate() {
        List<Task> tasks = new LinkedList<>();

        Map<LocalDate, List<Task>> map = taskMap.values()
                .stream()
                .collect(groupingBy(task -> task.getDateTime().toLocalDate()));

        return map;
    }

    public List<Task> getRemovedTasks() {
        return removedTasks;
    }
}
