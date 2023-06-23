package com.cyberpoint.service;

import com.cyberpoint.domain.RegistrationEvent;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link RegistrationEvent}.
 */
public interface RegistrationEventService {
    /**
     * Save a registrationEvent.
     *
     * @param registrationEvent the entity to save.
     * @return the persisted entity.
     */
    RegistrationEvent save(RegistrationEvent registrationEvent);

    /**
     * Updates a registrationEvent.
     *
     * @param registrationEvent the entity to update.
     * @return the persisted entity.
     */
    RegistrationEvent update(RegistrationEvent registrationEvent);

    /**
     * Partially updates a registrationEvent.
     *
     * @param registrationEvent the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RegistrationEvent> partialUpdate(RegistrationEvent registrationEvent);

    /**
     * Get all the registrationEvents.
     *
     * @return the list of entities.
     */
    List<RegistrationEvent> findAll();

    /**
     * Get the "id" registrationEvent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RegistrationEvent> findOne(Long id);

    /**
     * Delete the "id" registrationEvent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
