package com.cyberpoint.service;

import com.cyberpoint.domain.Agent;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Agent}.
 */
public interface AgentService {
    /**
     * Save a agent.
     *
     * @param agent the entity to save.
     * @return the persisted entity.
     */
    Agent save(Agent agent);

    /**
     * Updates a agent.
     *
     * @param agent the entity to update.
     * @return the persisted entity.
     */
    Agent update(Agent agent);

    /**
     * Partially updates a agent.
     *
     * @param agent the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Agent> partialUpdate(Agent agent);

    /**
     * Get all the agents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Agent> findAll(Pageable pageable);

    /**
     * Get all the Agent where RegistrationEvent is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Agent> findAllWhereRegistrationEventIsNull();

    /**
     * Get the "id" agent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Agent> findOne(Long id);

    /**
     * Delete the "id" agent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
