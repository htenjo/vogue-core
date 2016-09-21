package co.zero.vogue.report;

import co.zero.vogue.model.Employee;

/**
 * Created by htenjo on 9/16/16.
 */
public class ReportTasksByEmployee {
    private long openTasks;
    private long closedTasks;
    private Employee responsible;

    public ReportTasksByEmployee(Employee responsible, long openTasks, long closedTasks) {
        this.openTasks = openTasks;
        this.closedTasks = closedTasks;
        this.responsible = responsible;
    }

    public long getOpenTasks() {
        return openTasks;
    }

    public void setOpenTasks(long openTasks) {
        this.openTasks = openTasks;
    }

    public long getClosedTasks() {
        return closedTasks;
    }

    public void setClosedTasks(long closedTasks) {
        this.closedTasks = closedTasks;
    }

    public Employee getResponsible() {
        return responsible;
    }

    public void setResponsible(Employee responsible) {
        this.responsible = responsible;
    }
}
