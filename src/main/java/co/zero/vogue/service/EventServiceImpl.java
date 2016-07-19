package co.zero.vogue.service;

import co.zero.common.files.ExcelUtils;
import co.zero.vogue.helper.EventFileHelper;
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

    @Override
    public Page<Event> list(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    @Override
    public Event find(long incidentId) {
        return eventRepository.findOne(incidentId);
    }

    @Override
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event update(Event event) {
        return save(event);
    }

    @Override
    public void bulkLoad(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(DEFAULT_SHEET_INDEX);
        int lastRowIndex = sheet.getLastRowNum();
        Function<String, Employee> employeeValidator = employeeRepository::findFirstByNameIgnoreCase;
        Function<String, Area> areaValidator = areaRepository::findFirstByNameOrderByNameAsc;
        EventFileHelper fileHelper = new EventFileHelper(employeeValidator, employeeValidator, areaValidator);

        for (int rowIndex = DEFAULT_START_ROW_INDEX; rowIndex <= lastRowIndex; rowIndex++) {
            System.out.println(":::: Row to process = " + rowIndex);
            Row currentRow = sheet.getRow(rowIndex);
            System.out.println(ExcelUtils.rowToString(currentRow));
            fileHelper.processRow(currentRow);

            if(fileHelper.isValidRow()){
                Event event = fileHelper.buildEventFromRow();
                event = eventRepository.save(event);
                Task task = fileHelper.buildTaskFromRow(event);
                taskRepository.save(task);
            }
        }
    }
}
