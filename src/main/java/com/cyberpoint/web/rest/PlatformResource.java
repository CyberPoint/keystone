package com.cyberpoint.web.rest;

import com.cyberpoint.domain.Platform;
import com.cyberpoint.repository.PlatformRepository;
import com.cyberpoint.service.PlatformService;
import com.cyberpoint.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cyberpoint.domain.Platform}.
 */
@RestController
@RequestMapping("/api")
public class PlatformResource {

    private final Logger log = LoggerFactory.getLogger(PlatformResource.class);

    private static final String ENTITY_NAME = "platform";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlatformService platformService;

    private final PlatformRepository platformRepository;

    public PlatformResource(PlatformService platformService, PlatformRepository platformRepository) {
        this.platformService = platformService;
        this.platformRepository = platformRepository;
    }

    /**
     * {@code POST  /platforms} : Create a new platform.
     *
     * @param platform the platform to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new platform, or with status {@code 400 (Bad Request)} if the platform has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/platforms")
    public ResponseEntity<Platform> createPlatform(@Valid @RequestBody Platform platform) throws URISyntaxException {
        log.debug("REST request to save Platform : {}", platform);
        if (platform.getId() != null) {
            throw new BadRequestAlertException("A new platform cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Platform result = platformService.save(platform);
        return ResponseEntity
            .created(new URI("/api/platforms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /platforms/:id} : Updates an existing platform.
     *
     * @param id the id of the platform to save.
     * @param platform the platform to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated platform,
     * or with status {@code 400 (Bad Request)} if the platform is not valid,
     * or with status {@code 500 (Internal Server Error)} if the platform couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/platforms/{id}")
    public ResponseEntity<Platform> updatePlatform(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Platform platform
    ) throws URISyntaxException {
        log.debug("REST request to update Platform : {}, {}", id, platform);
        if (platform.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, platform.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!platformRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Platform result = platformService.update(platform);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, platform.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /platforms/:id} : Partial updates given fields of an existing platform, field will ignore if it is null
     *
     * @param id the id of the platform to save.
     * @param platform the platform to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated platform,
     * or with status {@code 400 (Bad Request)} if the platform is not valid,
     * or with status {@code 404 (Not Found)} if the platform is not found,
     * or with status {@code 500 (Internal Server Error)} if the platform couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/platforms/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Platform> partialUpdatePlatform(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Platform platform
    ) throws URISyntaxException {
        log.debug("REST request to partial update Platform partially : {}, {}", id, platform);
        if (platform.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, platform.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!platformRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Platform> result = platformService.partialUpdate(platform);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, platform.getId().toString())
        );
    }

    /**
     * {@code GET  /platforms} : get all the platforms.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of platforms in body.
     */
    @GetMapping("/platforms")
    public ResponseEntity<List<Platform>> getAllPlatforms(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Platforms");
        Page<Platform> page = platformService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /platforms/:id} : get the "id" platform.
     *
     * @param id the id of the platform to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the platform, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/platforms/{id}")
    public ResponseEntity<Platform> getPlatform(@PathVariable Long id) {
        log.debug("REST request to get Platform : {}", id);
        Optional<Platform> platform = platformService.findOne(id);
        return ResponseUtil.wrapOrNotFound(platform);
    }

    /**
     * {@code DELETE  /platforms/:id} : delete the "id" platform.
     *
     * @param id the id of the platform to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/platforms/{id}")
    public ResponseEntity<Void> deletePlatform(@PathVariable Long id) {
        log.debug("REST request to delete Platform : {}", id);
        platformService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
