package baseNoStates.Privileges;

import baseNoStates.Actions;
import baseNoStates.Space;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;

public class PrivilegesManager implements Privileges {
    public boolean canSendRequests(LocalDateTime now) {
        if (now.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return false;
        }

        if (now.getHour() < 8 || now.getHour() >= 20) {
            return false;
        }

        if (now.getYear() == 2025 &&
                (now.getMonth() == Month.SEPTEMBER
                || now.getMonth() == Month.OCTOBER
                || now.getMonth() == Month.NOVEMBER
                || now.getMonth() == Month.DECEMBER)) {

            return true;
        }

        if (now.getYear() == 2026 &&
                (now.getMonth() == Month.JANUARY
                || now.getMonth() == Month.FEBRUARY)) {

            return true;
        }

        return false;
    }

    public boolean canBeInSpace(Space space) {
        return true;
    }

    public boolean canDoAction(String action) {
        return true;
    }
}
