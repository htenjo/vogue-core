package co.zero.vogue.report;

/**
 * Class that represent the closed task percentage report of the last year
 * The start and end dates should be calculated from the current date
 * Created by htenjo on 9/15/16.
 */
public class ReportTasksClosedInLastYear {
    private long totalTasks;
    private long openTasks;
    private String startDate;
    private String endDate;

    public ReportTasksClosedInLastYear(long openTasks, long totalTasks) {
        this.totalTasks = totalTasks;
        this.openTasks = openTasks;
    }

    public long getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(long totalTasks) {
        this.totalTasks = totalTasks;
    }

    public long getOpenTasks() {
        return openTasks;
    }

    public void setOpenTasks(long openTasks) {
        this.openTasks = openTasks;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
