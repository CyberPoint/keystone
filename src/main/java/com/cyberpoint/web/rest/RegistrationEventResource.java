package com.cyberpoint.web.rest;

import com.cyberpoint.domain.RegistrationEvent;
import com.cyberpoint.repository.RegistrationEventRepository;
import com.cyberpoint.service.RegistrationEventService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cyberpoint.domain.RegistrationEvent}.
 */
@RestController
@RequestMapping("/api")
public class RegistrationEventResource {

    private final Logger log = LoggerFactory.getLogger(RegistrationEventResource.class);

    private static final String ENTITY_NAME = "registrationEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegistrationEventService registrationEventService;

    private final RegistrationEventRepository registrationEventRepository;

    public RegistrationEventResource(
        RegistrationEventService registrationEventService,
        RegistrationEventRepository registrationEventRepository
    ) {
        this.registrationEventService = registrationEventService;
        this.registrationEventRepository = registrationEventRepository;
    }

    /**
     * {@code POST  /registration-events} : Create a new registrationEvent.
     *
     * @param registrationEvent the registrationEvent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new registrationEvent, or with status {@code 400 (Bad Request)} if the registrationEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/registration-events")
    public ResponseEntity<RegistrationEvent> createRegistrationEvent(@Valid @RequestBody RegistrationEvent registrationEvent)
        throws URISyntaxException {
        log.debug("REST request to save RegistrationEvent : {}", registrationEvent);
        if (registrationEvent.getId() != null) {
            throw new BadRequestAlertException("A new registrationEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegistrationEvent result = registrationEventService.save(registrationEvent);
        return ResponseEntity
            .created(new URI("/api/registration-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /registration-events/:id} : Updates an existing registrationEvent.
     *
     * @param id the id of the registrationEvent to save.
     * @param registrationEvent the registrationEvent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated registrationEvent,
     * or with status {@code 400 (Bad Request)} if the registrationEvent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the registrationEvent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/registration-events/{id}")
    public ResponseEntity<RegistrationEvent> updateRegistrationEvent(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RegistrationEvent registrationEvent
    ) throws URISyntaxException {
        log.debug("REST request to update RegistrationEvent : {}, {}", id, registrationEvent);
        if (registrationEvent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, registrationEvent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!registrationEventRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RegistrationEvent result = registrationEventService.update(registrationEvent);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, registrationEvent.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /registration-events/:id} : Partial updates given fields of an existing registrationEvent, field will ignore if it is null
     *
     * @param id the id of the registrationEvent to save.
     * @param registrationEvent the registrationEvent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated registrationEvent,
     * or with status {@code 400 (Bad Request)} if the registrationEvent is not valid,
     * or with status {@code 404 (Not Found)} if the registrationEvent is not found,
     * or with status {@code 500 (Internal Server Error)} if the registrationEvent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/registration-events/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RegistrationEvent> partialUpdateRegistrationEvent(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RegistrationEvent registrationEvent
    ) throws URISyntaxException {
        log.debug("REST request to partial update RegistrationEvent partially : {}, {}", id, registrationEvent);
        if (registrationEvent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, registrationEvent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!registrationEventRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RegistrationEvent> result = registrationEventService.partialUpdate(registrationEvent);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, registrationEvent.getId().toString())
        );
    }

    /**
     * {@code GET  /registration-events} : get all the registrationEvents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of registrationEvents in body.
     */
    @GetMapping("/registration-events")
    public List<RegistrationEvent> getAllRegistrationEvents() {
        log.debug("REST request to get all RegistrationEvents");
        return registrationEventService.findAll();
    }

    /**
     * {@code GET  /registration-events/:id} : get the "id" registrationEvent.
     *
     * @param id the id of the registrationEvent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the registrationEvent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/registration-events/{id}")
    public ResponseEntity<RegistrationEvent> getRegistrationEvent(@PathVariable Long id) {
        log.debug("REST request to get RegistrationEvent : {}", id);
        Optional<RegistrationEvent> registrationEvent = registrationEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(registrationEvent);
    }

    /**
     * {@code DELETE  /registration-events/:id} : delete the "id" registrationEvent.
     *
     * @param id the id of the registrationEvent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/registration-events/{id}")
    public ResponseEntity<Void> deleteRegistrationEvent(@PathVariable Long id) {
        log.debug("REST request to delete RegistrationEvent : {}", id);
        registrationEventService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
