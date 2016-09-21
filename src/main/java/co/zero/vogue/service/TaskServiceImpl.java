package co.zero.vogue.service;

import co.zero.vogue.common.Constants;
import co.zero.vogue.model.Task;
import co.zero.vogue.persistence.TaskRepository;
import co.zero.vogue.report.ReportTasksByEmployee;
import co.zero.vogue.report.ReportTasksClosedInLastYear;
import co.zero.vogue.report.ReportTasksOpenPerEventType;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    public ReportTasksClosedInLastYear reportClosedTasksInLastYear() {
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        startDate.set(Calendar.YEAR, startDate.get(Calendar.YEAR) - 1);
        startDate.set(Calendar.DAY_OF_MONTH, startDate.getActualMinimum(Calendar.DAY_OF_MONTH));
        endDate.set(Calendar.DAY_OF_MONTH, endDate.getActualMaximum(Calendar.DAY_OF_MONTH));

        String startDateFormated = DateFormatUtils.format(startDate, Constants.DEFAULT_DATE_FORMAT);
        String endDateFormated = DateFormatUtils.format(endDate, Constants.DEFAULT_DATE_FORMAT);
        ReportTasksClosedInLastYear report = repository.reportClosedTasksInLastYear(startDate.getTime(), endDate.getTime());
        report.setStartDate(startDateFormated);
        report.setEndDate(endDateFormated);
        return report;
    }

    /**
     *
     * @return
     */
    @Override
    public List<ReportTasksOpenPerEventType> reportOpenTasksPerEventType() {
        return repository.reportOpenTasksPerEventType();
    }

    /**
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public Page<List<ReportTasksByEmployee>> reportTasksByEmployee(Date startDate, Date endDate, Pageable pageable) {
        return repository.reportTasksByEmployee(startDate, endDate, pageable);
    }
}