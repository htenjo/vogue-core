package co.zero.vogue.service;

import co.zero.vogue.helper.EventFileCleanerHelper;
import co.zero.vogue.helper.EventFileProcessingHelper;
import co.zero.vogue.model.Area;
import co.zero.vogue.model.Employee;
import co.zero.vogue.model.Event;
import co.zero.vogue.model.Task;
import co.zero.vogue.persistence.AreaRepository;
import co.zero.vogue.persistence.EmployeeRepository;
import co.zero.vogue.persistence.EventRepository;
import co.zero.vogue.persistence.TaskRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * Created by htenjo on 6/2/16.
 */
@Service
public class EventServiceImpl implements EventService {
    private static final int DEFAULT_SHEET_INDEX = 0;
    private static final int DEFAULT_START_ROW_INDEX = 9;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    AreaRepository areaRepository;

    @Autowired
    TaskRepository taskRepository;

    /**
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<Event> list(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    /**
     *
     * @param incidentId
     * @return
     */
    @Override
    public Event find(long incidentId) {
        return eventRepository.findOne(incidentId);
    }

    /**
     *
     * @param event
     * @return
     */
    @Override
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    /**
     *
     * @param event
     * @return
     */
    @Override
    public Event update(Event event) {
        return save(event);
    }

    /**
     *
     */
    @Override
    public void deleteAll() {
        taskRepository.deleteAll();
        eventRepository.deleteAll();
    }

    /**
     * This method load a cleaned file with the information of the last events
     * @param workbook The file with the information to be processed
     */
    @Override
    public void bulkLoad(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(DEFAULT_SHEET_INDEX);
        int lastRowIndex = sheet.getLastRowNum();
        Function<String, Employee> employeeValidator = employeeRepository::findFirstByNameIgnoreCase;
        Function<String, Area> areaValidator = areaRepository::findFirstByNameOrderByNameAsc;
        EventFileProcessingHelper fileHelper = new EventFileProcessingHelper(employeeValidator, employeeValidator, areaValidator);

        for (int rowIndex = DEFAULT_START_ROW_INDEX; rowIndex <= lastRowIndex; rowIndex++) {
            Row currentRow = sheet.getRow(rowIndex);

            if(currentRow != null) {
                fileHelper.processRow(currentRow);

                if (fileHelper.isValidRow()) {
                    try {
                        Event event = fileHelper.buildEventFromRow();
                        event = eventRepository.save(event);
                        Task task = fileHelper.buildTaskFromRow(event);
                        taskRepository.save(task);
                    } catch (Exception e) {
                        fileHelper.addRowErrorMessage("Error saving the row in the DB (Maybe the SIO already exist)");
                    }
                }
            }
        }
    }

    /**
     * This method creates a new sheet in the index-0 of the book and copy there the information
     * of the old sheet-0 (now sheet-1) without styles or hidden formats the could generate errors
     * in the final processing.
     * @param workbook The file with the original information to be processed
     */
    @Override
    public void copyOriginalFileInCleanFile(Workbook workbook) {
        EventFileCleanerHelper fileCleanerHelper = new EventFileCleanerHelper();
        fileCleanerHelper.clean(workbook, DEFAULT_SHEET_INDEX, DEFAULT_START_ROW_INDEX);
    }

    /**
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<Event> listCloseToExpire(Pageable pageable) {
        return eventRepository.findCloseToExpire(pageable);
    }
}