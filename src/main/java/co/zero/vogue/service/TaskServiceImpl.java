package co.zero.vogue.service;

import co.zero.vogue.model.Event;
import co.zero.vogue.model.Task;
import co.zero.vogue.persistence.EventRepository;
import co.zero.vogue.persistence.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by htenjo on 6/2/16.
 */
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository repository;

    @Override
    public Page<Task> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Task find(long taskId) {
        return repository.findOne(taskId);
    }

    @Override
    public Task save(Task task) {
        return repository.save(task);
    }

    @Override
    public Task update(Task task) {
        return save(task);
    }
}