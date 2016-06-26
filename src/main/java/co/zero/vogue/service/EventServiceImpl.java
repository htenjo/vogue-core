package co.zero.vogue.service;

import co.zero.common.files.ExcelUtils;
import co.zero.vogue.common.type.EventType;
import co.zero.vogue.common.type.ProbabilityType;
import co.zero.vogue.common.type.SeverityType;
import co.zero.vogue.model.Area;
import co.zero.vogue.model.Employee;
import co.zero.vogue.model.Event;
import co.zero.vogue.model.Task;
import co.zero.vogue.persistence.AreaRepository;
import co.zero.vogue.persistence.EmployeeRepository;
import co.zero.vogue.persistence.EventRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by htenjo on 6/2/16.
 */
@Service
public class EventServiceImpl implements EventService {
    private static final int DEFAULT_SHEET_INDEX = 0;
    private static final int DEFAULT_START_ROW_INDEX = 9;
    private static final int SIO_COLUMN_INDEX = 0;
    private static final int TYPE_COLUMN_INDEX = 1;
    private static final int COLLABORATOR_COLUMN_INDEX = 2;
    private static final int AREA_COLUMN_INDEX = 3;
    private static final int CREATED_DATE_COLUMN_INDEX = 4;
    private static final int DESCRIPTION_COLUMN_INDEX = 5;
    private static final int MEASURES_COLUMN_INDEX = 6;
    private static final int TASKS_COLUMN_INDEX = 7;
    private static final int RESPONSIBLE_COLUMN_INDEX = 8;
    private static final int SEVERITY_COLUMN_INDEX = 9;
    private static final int PROBABILITY_COLUMN_INDEX = 10;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    AreaRepository areaRepository;

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

        for (int rowIndex = DEFAULT_START_ROW_INDEX; rowIndex <= lastRowIndex; rowIndex++) {
            Row currentRow = sheet.getRow(rowIndex);

            if(validateRow(currentRow)){
                Event event = buildEventFromRow(currentRow);
                //TODO: Implements this logic
                //1. If event exist then just create the new task
                String task = currentRow.getCell(TASKS_COLUMN_INDEX).getStringCellValue();
            }
        }
    }

    private boolean validateRow(Row row){
        boolean validRow = true;
        CellStyle validStyle = ExcelUtils.buildBasicCellStyle(
                row, IndexedColors.LIGHT_GREEN.getIndex(), CellStyle.SOLID_FOREGROUND);
        CellStyle errorStyle = ExcelUtils.buildBasicCellStyle(
                row, IndexedColors.RED.getIndex(), CellStyle.SOLID_FOREGROUND);

        validRow &= validateSIO(row.getCell(SIO_COLUMN_INDEX), validStyle, errorStyle);
        validRow &= validateType(row.getCell(TYPE_COLUMN_INDEX), validStyle, errorStyle);
        validRow &= validateCreatedDate(row.getCell(CREATED_DATE_COLUMN_INDEX), validStyle, errorStyle);
        validRow &= validateSeverity(row.getCell(SEVERITY_COLUMN_INDEX), validStyle, errorStyle);
        validRow &= validateProbability(row.getCell(PROBABILITY_COLUMN_INDEX), validStyle, errorStyle);
        validRow &= validateArea(row.getCell(AREA_COLUMN_INDEX), validStyle, errorStyle);
        validRow &= validateEmployee(row.getCell(COLLABORATOR_COLUMN_INDEX), validStyle, errorStyle);
        validRow &= validateEmployee(row.getCell(RESPONSIBLE_COLUMN_INDEX), validStyle, errorStyle);
        return validRow;
    }

    private Event buildEventFromRow(Row row){
        Event event;
        String sio = getSIO(row.getCell(SIO_COLUMN_INDEX));

        if(eventRepository.existBySio(sio)){
            event = eventRepository.findFirstBySio(sio);
        }else{
            EventType type = getEventTypeFromCell(row.getCell(TYPE_COLUMN_INDEX));
            Employee collaborator = getEmployeeFromCell(row.getCell(COLLABORATOR_COLUMN_INDEX));
            Area area = getAreaFromCell(row.getCell(AREA_COLUMN_INDEX));
            Date createdDate = getCreatedDateFromCell(row.getCell(CREATED_DATE_COLUMN_INDEX));
            String description = row.getCell(DESCRIPTION_COLUMN_INDEX).getStringCellValue();
            String measures = row.getCell(MEASURES_COLUMN_INDEX).getStringCellValue();
            SeverityType severity = getSeverityTypeFromCell(row.getCell(SEVERITY_COLUMN_INDEX));
            ProbabilityType probability = getProbabilityTypeFromCell(row.getCell(PROBABILITY_COLUMN_INDEX));
            event = new Event(sio, type, collaborator, area, createdDate, description,
                    measures, severity, probability);
        }

        return event;
    }

    private Task buildTaskFromRow(Row row, Event event){
        Task task = new Task();
        task.setEvent(event);
        task.setCreatedDate(new Date());
        task.setPercentageCompleted(0);
        task.setResponsible(null);
        task.setExpectedClosedDate();
        return task;
    }

    //TODO: Think a better way the help to simplify this amount of similar methods... maybe a lambda
    private boolean validateSIO(Cell cell, CellStyle validStyle, CellStyle errorStyle){
        try{
            getSIO(cell);
            cell.setCellStyle(validStyle);
            return true;
        }catch (IllegalArgumentException e){
            cell.setCellStyle(errorStyle);
            return false;
        }
    }

    private boolean validateType(Cell cell, CellStyle validStyle, CellStyle errorStyle){
        try{
            getEventTypeFromCell(cell);
            cell.setCellStyle(validStyle);
            return true;
        }catch (IllegalArgumentException e){
            cell.setCellStyle(errorStyle);
            return false;
        }
    }

    private boolean validateCreatedDate(Cell cell, CellStyle validStyle, CellStyle errorStyle) {
        try {
            getCreatedDateFromCell(cell);
            cell.setCellStyle(validStyle);
            return true;
        } catch (IllegalArgumentException e) {
            cell.setCellStyle(errorStyle);
            return false;
        }
    }

    private boolean validateSeverity(Cell cell, CellStyle validStyle, CellStyle errorStyle){
        try{
            getSeverityTypeFromCell(cell);
            cell.setCellStyle(validStyle);
            return true;
        }catch (IllegalArgumentException e){
            cell.setCellStyle(errorStyle);
            return false;
        }
    }

    private boolean validateProbability(Cell cell, CellStyle validStyle, CellStyle errorStyle){
        try{
            getProbabilityTypeFromCell(cell);
            cell.setCellStyle(validStyle);
            return true;
        }catch (IllegalArgumentException e){
            cell.setCellStyle(errorStyle);
            return false;
        }
    }

    //TODO: Think a better way to know if a employee exist without retrieve the employee
    private boolean validateEmployee(Cell cell, CellStyle validStyle, CellStyle errorStyle){
        try{
            getEmployeeFromCell(cell);
            cell.setCellStyle(validStyle);
            return true;
        }catch (IllegalArgumentException e){
            cell.setCellStyle(errorStyle);
            return false;
        }
    }

    //TODO: Think a better way to know if a employee exist without retrieve the employee
    private boolean validateArea(Cell cell, CellStyle validStyle, CellStyle errorStyle){
        try{
            getAreaFromCell(cell);
            cell.setCellStyle(validStyle);
            return true;
        }catch (IllegalArgumentException e){
            cell.setCellStyle(errorStyle);
            return false;
        }
    }

    private String getSIO(Cell cell){
        Object value = ExcelUtils.getCellValue(cell);

        if(value instanceof Number){
            return value.toString();
        }else if(value instanceof String && StringUtils.isNumeric((String)value)){
            return (String) value;
        }else{
            throw new IllegalArgumentException("SIO should be Number or String");
        }
    }

    private EventType getEventTypeFromCell(Cell cell){
        try{
            Object value = ExcelUtils.getCellValue(cell);
            return EventType.valueOf((String) value);
        }catch(NullPointerException e){
            throw new IllegalArgumentException(e);
        }
    }

    private SeverityType getSeverityTypeFromCell(Cell cell){
        try{
            Object value = ExcelUtils.getCellValue(cell);
            return SeverityType.valueOf((String) value);
        }catch(NullPointerException e){
            throw new IllegalArgumentException(e);
        }
    }

    private ProbabilityType getProbabilityTypeFromCell(Cell cell){
        try{
            Object value = ExcelUtils.getCellValue(cell);
            return ProbabilityType.valueOf((String) value);
        }catch(NullPointerException e){
            throw new IllegalArgumentException(e);
        }
    }

    private Date getCreatedDateFromCell(Cell cell){
        Object value = ExcelUtils.getCellValue(cell);

        if(value instanceof Date){
            return (Date) value;
        }else{
            throw new IllegalArgumentException("Invalid date value in cell createdDate");
        }
    }

    private Employee getEmployeeFromCell(Cell cell){
        Object value = ExcelUtils.getCellValue(cell);
        String employeeName;

        if(value instanceof String){
            employeeName = (String) value;

            if(employeeRepository.existByName(employeeName)){
                return employeeRepository.findFirstByNameIgnoreCase(employeeName);
            }else{
                throw new IllegalArgumentException("Employee not found by name");
            }
        }else{
            throw new IllegalArgumentException("Employee name should be a String");
        }

    }

    private Area getAreaFromCell(Cell cell){
        Object value = ExcelUtils.getCellValue(cell);
        String areaName;

        if(value instanceof String){
            areaName = (String) value;

            if(areaRepository.existByName(areaName)){
                return areaRepository.findFirstByNameOrderByNameAsc(areaName);
            }else{
                throw new IllegalArgumentException("Employee not found by name");
            }
        }else{
            throw new IllegalArgumentException("Employee name should be a String");
        }

    }
}
