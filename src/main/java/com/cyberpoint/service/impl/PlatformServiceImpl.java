package com.cyberpoint.service.impl;

import com.cyberpoint.domain.Platform;
import com.cyberpoint.repository.PlatformRepository;
import com.cyberpoint.service.PlatformService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Platform}.
 */
@Service
@Transactional
public class PlatformServiceImpl implements PlatformService {

    private final Logger log = LoggerFactory.getLogger(PlatformServiceImpl.class);

    private final PlatformRepository platformRepository;

    public PlatformServiceImpl(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }

    @Override
    public Platform save(Platform platform) {
        log.debug("Request to save Platform : {}", platform);
        return platformRepository.save(platform);
    }

    @Override
    public Platform update(Platform platform) {
        log.debug("Request to update Platform : {}", platform);
        return platformRepository.save(platform);
    }

    @Override
    public Optional<Platform> partialUpdate(Platform platform) {
        log.debug("Request to partially update Platform : {}", platform);

        return platformRepository
            .findById(platform.getId())
            .map(existingPlatform -> {
                if (platform.getName() != null) {
                    existingPlatform.setName(platform.getName());
                }
                if (platform.getDescription() != null) {
                    existingPlatform.setDescription(platform.getDescription());
                }
                if (platform.getAccessLevel() != null) {
                    existingPlatform.setAccessLevel(platform.getAccessLevel());
                }
                if (platform.getVersion() != null) {
                    existingPlatform.setVersion(platform.getVersion());
                }
                if (platform.getOs() != null) {
                    existingPlatform.setOs(platform.getOs());
                }
                if (platform.getOsContentType() != null) {
                    existingPlatform.setOsContentType(platform.getOsContentType());
                }
                if (platform.getAdded() != null) {
                    existingPlatform.setAdded(platform.getAdded());
                }
                if (platform.getUpdated() != null) {
                    existingPlatform.setUpdated(platform.getUpdated());
                }
                if (platform.getActive() != null) {
                    existingPlatform.setActive(platform.getActive());
                }

                return existingPlatform;
            })
            .map(platformRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Platform> findAll(Pageable pageable) {
        log.debug("Request to get all Platforms");
        return platformRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Platform> findOne(Long id) {
        log.debug("Request to get Platform : {}", id);
        return platformRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Platform : {}", id);
        platformRepository.deleteById(id);
    }
}
