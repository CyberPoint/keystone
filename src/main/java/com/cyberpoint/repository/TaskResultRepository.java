package com.cyberpoint.repository;

import com.cyberpoint.domain.TaskResult;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TaskResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskResultRepository extends JpaRepository<TaskResult, Long> {}
