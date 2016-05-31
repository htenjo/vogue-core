package co.zero.vogue.model;

import co.zero.vogue.common.ProbabilityType;
import co.zero.vogue.common.ReportType;
import co.zero.vogue.common.SeverityType;

import java.util.Date;

/**
 * Created by htenjo on 5/30/16.
 */
public class Report {
    /** This is a kind of id given by an external system */
    private String sio;
    private ReportType type;
    private Employee collaborator;
    private Area area;
    private Date createdDate;
    private String description;
    private String taken_measures;
    private String additional_measures;
    private Employee responsible;
    private SeverityType severity;
    private ProbabilityType probability;
    private String clazz;
    private double percentageCompleted;
    private Date closedDate;
    private String closedComments;
}
