package com.cyberpoint.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cyberpoint.IntegrationTest;
import com.cyberpoint.domain.TaskResult;
import com.cyberpoint.repository.TaskResultRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link TaskResultResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaskResultResourceIT {

    private static final byte[] DEFAULT_EMBEDDEDDATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_EMBEDDEDDATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_EMBEDDEDDATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_EMBEDDEDDATA_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_ADDED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ADDED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_REVIEWED = false;
    private static final Boolean UPDATED_REVIEWED = true;

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_HEADERS = "AAAAAAAAAA";
    private static final String UPDATED_HEADERS = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/task-results";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TaskResultRepository taskResultRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaskResultMockMvc;

    private TaskResult taskResult;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskResult createEntity(EntityManager em) {
        TaskResult taskResult = new TaskResult()
            .embeddeddata(DEFAULT_EMBEDDEDDATA)
            .embeddeddataContentType(DEFAULT_EMBEDDEDDATA_CONTENT_TYPE)
            .added(DEFAULT_ADDED)
            .reviewed(DEFAULT_REVIEWED)
            .ipAddress(DEFAULT_IP_ADDRESS)
            .headers(DEFAULT_HEADERS)
            .url(DEFAULT_URL)
            .content(DEFAULT_CONTENT);
        return taskResult;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskResult createUpdatedEntity(EntityManager em) {
        TaskResult taskResult = new TaskResult()
            .embeddeddata(UPDATED_EMBEDDEDDATA)
            .embeddeddataContentType(UPDATED_EMBEDDEDDATA_CONTENT_TYPE)
            .added(UPDATED_ADDED)
            .reviewed(UPDATED_REVIEWED)
            .ipAddress(UPDATED_IP_ADDRESS)
            .headers(UPDATED_HEADERS)
            .url(UPDATED_URL)
            .content(UPDATED_CONTENT);
        return taskResult;
    }

    @BeforeEach
    public void initTest() {
        taskResult = createEntity(em);
    }

    @Test
    @Transactional
    void createTaskResult() throws Exception {
        int databaseSizeBeforeCreate = taskResultRepository.findAll().size();
        // Create the TaskResult
        restTaskResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskResult)))
            .andExpect(status().isCreated());

        // Validate the TaskResult in the database
        List<TaskResult> taskResultList = taskResultRepository.findAll();
        assertThat(taskResultList).hasSize(databaseSizeBeforeCreate + 1);
        TaskResult testTaskResult = taskResultList.get(taskResultList.size() - 1);
        assertThat(testTaskResult.getEmbeddeddata()).isEqualTo(DEFAULT_EMBEDDEDDATA);
        assertThat(testTaskResult.getEmbeddeddataContentType()).isEqualTo(DEFAULT_EMBEDDEDDATA_CONTENT_TYPE);
        assertThat(testTaskResult.getAdded()).isEqualTo(DEFAULT_ADDED);
        assertThat(testTaskResult.getReviewed()).isEqualTo(DEFAULT_REVIEWED);
        assertThat(testTaskResult.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testTaskResult.getHeaders()).isEqualTo(DEFAULT_HEADERS);
        assertThat(testTaskResult.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testTaskResult.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    void createTaskResultWithExistingId() throws Exception {
        // Create the TaskResult with an existing ID
        taskResult.setId(1L);

        int databaseSizeBeforeCreate = taskResultRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskResult)))
            .andExpect(status().isBadRequest());

        // Validate the TaskResult in the database
        List<TaskResult> taskResultList = taskResultRepository.findAll();
        assertThat(taskResultList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIpAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskResultRepository.findAll().size();
        // set the field null
        taskResult.setIpAddress(null);

        // Create the TaskResult, which fails.

        restTaskResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskResult)))
            .andExpect(status().isBadRequest());

        List<TaskResult> taskResultList = taskResultRepository.findAll();
        assertThat(taskResultList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTaskResults() throws Exception {
        // Initialize the database
        taskResultRepository.saveAndFlush(taskResult);

        // Get all the taskResultList
        restTaskResultMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].embeddeddataContentType").value(hasItem(DEFAULT_EMBEDDEDDATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].embeddeddata").value(hasItem(Base64Utils.encodeToString(DEFAULT_EMBEDDEDDATA))))
            .andExpect(jsonPath("$.[*].added").value(hasItem(DEFAULT_ADDED.toString())))
            .andExpect(jsonPath("$.[*].reviewed").value(hasItem(DEFAULT_REVIEWED.booleanValue())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].headers").value(hasItem(DEFAULT_HEADERS)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    void getTaskResult() throws Exception {
        // Initialize the database
        taskResultRepository.saveAndFlush(taskResult);

        // Get the taskResult
        restTaskResultMockMvc
            .perform(get(ENTITY_API_URL_ID, taskResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskResult.getId().intValue()))
            .andExpect(jsonPath("$.embeddeddataContentType").value(DEFAULT_EMBEDDEDDATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.embeddeddata").value(Base64Utils.encodeToString(DEFAULT_EMBEDDEDDATA)))
            .andExpect(jsonPath("$.added").value(DEFAULT_ADDED.toString()))
            .andExpect(jsonPath("$.reviewed").value(DEFAULT_REVIEWED.booleanValue()))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS))
            .andExpect(jsonPath("$.headers").value(DEFAULT_HEADERS))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    void getNonExistingTaskResult() throws Exception {
        // Get the taskResult
        restTaskResultMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTaskResult() throws Exception {
        // Initialize the database
        taskResultRepository.saveAndFlush(taskResult);

        int databaseSizeBeforeUpdate = taskResultRepository.findAll().size();

        // Update the taskResult
        TaskResult updatedTaskResult = taskResultRepository.findById(taskResult.getId()).get();
        // Disconnect from session so that the updates on updatedTaskResult are not directly saved in db
        em.detach(updatedTaskResult);
        updatedTaskResult
            .embeddeddata(UPDATED_EMBEDDEDDATA)
            .embeddeddataContentType(UPDATED_EMBEDDEDDATA_CONTENT_TYPE)
            .added(UPDATED_ADDED)
            .reviewed(UPDATED_REVIEWED)
            .ipAddress(UPDATED_IP_ADDRESS)
            .headers(UPDATED_HEADERS)
            .url(UPDATED_URL)
            .content(UPDATED_CONTENT);

        restTaskResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTaskResult.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTaskResult))
            )
            .andExpect(status().isOk());

        // Validate the TaskResult in the database
        List<TaskResult> taskResultList = taskResultRepository.findAll();
        assertThat(taskResultList).hasSize(databaseSizeBeforeUpdate);
        TaskResult testTaskResult = taskResultList.get(taskResultList.size() - 1);
        assertThat(testTaskResult.getEmbeddeddata()).isEqualTo(UPDATED_EMBEDDEDDATA);
        assertThat(testTaskResult.getEmbeddeddataContentType()).isEqualTo(UPDATED_EMBEDDEDDATA_CONTENT_TYPE);
        assertThat(testTaskResult.getAdded()).isEqualTo(UPDATED_ADDED);
        assertThat(testTaskResult.getReviewed()).isEqualTo(UPDATED_REVIEWED);
        assertThat(testTaskResult.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testTaskResult.getHeaders()).isEqualTo(UPDATED_HEADERS);
        assertThat(testTaskResult.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testTaskResult.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void putNonExistingTaskResult() throws Exception {
        int databaseSizeBeforeUpdate = taskResultRepository.findAll().size();
        taskResult.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taskResult.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskResult))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskResult in the database
        List<TaskResult> taskResultList = taskResultRepository.findAll();
        assertThat(taskResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaskResult() throws Exception {
        int databaseSizeBeforeUpdate = taskResultRepository.findAll().size();
        taskResult.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskResult))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskResult in the database
        List<TaskResult> taskResultList = taskResultRepository.findAll();
        assertThat(taskResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaskResult() throws Exception {
        int databaseSizeBeforeUpdate = taskResultRepository.findAll().size();
        taskResult.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskResultMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskResult)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaskResult in the database
        List<TaskResult> taskResultList = taskResultRepository.findAll();
        assertThat(taskResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaskResultWithPatch() throws Exception {
        // Initialize the database
        taskResultRepository.saveAndFlush(taskResult);

        int databaseSizeBeforeUpdate = taskResultRepository.findAll().size();

        // Update the taskResult using partial update
        TaskResult partialUpdatedTaskResult = new TaskResult();
        partialUpdatedTaskResult.setId(taskResult.getId());

        partialUpdatedTaskResult
            .embeddeddata(UPDATED_EMBEDDEDDATA)
            .embeddeddataContentType(UPDATED_EMBEDDEDDATA_CONTENT_TYPE)
            .headers(UPDATED_HEADERS)
            .url(UPDATED_URL);

        restTaskResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaskResult.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaskResult))
            )
            .andExpect(status().isOk());

        // Validate the TaskResult in the database
        List<TaskResult> taskResultList = taskResultRepository.findAll();
        assertThat(taskResultList).hasSize(databaseSizeBeforeUpdate);
        TaskResult testTaskResult = taskResultList.get(taskResultList.size() - 1);
        assertThat(testTaskResult.getEmbeddeddata()).isEqualTo(UPDATED_EMBEDDEDDATA);
        assertThat(testTaskResult.getEmbeddeddataContentType()).isEqualTo(UPDATED_EMBEDDEDDATA_CONTENT_TYPE);
        assertThat(testTaskResult.getAdded()).isEqualTo(DEFAULT_ADDED);
        assertThat(testTaskResult.getReviewed()).isEqualTo(DEFAULT_REVIEWED);
        assertThat(testTaskResult.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testTaskResult.getHeaders()).isEqualTo(UPDATED_HEADERS);
        assertThat(testTaskResult.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testTaskResult.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    void fullUpdateTaskResultWithPatch() throws Exception {
        // Initialize the database
        taskResultRepository.saveAndFlush(taskResult);

        int databaseSizeBeforeUpdate = taskResultRepository.findAll().size();

        // Update the taskResult using partial update
        TaskResult partialUpdatedTaskResult = new TaskResult();
        partialUpdatedTaskResult.setId(taskResult.getId());

        partialUpdatedTaskResult
            .embeddeddata(UPDATED_EMBEDDEDDATA)
            .embeddeddataContentType(UPDATED_EMBEDDEDDATA_CONTENT_TYPE)
            .added(UPDATED_ADDED)
            .reviewed(UPDATED_REVIEWED)
            .ipAddress(UPDATED_IP_ADDRESS)
            .headers(UPDATED_HEADERS)
            .url(UPDATED_URL)
            .content(UPDATED_CONTENT);

        restTaskResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaskResult.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaskResult))
            )
            .andExpect(status().isOk());

        // Validate the TaskResult in the database
        List<TaskResult> taskResultList = taskResultRepository.findAll();
        assertThat(taskResultList).hasSize(databaseSizeBeforeUpdate);
        TaskResult testTaskResult = taskResultList.get(taskResultList.size() - 1);
        assertThat(testTaskResult.getEmbeddeddata()).isEqualTo(UPDATED_EMBEDDEDDATA);
        assertThat(testTaskResult.getEmbeddeddataContentType()).isEqualTo(UPDATED_EMBEDDEDDATA_CONTENT_TYPE);
        assertThat(testTaskResult.getAdded()).isEqualTo(UPDATED_ADDED);
        assertThat(testTaskResult.getReviewed()).isEqualTo(UPDATED_REVIEWED);
        assertThat(testTaskResult.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testTaskResult.getHeaders()).isEqualTo(UPDATED_HEADERS);
        assertThat(testTaskResult.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testTaskResult.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void patchNonExistingTaskResult() throws Exception {
        int databaseSizeBeforeUpdate = taskResultRepository.findAll().size();
        taskResult.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taskResult.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taskResult))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskResult in the database
        List<TaskResult> taskResultList = taskResultRepository.findAll();
        assertThat(taskResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaskResult() throws Exception {
        int databaseSizeBeforeUpdate = taskResultRepository.findAll().size();
        taskResult.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taskResult))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskResult in the database
        List<TaskResult> taskResultList = taskResultRepository.findAll();
        assertThat(taskResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaskResult() throws Exception {
        int databaseSizeBeforeUpdate = taskResultRepository.findAll().size();
        taskResult.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskResultMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(taskResult))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaskResult in the database
        List<TaskResult> taskResultList = taskResultRepository.findAll();
        assertThat(taskResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaskResult() throws Exception {
        // Initialize the database
        taskResultRepository.saveAndFlush(taskResult);

        int databaseSizeBeforeDelete = taskResultRepository.findAll().size();

        // Delete the taskResult
        restTaskResultMockMvc
            .perform(delete(ENTITY_API_URL_ID, taskResult.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskResult> taskResultList = taskResultRepository.findAll();
        assertThat(taskResultList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
