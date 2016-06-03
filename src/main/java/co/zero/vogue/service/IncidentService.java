package co.zero.vogue.service;

import co.zero.vogue.model.Incident;

import java.util.List;

/**
 * Created by htenjo on 6/2/16.
 */
public interface IncidentService {
    public List<Incident> listAll();
    public Incident find(long incidentId);
    public Incident save(Incident incident);
    public Incident update(Incident incident);
    public void finalizeIncident(long id);
    public void buildIncidents(int amount);
}
