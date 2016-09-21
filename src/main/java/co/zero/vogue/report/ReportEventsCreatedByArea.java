package co.zero.vogue.report;

import co.zero.vogue.model.Area;

/**
 * Created by htenjo on 9/16/16.
 */
public class ReportEventsCreatedByArea {
    private Area area;
    private long createdEvents;

    public ReportEventsCreatedByArea(Area area, long createdEvents) {
        this.area = area;
        this.createdEvents = createdEvents;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public long getCreatedEvents() {
        return createdEvents;
    }

    public void setCreatedEvents(long createdEvents) {
        this.createdEvents = createdEvents;
    }
}
