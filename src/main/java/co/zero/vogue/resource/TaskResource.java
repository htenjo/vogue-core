package co.zero.vogue.resource;

import co.zero.vogue.model.Event;
import co.zero.vogue.model.Task;
import co.zero.vogue.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by htenjo on 5/29/16.
 */
@RestController
@RequestMapping("/event/{id}/task")
@CrossOrigin(origins = "*")
public class TaskResource {
    @Autowired
    TaskService service;

    @RequestMapping(method = RequestMethod.GET)
    //TODO: Determine if is still required the JsonView to not retrieve all relations
    public Page<Task> list(Pageable pageable){
        return service.list(pageable);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Task findEvent(@PathVariable("id") long eventId){
        return service.find(eventId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Task> save(@RequestBody Task task){
        Task taskPersisted = service.save(task);
        return new ResponseEntity<>(taskPersisted, HttpStatus.CREATED);
    }
}