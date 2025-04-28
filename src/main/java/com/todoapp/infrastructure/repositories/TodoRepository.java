package com.todoapp.infrastructure.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todoapp.infrastructure.data_mappers.TodoDataMapper;

@Repository
public interface TodoRepository extends JpaRepository<TodoDataMapper, UUID> {

    Page<TodoDataMapper> findByUserId(UUID id, Pageable pageable);
}
