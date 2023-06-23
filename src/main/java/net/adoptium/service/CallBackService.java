package com.cyberpoint.service;

import com.cyberpoint.domain.CallBack;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CallBack}.
 */
public interface CallBackService {
    /**
     * Save a callBack.
     *
     * @param callBack the entity to save.
     * @return the persisted entity.
     */
    CallBack save(CallBack callBack);

    /**
     * Updates a callBack.
     *
     * @param callBack the entity to update.
     * @return the persisted entity.
     */
    CallBack update(CallBack callBack);

    /**
     * Partially updates a callBack.
     *
     * @param callBack the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CallBack> partialUpdate(CallBack callBack);

    /**
     * Get all the callBacks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CallBack> findAll(Pageable pageable);

    /**
     * Get the "id" callBack.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CallBack> findOne(Long id);

    /**
     * Delete the "id" callBack.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
