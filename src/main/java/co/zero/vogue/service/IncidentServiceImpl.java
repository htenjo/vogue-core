package co.zero.vogue.service;

import co.zero.vogue.common.IncidentType;
import co.zero.vogue.common.ProbabilityType;
import co.zero.vogue.common.SeverityType;
import co.zero.vogue.model.Incident;
import co.zero.vogue.persistence.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by htenjo on 6/2/16.
 */
@Service
public class IncidentServiceImpl implements IncidentService {
    @Autowired
    IncidentRepository repository;

    @Override
    public List<Incident> listAll() {
        Iterable<Incident> incidentIterable = repository.findAll();
        List<Incident> incidentList = new ArrayList<>();
        incidentIterable.forEach(incidentList :: add);
        return incidentList;
    }

    @Override
    public Incident find(long incidentId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Incident update(Incident incident) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void finalizeIncident(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Incident save(Incident incident) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void buildIncidents(int amount) {
        Incident incident;

        for(int i=0; i < amount; i++){
            incident = buildIncidentMock(i);
            repository.save(incident);
        }
    }

    //TODO: Delete this when all features related will be implemented
    private Incident buildIncidentMock(int mockId){
        Incident incident = new Incident();
        incident.setAdditional_measures("Additional Measures " + mockId);
        incident.setClazz("Clazz " + mockId);
        incident.setCreatedDate(new Date());
        incident.setDescription("Description " + mockId);
        incident.setPercentageCompleted(new Random().nextInt(101));
        incident.setProbability(ProbabilityType.FRECUENTE);
        incident.setSeverity(SeverityType.CRITICO);
        incident.setSio("ASVB-"+ mockId);
        incident.setTaken_measures("Taken measures " + mockId);
        incident.setType(IncidentType.ACTO_INSEGURO);
        return incident;
    }
}
