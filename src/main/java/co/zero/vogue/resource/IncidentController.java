package co.zero.vogue.resource;

import co.zero.vogue.common.ProbabilityType;
import co.zero.vogue.common.IncidentType;
import co.zero.vogue.common.SeverityType;
import co.zero.vogue.model.Incident;
import co.zero.vogue.service.EmployeeService;
import co.zero.vogue.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by htenjo on 5/29/16.
 */
@RestController
@RequestMapping("/incidents")
@CrossOrigin(origins = "http://localhost:3000")
public class IncidentController {
    @Autowired
    IncidentService incidentService;
    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Incident> listAllIncidents(){
        return incidentService.listAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Incident findIncident(@PathVariable("id") long incidentId){
        return incidentService.find(incidentId);
    }

    @RequestMapping(value = "/build/{amount}", method = RequestMethod.POST)
    public void buildIncidents(@PathVariable("amount") int amount){
        incidentService.buildIncidents(amount);
    }
}
