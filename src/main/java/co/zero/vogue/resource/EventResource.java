package co.zero.vogue.resource;

import co.zero.common.files.ExcelUtils;
import co.zero.vogue.model.Event;
import co.zero.vogue.service.EventService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by htenjo on 5/29/16.
 */
@RestController
@RequestMapping("/event")
@CrossOrigin
public class EventResource {
    @Autowired
    EventService service;

    @RequestMapping(method = RequestMethod.GET)
    //TODO: Determine if is still required the JsonView to not retrieve all relations
    public Page<Event> list(Pageable pageable){
        return service.list(pageable);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Event findEvent(@PathVariable("id") long eventId){
        return service.find(eventId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Event> save(@RequestBody Event event){
        Event eventPersisted = service.save(event);
        return new ResponseEntity<>(eventPersisted, HttpStatus.CREATED);
    }

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

    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteAll(){
        service.deleteAll();
    }
}
