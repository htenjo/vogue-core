package co.zero.vogue.report;

import co.zero.vogue.common.type.EventType;

/**
 * Created by htenjo on 9/15/16.
 */
public class ReportOpenTasksPerEventType {
    private EventType type;
    private long openTasks;

    public ReportOpenTasksPerEventType(EventType type, long openTasks) {
        this.type = type;
        this.openTasks = openTasks;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public long getOpenTasks() {
        return openTasks;
    }

    public void setOpenTasks(long openTasks) {
        this.openTasks = openTasks;
    }
}
