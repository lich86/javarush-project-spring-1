package com.chervonnaya.service;

import com.chervonnaya.dao.TaskDAO;
import com.chervonnaya.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskDAO taskDAO;

    @Autowired
    public TaskServiceImpl(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Integer getAllCount() {
        return (int) taskDAO.count();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Page<Task> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page-1, size);
        return taskDAO.findAll(pageRequest);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Task save(Task task) {
        return taskDAO.save(task);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Task getById(Integer id) {
        return taskDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Task task) {
        taskDAO.delete(task);
    }


}
