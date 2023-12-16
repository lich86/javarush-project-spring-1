package com.chervonnaya.service;

import com.chervonnaya.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public interface TaskService {
    @Transactional
    Integer getAllCount();
    @Transactional
    Page<Task> getAll(int page, int size);
    @Transactional
    Task save(Task task);
    @Transactional
    Task getById(Integer id);
    @Transactional
    void delete(Task task);


}
