package co.zero.vogue.persistence;

import co.zero.vogue.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by htenjo on 6/1/16.
 */

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Employee e WHERE e.name = :name")
    boolean existByName(@Param("name") String name);

    Employee findFirstByNameIgnoreCase(String name);
}
