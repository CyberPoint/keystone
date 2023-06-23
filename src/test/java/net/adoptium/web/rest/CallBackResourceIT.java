package com.cyberpoint.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cyberpoint.IntegrationTest;
import com.cyberpoint.domain.CallBack;
import com.cyberpoint.repository.CallBackRepository;
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
 * Integration tests for the {@link CallBackResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CallBackResourceIT {

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_REMOTE_PORT = 1;
    private static final Integer UPDATED_REMOTE_PORT = 2;

    private static final Instant DEFAULT_TIMESTAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIMESTAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_BUFFER = "AAAAAAAAAA";
    private static final String UPDATED_BUFFER = "BBBBBBBBBB";

    private static final byte[] DEFAULT_RAWCONTENTS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_RAWCONTENTS = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_RAWCONTENTS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_RAWCONTENTS_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/call-backs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CallBackRepository callBackRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCallBackMockMvc;

    private CallBack callBack;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CallBack createEntity(EntityManager em) {
        CallBack callBack = new CallBack()
            .ipAddress(DEFAULT_IP_ADDRESS)
            .url(DEFAULT_URL)
            .remotePort(DEFAULT_REMOTE_PORT)
            .timestamp(DEFAULT_TIMESTAMP)
            .buffer(DEFAULT_BUFFER)
            .rawcontents(DEFAULT_RAWCONTENTS)
            .rawcontentsContentType(DEFAULT_RAWCONTENTS_CONTENT_TYPE);
        return callBack;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CallBack createUpdatedEntity(EntityManager em) {
        CallBack callBack = new CallBack()
            .ipAddress(UPDATED_IP_ADDRESS)
            .url(UPDATED_URL)
            .remotePort(UPDATED_REMOTE_PORT)
            .timestamp(UPDATED_TIMESTAMP)
            .buffer(UPDATED_BUFFER)
            .rawcontents(UPDATED_RAWCONTENTS)
            .rawcontentsContentType(UPDATED_RAWCONTENTS_CONTENT_TYPE);
        return callBack;
    }

    @BeforeEach
    public void initTest() {
        callBack = createEntity(em);
    }

    @Test
    @Transactional
    void createCallBack() throws Exception {
        int databaseSizeBeforeCreate = callBackRepository.findAll().size();
        // Create the CallBack
        restCallBackMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(callBack)))
            .andExpect(status().isCreated());

        // Validate the CallBack in the database
        List<CallBack> callBackList = callBackRepository.findAll();
        assertThat(callBackList).hasSize(databaseSizeBeforeCreate + 1);
        CallBack testCallBack = callBackList.get(callBackList.size() - 1);
        assertThat(testCallBack.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testCallBack.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testCallBack.getRemotePort()).isEqualTo(DEFAULT_REMOTE_PORT);
        assertThat(testCallBack.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testCallBack.getBuffer()).isEqualTo(DEFAULT_BUFFER);
        assertThat(testCallBack.getRawcontents()).isEqualTo(DEFAULT_RAWCONTENTS);
        assertThat(testCallBack.getRawcontentsContentType()).isEqualTo(DEFAULT_RAWCONTENTS_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createCallBackWithExistingId() throws Exception {
        // Create the CallBack with an existing ID
        callBack.setId(1L);

        int databaseSizeBeforeCreate = callBackRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCallBackMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(callBack)))
            .andExpect(status().isBadRequest());

        // Validate the CallBack in the database
        List<CallBack> callBackList = callBackRepository.findAll();
        assertThat(callBackList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIpAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = callBackRepository.findAll().size();
        // set the field null
        callBack.setIpAddress(null);

        // Create the CallBack, which fails.

        restCallBackMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(callBack)))
            .andExpect(status().isBadRequest());

        List<CallBack> callBackList = callBackRepository.findAll();
        assertThat(callBackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCallBacks() throws Exception {
        // Initialize the database
        callBackRepository.saveAndFlush(callBack);

        // Get all the callBackList
        restCallBackMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(callBack.getId().intValue())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].remotePort").value(hasItem(DEFAULT_REMOTE_PORT)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].buffer").value(hasItem(DEFAULT_BUFFER)))
            .andExpect(jsonPath("$.[*].rawcontentsContentType").value(hasItem(DEFAULT_RAWCONTENTS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].rawcontents").value(hasItem(Base64Utils.encodeToString(DEFAULT_RAWCONTENTS))));
    }

    @Test
    @Transactional
    void getCallBack() throws Exception {
        // Initialize the database
        callBackRepository.saveAndFlush(callBack);

        // Get the callBack
        restCallBackMockMvc
            .perform(get(ENTITY_API_URL_ID, callBack.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(callBack.getId().intValue()))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.remotePort").value(DEFAULT_REMOTE_PORT))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.toString()))
            .andExpect(jsonPath("$.buffer").value(DEFAULT_BUFFER))
            .andExpect(jsonPath("$.rawcontentsContentType").value(DEFAULT_RAWCONTENTS_CONTENT_TYPE))
            .andExpect(jsonPath("$.rawcontents").value(Base64Utils.encodeToString(DEFAULT_RAWCONTENTS)));
    }

    @Test
    @Transactional
    void getNonExistingCallBack() throws Exception {
        // Get the callBack
        restCallBackMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCallBack() throws Exception {
        // Initialize the database
        callBackRepository.saveAndFlush(callBack);

        int databaseSizeBeforeUpdate = callBackRepository.findAll().size();

        // Update the callBack
        CallBack updatedCallBack = callBackRepository.findById(callBack.getId()).get();
        // Disconnect from session so that the updates on updatedCallBack are not directly saved in db
        em.detach(updatedCallBack);
        updatedCallBack
            .ipAddress(UPDATED_IP_ADDRESS)
            .url(UPDATED_URL)
            .remotePort(UPDATED_REMOTE_PORT)
            .timestamp(UPDATED_TIMESTAMP)
            .buffer(UPDATED_BUFFER)
            .rawcontents(UPDATED_RAWCONTENTS)
            .rawcontentsContentType(UPDATED_RAWCONTENTS_CONTENT_TYPE);

        restCallBackMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCallBack.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCallBack))
            )
            .andExpect(status().isOk());

        // Validate the CallBack in the database
        List<CallBack> callBackList = callBackRepository.findAll();
        assertThat(callBackList).hasSize(databaseSizeBeforeUpdate);
        CallBack testCallBack = callBackList.get(callBackList.size() - 1);
        assertThat(testCallBack.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testCallBack.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testCallBack.getRemotePort()).isEqualTo(UPDATED_REMOTE_PORT);
        assertThat(testCallBack.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testCallBack.getBuffer()).isEqualTo(UPDATED_BUFFER);
        assertThat(testCallBack.getRawcontents()).isEqualTo(UPDATED_RAWCONTENTS);
        assertThat(testCallBack.getRawcontentsContentType()).isEqualTo(UPDATED_RAWCONTENTS_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingCallBack() throws Exception {
        int databaseSizeBeforeUpdate = callBackRepository.findAll().size();
        callBack.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCallBackMockMvc
            .perform(
                put(ENTITY_API_URL_ID, callBack.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(callBack))
            )
            .andExpect(status().isBadRequest());

        // Validate the CallBack in the database
        List<CallBack> callBackList = callBackRepository.findAll();
        assertThat(callBackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCallBack() throws Exception {
        int databaseSizeBeforeUpdate = callBackRepository.findAll().size();
        callBack.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCallBackMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(callBack))
            )
            .andExpect(status().isBadRequest());

        // Validate the CallBack in the database
        List<CallBack> callBackList = callBackRepository.findAll();
        assertThat(callBackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCallBack() throws Exception {
        int databaseSizeBeforeUpdate = callBackRepository.findAll().size();
        callBack.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCallBackMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(callBack)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CallBack in the database
        List<CallBack> callBackList = callBackRepository.findAll();
        assertThat(callBackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCallBackWithPatch() throws Exception {
        // Initialize the database
        callBackRepository.saveAndFlush(callBack);

        int databaseSizeBeforeUpdate = callBackRepository.findAll().size();

        // Update the callBack using partial update
        CallBack partialUpdatedCallBack = new CallBack();
        partialUpdatedCallBack.setId(callBack.getId());

        partialUpdatedCallBack
            .ipAddress(UPDATED_IP_ADDRESS)
            .remotePort(UPDATED_REMOTE_PORT)
            .timestamp(UPDATED_TIMESTAMP)
            .rawcontents(UPDATED_RAWCONTENTS)
            .rawcontentsContentType(UPDATED_RAWCONTENTS_CONTENT_TYPE);

        restCallBackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCallBack.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCallBack))
            )
            .andExpect(status().isOk());

        // Validate the CallBack in the database
        List<CallBack> callBackList = callBackRepository.findAll();
        assertThat(callBackList).hasSize(databaseSizeBeforeUpdate);
        CallBack testCallBack = callBackList.get(callBackList.size() - 1);
        assertThat(testCallBack.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testCallBack.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testCallBack.getRemotePort()).isEqualTo(UPDATED_REMOTE_PORT);
        assertThat(testCallBack.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testCallBack.getBuffer()).isEqualTo(DEFAULT_BUFFER);
        assertThat(testCallBack.getRawcontents()).isEqualTo(UPDATED_RAWCONTENTS);
        assertThat(testCallBack.getRawcontentsContentType()).isEqualTo(UPDATED_RAWCONTENTS_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateCallBackWithPatch() throws Exception {
        // Initialize the database
        callBackRepository.saveAndFlush(callBack);

        int databaseSizeBeforeUpdate = callBackRepository.findAll().size();

        // Update the callBack using partial update
        CallBack partialUpdatedCallBack = new CallBack();
        partialUpdatedCallBack.setId(callBack.getId());

        partialUpdatedCallBack
            .ipAddress(UPDATED_IP_ADDRESS)
            .url(UPDATED_URL)
            .remotePort(UPDATED_REMOTE_PORT)
            .timestamp(UPDATED_TIMESTAMP)
            .buffer(UPDATED_BUFFER)
            .rawcontents(UPDATED_RAWCONTENTS)
            .rawcontentsContentType(UPDATED_RAWCONTENTS_CONTENT_TYPE);

        restCallBackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCallBack.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCallBack))
            )
            .andExpect(status().isOk());

        // Validate the CallBack in the database
        List<CallBack> callBackList = callBackRepository.findAll();
        assertThat(callBackList).hasSize(databaseSizeBeforeUpdate);
        CallBack testCallBack = callBackList.get(callBackList.size() - 1);
        assertThat(testCallBack.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testCallBack.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testCallBack.getRemotePort()).isEqualTo(UPDATED_REMOTE_PORT);
        assertThat(testCallBack.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testCallBack.getBuffer()).isEqualTo(UPDATED_BUFFER);
        assertThat(testCallBack.getRawcontents()).isEqualTo(UPDATED_RAWCONTENTS);
        assertThat(testCallBack.getRawcontentsContentType()).isEqualTo(UPDATED_RAWCONTENTS_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingCallBack() throws Exception {
        int databaseSizeBeforeUpdate = callBackRepository.findAll().size();
        callBack.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCallBackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, callBack.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(callBack))
            )
            .andExpect(status().isBadRequest());

        // Validate the CallBack in the database
        List<CallBack> callBackList = callBackRepository.findAll();
        assertThat(callBackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCallBack() throws Exception {
        int databaseSizeBeforeUpdate = callBackRepository.findAll().size();
        callBack.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCallBackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(callBack))
            )
            .andExpect(status().isBadRequest());

        // Validate the CallBack in the database
        List<CallBack> callBackList = callBackRepository.findAll();
        assertThat(callBackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCallBack() throws Exception {
        int databaseSizeBeforeUpdate = callBackRepository.findAll().size();
        callBack.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCallBackMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(callBack)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CallBack in the database
        List<CallBack> callBackList = callBackRepository.findAll();
        assertThat(callBackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCallBack() throws Exception {
        // Initialize the database
        callBackRepository.saveAndFlush(callBack);

        int databaseSizeBeforeDelete = callBackRepository.findAll().size();

        // Delete the callBack
        restCallBackMockMvc
            .perform(delete(ENTITY_API_URL_ID, callBack.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CallBack> callBackList = callBackRepository.findAll();
        assertThat(callBackList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
