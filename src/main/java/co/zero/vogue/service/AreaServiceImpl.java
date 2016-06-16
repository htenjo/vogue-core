package co.zero.vogue.service;

import co.zero.vogue.model.Area;
import co.zero.vogue.model.Employee;
import co.zero.vogue.persistence.AreaRepository;
import co.zero.vogue.persistence.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by htenjo on 6/2/16.
 */
@Service
//TODO: Add basic validations to all methods (Not null, required, etc)
public class AreaServiceImpl implements AreaService {
    @Autowired
    AreaRepository repository;

    @Override
    public Page<Area> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Area save(Area area) {
        return repository.save(area);
    }

    @Override
    public Area find(long id) {
        return repository.findOne(id);
    }
}
