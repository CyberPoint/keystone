package com.cyberpoint.web.rest;

import com.cyberpoint.domain.RegistrationSecret;
import com.cyberpoint.repository.RegistrationSecretRepository;
import com.cyberpoint.service.RegistrationSecretService;
import com.cyberpoint.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cyberpoint.domain.RegistrationSecret}.
 */
@RestController
@RequestMapping("/api")
public class RegistrationSecretResource {

    private final Logger log = LoggerFactory.getLogger(RegistrationSecretResource.class);

    private static final String ENTITY_NAME = "registrationSecret";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegistrationSecretService registrationSecretService;

    private final RegistrationSecretRepository registrationSecretRepository;

    public RegistrationSecretResource(
        RegistrationSecretService registrationSecretService,
        RegistrationSecretRepository registrationSecretRepository
    ) {
        this.registrationSecretService = registrationSecretService;
        this.registrationSecretRepository = registrationSecretRepository;
    }

    /**
     * {@code POST  /registration-secrets} : Create a new registrationSecret.
     *
     * @param registrationSecret the registrationSecret to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new registrationSecret, or with status {@code 400 (Bad Request)} if the registrationSecret has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/registration-secrets")
    public ResponseEntity<RegistrationSecret> createRegistrationSecret(@RequestBody RegistrationSecret registrationSecret)
        throws URISyntaxException {
        log.debug("REST request to save RegistrationSecret : {}", registrationSecret);
        if (registrationSecret.getId() != null) {
            throw new BadRequestAlertException("A new registrationSecret cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegistrationSecret result = registrationSecretService.save(registrationSecret);
        return ResponseEntity
            .created(new URI("/api/registration-secrets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /registration-secrets/:id} : Updates an existing registrationSecret.
     *
     * @param id the id of the registrationSecret to save.
     * @param registrationSecret the registrationSecret to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated registrationSecret,
     * or with status {@code 400 (Bad Request)} if the registrationSecret is not valid,
     * or with status {@code 500 (Internal Server Error)} if the registrationSecret couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/registration-secrets/{id}")
    public ResponseEntity<RegistrationSecret> updateRegistrationSecret(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RegistrationSecret registrationSecret
    ) throws URISyntaxException {
        log.debug("REST request to update RegistrationSecret : {}, {}", id, registrationSecret);
        if (registrationSecret.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, registrationSecret.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!registrationSecretRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RegistrationSecret result = registrationSecretService.update(registrationSecret);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, registrationSecret.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /registration-secrets/:id} : Partial updates given fields of an existing registrationSecret, field will ignore if it is null
     *
     * @param id the id of the registrationSecret to save.
     * @param registrationSecret the registrationSecret to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated registrationSecret,
     * or with status {@code 400 (Bad Request)} if the registrationSecret is not valid,
     * or with status {@code 404 (Not Found)} if the registrationSecret is not found,
     * or with status {@code 500 (Internal Server Error)} if the registrationSecret couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/registration-secrets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RegistrationSecret> partialUpdateRegistrationSecret(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RegistrationSecret registrationSecret
    ) throws URISyntaxException {
        log.debug("REST request to partial update RegistrationSecret partially : {}, {}", id, registrationSecret);
        if (registrationSecret.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, registrationSecret.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!registrationSecretRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RegistrationSecret> result = registrationSecretService.partialUpdate(registrationSecret);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, registrationSecret.getId().toString())
        );
    }

    /**
     * {@code GET  /registration-secrets} : get all the registrationSecrets.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of registrationSecrets in body.
     */
    @GetMapping("/registration-secrets")
    public List<RegistrationSecret> getAllRegistrationSecrets(@RequestParam(required = false) String filter) {
        if ("registrationevent-is-null".equals(filter)) {
            log.debug("REST request to get all RegistrationSecrets where registrationEvent is null");
            return registrationSecretService.findAllWhereRegistrationEventIsNull();
        }
        log.debug("REST request to get all RegistrationSecrets");
        return registrationSecretService.findAll();
    }

    /**
     * {@code GET  /registration-secrets/:id} : get the "id" registrationSecret.
     *
     * @param id the id of the registrationSecret to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the registrationSecret, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/registration-secrets/{id}")
    public ResponseEntity<RegistrationSecret> getRegistrationSecret(@PathVariable Long id) {
        log.debug("REST request to get RegistrationSecret : {}", id);
        Optional<RegistrationSecret> registrationSecret = registrationSecretService.findOne(id);
        return ResponseUtil.wrapOrNotFound(registrationSecret);
    }

    /**
     * {@code DELETE  /registration-secrets/:id} : delete the "id" registrationSecret.
     *
     * @param id the id of the registrationSecret to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/registration-secrets/{id}")
    public ResponseEntity<Void> deleteRegistrationSecret(@PathVariable Long id) {
        log.debug("REST request to delete RegistrationSecret : {}", id);
        registrationSecretService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
