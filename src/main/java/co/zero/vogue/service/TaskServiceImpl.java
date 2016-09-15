package co.zero.vogue.service;

import co.zero.vogue.common.Constant;
import co.zero.vogue.model.Event;
import co.zero.vogue.model.Task;
import co.zero.vogue.persistence.EventRepository;
import co.zero.vogue.persistence.TaskRepository;
import co.zero.vogue.report.ReportClosedTasksInLastYear;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.Calendar;

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

    /**
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<Task> listCloseToExpire(Pageable pageable) {
        return repository.findCloseToExpire(pageable);
    }

    /**
     *
     * @return
     */
    @Override
    public ReportClosedTasksInLastYear reportClosedTasksInLastYear() {
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        startDate.set(Calendar.YEAR, startDate.get(Calendar.YEAR) - 1);
        startDate.set(Calendar.DAY_OF_MONTH, startDate.getActualMinimum(Calendar.DAY_OF_MONTH));
        endDate.set(Calendar.DAY_OF_MONTH, endDate.getActualMaximum(Calendar.DAY_OF_MONTH));

        String startDateFormated = DateFormatUtils.format(startDate, Constant.DEFAULT_DATE_FORMAT);
        String endDateFormated = DateFormatUtils.format(endDate, Constant.DEFAULT_DATE_FORMAT);
        ReportClosedTasksInLastYear report = repository.reportClosedTasksInLastYear(startDate.getTime(), endDate.getTime());
        report.setStartDate(startDateFormated);
        report.setEndDate(endDateFormated);
        return report;
    }
}