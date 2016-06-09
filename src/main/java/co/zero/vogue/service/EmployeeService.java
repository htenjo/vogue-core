package co.zero.vogue.service;

import co.zero.vogue.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by htenjo on 6/2/16.
 */
public interface EmployeeService {
    public Page<Employee> list(Pageable pageable);
    public Employee save(Employee employee);
    public Employee find(long id);
}
