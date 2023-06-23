package com.cyberpoint.service;

import com.cyberpoint.domain.TaskResult;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link TaskResult}.
 */
public interface TaskResultService {
    /**
     * Save a taskResult.
     *
     * @param taskResult the entity to save.
     * @return the persisted entity.
     */
    TaskResult save(TaskResult taskResult);

    /**
     * Updates a taskResult.
     *
     * @param taskResult the entity to update.
     * @return the persisted entity.
     */
    TaskResult update(TaskResult taskResult);

    /**
     * Partially updates a taskResult.
     *
     * @param taskResult the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TaskResult> partialUpdate(TaskResult taskResult);

    /**
     * Get all the taskResults.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TaskResult> findAll(Pageable pageable);

    /**
     * Get the "id" taskResult.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaskResult> findOne(Long id);

    /**
     * Delete the "id" taskResult.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
