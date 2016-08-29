package co.zero.vogue.service;

import co.zero.vogue.model.Event;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by htenjo on 6/2/16.
 */
public interface EventService {
    public Page<Event> list(Pageable pageable);
    public Event find(long incidentId);
    public Event save(Event event);
    public Event update(Event event);
    public void bulkLoad(Workbook workbook);
    public void copyOriginalFileInCleanFile(Workbook workbook);
    public void deleteAll();
}
