package com.cyberpoint.web.rest;

import com.cyberpoint.domain.TaskResult;
import com.cyberpoint.repository.TaskResultRepository;
import com.cyberpoint.service.TaskResultService;
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
 * REST controller for managing {@link com.cyberpoint.domain.TaskResult}.
 */
@RestController
@RequestMapping("/api")
public class TaskResultResource {

    private final Logger log = LoggerFactory.getLogger(TaskResultResource.class);

    private static final String ENTITY_NAME = "taskResult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskResultService taskResultService;

    private final TaskResultRepository taskResultRepository;

    public TaskResultResource(TaskResultService taskResultService, TaskResultRepository taskResultRepository) {
        this.taskResultService = taskResultService;
        this.taskResultRepository = taskResultRepository;
    }

    /**
     * {@code POST  /task-results} : Create a new taskResult.
     *
     * @param taskResult the taskResult to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskResult, or with status {@code 400 (Bad Request)} if the taskResult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-results")
    public ResponseEntity<TaskResult> createTaskResult(@Valid @RequestBody TaskResult taskResult) throws URISyntaxException {
        log.debug("REST request to save TaskResult : {}", taskResult);
        if (taskResult.getId() != null) {
            throw new BadRequestAlertException("A new taskResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskResult result = taskResultService.save(taskResult);
        return ResponseEntity
            .created(new URI("/api/task-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-results/:id} : Updates an existing taskResult.
     *
     * @param id the id of the taskResult to save.
     * @param taskResult the taskResult to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskResult,
     * or with status {@code 400 (Bad Request)} if the taskResult is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskResult couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-results/{id}")
    public ResponseEntity<TaskResult> updateTaskResult(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TaskResult taskResult
    ) throws URISyntaxException {
        log.debug("REST request to update TaskResult : {}, {}", id, taskResult);
        if (taskResult.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taskResult.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taskResultRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaskResult result = taskResultService.update(taskResult);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskResult.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /task-results/:id} : Partial updates given fields of an existing taskResult, field will ignore if it is null
     *
     * @param id the id of the taskResult to save.
     * @param taskResult the taskResult to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskResult,
     * or with status {@code 400 (Bad Request)} if the taskResult is not valid,
     * or with status {@code 404 (Not Found)} if the taskResult is not found,
     * or with status {@code 500 (Internal Server Error)} if the taskResult couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/task-results/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TaskResult> partialUpdateTaskResult(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TaskResult taskResult
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaskResult partially : {}, {}", id, taskResult);
        if (taskResult.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taskResult.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taskResultRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaskResult> result = taskResultService.partialUpdate(taskResult);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskResult.getId().toString())
        );
    }

    /**
     * {@code GET  /task-results} : get all the taskResults.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskResults in body.
     */
    @GetMapping("/task-results")
    public ResponseEntity<List<TaskResult>> getAllTaskResults(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TaskResults");
        Page<TaskResult> page = taskResultService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-results/:id} : get the "id" taskResult.
     *
     * @param id the id of the taskResult to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskResult, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-results/{id}")
    public ResponseEntity<TaskResult> getTaskResult(@PathVariable Long id) {
        log.debug("REST request to get TaskResult : {}", id);
        Optional<TaskResult> taskResult = taskResultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskResult);
    }

    /**
     * {@code DELETE  /task-results/:id} : delete the "id" taskResult.
     *
     * @param id the id of the taskResult to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-results/{id}")
    public ResponseEntity<Void> deleteTaskResult(@PathVariable Long id) {
        log.debug("REST request to delete TaskResult : {}", id);
        taskResultService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
