package co.zero.vogue.persistence;

import co.zero.vogue.model.Event;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by htenjo on 6/1/16.
 */
public interface EventRepository extends PagingAndSortingRepository<Event, Long>{
}
