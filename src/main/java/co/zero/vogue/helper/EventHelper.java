package co.zero.vogue.helper;

import co.zero.common.files.ExcelUtils;
import co.zero.vogue.common.type.EventType;
import co.zero.vogue.common.type.ProbabilityType;
import co.zero.vogue.common.type.SeverityType;
import co.zero.vogue.model.Area;
import co.zero.vogue.model.Employee;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;

import java.util.Date;

/**
 * Created by htenjo on 8/28/16.
 */
public abstract class EventHelper {
    //Column index in excel file
    protected static final int SIO_COLUMN_INDEX = 0;
    protected static final int TYPE_COLUMN_INDEX = 1;
    protected static final int COLLABORATOR_COLUMN_INDEX = 2;
    protected static final int AREA_COLUMN_INDEX = 3;
    protected static final int CREATED_DATE_COLUMN_INDEX = 4;
    protected static final int DESCRIPTION_COLUMN_INDEX = 5;
    protected static final int MEASURES_COLUMN_INDEX = 6;
    protected static final int TASK_DESCRIPTION_COLUMN_INDEX = 7;
    protected static final int TASK_RESPONSIBLE_COLUMN_INDEX = 8;
    protected static final int SEVERITY_COLUMN_INDEX = 9;
    protected static final int PROBABILITY_COLUMN_INDEX = 10;
    protected static final int TASK_PERCENTAGE_COLUMN_INDEX = 12;
    protected static final int TASK_CLOSED_DATE_COLUMN_INDEX = 13;
    protected static final int TASK_COMMENTS_COLUMN_INDEX = 15;
    protected static final int VALID_ROW_COLUMN_INDEX = 16;
    protected static final int MAX_COLUMNS = 17;

    //Default attributes in the excel
    protected String sio;
    protected EventType type;
    protected Date createdDate;
    protected String description;
    protected String measures;
    protected String taskDescription;
    protected SeverityType severityType;
    protected ProbabilityType probabilityType;
    protected Double taskPercentage;
    protected Date taskClosedDate;
    protected String taskComments;
    protected Employee collaborator;
    protected Employee responsible;
    protected Area area;

    //Helper attributes
    protected Row row;
    protected Cell cell;

    /**
     *
     * @param cell
     * @return
     */
    protected String getSIOFromCell(Cell cell){
        Object value = ExcelUtils.getCellValue(cell);
        DataFormatter formatter = new DataFormatter();

        if(value instanceof Number || (value instanceof String && StringUtils.isNumeric((String)value))){
            return formatter.formatCellValue(cell);
        }else{
            throw new IllegalArgumentException("SIO should be a Number");
        }
    }

    /**
     *
     * @param cell
     * @return
     */
    protected EventType getEventTypeFromCell(Cell cell){
        try{
            String value = ExcelUtils.getCellStringValueNoSpaces(cell);
            return EventType.valueOf(value);
        }catch(NullPointerException e){
            throw new IllegalArgumentException(e);
        }
    }

    /**
     *
     * @param cell
     * @return
     */
    protected SeverityType getSeverityTypeFromCell(Cell cell){
        try{
            String value = ExcelUtils.getCellStringValueNoSpaces(cell);
            return SeverityType.valueOf(value);
        }catch(NullPointerException e){
            throw new IllegalArgumentException(e);
        }
    }

    /**
     *
     * @param cell
     * @return
     */
    protected ProbabilityType getProbabilityTypeFromCell(Cell cell){
        try{
            String value = ExcelUtils.getCellStringValueNoSpaces(cell);
            return ProbabilityType.valueOf(value);
        }catch(NullPointerException e){
            throw new IllegalArgumentException(e);
        }
    }

    /**
     *
     * @param cell
     * @return
     */
    protected Date getCreatedDateFromCell(Cell cell){
        return ExcelUtils.getCellDateValue(cell);
    }

    /**
     *
     * @param cell
     * @return
     */
    protected String getRequiredString(Cell cell){
        String value = ExcelUtils.getCellStringValueNoSpaces(cell);

        if(StringUtils.isBlank(value)){
            throw new IllegalArgumentException("Required field empty");
        }else{
            return value;
        }
    }

    /**
     *
     * @param cell
     * @return
     */
    protected String getStringValueFromCell(Cell cell){
        if(cell != null){
            return ExcelUtils.getCellStringValueNoSpaces(cell);
        }else{
            return null;
        }
    }

    /**
     *
     * @param cell
     * @return
     */
    protected Date getDateValueFromCell(Cell cell){
        Object value = ExcelUtils.getCellValue(cell);

        if(value instanceof Date){
            return (Date) value;
        }else{
            return null;
        }
    }

    /**
     *
     * @param cell
     * @return
     */
    protected Double getNumericValueFromCell(Cell cell){
        Object value = ExcelUtils.getCellValue(cell);

        if(value instanceof Double){
            return (Double) value;
        }else{
            return 0d;
        }
    }
}