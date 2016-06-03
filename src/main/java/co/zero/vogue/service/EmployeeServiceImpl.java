package co.zero.vogue.service;

import co.zero.vogue.model.Employee;
import co.zero.vogue.persistence.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by htenjo on 6/2/16.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository repository;

    /*
        This is just a helper method to populate the BD not for production use,
        so the flush call after amount times behavior wouldn't be implemented
     */
    @Override
    public void buildEmployees(int amount) {
        Employee employee;

        for(int i=0; i < amount; i++){
            employee = buildEmployee(i);
            repository.save(employee);
        }
    }

    @Override
    public List<Employee> listAll() {
        Iterable<Employee> employeeIterable = repository.findAll();
        List<Employee> employeeList = new ArrayList<>();
        employeeIterable.forEach(employeeList::add);
        return employeeList;
    }

    @Override
    public Employee save(Employee employee) {
        return repository.save(employee);
    }

    private Employee buildEmployee(int employeeSerial){
        Employee employee = new Employee();
        String emailTemplate = "mockEmail%d@vogue.com.co";
        String firstNameTemplate = "firstName%d ";
        String lastNameTemplate = "LastName%d";

        employee.setEmail(String.format(emailTemplate, employeeSerial));
        employee.setFirstName(String.format(firstNameTemplate, employeeSerial));
        employee.setLastName(String.format(lastNameTemplate, employeeSerial));
        return employee;
    }
}
