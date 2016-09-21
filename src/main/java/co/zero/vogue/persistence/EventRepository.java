package co.zero.vogue.persistence;

import co.zero.vogue.model.Event;
import co.zero.vogue.report.ReportEventsCreatedByArea;
import co.zero.vogue.report.ReportEventsCreatedByEventType;
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
public interface EventRepository extends PagingAndSortingRepository<Event, Long>{
    /**
     *
     * @param sio
     * @return
     */
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Event e WHERE e.sio = :sio")
    boolean existBySio(@Param("sio") String sio);

    /**
     *
     * @param sio
     * @return
     */
    Event findFirstBySio(String sio);

    /**
     *
     * @param page
     * @return
     */
    @Query("SELECT t.event FROM Task t WHERE t.expectedClosedDate IS NOT NULL AND t.percentageCompleted != 1")
    Page<Event> findCloseToExpire(Pageable page);


    /**
     *
     * @param startDate Start filter
     * @param endDate End filter
     * @return
     */
    @Query(" SELECT new co.zero.vogue.report.ReportEventsCreatedByArea(a, COUNT(e.id))" +
            " FROM Event AS e INNER JOIN e.area AS a" +
            " WHERE e.createdDate BETWEEN :startDate AND :endDate" +
            " GROUP BY a" +
            " ORDER BY COUNT(e.id) DESC")
    Page<List<ReportEventsCreatedByArea>> createdByArea(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            Pageable pageable);

    /**
     *
     * @param startDate
     * @param endDate
     * @return
     */
    Long countByCreatedDateBetween(Date startDate, Date endDate);

    /**
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Query("SELECT new co.zero.vogue.report.ReportEventsCreatedByEventType(e.eventType, count(e.id))" +
            " FROM Event e" +
            " WHERE e.createdDate BETWEEN :startDate AND :endDate" +
            " GROUP BY e.eventType" +
            " ORDER BY count(e.id) DESC")
    List<ReportEventsCreatedByEventType> createdByEventType(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);
}
