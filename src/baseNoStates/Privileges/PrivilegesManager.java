package baseNoStates.Privileges;

import baseNoStates.Actions;
import baseNoStates.Space;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;

// Can perform any action, but only between 8AM and 8PM during workdays + saturday and between 2025/09/01 and 2026/02/28 (both included)
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
