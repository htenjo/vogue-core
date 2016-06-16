package co.zero.vogue.service;

import co.zero.vogue.model.Area;
import co.zero.vogue.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by htenjo on 6/2/16.
 */
public interface AreaService {
    public Page<Area> list(Pageable pageable);
    public Area save(Area area);
    public Area find(long id);
}
