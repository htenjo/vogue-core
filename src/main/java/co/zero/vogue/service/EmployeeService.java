package co.zero.vogue.service;

import co.zero.vogue.model.Employee;
import co.zero.vogue.model.Incident;

import java.util.List;

/**
 * Created by htenjo on 6/2/16.
 */
public interface EmployeeService {
    public void buildEmployees(int amount);
    public List<Employee> listAll();
    public Employee save(Employee employee);
}
