package com.chervonnaya.dao;

import com.chervonnaya.model.Status;
import com.chervonnaya.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskDAO extends BaseDAO<Task>{
    Page<Task> findAll(Pageable pageable);
}
