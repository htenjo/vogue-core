package co.zero.vogue.helper;

import co.zero.common.files.ExcelUtils;
import co.zero.vogue.model.Area;
import co.zero.vogue.model.Employee;
import co.zero.vogue.model.Event;
import co.zero.vogue.model.Task;
import org.apache.poi.ss.usermodel.*;

import java.util.function.Function;

/**
 * Created by htenjo on 6/28/16.
 */
public class EventFileProcessingHelper extends EventHelper{
    private boolean validRow;
    private CellStyle validStyle;
    private CellStyle warningStyle;
    private CellStyle errorStyle;
    private CellStyle validDateStyle;
    private CellStyle errorDateStyle;
    private Function<String, Employee> collaboratorValidator;
    private Function<String, Employee> responsibleValidator;
    private Function<String, Area> areaValidator;

    /**
     * Helper's constructor that requires the function that can find entities
     * @param collaboratorValidator function that can find collaborator by name
     * @param responsibleValidator function that can find responsible by name
     * @param areaValidator function that can find area by name
     */
    public EventFileProcessingHelper(Function<String, Employee> collaboratorValidator,
                                     Function<String, Employee> responsibleValidator,
                                     Function<String, Area> areaValidator) {
        this.collaboratorValidator = collaboratorValidator;
        this.responsibleValidator = responsibleValidator;
        this.areaValidator = areaValidator;
    }

    /**
     *
     * @param row
     */
    public void processRow(Row row){
        this.row = row;
        initStyles();
        validateRow(row);
        initAttributes();
    }

    /**
     *
     * @return
     */
    public Event buildEventFromRow(){
        if(validRow){
            return new Event(sio, type, collaborator, area, createdDate, description,
                    measures, severityType, probabilityType);
        }else{
            throw new IllegalArgumentException("Required fields in the row are invalid");
        }
    }

    /**
     *
     * @param event
     * @return
     */
    public Task buildTaskFromRow(Event event){
        if(validRow && event != null && event.getId() != null){
            Task task = new Task();
            task.setDescription(taskDescription);
            task.setCreatedDate(event.getCreatedDate());
            task.setPercentageCompleted(taskPercentage);
            task.setResponsible(responsible);
            task.setExpectedClosedDate(taskClosedDate);
            task.setClosedComments(taskComments);
            task.setEvent(event);
            return task;
        }else{
            throw new IllegalArgumentException("Required fields in the row are invalid");
        }
    }

    /**
     *
     * @param message
     */
    public void addRowErrorMessage(String message){
        if(row.getCell(VALID_ROW_COLUMN_INDEX) == null){
            row.createCell(VALID_ROW_COLUMN_INDEX);
        }

        row.getCell(VALID_ROW_COLUMN_INDEX).setCellValue(message);
        row.getCell(VALID_ROW_COLUMN_INDEX).setCellStyle(errorStyle);
    }

    /**
     *
     */
    private void initAttributes(){
        if(isValidRow()){
            sio = getSIOFromCell(row.getCell(SIO_COLUMN_INDEX));
            type = getEventTypeFromCell(row.getCell(TYPE_COLUMN_INDEX));
            createdDate = getCreatedDateFromCell(row.getCell(CREATED_DATE_COLUMN_INDEX));
            description = getStringValueFromCell(row.getCell(DESCRIPTION_COLUMN_INDEX));
            measures = getStringValueFromCell(row.getCell(MEASURES_COLUMN_INDEX));
            taskDescription = getStringValueFromCell(row.getCell(TASK_DESCRIPTION_COLUMN_INDEX));
            severityType = getSeverityTypeFromCell(row.getCell(SEVERITY_COLUMN_INDEX));
            probabilityType = getProbabilityTypeFromCell(row.getCell(PROBABILITY_COLUMN_INDEX));
            taskPercentage = getNumericValueFromCell(row.getCell(TASK_PERCENTAGE_COLUMN_INDEX));
            taskClosedDate = getDateValueFromCell(row.getCell(TASK_CLOSED_DATE_COLUMN_INDEX));
            taskComments = getStringValueFromCell(row.getCell(TASK_COMMENTS_COLUMN_INDEX));
        }
    }

    /**
     *
     */
    private void initStyles(){
        if(validStyle == null){
            validStyle = ExcelUtils.buildBasicCellStyle( this.row, IndexedColors.LIGHT_GREEN.getIndex(), CellStyle.SOLID_FOREGROUND);
            warningStyle = ExcelUtils.buildBasicCellStyle( this.row, IndexedColors.ORANGE.getIndex(), CellStyle.SOLID_FOREGROUND);
            errorStyle = ExcelUtils.buildBasicCellStyle( this.row, IndexedColors.RED.getIndex(), CellStyle.SOLID_FOREGROUND);

            DataFormat dataFormat = row.getSheet().getWorkbook().createDataFormat();
            validDateStyle = ExcelUtils.buildBasicCellStyle( this.row, IndexedColors.LIGHT_GREEN.getIndex(), CellStyle.SOLID_FOREGROUND);
            validDateStyle.setDataFormat(dataFormat.getFormat("yyyy-MM-dd"));
            errorDateStyle = ExcelUtils.buildBasicCellStyle( this.row, IndexedColors.ORANGE.getIndex(), CellStyle.SOLID_FOREGROUND);
        }
    }

    /**
     *
     * @param row
     */
    private void validateRow(Row row){
        Cell areaCell = row.getCell(AREA_COLUMN_INDEX) == null ?
                row.createCell(AREA_COLUMN_INDEX) : row.getCell(AREA_COLUMN_INDEX);
        Cell collaboratorCell = row.getCell(COLLABORATOR_COLUMN_INDEX) == null ?
                row.createCell(COLLABORATOR_COLUMN_INDEX) : row.getCell(COLLABORATOR_COLUMN_INDEX);
        Cell responsibleCell = row.getCell(TASK_RESPONSIBLE_COLUMN_INDEX) == null ?
                row.createCell(TASK_RESPONSIBLE_COLUMN_INDEX) : row.getCell(TASK_RESPONSIBLE_COLUMN_INDEX);

        validRow = true;
        validRow &= validateCell(row, SIO_COLUMN_INDEX, this::getSIOFromCell, validStyle, warningStyle);
        validRow &= validateCell(row, TYPE_COLUMN_INDEX, this::getEventTypeFromCell, validStyle, warningStyle);
        validRow &= validateCollaborator(collaboratorCell);
        validRow &= validateArea(areaCell);
        validRow &= validateCell(row, CREATED_DATE_COLUMN_INDEX, this::getCreatedDateFromCell, validDateStyle, errorDateStyle);
        validRow &= validateCell(row, DESCRIPTION_COLUMN_INDEX, this::getRequiredString, validStyle, warningStyle);
        validRow &= validateCell(row, TASK_DESCRIPTION_COLUMN_INDEX, this::getRequiredString, validStyle, warningStyle);
        validRow &= validateResponsible(responsibleCell);
        validRow &= validateCell(row, SEVERITY_COLUMN_INDEX, this::getSeverityTypeFromCell, validStyle, warningStyle);
        validRow &= validateCell(row, PROBABILITY_COLUMN_INDEX, this::getProbabilityTypeFromCell, validStyle, warningStyle);
        setRowValidationResult();
    }

    /**
     *
     * @param row
     * @param cellIndex
     * @param function
     * @param validStyle
     * @param errorStyle
     * @return
     */
    private boolean validateCell(Row row, int cellIndex, Function<Cell, Object> function, CellStyle validStyle, CellStyle errorStyle){
        Cell cell = row.getCell(cellIndex) == null ? row.createCell(cellIndex) : row.getCell(cellIndex);

        try{
            function.apply(cell);
            cell.setCellStyle(validStyle);
            return true;
        }catch (IllegalArgumentException | NullPointerException  e){
            cell.setCellStyle(warningStyle);
            return false;
        }
    }

    /**
     *
     * @param collaboratorCell
     * @return
     */
    private boolean validateCollaborator(Cell collaboratorCell){
        boolean validCollaborator;

        try{
            collaborator = getEmployeeFromCell(collaboratorCell, collaboratorValidator);
            validCollaborator = collaborator != null;
        }catch(IllegalArgumentException | NullPointerException e){
            validCollaborator = false;
        }

        applyValidationStyleToEntityCell(validCollaborator, collaboratorCell);
        return validCollaborator;
    }

    /**
     *
     * @param responsibleCell
     * @return
     */
    private boolean validateResponsible(Cell responsibleCell){
        boolean validResponsible;

        try{
            responsible = getEmployeeFromCell(responsibleCell, responsibleValidator);
            validResponsible = responsible != null;
        }catch(IllegalArgumentException | NullPointerException e){
            validResponsible = false;
        }

        applyValidationStyleToEntityCell(validResponsible, responsibleCell);
        return validResponsible;
    }

    /**
     *
     * @param cell
     * @param validator
     * @return
     */
    private Employee getEmployeeFromCell(Cell cell, Function<String, Employee> validator){
        String employeeName = getRequiredString(cell);
        return validator.apply(employeeName);
    }

    /**
     *
     * @param areaCell
     * @return
     */
    private boolean validateArea(Cell areaCell){
        boolean validArea;

        try{
            String areaName = getRequiredString(areaCell);
            area = areaValidator.apply(areaName);
            validArea = area != null;
        }catch(IllegalArgumentException e){
            validArea = false;
        }

        applyValidationStyleToEntityCell(validArea, areaCell);
        return validArea;
    }

    /**
     *
     * @param validEntity
     * @param entityCell
     */
    private void applyValidationStyleToEntityCell(boolean validEntity, Cell entityCell){
        if(entityCell != null) {
            if (validEntity) {
                entityCell.setCellStyle(validStyle);
            } else {
                validRow &= false;
                entityCell.setCellStyle(warningStyle);
            }
        }
    }

    /**
     *
     */
    private void setRowValidationResult(){
        if(row.getCell(VALID_ROW_COLUMN_INDEX) == null){
            row.createCell(VALID_ROW_COLUMN_INDEX);
        }

        if(validRow){
            row.getCell(VALID_ROW_COLUMN_INDEX).setCellStyle(validStyle);
        }else{
            row.getCell(VALID_ROW_COLUMN_INDEX).setCellStyle(warningStyle);
        }
    }

    /**
     *
     * @return
     */
    public boolean isValidRow() {
        return validRow;
    }
}
