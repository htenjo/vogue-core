package co.zero.vogue.persistence;

import co.zero.vogue.model.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by htenjo on 6/1/16.
 */
public interface EventRepository extends PagingAndSortingRepository<Event, Long>{
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Event e WHERE e.sio = :sio")
    boolean existBySio(@Param("sio") String sio);
    Event findFirstBySio(String sio);
}
