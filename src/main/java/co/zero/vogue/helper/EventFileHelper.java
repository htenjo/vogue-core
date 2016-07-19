package co.zero.vogue.helper;

import co.zero.common.files.ExcelUtils;
import co.zero.vogue.common.type.EventType;
import co.zero.vogue.common.type.ProbabilityType;
import co.zero.vogue.common.type.SeverityType;
import co.zero.vogue.model.Area;
import co.zero.vogue.model.Employee;
import co.zero.vogue.model.Event;
import co.zero.vogue.model.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;

import java.util.Date;
import java.util.function.Function;

/**
 * Created by htenjo on 6/28/16.
 */
public class EventFileHelper {
    //Column index in excel file
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

    //Default attributes in the excel
    private String sio;
    private EventType type;
    private Date createdDate;
    private String description;
    private String measures;
    private String taskDescription;
    private SeverityType severityType;
    private ProbabilityType probabilityType;
    private double taskPercentage;
    private Date taskClosedDate;
    private String taskComments;
    private Employee collaborator;
    private Employee responsible;
    private Area area;

    //Helper attributes
    private Row row;
    private boolean validRow;
    private CellStyle validStyle;
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
    public EventFileHelper(Function<String, Employee> collaboratorValidator,
                           Function<String, Employee> responsibleValidator,
                           Function<String, Area> areaValidator) {
        this.collaboratorValidator = collaboratorValidator;
        this.responsibleValidator = responsibleValidator;
        this.areaValidator = areaValidator;
    }

    public void processRow(Row row){
        this.row = row;
        initStyles();
        validateRow(row);
        initAttributes();
    }

    public Event buildEventFromRow(){
        if(validRow){
            return new Event(sio, type, collaborator, area, createdDate, description,
                    measures, severityType, probabilityType);
        }else{
            throw new IllegalArgumentException("Required fields in the row are invalid");
        }
    }

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

    private void initStyles(){
        if(validStyle == null){
            validStyle = ExcelUtils.buildBasicCellStyle( this.row, IndexedColors.LIGHT_GREEN.getIndex(), CellStyle.SOLID_FOREGROUND);
            errorStyle = ExcelUtils.buildBasicCellStyle( this.row, IndexedColors.RED.getIndex(), CellStyle.SOLID_FOREGROUND);

            DataFormat dataFormat = row.getSheet().getWorkbook().createDataFormat();
            validDateStyle = ExcelUtils.buildBasicCellStyle( this.row, IndexedColors.LIGHT_GREEN.getIndex(), CellStyle.SOLID_FOREGROUND);
            validDateStyle.setDataFormat(dataFormat.getFormat("yyyy-MM-dd"));
            errorDateStyle = ExcelUtils.buildBasicCellStyle( this.row, IndexedColors.RED.getIndex(), CellStyle.SOLID_FOREGROUND);
        }
    }

    private void validateRow(Row row){
        validRow = true;
        validRow &= validateCell(row.getCell(SIO_COLUMN_INDEX), this::getSIOFromCell, validStyle, errorStyle);
        validRow &= validateCell(row.getCell(TYPE_COLUMN_INDEX), this::getEventTypeFromCell, validStyle, errorStyle);
        validRow &= validateCollaborator(row.getCell(COLLABORATOR_COLUMN_INDEX));
        validRow &= validateArea(row.getCell(AREA_COLUMN_INDEX));
        validRow &= validateCell(row.getCell(CREATED_DATE_COLUMN_INDEX), this::getCreatedDateFromCell, validDateStyle, errorDateStyle);
        validRow &= validateCell(row.getCell(DESCRIPTION_COLUMN_INDEX), this::getRequiredString, validStyle, errorStyle);
        validRow &= validateCell(row.getCell(TASK_DESCRIPTION_COLUMN_INDEX), this::getRequiredString, validStyle, errorStyle);
        validRow &= validateResponsible(row.getCell(TASK_RESPONSIBLE_COLUMN_INDEX));
        validRow &= validateCell(row.getCell(SEVERITY_COLUMN_INDEX), this::getSeverityTypeFromCell, validStyle, errorStyle);
        validRow &= validateCell(row.getCell(PROBABILITY_COLUMN_INDEX), this::getProbabilityTypeFromCell, validStyle, errorStyle);
        setRowValidationResult();
    }

    private boolean validateCell(Cell cell, Function<Cell, Object> function, CellStyle validStyle, CellStyle errorStyle){
        try{
            function.apply(cell);
            cell.setCellStyle(validStyle);
            System.out.println("Cell " + cell.getColumnIndex() + " is VALID");
            return true;
        }catch (IllegalArgumentException e){
            cell.setCellStyle(errorStyle);
            System.out.println("Cell " + cell.getColumnIndex() + " is INVALID");
            return false;
        }
    }

    private boolean validateCollaborator(Cell collaboratorCell){
        boolean validCollaborator;

        try{
            collaborator = getEmployeeFromCell(collaboratorCell, collaboratorValidator);
            validCollaborator = collaborator != null;
        }catch(IllegalArgumentException e){
            validCollaborator = false;
        }

        applyValidationStyleToEntityCell(validCollaborator, collaboratorCell);
        System.out.println("Cell " + collaboratorCell.getColumnIndex() + " is " + (validCollaborator ? "VALID" : "INVALID"));
        return validCollaborator;
    }

    private boolean validateResponsible(Cell responsibleCell){
        boolean validResponsible;

        try{
            responsible = getEmployeeFromCell(responsibleCell, responsibleValidator);
            validResponsible = responsible != null;
        }catch(IllegalArgumentException e){
            validResponsible = false;
        }

        applyValidationStyleToEntityCell(validResponsible, responsibleCell);
        System.out.println("Cell " + responsibleCell.getColumnIndex() + " is " + (validResponsible ? "VALID" : "X-INVALID"));
        return validResponsible;
    }

    private Employee getEmployeeFromCell(Cell cell, Function<String, Employee> validator){
        String employeeName = getRequiredString(cell);
        return validator.apply(employeeName);
    }

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

    private void applyValidationStyleToEntityCell(boolean validEntity, Cell entityCell){
        validStyle = ExcelUtils.buildBasicCellStyle( this.row, IndexedColors.LIGHT_GREEN.getIndex(), CellStyle.SOLID_FOREGROUND);
        errorStyle = ExcelUtils.buildBasicCellStyle( this.row, IndexedColors.RED.getIndex(), CellStyle.SOLID_FOREGROUND);

        DataFormat dataFormat = row.getSheet().getWorkbook().createDataFormat();
        validDateStyle = ExcelUtils.buildBasicCellStyle( this.row, IndexedColors.LIGHT_GREEN.getIndex(), CellStyle.SOLID_FOREGROUND);
        validDateStyle.setDataFormat(dataFormat.getFormat("yyyy-MM-dd"));
        errorDateStyle = ExcelUtils.buildBasicCellStyle( this.row, IndexedColors.RED.getIndex(), CellStyle.SOLID_FOREGROUND);


        if(validEntity){
            entityCell.getCellStyle().setFillForegroundColor(validStyle.getFillForegroundColor());
            entityCell.getCellStyle().setFillPattern(validStyle.getFillPattern());
        }else{
            validRow &= false;
            entityCell.getCellStyle().setFillForegroundColor(errorStyle.getFillForegroundColor());
            entityCell.getCellStyle().setFillPattern(errorStyle.getFillPattern());
        }
    }

    private String getSIOFromCell(Cell cell){
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

    private String getStringValueFromCell(Cell cell){
        return ExcelUtils.getCellStringValue(cell);
    }

    private Date getDateValueFromCell(Cell cell){
        Object value = ExcelUtils.getCellValue(cell);

        if(value instanceof Date){
            return (Date) value;
        }else{
            return null;
        }
    }

    private double getNumericValueFromCell(Cell cell){
        Object value = ExcelUtils.getCellValue(cell);

        if(value instanceof Double){
            return (Double) value;
        }else{
            return 0;
        }
    }

    private void setRowValidationResult(){
        if(row.getCell(VALID_ROW_COLUMN_INDEX) == null){
            row.createCell(VALID_ROW_COLUMN_INDEX);
        }

        if(validRow){
            row.getCell(VALID_ROW_COLUMN_INDEX).setCellStyle(validStyle);
        }else{
            row.getCell(VALID_ROW_COLUMN_INDEX).setCellStyle(errorStyle);
        }
    }

    public boolean isValidRow() {
        return validRow;
    }
}
