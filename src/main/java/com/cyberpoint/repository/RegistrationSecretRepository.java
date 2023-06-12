package com.cyberpoint.repository;

import com.cyberpoint.domain.RegistrationSecret;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RegistrationSecret entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegistrationSecretRepository extends JpaRepository<RegistrationSecret, Long> {}
