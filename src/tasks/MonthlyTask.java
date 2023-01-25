package tasks;

import exceptions.IncorrectArgumentException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class MonthlyTask extends Task {
    public MonthlyTask(String title, Type type, LocalDateTime dateTime, String description) throws IncorrectArgumentException {
        super(title, type, dateTime, description);
    }

    @Override
    public boolean appearsIn(LocalDate date) {
        int dateDayOfMonth = date.getDayOfMonth();
        int taskDayOfMonth = getDateTime().getDayOfMonth();

        if (date.isBefore(getDateTime().toLocalDate())) return false;
        if (dateDayOfMonth == taskDayOfMonth) return true;

        // Ежемесячная задача, выставленная 31 числа какого-либо месяца, будет
        // также срабатывать в последние дни тех месяцев, в которых менее
        // 31 дня.
        if (taskDayOfMonth == 31
                && dateDayOfMonth == date.getMonth().maxLength()) return true;

        // Ежемесячная задача, выставленная 30 числа какого-либо месяца, будет
        // срабатывать 28/29 февраля (в зависимости от года).
        boolean dateIsInFeb = date.getMonth().equals(Month.FEBRUARY);

        if (taskDayOfMonth == 30 && dateIsInFeb) {
            return (date.isLeapYear() && dateDayOfMonth == 29)
                    || (dateDayOfMonth == 28);
        }

        // Ежемесячная задача, выставленная 29 февраля, будет
        // срабатывать 28 февраля невисокосного года (и 29 февраля
        // високосного).
        if (taskDayOfMonth == 29 && getDateTime().getMonth().equals(Month.FEBRUARY)
            && dateIsInFeb) {
            return (date.isLeapYear() && dateDayOfMonth == 29)
                    || (dateDayOfMonth == 28);
        }

        return false;
    }
}
