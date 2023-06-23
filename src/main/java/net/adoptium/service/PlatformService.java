package com.cyberpoint.service;

import com.cyberpoint.domain.Platform;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Platform}.
 */
public interface PlatformService {
    /**
     * Save a platform.
     *
     * @param platform the entity to save.
     * @return the persisted entity.
     */
    Platform save(Platform platform);

    /**
     * Updates a platform.
     *
     * @param platform the entity to update.
     * @return the persisted entity.
     */
    Platform update(Platform platform);

    /**
     * Partially updates a platform.
     *
     * @param platform the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Platform> partialUpdate(Platform platform);

    /**
     * Get all the platforms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Platform> findAll(Pageable pageable);

    /**
     * Get the "id" platform.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Platform> findOne(Long id);

    /**
     * Delete the "id" platform.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
