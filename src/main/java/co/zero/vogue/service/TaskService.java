package co.zero.vogue.service;

import co.zero.vogue.model.Task;
import co.zero.vogue.report.ReportTasksByEmployee;
import co.zero.vogue.report.ReportTasksClosedInLastYear;
import co.zero.vogue.report.ReportTasksOpenPerEventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by htenjo on 6/2/16.
 */
public interface TaskService {
    Page<Task> list(Pageable pageable);
    Task find(long taskId);
    Task save(Task task);
    Task update(Task task);
    Page<Task> listCloseToExpire(Pageable pageable);
    ReportTasksClosedInLastYear reportClosedTasksInLastYear();
    List<ReportTasksOpenPerEventType> reportOpenTasksPerEventType();
    Page<List<ReportTasksByEmployee>> reportTasksByEmployee(Date startDate, Date endDate, Pageable pageable);
}
