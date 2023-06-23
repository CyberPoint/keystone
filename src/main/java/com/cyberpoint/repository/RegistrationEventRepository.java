package com.cyberpoint.repository;

import com.cyberpoint.domain.RegistrationEvent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RegistrationEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegistrationEventRepository extends JpaRepository<RegistrationEvent, Long> {}
