package co.zero.vogue.persistence;

import co.zero.vogue.model.Employee;
import co.zero.vogue.model.Task;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by htenjo on 6/1/16.
 */
public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {
}
