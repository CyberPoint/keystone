package com.cyberpoint.service.impl;

import com.cyberpoint.domain.Agent;
import com.cyberpoint.repository.AgentRepository;
import com.cyberpoint.service.AgentService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Agent}.
 */
@Service
@Transactional
public class AgentServiceImpl implements AgentService {

    private final Logger log = LoggerFactory.getLogger(AgentServiceImpl.class);

    private final AgentRepository agentRepository;

    public AgentServiceImpl(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @Override
    public Agent save(Agent agent) {
        log.debug("Request to save Agent : {}", agent);
        return agentRepository.save(agent);
    }

    @Override
    public Agent update(Agent agent) {
        log.debug("Request to update Agent : {}", agent);
        return agentRepository.save(agent);
    }

    @Override
    public Optional<Agent> partialUpdate(Agent agent) {
        log.debug("Request to partially update Agent : {}", agent);

        return agentRepository
            .findById(agent.getId())
            .map(existingAgent -> {
                if (agent.getName() != null) {
                    existingAgent.setName(agent.getName());
                }
                if (agent.getClassification() != null) {
                    existingAgent.setClassification(agent.getClassification());
                }
                if (agent.getDescription() != null) {
                    existingAgent.setDescription(agent.getDescription());
                }
                if (agent.getInstalledOn() != null) {
                    existingAgent.setInstalledOn(agent.getInstalledOn());
                }
                if (agent.getUninstallDate() != null) {
                    existingAgent.setUninstallDate(agent.getUninstallDate());
                }
                if (agent.getActive() != null) {
                    existingAgent.setActive(agent.getActive());
                }
                if (agent.getDeactivate() != null) {
                    existingAgent.setDeactivate(agent.getDeactivate());
                }
                if (agent.getAutoRegistered() != null) {
                    existingAgent.setAutoRegistered(agent.getAutoRegistered());
                }
                if (agent.getApproved() != null) {
                    existingAgent.setApproved(agent.getApproved());
                }

                return existingAgent;
            })
            .map(agentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Agent> findAll(Pageable pageable) {
        log.debug("Request to get all Agents");
        return agentRepository.findAll(pageable);
    }

    /**
     *  Get all the agents where RegistrationEvent is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Agent> findAllWhereRegistrationEventIsNull() {
        log.debug("Request to get all agents where RegistrationEvent is null");
        return StreamSupport
            .stream(agentRepository.findAll().spliterator(), false)
            .filter(agent -> agent.getRegistrationEvent() == null)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Agent> findOne(Long id) {
        log.debug("Request to get Agent : {}", id);
        return agentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Agent : {}", id);
        agentRepository.deleteById(id);
    }
}
