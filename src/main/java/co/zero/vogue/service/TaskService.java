package co.zero.vogue.service;

import co.zero.vogue.model.Task;
import co.zero.vogue.report.ReportClosedTasksInLastYear;
import co.zero.vogue.report.ReportOpenTasksPerEventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by htenjo on 6/2/16.
 */
public interface TaskService {
    public Page<Task> list(Pageable pageable);
    public Task find(long taskId);
    public Task save(Task task);
    public Task update(Task task);
    public Page<Task> listCloseToExpire(Pageable pageable);
    public ReportClosedTasksInLastYear reportClosedTasksInLastYear();
    public List<ReportOpenTasksPerEventType> reportOpenTasksPerEventType();
}
