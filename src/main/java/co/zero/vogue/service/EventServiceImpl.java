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
import co.zero.vogue.persistence.TaskRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

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
    private static final int TASK_DESCRIPTION_COLUMN_INDEX = 7;
    private static final int TASK_RESPONSIBLE_COLUMN_INDEX = 8;
    private static final int SEVERITY_COLUMN_INDEX = 9;
    private static final int PROBABILITY_COLUMN_INDEX = 10;
    private static final int TASK_PERCENTAGE_COLUMN_INDEX = 12;
    private static final int TASK_CLOSED_DATE_COLUMN_INDEX = 13;
    private static final int TASK_COMMENTS_COLUMN_INDEX = 15;
    private static final int VALID_ROW_COLUMN_INDEX = 16;

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

        for (int rowIndex = DEFAULT_START_ROW_INDEX; rowIndex <= lastRowIndex; rowIndex++) {
            Row currentRow = sheet.getRow(rowIndex);

            if(validateRow(currentRow)){
                Event event = buildEventFromRow(currentRow);
                buildTaskFromRow(currentRow, event);
            }
        }
    }

    private boolean validateRow(Row row){
        boolean validRow = true;
        CellStyle validStyle = ExcelUtils.buildBasicCellStyle(
                row, IndexedColors.LIGHT_GREEN.getIndex(), CellStyle.SOLID_FOREGROUND);
        CellStyle errorStyle = ExcelUtils.buildBasicCellStyle(
                row, IndexedColors.RED.getIndex(), CellStyle.SOLID_FOREGROUND);

        validRow &= validateCell(row.getCell(SIO_COLUMN_INDEX), validStyle, errorStyle, this::getSIO);
        validRow &= validateCell(row.getCell(TYPE_COLUMN_INDEX), validStyle, errorStyle, this::getEventTypeFromCell);
        validRow &= validateCell(row.getCell(CREATED_DATE_COLUMN_INDEX), validStyle, errorStyle, this::getCreatedDateFromCell);
        validRow &= validateCell(row.getCell(SEVERITY_COLUMN_INDEX), validStyle, errorStyle, this::getSeverityTypeFromCell);
        validRow &= validateCell(row.getCell(PROBABILITY_COLUMN_INDEX), validStyle, errorStyle, this::getProbabilityTypeFromCell);
        validRow &= validateCell(row.getCell(AREA_COLUMN_INDEX), validStyle, errorStyle, this::getAreaFromCell);
        validRow &= validateCell(row.getCell(COLLABORATOR_COLUMN_INDEX), validStyle, errorStyle, this::getEmployeeFromCell);
        validRow &= validateCell(row.getCell(TASK_RESPONSIBLE_COLUMN_INDEX), validStyle, errorStyle, this::getEmployeeFromCell);
        validRow &= validateCell(row.getCell(DESCRIPTION_COLUMN_INDEX), validStyle, errorStyle, this::getRequiredString);
        validRow &= validateCell(row.getCell(TASK_DESCRIPTION_COLUMN_INDEX), validStyle, errorStyle, this::getRequiredString);

        if(validRow){
            row.createCell(VALID_ROW_COLUMN_INDEX).setCellStyle(validStyle);
        }else{
            row.createCell(VALID_ROW_COLUMN_INDEX).setCellStyle(errorStyle);
        }

        return validRow;
    }

    private boolean validateCell(Cell cell, CellStyle validStyle, CellStyle errorStyle, Function<Cell, Object> function){
        try{
            function.apply(cell);
            cell.setCellStyle(validStyle);
            return true;
        }catch (IllegalArgumentException e){
            cell.setCellStyle(errorStyle);
            return false;
        }
    }

    private Event buildEventFromRow(Row row){
        String sio = getSIO(row.getCell(SIO_COLUMN_INDEX));

        if(eventRepository.existBySio(sio)){
            return eventRepository.findFirstBySio(sio);
        }else{
            EventType type = getEventTypeFromCell(row.getCell(TYPE_COLUMN_INDEX));
            Employee collaborator = getEmployeeFromCell(row.getCell(COLLABORATOR_COLUMN_INDEX));
            Area area = getAreaFromCell(row.getCell(AREA_COLUMN_INDEX));
            Date createdDate = getCreatedDateFromCell(row.getCell(CREATED_DATE_COLUMN_INDEX));
            String description = row.getCell(DESCRIPTION_COLUMN_INDEX).getStringCellValue();
            String measures = row.getCell(MEASURES_COLUMN_INDEX).getStringCellValue();
            SeverityType severity = getSeverityTypeFromCell(row.getCell(SEVERITY_COLUMN_INDEX));
            ProbabilityType probability = getProbabilityTypeFromCell(row.getCell(PROBABILITY_COLUMN_INDEX));
            Event event = new Event(sio, type, collaborator, area, createdDate, description,
                    measures, severity, probability);
            return eventRepository.save(event);
        }
    }

    private Task buildTaskFromRow(Row row, Event event){
        String description = ExcelUtils.getCellStringValue(row.getCell(TASK_DESCRIPTION_COLUMN_INDEX));
        double percentage = ExcelUtils.getCellNumericValue(row.getCell(TASK_PERCENTAGE_COLUMN_INDEX));
        Date closedDate = ExcelUtils.getCellDateValue(row.getCell(TASK_CLOSED_DATE_COLUMN_INDEX));
        String comments = ExcelUtils.getCellStringValue(row.getCell(TASK_COMMENTS_COLUMN_INDEX));
        Employee responsible = getEmployeeFromCell(row.getCell(TASK_RESPONSIBLE_COLUMN_INDEX));

        Task task = new Task();
        task.setDescription(description);
        task.setCreatedDate(event.getCreatedDate());
        task.setPercentageCompleted(percentage);
        task.setResponsible(responsible);
        task.setExpectedClosedDate(closedDate);
        task.setClosedComments(comments);
        task.setEvent(event);
        return taskRepository.save(task);
    }

    private String getSIO(Cell cell){
        Object value = ExcelUtils.getCellValue(cell);

        if(value instanceof Number){
            return value.toString();
        }else if(value instanceof String && StringUtils.isNumeric((String)value)){
            return (String) value;
        }else{
            throw new IllegalArgumentException("SIO should be a Number");
        }
    }

    private EventType getEventTypeFromCell(Cell cell){
        try{
            String value = ExcelUtils.getCellStringValue(cell);
            return EventType.valueOf(value);
        }catch(NullPointerException e){
            throw new IllegalArgumentException(e);
        }
    }

    private SeverityType getSeverityTypeFromCell(Cell cell){
        try{
            String value = ExcelUtils.getCellStringValue(cell);
            return SeverityType.valueOf(value);
        }catch(NullPointerException e){
            throw new IllegalArgumentException(e);
        }
    }

    private ProbabilityType getProbabilityTypeFromCell(Cell cell){
        try{
            String value = ExcelUtils.getCellStringValue(cell);
            return ProbabilityType.valueOf(value);
        }catch(NullPointerException e){
            throw new IllegalArgumentException(e);
        }
    }

    private Date getCreatedDateFromCell(Cell cell){
        return ExcelUtils.getCellDateValue(cell);
    }

    private String getRequiredString(Cell cell){
        String value = ExcelUtils.getCellStringValue(cell);

        if(StringUtils.isBlank(value)){
            throw new IllegalArgumentException("Required field empty");
        }else{
            return value;
        }
    }

    private Employee getEmployeeFromCell(Cell cell){
        String employeeName = ExcelUtils.getCellStringValue(cell);

        if(employeeRepository.existByName(employeeName)){
            return employeeRepository.findFirstByNameIgnoreCase(employeeName);
        }else{
            throw new IllegalArgumentException("Employee not found by name");
        }
    }

    private Area getAreaFromCell(Cell cell){
        String areaName = ExcelUtils.getCellStringValue(cell);

        if(areaRepository.existByName(areaName)){
            return areaRepository.findFirstByNameOrderByNameAsc(areaName);
        }else{
            throw new IllegalArgumentException("Area not found by name");
        }
    }
}
