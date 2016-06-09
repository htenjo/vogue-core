package co.zero.vogue.model;

import co.zero.vogue.common.type.ClassType;
import co.zero.vogue.common.type.ProbabilityType;
import co.zero.vogue.common.type.SeverityType;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by htenjo on 6/8/16.
 */
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Task extends BaseEntity{
    @Enumerated(value = EnumType.STRING)
    private SeverityType severity;

    @Enumerated(value = EnumType.STRING)
    private ProbabilityType probability;

    @Enumerated(value = EnumType.STRING)
    private ClassType classType;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date closedDate;

    @ManyToOne
    private Employee responsible;

    @ManyToOne
    private Event event;

    private double percentageCompleted;
    private boolean immediate;
    private String closedComments;


    public Employee getResponsible() {
        return responsible;
    }

    public void setResponsible(Employee responsible) {
        this.responsible = responsible;
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

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    public double getPercentageCompleted() {
        return percentageCompleted;
    }

    public void setPercentageCompleted(double percentageCompleted) {
        this.percentageCompleted = percentageCompleted;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public String getClosedComments() {
        return closedComments;
    }

    public void setClosedComments(String closedComments) {
        this.closedComments = closedComments;
    }

    public boolean isImmediate() {
        return immediate;
    }

    public void setImmediate(boolean immediate) {
        this.immediate = immediate;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}