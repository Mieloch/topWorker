package topworker.view.naviagtion.calendar;

import com.vaadin.ui.Calendar;
import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import topworker.model.bo.WorkPeriod;
import topworker.service.WorkPeriodService;
import topworker.utils.TimeUtils;
import topworker.view.naviagtion.calendar.enums.CalendarRange;

import java.text.SimpleDateFormat;
import java.util.*;

@Scope(value = WebApplicationContext.SCOPE_SESSION)
@Component
class WorkCalendarController {

    private GregorianCalendar calendar;
    private Calendar calendarComponent;
    private CalendarRange currentRange;

    @Autowired
    private WorkPeriodService workPeriodService;

    public WorkCalendarController() {
        calendar = new GregorianCalendar(TimeZone.getTimeZone("Europe/Warsaw"), new Locale("pl", "PL"));
    }

    public void loadWorkPeriods() {
        List<WorkPeriod> periods = workPeriodService.getAllBelongToUser();
        for (WorkPeriod workPeriod : periods) {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm", new Locale("pl", "PL"));
            String endTime = format.format(workPeriod.getStop());
            String startTime = format.format(workPeriod.getStart());
            CalendarEvent calEvent = new BasicEvent(startTime + "-" + endTime, TimeUtils.formatTime(workPeriod.getDuration()), workPeriod.getStart(),
                    workPeriod.getStop());

            calendarComponent.addEvent(calEvent);
        }
    }

    public void setCalendar(Calendar calendarComponent) {
        this.calendarComponent = calendarComponent;
        setCurrentMonth();
    }

    public void setCurrentMonth() {

        calendar.setTime(new Date());
        setWholeMonth();
    }

    public void changeMonth(int value) {
        calendar.add(GregorianCalendar.MONTH, value);
        setWholeMonth();
    }

    public void navigate(int direction) {
        switch (currentRange) {
            case MONTH:
                changeMonth(direction);
                break;
            case WEEK:
                changeWeek(direction);
                break;
            default:
                throw new IllegalStateException();
        }
    }

    public void changeWeek(int direction) {
        calendar.add(GregorianCalendar.DAY_OF_MONTH, 7 * direction);
        calendarComponent.setStartDate(calendar.getTime());
        calendar.add(GregorianCalendar.DAY_OF_MONTH, 6);
        calendarComponent.setEndDate(calendar.getTime());
        calendar.add(GregorianCalendar.DAY_OF_MONTH, -6);

    }

    public void changePerspective() {
        switch (currentRange) {
            case MONTH:
                setWeek(calendar.get(GregorianCalendar.WEEK_OF_YEAR));
                break;
            case WEEK:
                setWholeMonth();
                break;
            default:
                throw new IllegalStateException();
        }
    }

    public void setWeekPerspective() {
        setWeek(calendar.get(GregorianCalendar.WEEK_OF_YEAR));
    }

    public void setMonthPerspective(){
        setWholeMonth();
    }

    public void setWeek(int week) {

        calendar.set(GregorianCalendar.WEEK_OF_YEAR, week);
        calendar.set(GregorianCalendar.DAY_OF_WEEK, 2);

        calendarComponent.setStartDate(calendar.getTime());
        calendar.add(GregorianCalendar.DAY_OF_MONTH, 6);
        calendarComponent.setEndDate(calendar.getTime());
        calendar.add(GregorianCalendar.DAY_OF_MONTH, -6);
        currentRange = CalendarRange.WEEK;

    }

    private void setWholeMonth() {
        calendar.add(java.util.Calendar.DAY_OF_MONTH,7);
        calendar.set(GregorianCalendar.DAY_OF_MONTH, 1);
        calendarComponent.setStartDate(calendar.getTime());
        calendar.add(GregorianCalendar.MONTH, 1);
        calendarComponent.setEndDate(calendar.getTime());
        calendar.add(GregorianCalendar.MONTH, -1);
        calendar.set(GregorianCalendar.DAY_OF_MONTH, 1);
        currentRange = CalendarRange.MONTH;
    }
}