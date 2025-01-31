package com.alkemy.technical.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alkemy.technical.test.entities.Task;

@Repository
public interface ITaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(Boolean status);
}
