package com.chervonnaya.dao;

import com.chervonnaya.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;



@Repository
public interface TaskDAO extends BaseDAO<Task>{
    Page<Task> findAll(Pageable pageable);
}
