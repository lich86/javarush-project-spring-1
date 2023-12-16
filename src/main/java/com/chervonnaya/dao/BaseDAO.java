package com.chervonnaya.dao;

import com.chervonnaya.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseDAO<T extends BaseEntity> extends JpaRepository<T, Integer> {
}
