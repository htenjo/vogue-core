package co.zero.vogue.model;

import co.zero.vogue.common.ProbabilityType;
import co.zero.vogue.common.IncidentType;
import co.zero.vogue.common.SeverityType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by htenjo on 5/30/16.
 */
@Entity
public class Incident {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    /** This is a kind of id given by an external system */
    private String sio;
    @Enumerated(value = EnumType.STRING)
    private IncidentType type;
    @ManyToOne
    private Employee collaborator;
    @ManyToOne
    private Area area;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdDate;
    private String description;
    private String taken_measures;
    private String additional_measures;
    @ManyToOne
    private Employee responsible;
    @Enumerated(value = EnumType.STRING)
    private SeverityType severity;
    @Enumerated(value = EnumType.STRING)
    private ProbabilityType probability;
    private String clazz;
    private double percentageCompleted;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date closedDate;
    private String closedComments;
    @ManyToOne
    private Employee supervisor;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSio() {
        return sio;
    }

    public void setSio(String sio) {
        this.sio = sio;
    }

    public IncidentType getType() {
        return type;
    }

    public void setType(IncidentType type) {
        this.type = type;
    }

    public Employee getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(Employee collaborator) {
        this.collaborator = collaborator;
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

    public String getTaken_measures() {
        return taken_measures;
    }

    public void setTaken_measures(String taken_measures) {
        this.taken_measures = taken_measures;
    }

    public String getAdditional_measures() {
        return additional_measures;
    }

    public void setAdditional_measures(String additional_measures) {
        this.additional_measures = additional_measures;
    }

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

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public double getPercentageCompleted() {
        return percentageCompleted;
    }

    public void setPercentageCompleted(double percentageCompleted) {
        this.percentageCompleted = percentageCompleted;
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

    public Employee getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Employee supervisor) {
        this.supervisor = supervisor;
    }
}
