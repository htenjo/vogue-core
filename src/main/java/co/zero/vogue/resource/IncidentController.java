package co.zero.vogue.resource;

import co.zero.vogue.model.Incident;
import co.zero.vogue.persistence.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by htenjo on 5/29/16.
 */
@RestController
public class IncidentController {
    @Autowired
    IncidentRepository repository;

    @RequestMapping("/incidents")
    public List<Incident> getIncidentList(){
        Iterator<Incident> incidents = repository.findAll().iterator();
        List<Incident> incidentList = new ArrayList<>();

        while(incidents.hasNext()){
            incidentList.add(incidents.next());
        }

        return incidentList;
    }

}
