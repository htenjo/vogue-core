package co.zero.vogue.persistence;

import co.zero.vogue.model.Incident;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by htenjo on 6/1/16.
 */
public interface IncidentRepository extends PagingAndSortingRepository<Incident, Long>{
}
