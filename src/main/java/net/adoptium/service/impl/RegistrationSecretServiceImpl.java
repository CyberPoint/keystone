package com.cyberpoint.service.impl;

import com.cyberpoint.domain.RegistrationSecret;
import com.cyberpoint.repository.RegistrationSecretRepository;
import com.cyberpoint.service.RegistrationSecretService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RegistrationSecret}.
 */
@Service
@Transactional
public class RegistrationSecretServiceImpl implements RegistrationSecretService {

    private final Logger log = LoggerFactory.getLogger(RegistrationSecretServiceImpl.class);

    private final RegistrationSecretRepository registrationSecretRepository;

    public RegistrationSecretServiceImpl(RegistrationSecretRepository registrationSecretRepository) {
        this.registrationSecretRepository = registrationSecretRepository;
    }

    @Override
    public RegistrationSecret save(RegistrationSecret registrationSecret) {
        log.debug("Request to save RegistrationSecret : {}", registrationSecret);
        return registrationSecretRepository.save(registrationSecret);
    }

    @Override
    public RegistrationSecret update(RegistrationSecret registrationSecret) {
        log.debug("Request to update RegistrationSecret : {}", registrationSecret);
        return registrationSecretRepository.save(registrationSecret);
    }

    @Override
    public Optional<RegistrationSecret> partialUpdate(RegistrationSecret registrationSecret) {
        log.debug("Request to partially update RegistrationSecret : {}", registrationSecret);

        return registrationSecretRepository
            .findById(registrationSecret.getId())
            .map(existingRegistrationSecret -> {
                if (registrationSecret.getUniqueValue() != null) {
                    existingRegistrationSecret.setUniqueValue(registrationSecret.getUniqueValue());
                }
                if (registrationSecret.getNumericalValue() != null) {
                    existingRegistrationSecret.setNumericalValue(registrationSecret.getNumericalValue());
                }

                return existingRegistrationSecret;
            })
            .map(registrationSecretRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistrationSecret> findAll() {
        log.debug("Request to get all RegistrationSecrets");
        return registrationSecretRepository.findAll();
    }

    /**
     *  Get all the registrationSecrets where RegistrationEvent is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RegistrationSecret> findAllWhereRegistrationEventIsNull() {
        log.debug("Request to get all registrationSecrets where RegistrationEvent is null");
        return StreamSupport
            .stream(registrationSecretRepository.findAll().spliterator(), false)
            .filter(registrationSecret -> registrationSecret.getRegistrationEvent() == null)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RegistrationSecret> findOne(Long id) {
        log.debug("Request to get RegistrationSecret : {}", id);
        return registrationSecretRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RegistrationSecret : {}", id);
        registrationSecretRepository.deleteById(id);
    }
}
