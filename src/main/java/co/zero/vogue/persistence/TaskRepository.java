package co.zero.vogue.persistence;

import co.zero.vogue.model.Task;
import co.zero.vogue.report.ReportTasksByEmployee;
import co.zero.vogue.report.ReportTasksClosedInLastYear;
import co.zero.vogue.report.ReportTasksOpenPerEventType;
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
    @Query("SELECT new co.zero.vogue.report.ReportTasksClosedInLastYear(" +
            "SUM(CASE WHEN t.percentageCompleted < 1 THEN 1 ELSE 0 END), COUNT(1))" +
            " FROM Task t" +
            " WHERE t.createdDate BETWEEN :startDate AND :endDate")
    ReportTasksClosedInLastYear reportClosedTasksInLastYear(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    /**
     *
     * @return
     */
    @Query("SELECT new co.zero.vogue.report.ReportTasksOpenPerEventType(t.event.eventType, count(1) as open_tasks)" +
            " FROM Task t" +
            " WHERE t.percentageCompleted < 1" +
            " GROUP BY t.event.eventType" +
            " ORDER BY open_tasks DESC")
    List<ReportTasksOpenPerEventType> reportOpenTasksPerEventType();

    /**
     *
     * @return
     */
    @Query("SELECT new co.zero.vogue.report.ReportTasksByEmployee(emp, " +
            " SUM(CASE WHEN t.percentageCompleted < 1 THEN 1 ELSE 0 END) as open, " +
            " SUM(CASE WHEN t.percentageCompleted = 1 THEN 1 ELSE 0 END) as closed)" +
            " FROM Task t" +
            "   INNER JOIN t.responsible as emp" +
            " WHERE t.createdDate BETWEEN :startDate AND :endDate" +
            " GROUP BY emp" +
            " HAVING SUM(CASE WHEN t.percentageCompleted < 1 THEN 1 ELSE 0 END) > 0" +
            " ORDER BY open DESC")
    Page<List<ReportTasksByEmployee>> reportTasksByEmployee(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            Pageable pageable);
}
