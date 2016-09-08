package co.zero.vogue.persistence;

import co.zero.vogue.model.Employee;
import co.zero.vogue.model.Event;
import co.zero.vogue.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by htenjo on 6/1/16.
 */
public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {
    /**
     *
     * @param page
     * @return
     */
    @Query("SELECT t FROM Task t WHERE t.expectedClosedDate IS NOT NULL AND t.percentageCompleted != 1")
    Page<Task> findCloseToExpire(Pageable page);
}
