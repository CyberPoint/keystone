package com.cyberpoint.service.impl;

import com.cyberpoint.domain.RegistrationEvent;
import com.cyberpoint.repository.RegistrationEventRepository;
import com.cyberpoint.service.RegistrationEventService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RegistrationEvent}.
 */
@Service
@Transactional
public class RegistrationEventServiceImpl implements RegistrationEventService {

    private final Logger log = LoggerFactory.getLogger(RegistrationEventServiceImpl.class);

    private final RegistrationEventRepository registrationEventRepository;

    public RegistrationEventServiceImpl(RegistrationEventRepository registrationEventRepository) {
        this.registrationEventRepository = registrationEventRepository;
    }

    @Override
    public RegistrationEvent save(RegistrationEvent registrationEvent) {
        log.debug("Request to save RegistrationEvent : {}", registrationEvent);
        return registrationEventRepository.save(registrationEvent);
    }

    @Override
    public RegistrationEvent update(RegistrationEvent registrationEvent) {
        log.debug("Request to update RegistrationEvent : {}", registrationEvent);
        return registrationEventRepository.save(registrationEvent);
    }

    @Override
    public Optional<RegistrationEvent> partialUpdate(RegistrationEvent registrationEvent) {
        log.debug("Request to partially update RegistrationEvent : {}", registrationEvent);

        return registrationEventRepository
            .findById(registrationEvent.getId())
            .map(existingRegistrationEvent -> {
                if (registrationEvent.getIpAddress() != null) {
                    existingRegistrationEvent.setIpAddress(registrationEvent.getIpAddress());
                }
                if (registrationEvent.getRawContents() != null) {
                    existingRegistrationEvent.setRawContents(registrationEvent.getRawContents());
                }
                if (registrationEvent.getRemotePort() != null) {
                    existingRegistrationEvent.setRemotePort(registrationEvent.getRemotePort());
                }
                if (registrationEvent.getName() != null) {
                    existingRegistrationEvent.setName(registrationEvent.getName());
                }
                if (registrationEvent.getApproved() != null) {
                    existingRegistrationEvent.setApproved(registrationEvent.getApproved());
                }
                if (registrationEvent.getRegistrationDate() != null) {
                    existingRegistrationEvent.setRegistrationDate(registrationEvent.getRegistrationDate());
                }

                return existingRegistrationEvent;
            })
            .map(registrationEventRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistrationEvent> findAll() {
        log.debug("Request to get all RegistrationEvents");
        return registrationEventRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RegistrationEvent> findOne(Long id) {
        log.debug("Request to get RegistrationEvent : {}", id);
        return registrationEventRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RegistrationEvent : {}", id);
        registrationEventRepository.deleteById(id);
    }
}
