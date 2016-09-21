package co.zero.vogue.service;

import co.zero.vogue.model.Area;
import co.zero.vogue.model.Event;
import co.zero.vogue.report.ReportEventsCreatedByArea;
import co.zero.vogue.report.ReportEventsCreatedByEventType;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by htenjo on 6/2/16.
 */
public interface EventService {
    Page<Event> list(Pageable pageable);
    Event find(long incidentId);
    Event save(Event event);
    Event update(Event event);
    void bulkLoad(Workbook workbook);
    void copyOriginalFileInCleanFile(Workbook workbook);
    void deleteAll();
    Page<Event> listCloseToExpire(Pageable pageable);
    Page<List<ReportEventsCreatedByArea>> createdByArea(Date startDate, Date endDate, Pageable pageable);
    Long countByCreatedDateBetween(Date startDate, Date endDate);
    List<ReportEventsCreatedByEventType> createdByEventType(Date startDate, Date endDate);
}
