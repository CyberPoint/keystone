package com.cyberpoint.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cyberpoint.IntegrationTest;
import com.cyberpoint.domain.RegistrationEvent;
import com.cyberpoint.repository.RegistrationEventRepository;
import com.cyberpoint.web.rest.RegistrationEventResource;
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

/**
 * Integration tests for the {@link RegistrationEventResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RegistrationEventResourceIT {

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_RAW_CONTENTS = "AAAAAAAAAA";
    private static final String UPDATED_RAW_CONTENTS = "BBBBBBBBBB";

    private static final Integer DEFAULT_REMOTE_PORT = 1;
    private static final Integer UPDATED_REMOTE_PORT = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_APPROVED = false;
    private static final Boolean UPDATED_APPROVED = true;

    private static final Instant DEFAULT_REGISTRATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REGISTRATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/registration-events";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RegistrationEventRepository registrationEventRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegistrationEventMockMvc;

    private RegistrationEvent registrationEvent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegistrationEvent createEntity(EntityManager em) {
        RegistrationEvent registrationEvent = new RegistrationEvent()
            .ipAddress(DEFAULT_IP_ADDRESS)
            .rawContents(DEFAULT_RAW_CONTENTS)
            .remotePort(DEFAULT_REMOTE_PORT)
            .name(DEFAULT_NAME)
            .approved(DEFAULT_APPROVED)
            .registrationDate(DEFAULT_REGISTRATION_DATE);
        return registrationEvent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegistrationEvent createUpdatedEntity(EntityManager em) {
        RegistrationEvent registrationEvent = new RegistrationEvent()
            .ipAddress(UPDATED_IP_ADDRESS)
            .rawContents(UPDATED_RAW_CONTENTS)
            .remotePort(UPDATED_REMOTE_PORT)
            .name(UPDATED_NAME)
            .approved(UPDATED_APPROVED)
            .registrationDate(UPDATED_REGISTRATION_DATE);
        return registrationEvent;
    }

    @BeforeEach
    public void initTest() {
        registrationEvent = createEntity(em);
    }

    @Test
    @Transactional
    void createRegistrationEvent() throws Exception {
        int databaseSizeBeforeCreate = registrationEventRepository.findAll().size();
        // Create the RegistrationEvent
        restRegistrationEventMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(registrationEvent))
            )
            .andExpect(status().isCreated());

        // Validate the RegistrationEvent in the database
        List<RegistrationEvent> registrationEventList = registrationEventRepository.findAll();
        assertThat(registrationEventList).hasSize(databaseSizeBeforeCreate + 1);
        RegistrationEvent testRegistrationEvent = registrationEventList.get(registrationEventList.size() - 1);
        assertThat(testRegistrationEvent.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testRegistrationEvent.getRawContents()).isEqualTo(DEFAULT_RAW_CONTENTS);
        assertThat(testRegistrationEvent.getRemotePort()).isEqualTo(DEFAULT_REMOTE_PORT);
        assertThat(testRegistrationEvent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRegistrationEvent.getApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testRegistrationEvent.getRegistrationDate()).isEqualTo(DEFAULT_REGISTRATION_DATE);
    }

    @Test
    @Transactional
    void createRegistrationEventWithExistingId() throws Exception {
        // Create the RegistrationEvent with an existing ID
        registrationEvent.setId(1L);

        int databaseSizeBeforeCreate = registrationEventRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegistrationEventMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(registrationEvent))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegistrationEvent in the database
        List<RegistrationEvent> registrationEventList = registrationEventRepository.findAll();
        assertThat(registrationEventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIpAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = registrationEventRepository.findAll().size();
        // set the field null
        registrationEvent.setIpAddress(null);

        // Create the RegistrationEvent, which fails.

        restRegistrationEventMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(registrationEvent))
            )
            .andExpect(status().isBadRequest());

        List<RegistrationEvent> registrationEventList = registrationEventRepository.findAll();
        assertThat(registrationEventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRegistrationEvents() throws Exception {
        // Initialize the database
        registrationEventRepository.saveAndFlush(registrationEvent);

        // Get all the registrationEventList
        restRegistrationEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registrationEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].rawContents").value(hasItem(DEFAULT_RAW_CONTENTS)))
            .andExpect(jsonPath("$.[*].remotePort").value(hasItem(DEFAULT_REMOTE_PORT)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].registrationDate").value(hasItem(DEFAULT_REGISTRATION_DATE.toString())));
    }

    @Test
    @Transactional
    void getRegistrationEvent() throws Exception {
        // Initialize the database
        registrationEventRepository.saveAndFlush(registrationEvent);

        // Get the registrationEvent
        restRegistrationEventMockMvc
            .perform(get(ENTITY_API_URL_ID, registrationEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(registrationEvent.getId().intValue()))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS))
            .andExpect(jsonPath("$.rawContents").value(DEFAULT_RAW_CONTENTS))
            .andExpect(jsonPath("$.remotePort").value(DEFAULT_REMOTE_PORT))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.registrationDate").value(DEFAULT_REGISTRATION_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRegistrationEvent() throws Exception {
        // Get the registrationEvent
        restRegistrationEventMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRegistrationEvent() throws Exception {
        // Initialize the database
        registrationEventRepository.saveAndFlush(registrationEvent);

        int databaseSizeBeforeUpdate = registrationEventRepository.findAll().size();

        // Update the registrationEvent
        RegistrationEvent updatedRegistrationEvent = registrationEventRepository.findById(registrationEvent.getId()).get();
        // Disconnect from session so that the updates on updatedRegistrationEvent are not directly saved in db
        em.detach(updatedRegistrationEvent);
        updatedRegistrationEvent
            .ipAddress(UPDATED_IP_ADDRESS)
            .rawContents(UPDATED_RAW_CONTENTS)
            .remotePort(UPDATED_REMOTE_PORT)
            .name(UPDATED_NAME)
            .approved(UPDATED_APPROVED)
            .registrationDate(UPDATED_REGISTRATION_DATE);

        restRegistrationEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRegistrationEvent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRegistrationEvent))
            )
            .andExpect(status().isOk());

        // Validate the RegistrationEvent in the database
        List<RegistrationEvent> registrationEventList = registrationEventRepository.findAll();
        assertThat(registrationEventList).hasSize(databaseSizeBeforeUpdate);
        RegistrationEvent testRegistrationEvent = registrationEventList.get(registrationEventList.size() - 1);
        assertThat(testRegistrationEvent.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testRegistrationEvent.getRawContents()).isEqualTo(UPDATED_RAW_CONTENTS);
        assertThat(testRegistrationEvent.getRemotePort()).isEqualTo(UPDATED_REMOTE_PORT);
        assertThat(testRegistrationEvent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRegistrationEvent.getApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testRegistrationEvent.getRegistrationDate()).isEqualTo(UPDATED_REGISTRATION_DATE);
    }

    @Test
    @Transactional
    void putNonExistingRegistrationEvent() throws Exception {
        int databaseSizeBeforeUpdate = registrationEventRepository.findAll().size();
        registrationEvent.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegistrationEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, registrationEvent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registrationEvent))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegistrationEvent in the database
        List<RegistrationEvent> registrationEventList = registrationEventRepository.findAll();
        assertThat(registrationEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRegistrationEvent() throws Exception {
        int databaseSizeBeforeUpdate = registrationEventRepository.findAll().size();
        registrationEvent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistrationEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registrationEvent))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegistrationEvent in the database
        List<RegistrationEvent> registrationEventList = registrationEventRepository.findAll();
        assertThat(registrationEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRegistrationEvent() throws Exception {
        int databaseSizeBeforeUpdate = registrationEventRepository.findAll().size();
        registrationEvent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistrationEventMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(registrationEvent))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RegistrationEvent in the database
        List<RegistrationEvent> registrationEventList = registrationEventRepository.findAll();
        assertThat(registrationEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRegistrationEventWithPatch() throws Exception {
        // Initialize the database
        registrationEventRepository.saveAndFlush(registrationEvent);

        int databaseSizeBeforeUpdate = registrationEventRepository.findAll().size();

        // Update the registrationEvent using partial update
        RegistrationEvent partialUpdatedRegistrationEvent = new RegistrationEvent();
        partialUpdatedRegistrationEvent.setId(registrationEvent.getId());

        partialUpdatedRegistrationEvent.ipAddress(UPDATED_IP_ADDRESS);

        restRegistrationEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegistrationEvent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegistrationEvent))
            )
            .andExpect(status().isOk());

        // Validate the RegistrationEvent in the database
        List<RegistrationEvent> registrationEventList = registrationEventRepository.findAll();
        assertThat(registrationEventList).hasSize(databaseSizeBeforeUpdate);
        RegistrationEvent testRegistrationEvent = registrationEventList.get(registrationEventList.size() - 1);
        assertThat(testRegistrationEvent.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testRegistrationEvent.getRawContents()).isEqualTo(DEFAULT_RAW_CONTENTS);
        assertThat(testRegistrationEvent.getRemotePort()).isEqualTo(DEFAULT_REMOTE_PORT);
        assertThat(testRegistrationEvent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRegistrationEvent.getApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testRegistrationEvent.getRegistrationDate()).isEqualTo(DEFAULT_REGISTRATION_DATE);
    }

    @Test
    @Transactional
    void fullUpdateRegistrationEventWithPatch() throws Exception {
        // Initialize the database
        registrationEventRepository.saveAndFlush(registrationEvent);

        int databaseSizeBeforeUpdate = registrationEventRepository.findAll().size();

        // Update the registrationEvent using partial update
        RegistrationEvent partialUpdatedRegistrationEvent = new RegistrationEvent();
        partialUpdatedRegistrationEvent.setId(registrationEvent.getId());

        partialUpdatedRegistrationEvent
            .ipAddress(UPDATED_IP_ADDRESS)
            .rawContents(UPDATED_RAW_CONTENTS)
            .remotePort(UPDATED_REMOTE_PORT)
            .name(UPDATED_NAME)
            .approved(UPDATED_APPROVED)
            .registrationDate(UPDATED_REGISTRATION_DATE);

        restRegistrationEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegistrationEvent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegistrationEvent))
            )
            .andExpect(status().isOk());

        // Validate the RegistrationEvent in the database
        List<RegistrationEvent> registrationEventList = registrationEventRepository.findAll();
        assertThat(registrationEventList).hasSize(databaseSizeBeforeUpdate);
        RegistrationEvent testRegistrationEvent = registrationEventList.get(registrationEventList.size() - 1);
        assertThat(testRegistrationEvent.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testRegistrationEvent.getRawContents()).isEqualTo(UPDATED_RAW_CONTENTS);
        assertThat(testRegistrationEvent.getRemotePort()).isEqualTo(UPDATED_REMOTE_PORT);
        assertThat(testRegistrationEvent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRegistrationEvent.getApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testRegistrationEvent.getRegistrationDate()).isEqualTo(UPDATED_REGISTRATION_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingRegistrationEvent() throws Exception {
        int databaseSizeBeforeUpdate = registrationEventRepository.findAll().size();
        registrationEvent.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegistrationEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, registrationEvent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(registrationEvent))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegistrationEvent in the database
        List<RegistrationEvent> registrationEventList = registrationEventRepository.findAll();
        assertThat(registrationEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRegistrationEvent() throws Exception {
        int databaseSizeBeforeUpdate = registrationEventRepository.findAll().size();
        registrationEvent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistrationEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(registrationEvent))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegistrationEvent in the database
        List<RegistrationEvent> registrationEventList = registrationEventRepository.findAll();
        assertThat(registrationEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRegistrationEvent() throws Exception {
        int databaseSizeBeforeUpdate = registrationEventRepository.findAll().size();
        registrationEvent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistrationEventMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(registrationEvent))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RegistrationEvent in the database
        List<RegistrationEvent> registrationEventList = registrationEventRepository.findAll();
        assertThat(registrationEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRegistrationEvent() throws Exception {
        // Initialize the database
        registrationEventRepository.saveAndFlush(registrationEvent);

        int databaseSizeBeforeDelete = registrationEventRepository.findAll().size();

        // Delete the registrationEvent
        restRegistrationEventMockMvc
            .perform(delete(ENTITY_API_URL_ID, registrationEvent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RegistrationEvent> registrationEventList = registrationEventRepository.findAll();
        assertThat(registrationEventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
