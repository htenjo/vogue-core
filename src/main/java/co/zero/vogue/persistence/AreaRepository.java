package co.zero.vogue.persistence;

import co.zero.vogue.model.Area;
import co.zero.vogue.model.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by htenjo on 6/1/16.
 */
public interface AreaRepository extends PagingAndSortingRepository<Area, Long> {
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Area a WHERE a.name = :name")
    boolean existByName(@Param("name") String name);

    Area findFirstByNameOrderByNameAsc(String name);
}
