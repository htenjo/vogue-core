package co.zero.vogue.helper;

import co.zero.common.files.ExcelUtils;
import org.apache.poi.ss.usermodel.*;

import java.util.Date;

/**
 * Created by htenjo on 8/28/16.
 */
public class EventFileCleanerHelper extends EventHelper{
    private CellStyle warningCellStyle;
    private CellStyle dateCellStyle;

    /**
     *
     * @param workbook
     */
    public void clean(Workbook workbook, int sheetIndex, int startRowIndex){
        Sheet oldSheet = workbook.getSheetAt(sheetIndex);
        Sheet cleanSheet = workbook.createSheet("CleanSheet");
        workbook.setSheetOrder(cleanSheet.getSheetName(), sheetIndex);
        workbook.setSheetOrder(oldSheet.getSheetName(), sheetIndex + 1);
        workbook.setActiveSheet(sheetIndex);
        int lastRowIndex = oldSheet.getLastRowNum();
        String[] info = new String[MAX_COLUMNS];
        initStyles(cleanSheet);

        for (int rowIndex = startRowIndex; rowIndex < lastRowIndex; rowIndex++) {
            cleanInfo(info);

            try{
                row = oldSheet.getRow(rowIndex);
                readRowInfo(row, info);
                writeInfoToCleanSheet(cleanSheet, rowIndex, info);
            }catch(Exception e){
                System.out.println(":::: ERROR IN LINE " + rowIndex);
                throw e;
            }
        }
    }

    /**
     *
     * @param row
     * @param info
     */
    private void readRowInfo(Row row, String[] info){
        info[SIO_COLUMN_INDEX] = getStringValueFromCell(row.getCell(SIO_COLUMN_INDEX));
        info[TYPE_COLUMN_INDEX] = getStringValueFromCell(row.getCell(TYPE_COLUMN_INDEX));
        info[COLLABORATOR_COLUMN_INDEX] = getStringValueFromCell(row.getCell(COLLABORATOR_COLUMN_INDEX));
        info[AREA_COLUMN_INDEX] = getStringValueFromCell(row.getCell(AREA_COLUMN_INDEX));
        createdDate = getDateValueFromCell(row.getCell(CREATED_DATE_COLUMN_INDEX));
        info[DESCRIPTION_COLUMN_INDEX] = getStringValueFromCell(row.getCell(DESCRIPTION_COLUMN_INDEX));
        info[MEASURES_COLUMN_INDEX] = getStringValueFromCell(row.getCell(MEASURES_COLUMN_INDEX));
        info[TASK_DESCRIPTION_COLUMN_INDEX] = getStringValueFromCell(row.getCell(TASK_DESCRIPTION_COLUMN_INDEX));
        info[TASK_RESPONSIBLE_COLUMN_INDEX] = getStringValueFromCell(row.getCell(TASK_RESPONSIBLE_COLUMN_INDEX));
        info[SEVERITY_COLUMN_INDEX] = getStringValueFromCell(row.getCell(SEVERITY_COLUMN_INDEX));
        info[PROBABILITY_COLUMN_INDEX] = getStringValueFromCell(row.getCell(PROBABILITY_COLUMN_INDEX));
        taskPercentage = getNumericValueFromCell(row.getCell(TASK_PERCENTAGE_COLUMN_INDEX));
        taskClosedDate = getDateValueFromCell(row.getCell(TASK_CLOSED_DATE_COLUMN_INDEX));
        info[TASK_COMMENTS_COLUMN_INDEX] = getStringValueFromCell(row.getCell(TASK_COMMENTS_COLUMN_INDEX));
    }

    /**
     *
     * @param cleanSheet
     * @param rowIndex
     * @param info
     */
    private void writeInfoToCleanSheet(Sheet cleanSheet, int rowIndex, String[] info){
        Row row = cleanSheet.createRow(rowIndex);
        writeNumericValue(row, SIO_COLUMN_INDEX, info[SIO_COLUMN_INDEX]);
        row.createCell(TYPE_COLUMN_INDEX).setCellValue(info[TYPE_COLUMN_INDEX]);
        row.createCell(COLLABORATOR_COLUMN_INDEX).setCellValue(info[COLLABORATOR_COLUMN_INDEX]);
        row.createCell(AREA_COLUMN_INDEX).setCellValue(info[AREA_COLUMN_INDEX]);
        writeDateValue(row, CREATED_DATE_COLUMN_INDEX, createdDate);
        row.createCell(DESCRIPTION_COLUMN_INDEX).setCellValue(info[DESCRIPTION_COLUMN_INDEX]);
        row.createCell(MEASURES_COLUMN_INDEX).setCellValue(info[MEASURES_COLUMN_INDEX]);
        row.createCell(TASK_DESCRIPTION_COLUMN_INDEX).setCellValue(info[TASK_DESCRIPTION_COLUMN_INDEX]);
        row.createCell(TASK_RESPONSIBLE_COLUMN_INDEX).setCellValue(info[TASK_RESPONSIBLE_COLUMN_INDEX]);
        row.createCell(SEVERITY_COLUMN_INDEX).setCellValue(info[SEVERITY_COLUMN_INDEX]);
        row.createCell(PROBABILITY_COLUMN_INDEX).setCellValue(info[PROBABILITY_COLUMN_INDEX]);
        writeNumericValue(row, TASK_PERCENTAGE_COLUMN_INDEX, taskPercentage);
        writeDateValue(row, TASK_CLOSED_DATE_COLUMN_INDEX, taskClosedDate);
        row.createCell(TASK_COMMENTS_COLUMN_INDEX).setCellValue(info[TASK_COMMENTS_COLUMN_INDEX]);
    }

    /**
     *
     * @param info
     */
    private void cleanInfo(String[] info){
        for (int i = 0; i < info.length; i++) {
            info[i] = null;
        }
    }

    /**
     *
     * @param cell
     * @return
     */
    @Override
    protected Double getNumericValueFromCell(Cell cell){
        try{
            return ExcelUtils.getCellNumericValue(cell);
        }catch (Exception e){
            return null;
        }
    }

    /**
     *
     * @param cell
     * @return
     */
    @Override
    protected Date getDateValueFromCell(Cell cell) {
        try{
            return ExcelUtils.getCellDateValue(cell);
        }catch (Exception e){
            return null;
        }
    }

    /**
     *
     * @param row
     * @param cellIndex
     * @param date
     */
    protected void writeDateValue(Row row, int cellIndex, Date date){
        cell = row.createCell(cellIndex);

        if(date == null){
            cell.setCellStyle(warningCellStyle);
        }else{
            cell.setCellValue(date);
            cell.setCellStyle(dateCellStyle);
        }
    }

    /**
     *
     * @param row
     * @param cellIndex
     * @param number
     */
    protected void writeNumericValue(Row row, int cellIndex, Double number){
        cell = row.createCell(cellIndex);

        if(number == null){
            cell.setCellStyle(warningCellStyle);
        }else{
            cell.setCellValue(number);
        }
    }

    /**
     *
     * @param row
     * @param cellIndex
     * @param number
     */
    protected void writeNumericValue(Row row, int cellIndex, String number){
        cell = row.createCell(cellIndex);

        if(number == null){
            cell.setCellStyle(warningCellStyle);
        }else{
            try{
                cell.setCellValue(Double.parseDouble(number));
            }catch(NumberFormatException e){
                cell.setCellStyle(warningCellStyle);
            }
        }
    }

    /**
     *
     * @param sheet
     */
    private void initStyles(Sheet sheet){
        if(warningCellStyle == null){
            warningCellStyle = ExcelUtils.buildBasicCellStyle(sheet, IndexedColors.ROSE.getIndex(), CellStyle.SOLID_FOREGROUND);
        }

        if(dateCellStyle == null){
            CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
            dateCellStyle = sheet.getWorkbook().createCellStyle();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
        }
    }
}