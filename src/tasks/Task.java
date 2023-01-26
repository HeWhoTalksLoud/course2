package tasks;

import exceptions.IncorrectArgumentException;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class Task {
    private static int idGenerator = 0;
    private String title;
    private final Type type;
    private final int id;
    private final LocalDateTime dateTime;
    private String description;

    public String getTitle() {
        return title;
    }

    public Type getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) throws IncorrectArgumentException {
        if ("".equals(title) || title.equals(null)) {
            throw new IncorrectArgumentException("Недопустимый заголовок задачи", title);
        }
        this.title = title;
    }

    public void setDescription(String description) throws IncorrectArgumentException {
        if ("".equals(description) || description.equals(null)) {
            throw new IncorrectArgumentException("Недопустимое описание задания", title);
        }
        this.description = description;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        return this.id == ((Task) obj).id;
    }

    @Override
    public String toString() {
        String taskType = new String();

        switch (type) {
            case WORK:
                taskType = "рабочая";
                break;
            case PERSONAL:
                taskType = "личная";
                break;
            default:
                taskType = "тип не определен";
        }

        return "ИД.: " + id +
                " ТИП: " + taskType + "\n" +
                "НАЗВАНИЕ: " + title +
                " ОПИСАНИЕ: " + description/* + "\n" +
                "ДАТА И ВРЕМЯ: " + dateTime*/;
    }

    public Task(String title, Type type, LocalDateTime dateTime, String description)
            throws IncorrectArgumentException {
        if ("".equals(title) || title.equals(null)) {
            throw new IncorrectArgumentException("Недопустимый заголовок задачи", title);
        }
        this.title = title;
        if (type.equals(null)) {
            throw new IncorrectArgumentException("Недопустимый тип задачи", "null");
        }
        this.type = type;
        if (dateTime.equals(null)) {
            throw new IncorrectArgumentException("Недопустимое время задачи", "null");
        }
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new IncorrectArgumentException("Время задачи установлено в прошлом",
                    dateTime.toString());
        }
        this.dateTime = dateTime;
        if (description.equals(null)) {
            throw new IncorrectArgumentException("Недопустимое описание задачи", "null");
        }
        this.description = description;
        this.id = idGenerator++;
    }

    public abstract boolean appearsIn(LocalDate date);

}
