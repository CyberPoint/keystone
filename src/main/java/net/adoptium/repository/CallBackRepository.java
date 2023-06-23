package com.cyberpoint.repository;

import com.cyberpoint.domain.CallBack;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CallBack entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CallBackRepository extends JpaRepository<CallBack, Long> {}
