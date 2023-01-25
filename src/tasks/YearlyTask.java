package tasks;

import exceptions.IncorrectArgumentException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class YearlyTask extends Task {
    public YearlyTask(String title, Type type, LocalDateTime dateTime, String description) throws IncorrectArgumentException {
        super(title, type, dateTime, description);
    }

    @Override
    public boolean appearsIn(LocalDate date) {
        if (date.isBefore(getDateTime().toLocalDate())) return false;

        // Ежемесячная задача, выставленная 29 февраля, будет
        // срабатывать 28 февраля невисокосного года (и 29 февраля
        // високосного).
        int dateDayOfMonth = date.getDayOfMonth();
        int taskDayOfMonth = getDateTime().getDayOfMonth();

        if (taskDayOfMonth == 29 && getDateTime().getMonth().equals(Month.FEBRUARY)
                && date.getMonth().equals(Month.FEBRUARY)) {
            return (date.isLeapYear() && dateDayOfMonth == 29)
                    || (dateDayOfMonth == 28);
        }

        return (getDateTime().toLocalDate().getMonthValue() == date.getMonthValue()
            && getDateTime().toLocalDate().getDayOfMonth() == dateDayOfMonth);
    }
}
