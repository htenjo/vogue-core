package co.zero.vogue.report;

import co.zero.vogue.common.type.EventType;

/**
 * Created by htenjo on 9/16/16.
 */
public class ReportEventsCreatedByEventType {
    private EventType eventType;
    private long createdEvents;

    public ReportEventsCreatedByEventType(EventType eventType, long createdEvents) {
        this.eventType = eventType;
        this.createdEvents = createdEvents;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public long getCreatedEvents() {
        return createdEvents;
    }

    public void setCreatedEvents(long createdEvents) {
        this.createdEvents = createdEvents;
    }
}
