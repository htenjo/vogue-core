package co.zero.vogue.resource;

import co.zero.vogue.common.view.View;
import co.zero.vogue.model.Employee;
import co.zero.vogue.service.EmployeeService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by htenjo on 5/29/16.
 */
@RestController
@RequestMapping("/employee")
public class EmployeeResource {
    @Autowired
    EmployeeService service;

    @RequestMapping(method = RequestMethod.GET)
    public Page<Employee> list(Pageable pageable){
        return service.list(pageable);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Employee  find(@PathVariable("id") long employeeId){
        return service.find(employeeId);
    }

    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity<Employee>  save(@RequestBody Employee employee){
        Employee employeePersisted = service.save(employee);
        return new ResponseEntity<>(employeePersisted, HttpStatus.CREATED);
    }
}
