package co.zero.vogue.model;

import co.zero.vogue.common.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by htenjo on 6/8/16.
 */
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Task extends BaseEntity{
    private String description;

    @JsonFormat(pattern= Constant.DEFAULT_DATE_FORMAT)
    @Temporal(value = TemporalType.DATE)
    private Date createdDate;

    @ManyToOne
    private Employee responsible;

    @ManyToOne
    private Event event;

    private double percentageCompleted;

    @JsonFormat(pattern= Constant.DEFAULT_DATE_FORMAT)
    @Temporal(value = TemporalType.DATE)
    private Date expectedClosedDate;

    private String closedComments;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employee getResponsible() {
        return responsible;
    }

    public void setResponsible(Employee responsible) {
        this.responsible = responsible;
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

    public Date getExpectedClosedDate() {
        return expectedClosedDate;
    }

    public void setExpectedClosedDate(Date expectedClosedDate) {
        this.expectedClosedDate = expectedClosedDate;
    }

    public String getClosedComments() {
        return closedComments;
    }

    public void setClosedComments(String closedComments) {
        this.closedComments = closedComments;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}