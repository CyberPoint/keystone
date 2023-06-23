package com.cyberpoint.web.rest;

import com.cyberpoint.domain.CallBack;
import com.cyberpoint.repository.CallBackRepository;
import com.cyberpoint.service.CallBackService;
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
 * REST controller for managing {@link com.cyberpoint.domain.CallBack}.
 */
@RestController
@RequestMapping("/api")
public class CallBackResource {

    private final Logger log = LoggerFactory.getLogger(CallBackResource.class);

    private static final String ENTITY_NAME = "callBack";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CallBackService callBackService;

    private final CallBackRepository callBackRepository;

    public CallBackResource(CallBackService callBackService, CallBackRepository callBackRepository) {
        this.callBackService = callBackService;
        this.callBackRepository = callBackRepository;
    }

    /**
     * {@code POST  /call-backs} : Create a new callBack.
     *
     * @param callBack the callBack to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new callBack, or with status {@code 400 (Bad Request)} if the callBack has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/call-backs")
    public ResponseEntity<CallBack> createCallBack(@Valid @RequestBody CallBack callBack) throws URISyntaxException {
        log.debug("REST request to save CallBack : {}", callBack);
        if (callBack.getId() != null) {
            throw new BadRequestAlertException("A new callBack cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CallBack result = callBackService.save(callBack);
        return ResponseEntity
            .created(new URI("/api/call-backs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /call-backs/:id} : Updates an existing callBack.
     *
     * @param id the id of the callBack to save.
     * @param callBack the callBack to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated callBack,
     * or with status {@code 400 (Bad Request)} if the callBack is not valid,
     * or with status {@code 500 (Internal Server Error)} if the callBack couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/call-backs/{id}")
    public ResponseEntity<CallBack> updateCallBack(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CallBack callBack
    ) throws URISyntaxException {
        log.debug("REST request to update CallBack : {}, {}", id, callBack);
        if (callBack.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, callBack.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!callBackRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CallBack result = callBackService.update(callBack);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, callBack.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /call-backs/:id} : Partial updates given fields of an existing callBack, field will ignore if it is null
     *
     * @param id the id of the callBack to save.
     * @param callBack the callBack to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated callBack,
     * or with status {@code 400 (Bad Request)} if the callBack is not valid,
     * or with status {@code 404 (Not Found)} if the callBack is not found,
     * or with status {@code 500 (Internal Server Error)} if the callBack couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/call-backs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CallBack> partialUpdateCallBack(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CallBack callBack
    ) throws URISyntaxException {
        log.debug("REST request to partial update CallBack partially : {}, {}", id, callBack);
        if (callBack.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, callBack.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!callBackRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CallBack> result = callBackService.partialUpdate(callBack);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, callBack.getId().toString())
        );
    }

    /**
     * {@code GET  /call-backs} : get all the callBacks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of callBacks in body.
     */
    @GetMapping("/call-backs")
    public ResponseEntity<List<CallBack>> getAllCallBacks(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CallBacks");
        Page<CallBack> page = callBackService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /call-backs/:id} : get the "id" callBack.
     *
     * @param id the id of the callBack to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the callBack, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/call-backs/{id}")
    public ResponseEntity<CallBack> getCallBack(@PathVariable Long id) {
        log.debug("REST request to get CallBack : {}", id);
        Optional<CallBack> callBack = callBackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(callBack);
    }

    /**
     * {@code DELETE  /call-backs/:id} : delete the "id" callBack.
     *
     * @param id the id of the callBack to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/call-backs/{id}")
    public ResponseEntity<Void> deleteCallBack(@PathVariable Long id) {
        log.debug("REST request to delete CallBack : {}", id);
        callBackService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
