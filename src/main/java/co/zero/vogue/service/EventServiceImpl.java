package co.zero.vogue.service;

import co.zero.vogue.model.Event;
import co.zero.vogue.persistence.EventRepository;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
    EventRepository repository;

    @Override
    public Page<Event> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Event find(long incidentId) {
        return repository.findOne(incidentId);
    }

    @Override
    public Event save(Event event) {
        return repository.save(event);
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
                //0. If event not exist then create it
                //1. If event exist then just create the new task
                //2. Move required attributes from Task to Event
                String task = currentRow.getCell(TASKS_COLUMN_INDEX).getStringCellValue();
            }
        }
    }

    private boolean validateRow(Row row){
        //TODO: Implement this validators
        //Validate if the SIO is a number
        //Validate if the type is a valid EventType
        //Validate if the created date is a valid date
        //Validate if severity is a valid SeverityType
        //Validate if probability is a valid ProbabilityType
        return false;
    }

    private Event buildEventFromRow(Row row){
        String sio = row.getCell(SIO_COLUMN_INDEX).getStringCellValue();
        String type = row.getCell(TYPE_COLUMN_INDEX).getStringCellValue();
        String collaborator = row.getCell(COLLABORATOR_COLUMN_INDEX).getStringCellValue();
        String area = row.getCell(AREA_COLUMN_INDEX).getStringCellValue();
        Date createdDate = row.getCell(CREATED_DATE_COLUMN_INDEX).getDateCellValue();
        String description = row.getCell(DESCRIPTION_COLUMN_INDEX).getStringCellValue();
        String measures = row.getCell(MEASURES_COLUMN_INDEX).getStringCellValue();
        String responsible = row.getCell(RESPONSIBLE_COLUMN_INDEX).getStringCellValue();
        String severity = row.getCell(SEVERITY_COLUMN_INDEX).getStringCellValue();
        String probability = row.getCell(PROBABILITY_COLUMN_INDEX).getStringCellValue();

        //TODO: When all required attributes be in the entity create it!
        Event event = null;
        return event;
    }
}
