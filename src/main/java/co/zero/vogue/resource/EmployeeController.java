package co.zero.vogue.resource;

import co.zero.vogue.model.Employee;
import co.zero.vogue.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by htenjo on 5/29/16.
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    EmployeeService service;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Employee> listAll(){
        return service.listAll();
    }

    @RequestMapping(value = "/{amount}", method = RequestMethod.POST)
    public void buildEmployees(@PathVariable("amount") int amount){
        service.buildEmployees(amount);
    }
}
