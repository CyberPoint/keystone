package com.cyberpoint.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cyberpoint.IntegrationTest;
import com.cyberpoint.domain.RegistrationSecret;
import com.cyberpoint.repository.RegistrationSecretRepository;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link RegistrationSecretResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RegistrationSecretResourceIT {

    private static final String DEFAULT_UNIQUE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_UNIQUE_VALUE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERICAL_VALUE = 1;
    private static final Integer UPDATED_NUMERICAL_VALUE = 2;

    private static final String ENTITY_API_URL = "/api/registration-secrets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RegistrationSecretRepository registrationSecretRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegistrationSecretMockMvc;

    private RegistrationSecret registrationSecret;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegistrationSecret createEntity(EntityManager em) {
        RegistrationSecret registrationSecret = new RegistrationSecret()
            .uniqueValue(DEFAULT_UNIQUE_VALUE)
            .numericalValue(DEFAULT_NUMERICAL_VALUE);
        return registrationSecret;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegistrationSecret createUpdatedEntity(EntityManager em) {
        RegistrationSecret registrationSecret = new RegistrationSecret()
            .uniqueValue(UPDATED_UNIQUE_VALUE)
            .numericalValue(UPDATED_NUMERICAL_VALUE);
        return registrationSecret;
    }

    @BeforeEach
    public void initTest() {
        registrationSecret = createEntity(em);
    }

    @Test
    @Transactional
    void createRegistrationSecret() throws Exception {
        int databaseSizeBeforeCreate = registrationSecretRepository.findAll().size();
        // Create the RegistrationSecret
        restRegistrationSecretMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(registrationSecret))
            )
            .andExpect(status().isCreated());

        // Validate the RegistrationSecret in the database
        List<RegistrationSecret> registrationSecretList = registrationSecretRepository.findAll();
        assertThat(registrationSecretList).hasSize(databaseSizeBeforeCreate + 1);
        RegistrationSecret testRegistrationSecret = registrationSecretList.get(registrationSecretList.size() - 1);
        assertThat(testRegistrationSecret.getUniqueValue()).isEqualTo(DEFAULT_UNIQUE_VALUE);
        assertThat(testRegistrationSecret.getNumericalValue()).isEqualTo(DEFAULT_NUMERICAL_VALUE);
    }

    @Test
    @Transactional
    void createRegistrationSecretWithExistingId() throws Exception {
        // Create the RegistrationSecret with an existing ID
        registrationSecret.setId(1L);

        int databaseSizeBeforeCreate = registrationSecretRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegistrationSecretMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(registrationSecret))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegistrationSecret in the database
        List<RegistrationSecret> registrationSecretList = registrationSecretRepository.findAll();
        assertThat(registrationSecretList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRegistrationSecrets() throws Exception {
        // Initialize the database
        registrationSecretRepository.saveAndFlush(registrationSecret);

        // Get all the registrationSecretList
        restRegistrationSecretMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registrationSecret.getId().intValue())))
            .andExpect(jsonPath("$.[*].uniqueValue").value(hasItem(DEFAULT_UNIQUE_VALUE)))
            .andExpect(jsonPath("$.[*].numericalValue").value(hasItem(DEFAULT_NUMERICAL_VALUE)));
    }

    @Test
    @Transactional
    void getRegistrationSecret() throws Exception {
        // Initialize the database
        registrationSecretRepository.saveAndFlush(registrationSecret);

        // Get the registrationSecret
        restRegistrationSecretMockMvc
            .perform(get(ENTITY_API_URL_ID, registrationSecret.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(registrationSecret.getId().intValue()))
            .andExpect(jsonPath("$.uniqueValue").value(DEFAULT_UNIQUE_VALUE))
            .andExpect(jsonPath("$.numericalValue").value(DEFAULT_NUMERICAL_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingRegistrationSecret() throws Exception {
        // Get the registrationSecret
        restRegistrationSecretMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRegistrationSecret() throws Exception {
        // Initialize the database
        registrationSecretRepository.saveAndFlush(registrationSecret);

        int databaseSizeBeforeUpdate = registrationSecretRepository.findAll().size();

        // Update the registrationSecret
        RegistrationSecret updatedRegistrationSecret = registrationSecretRepository.findById(registrationSecret.getId()).get();
        // Disconnect from session so that the updates on updatedRegistrationSecret are not directly saved in db
        em.detach(updatedRegistrationSecret);
        updatedRegistrationSecret.uniqueValue(UPDATED_UNIQUE_VALUE).numericalValue(UPDATED_NUMERICAL_VALUE);

        restRegistrationSecretMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRegistrationSecret.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRegistrationSecret))
            )
            .andExpect(status().isOk());

        // Validate the RegistrationSecret in the database
        List<RegistrationSecret> registrationSecretList = registrationSecretRepository.findAll();
        assertThat(registrationSecretList).hasSize(databaseSizeBeforeUpdate);
        RegistrationSecret testRegistrationSecret = registrationSecretList.get(registrationSecretList.size() - 1);
        assertThat(testRegistrationSecret.getUniqueValue()).isEqualTo(UPDATED_UNIQUE_VALUE);
        assertThat(testRegistrationSecret.getNumericalValue()).isEqualTo(UPDATED_NUMERICAL_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingRegistrationSecret() throws Exception {
        int databaseSizeBeforeUpdate = registrationSecretRepository.findAll().size();
        registrationSecret.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegistrationSecretMockMvc
            .perform(
                put(ENTITY_API_URL_ID, registrationSecret.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registrationSecret))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegistrationSecret in the database
        List<RegistrationSecret> registrationSecretList = registrationSecretRepository.findAll();
        assertThat(registrationSecretList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRegistrationSecret() throws Exception {
        int databaseSizeBeforeUpdate = registrationSecretRepository.findAll().size();
        registrationSecret.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistrationSecretMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registrationSecret))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegistrationSecret in the database
        List<RegistrationSecret> registrationSecretList = registrationSecretRepository.findAll();
        assertThat(registrationSecretList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRegistrationSecret() throws Exception {
        int databaseSizeBeforeUpdate = registrationSecretRepository.findAll().size();
        registrationSecret.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistrationSecretMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(registrationSecret))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RegistrationSecret in the database
        List<RegistrationSecret> registrationSecretList = registrationSecretRepository.findAll();
        assertThat(registrationSecretList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRegistrationSecretWithPatch() throws Exception {
        // Initialize the database
        registrationSecretRepository.saveAndFlush(registrationSecret);

        int databaseSizeBeforeUpdate = registrationSecretRepository.findAll().size();

        // Update the registrationSecret using partial update
        RegistrationSecret partialUpdatedRegistrationSecret = new RegistrationSecret();
        partialUpdatedRegistrationSecret.setId(registrationSecret.getId());

        partialUpdatedRegistrationSecret.numericalValue(UPDATED_NUMERICAL_VALUE);

        restRegistrationSecretMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegistrationSecret.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegistrationSecret))
            )
            .andExpect(status().isOk());

        // Validate the RegistrationSecret in the database
        List<RegistrationSecret> registrationSecretList = registrationSecretRepository.findAll();
        assertThat(registrationSecretList).hasSize(databaseSizeBeforeUpdate);
        RegistrationSecret testRegistrationSecret = registrationSecretList.get(registrationSecretList.size() - 1);
        assertThat(testRegistrationSecret.getUniqueValue()).isEqualTo(DEFAULT_UNIQUE_VALUE);
        assertThat(testRegistrationSecret.getNumericalValue()).isEqualTo(UPDATED_NUMERICAL_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateRegistrationSecretWithPatch() throws Exception {
        // Initialize the database
        registrationSecretRepository.saveAndFlush(registrationSecret);

        int databaseSizeBeforeUpdate = registrationSecretRepository.findAll().size();

        // Update the registrationSecret using partial update
        RegistrationSecret partialUpdatedRegistrationSecret = new RegistrationSecret();
        partialUpdatedRegistrationSecret.setId(registrationSecret.getId());

        partialUpdatedRegistrationSecret.uniqueValue(UPDATED_UNIQUE_VALUE).numericalValue(UPDATED_NUMERICAL_VALUE);

        restRegistrationSecretMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegistrationSecret.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegistrationSecret))
            )
            .andExpect(status().isOk());

        // Validate the RegistrationSecret in the database
        List<RegistrationSecret> registrationSecretList = registrationSecretRepository.findAll();
        assertThat(registrationSecretList).hasSize(databaseSizeBeforeUpdate);
        RegistrationSecret testRegistrationSecret = registrationSecretList.get(registrationSecretList.size() - 1);
        assertThat(testRegistrationSecret.getUniqueValue()).isEqualTo(UPDATED_UNIQUE_VALUE);
        assertThat(testRegistrationSecret.getNumericalValue()).isEqualTo(UPDATED_NUMERICAL_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingRegistrationSecret() throws Exception {
        int databaseSizeBeforeUpdate = registrationSecretRepository.findAll().size();
        registrationSecret.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegistrationSecretMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, registrationSecret.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(registrationSecret))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegistrationSecret in the database
        List<RegistrationSecret> registrationSecretList = registrationSecretRepository.findAll();
        assertThat(registrationSecretList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRegistrationSecret() throws Exception {
        int databaseSizeBeforeUpdate = registrationSecretRepository.findAll().size();
        registrationSecret.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistrationSecretMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(registrationSecret))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegistrationSecret in the database
        List<RegistrationSecret> registrationSecretList = registrationSecretRepository.findAll();
        assertThat(registrationSecretList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRegistrationSecret() throws Exception {
        int databaseSizeBeforeUpdate = registrationSecretRepository.findAll().size();
        registrationSecret.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistrationSecretMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(registrationSecret))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RegistrationSecret in the database
        List<RegistrationSecret> registrationSecretList = registrationSecretRepository.findAll();
        assertThat(registrationSecretList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRegistrationSecret() throws Exception {
        // Initialize the database
        registrationSecretRepository.saveAndFlush(registrationSecret);

        int databaseSizeBeforeDelete = registrationSecretRepository.findAll().size();

        // Delete the registrationSecret
        restRegistrationSecretMockMvc
            .perform(delete(ENTITY_API_URL_ID, registrationSecret.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RegistrationSecret> registrationSecretList = registrationSecretRepository.findAll();
        assertThat(registrationSecretList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
