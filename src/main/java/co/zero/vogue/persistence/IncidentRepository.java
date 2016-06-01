package co.zero.vogue.persistence;

import co.zero.vogue.model.Incident;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by htenjo on 6/1/16.
 */
public interface IncidentRepository extends CrudRepository<Incident, Long>{
}
