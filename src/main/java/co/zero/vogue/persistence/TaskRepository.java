package co.zero.vogue.persistence;

import co.zero.vogue.model.Task;
import co.zero.vogue.report.ReportClosedTasksInLastYear;
import co.zero.vogue.report.ReportOpenTasksPerEventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

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


    /**
     *
     * @param startDate Start date
     * @param endDate End date
     * @return
     */
    @Query("SELECT new co.zero.vogue.report.ReportClosedTasksInLastYear(" +
            "SUM(CASE WHEN t.percentageCompleted < 1 THEN 1 ELSE 0 END), COUNT(1))" +
            " FROM Task t" +
            " WHERE t.createdDate BETWEEN :startDate AND :endDate")
    ReportClosedTasksInLastYear reportClosedTasksInLastYear(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    /**
     *
     * @return
     */
    @Query("SELECT new co.zero.vogue.report.ReportOpenTasksPerEventType(t.event.eventType, count(1))" +
            " FROM Task t" +
            " WHERE t.percentageCompleted < 1" +
            " GROUP BY t.event.eventType")
    List<ReportOpenTasksPerEventType> reportOpenTasksPerEventType();


}
