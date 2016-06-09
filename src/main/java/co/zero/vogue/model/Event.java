package co.zero.vogue.model;

import co.zero.vogue.common.type.EventType;
import co.zero.vogue.common.view.View;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by htenjo on 5/30/16.
 */
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event extends BaseEntity{
    /** This is a kind of id given by an external system */
    @JsonView(View.Summary.class)
    private String sio;

    @Enumerated(value = EnumType.STRING)
    @JsonView(View.Summary.class)
    private EventType eventType;

    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonView(View.Summary.class)
    private Date createdDate;

    @JsonView(View.Summary.class)
    private String description;

    @ManyToOne
    private Area area;

    @ManyToOne
    private Employee supervisor;


    public String getSio() {
        return sio;
    }

    public void setSio(String sio) {
        this.sio = sio;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employee getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Employee supervisor) {
        this.supervisor = supervisor;
    }
}
