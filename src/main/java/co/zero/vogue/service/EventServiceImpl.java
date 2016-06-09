package co.zero.vogue.service;

import co.zero.vogue.common.type.EventType;
import co.zero.vogue.common.type.ProbabilityType;
import co.zero.vogue.common.type.SeverityType;
import co.zero.vogue.model.Event;
import co.zero.vogue.persistence.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by htenjo on 6/2/16.
 */
@Service
public class EventServiceImpl implements EventService {
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
}
