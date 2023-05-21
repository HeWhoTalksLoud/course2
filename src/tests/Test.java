package tests;

import services.TaskService;
import tasks.*;
import exceptions.IncorrectArgumentException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Test {

    // Сгенерировать задачу на заданную дату и с заданной регулярностью
    // (однократная, ежедневная, еженедельная и т.д.)
    public static Task generateTask(int year, int month, int day, Regularity regularity)
            throws IncorrectArgumentException {
        if (month < 1 || month > 12) {
            throw new IncorrectArgumentException("Недопустимый номер месяца", String.valueOf(month));
        }

        if (day < 1 || day > 31) {
            throw new IncorrectArgumentException("Недопустимый номер дня", String.valueOf(day));
        }

        LocalDateTime localDateTime = LocalDateTime.now().withYear(year)
                .withMonth(month).withDayOfMonth(day);

        switch (regularity) {
            case ONETIME:
                return new OneTimeTask("Одиноч. задание",
                        Type.WORK,localDateTime,
                        localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE));

            case DAILY:
                return new DailyTask("Ежеднев. задание",
                        Type.WORK,localDateTime,
                        localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE));

            case WEEKLY:
                return new WeeklyTask("Еженед. задание",
                        Type.WORK,localDateTime,
                        localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE));

            case MONTHLY:
                return new MonthlyTask("Ежемес. задание",
                        Type.WORK,localDateTime,
                        localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE));

            case YEARLY:
                return new YearlyTask("Еженед. задание",
                        Type.WORK,localDateTime,
                        localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE));
        }

        return null;
    }

    public static Task generateTask(int month, int day, Regularity regularity)
            throws IncorrectArgumentException {
        return generateTask(LocalDateTime.now().getYear(), month, day, regularity);
    }

    public static Task generateTask(LocalDate localDate, Regularity regularity)
            throws IncorrectArgumentException {
        return generateTask(localDate.getYear(), localDate.getMonth().getValue(),
                localDate.getDayOfMonth(), regularity);
    }

    // Распечатать список дат недели, к которой относится date,
    // с идентификаторами заданий на эти даты (если есть).
    public static void printWeek(LocalDate date, TaskService ts) {
        // Первый день (понедельник) недели, к которой относится
        // дата date.
        LocalDate d = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        do {
            System.out.printf("%1$td.%1$tm.%1$ty, %1$tA %n", d);
            ts.getAllByDate(d).forEach(System.out::println);
            System.out.println();
            d = d.plusDays(1);
        } while (!d.getDayOfWeek().equals(DayOfWeek.MONDAY));

    }

    // Распечатать список дат месяца, к которому относится date,
    // с идентификаторами заданий на эти даты (если есть).
    public static void printMonth(LocalDate date, TaskService ts) {
        LocalDate firstDayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
        // Первый день (понедельник) недели, в которой начинается
        // месяц даты date.
        LocalDate d = firstDayOfMonth.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        List<String> dateStrings = new LinkedList<>();
        StringBuilder sbDate = new StringBuilder("");
        do {
            for (int i = 0; i < 7; i++) {
                sbDate.delete(0, sbDate.toString().length());
                if (d.isBefore(firstDayOfMonth) || d.isAfter(lastDayOfMonth)) {
                    sbDate.append(".");
                } else {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");
                    sbDate.append(formatter.format(d));
                    List<Task> tasks = ts.getAllByDate(d);
                    sbDate.append(" [");
                    sbDate.append(tasks.stream()
                            .map(task -> String.valueOf(task.getId()))
                            .collect(Collectors.joining(",")));

                    sbDate.append("]");

                    if (d.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                        sbDate.append("\n");
                    }
                }
                d = d.plusDays(1);
                dateStrings.add(sbDate.toString());
            }
        } while (!d.isAfter(lastDayOfMonth));

        dateStrings.forEach(s -> System.out.printf("%15s", s));
    }

    // Распечатать список дат года, к которому относится date,
    // с идентификаторами заданий на эти даты (если есть).
    public static void printYear(LocalDate date, TaskService ts) {
        LocalDate d = date.with(TemporalAdjusters.firstDayOfYear());

        System.out.println();
        do {
            System.out.printf("%1$tB, %1$tY %n", d);
            printMonth(d, ts);
            System.out.println();
            d = d.plusMonths(1);
        } while (!d.getMonth().equals(Month.JANUARY));
    }

}
