package com.cyberpoint.service;

import com.cyberpoint.domain.RegistrationSecret;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link RegistrationSecret}.
 */
public interface RegistrationSecretService {
    /**
     * Save a registrationSecret.
     *
     * @param registrationSecret the entity to save.
     * @return the persisted entity.
     */
    RegistrationSecret save(RegistrationSecret registrationSecret);

    /**
     * Updates a registrationSecret.
     *
     * @param registrationSecret the entity to update.
     * @return the persisted entity.
     */
    RegistrationSecret update(RegistrationSecret registrationSecret);

    /**
     * Partially updates a registrationSecret.
     *
     * @param registrationSecret the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RegistrationSecret> partialUpdate(RegistrationSecret registrationSecret);

    /**
     * Get all the registrationSecrets.
     *
     * @return the list of entities.
     */
    List<RegistrationSecret> findAll();

    /**
     * Get all the RegistrationSecret where RegistrationEvent is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<RegistrationSecret> findAllWhereRegistrationEventIsNull();

    /**
     * Get the "id" registrationSecret.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RegistrationSecret> findOne(Long id);

    /**
     * Delete the "id" registrationSecret.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
