package co.zero.vogue.model;

import co.zero.vogue.common.Constants;
import co.zero.vogue.common.type.ClassType;
import co.zero.vogue.common.type.EventType;
import co.zero.vogue.common.type.ProbabilityType;
import co.zero.vogue.common.type.SeverityType;
import co.zero.vogue.common.view.View;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @Column(unique = true)
    private String sio;

    @Enumerated(value = EnumType.STRING)
    @JsonView(View.Summary.class)
    private EventType eventType;

    @ManyToOne
    private Employee collaborator;

    @ManyToOne
    private Area area;

    @Temporal(value = TemporalType.DATE)
    @JsonView(View.Summary.class)
    @JsonFormat(pattern= Constants.DEFAULT_DATE_FORMAT)
    private Date createdDate;

    @JsonView(View.Summary.class)
    @Column(length = 10000)
    private String description;

    @Column(length = 10000)
    private String measures;

    @Enumerated(value = EnumType.STRING)
    private SeverityType severity;

    @Enumerated(value = EnumType.STRING)
    private ProbabilityType probability;

    public Event() {
    }

    public Event(String sio, EventType eventType, Employee collaborator, Area area
            , Date createdDate, String description, String measures , SeverityType severity
            , ProbabilityType probability) {
        this.sio = sio;
        this.eventType = eventType;
        this.collaborator = collaborator;
        this.area = area;
        this.createdDate = createdDate;
        this.description = description;
        this.measures = measures;
        this.severity = severity;
        this.probability = probability;
    }

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

    public Employee getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(Employee collaborator) {
        this.collaborator = collaborator;
    }

    public String getMeasures() {
        return measures;
    }

    public void setMeasures(String measures) {
        this.measures = measures;
    }

    public SeverityType getSeverity() {
        return severity;
    }

    public void setSeverity(SeverityType severity) {
        this.severity = severity;
    }

    public ProbabilityType getProbability() {
        return probability;
    }

    public void setProbability(ProbabilityType probability) {
        this.probability = probability;
    }

    @Transient
    public ClassType getClassType(){
        return null;
    }
}
