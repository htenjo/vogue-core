package co.zero.vogue.resource;

import co.zero.vogue.model.Area;
import co.zero.vogue.model.Event;
import co.zero.vogue.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by htenjo on 6/8/16.
 */
@RestController
@RequestMapping("/area")
@CrossOrigin
public class AreaResource {
    @Autowired
    AreaService service;

    @RequestMapping(method = RequestMethod.GET)
    //TODO: Determine if is still required the JsonView to not retrieve all relations
    public Page<Area> list(Pageable pageable){
        return service.list(pageable);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Area findEvent(@PathVariable("id") long areaId){
        return service.find(areaId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Area> save(@RequestBody Area area){
        Area areaPersisted = service.save(area);
        return new ResponseEntity<>(areaPersisted, HttpStatus.CREATED);
    }
}
