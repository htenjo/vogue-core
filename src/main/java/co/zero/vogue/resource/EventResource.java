package co.zero.vogue.resource;

import co.zero.common.files.ExcelUtils;
import co.zero.vogue.common.Constants;
import co.zero.vogue.model.Event;
import co.zero.vogue.report.ReportEventsCreatedByArea;
import co.zero.vogue.report.ReportEventsCreatedByEventType;
import co.zero.vogue.service.EventService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by htenjo on 5/29/16.
 */
@RestController
@RequestMapping("/event")
@CrossOrigin
public class EventResource {
    @Autowired
    EventService service;

    /**
     *
     * @param pageable
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    //TODO: Determine if is still required the JsonView to not retrieve all relations
    public Page<Event> list(Pageable pageable){
        return service.list(pageable);
    }

    /**
     *
     * @param eventId
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Event findEvent(@PathVariable("id") long eventId){
        return service.find(eventId);
    }

    /**
     *
     * @param event
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Event> save(@RequestBody Event event){
        Event eventPersisted = service.save(event);
        return new ResponseEntity<>(eventPersisted, HttpStatus.CREATED);
    }

    /**
     * Massive loader of events
     * @param file File with the required information to be processed. This file should't
     *             have combined cells nor cells with special or hidden format.
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    @RequestMapping(value = "/bulkLoad", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> bulkLoadEvents(
            @RequestParam(value="eventsFile") MultipartFile file) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        service.bulkLoad(workbook);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        ByteArrayResource byteArrayResource = new ByteArrayResource(baos.toByteArray());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", "inline; filename=processed-" + file.getOriginalFilename());

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .body(byteArrayResource);
    }

    /**
     *
     */
    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteAll(){
        service.deleteAll();
    }

    /**
     *
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/report/closeToExpire", method = RequestMethod.GET)
    //TODO: Determine if is still required the JsonView to not retrieve all relations
    public Page<Event> reportListCloseToExpire(Pageable pageable){
        return service.listCloseToExpire(pageable);
    }

    /**
     *
     * @param startDate
     * @param endDate
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/report/createdByArea", method = RequestMethod.GET)
    public Page<List<ReportEventsCreatedByArea>> reportCreatedByArea(
            @RequestParam(name = "startDate")
            @DateTimeFormat(pattern = Constants.DEFAULT_DATE_FORMAT)
                    Date startDate,
            @RequestParam(name = "endDate")
            @DateTimeFormat(pattern = Constants.DEFAULT_DATE_FORMAT)
                    Date endDate,
            Pageable pageable) {
        return service.createdByArea(startDate, endDate, pageable);
    }

    /**
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "/report/countCreated", method = RequestMethod.GET)
    public Long reportCountCreatedBetween(
            @RequestParam(name = "startDate")
            @DateTimeFormat(pattern = Constants.DEFAULT_DATE_FORMAT)
                    Date startDate,
            @RequestParam(name = "endDate")
            @DateTimeFormat(pattern = Constants.DEFAULT_DATE_FORMAT)
                    Date endDate) {
        return service.countByCreatedDateBetween(startDate, endDate);
    }

    /**
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "/report/createdByEventType", method = RequestMethod.GET)
    public List<ReportEventsCreatedByEventType> reportCreatedByEventType(
            @RequestParam(name = "startDate")
            @DateTimeFormat(pattern = Constants.DEFAULT_DATE_FORMAT)
                    Date startDate,
            @RequestParam(name = "endDate")
            @DateTimeFormat(pattern = Constants.DEFAULT_DATE_FORMAT)
                    Date endDate) {
        return service.createdByEventType(startDate, endDate);
    }
}
