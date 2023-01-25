import exceptions.IncorrectArgumentException;
import services.TaskService;
import tasks.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import tests.*;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        TaskService taskService = new TaskService();

        LocalDate date1 = LocalDate.of(2024, 2, 1);
        LocalDate dateFeb29 = LocalDate.of(2024, 2, 29);
        LocalDate dateJan31 = LocalDate.of(2023, 1, 31);

        try {
            taskService.add(Test.generateTask(date1, Regularity.MONTHLY)); // id 0
            taskService.add(Test.generateTask(date1, Regularity.WEEKLY)); // id 1
            taskService.add(Test.generateTask(2, 4, Regularity.ONETIME)); // id 2
            taskService.add(Test.generateTask(dateFeb29, Regularity.MONTHLY)); // id 3
            taskService.add(Test.generateTask(dateJan31, Regularity.MONTHLY)); // id 4
        } catch (IncorrectArgumentException e) {
            System.out.println("ОШИБКА:");
            System.out.println(e);
            exit(1);
        }

        Test.printWeek(date1, taskService);
        System.out.println();
        Test.printMonth(date1, taskService);
        System.out.println();
        Test.printYear(dateFeb29, taskService);
    }
}