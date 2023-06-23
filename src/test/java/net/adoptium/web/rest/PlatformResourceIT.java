package com.cyberpoint.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cyberpoint.IntegrationTest;
import com.cyberpoint.domain.Platform;
import com.cyberpoint.repository.PlatformRepository;
import com.cyberpoint.web.rest.PlatformResource;
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
 * Integration tests for the {@link PlatformResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlatformResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_ACCESS_LEVEL = 1L;
    private static final Long UPDATED_ACCESS_LEVEL = 2L;

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CONTENTS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTENTS = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONTENTS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTENTS_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_ADDED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ADDED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/platforms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlatformRepository platformRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlatformMockMvc;

    private Platform platform;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Platform createEntity(EntityManager em) {
        Platform platform = new Platform()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .accessLevel(DEFAULT_ACCESS_LEVEL)
            .version(DEFAULT_VERSION)
            .contents(DEFAULT_CONTENTS)
            .contentsContentType(DEFAULT_CONTENTS_CONTENT_TYPE)
            .added(DEFAULT_ADDED)
            .updated(DEFAULT_UPDATED)
            .active(DEFAULT_ACTIVE);
        return platform;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Platform createUpdatedEntity(EntityManager em) {
        Platform platform = new Platform()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .accessLevel(UPDATED_ACCESS_LEVEL)
            .version(UPDATED_VERSION)
            .contents(UPDATED_CONTENTS)
            .contentsContentType(UPDATED_CONTENTS_CONTENT_TYPE)
            .added(UPDATED_ADDED)
            .updated(UPDATED_UPDATED)
            .active(UPDATED_ACTIVE);
        return platform;
    }

    @BeforeEach
    public void initTest() {
        platform = createEntity(em);
    }

    @Test
    @Transactional
    void createPlatform() throws Exception {
        int databaseSizeBeforeCreate = platformRepository.findAll().size();
        // Create the Platform
        restPlatformMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(platform)))
            .andExpect(status().isCreated());

        // Validate the Platform in the database
        List<Platform> platformList = platformRepository.findAll();
        assertThat(platformList).hasSize(databaseSizeBeforeCreate + 1);
        Platform testPlatform = platformList.get(platformList.size() - 1);
        assertThat(testPlatform.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlatform.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPlatform.getAccessLevel()).isEqualTo(DEFAULT_ACCESS_LEVEL);
        assertThat(testPlatform.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testPlatform.getContents()).isEqualTo(DEFAULT_CONTENTS);
        assertThat(testPlatform.getContentsContentType()).isEqualTo(DEFAULT_CONTENTS_CONTENT_TYPE);
        assertThat(testPlatform.getAdded()).isEqualTo(DEFAULT_ADDED);
        assertThat(testPlatform.getUpdated()).isEqualTo(DEFAULT_UPDATED);
        assertThat(testPlatform.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    void createPlatformWithExistingId() throws Exception {
        // Create the Platform with an existing ID
        platform.setId(1L);

        int databaseSizeBeforeCreate = platformRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlatformMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(platform)))
            .andExpect(status().isBadRequest());

        // Validate the Platform in the database
        List<Platform> platformList = platformRepository.findAll();
        assertThat(platformList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = platformRepository.findAll().size();
        // set the field null
        platform.setName(null);

        // Create the Platform, which fails.

        restPlatformMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(platform)))
            .andExpect(status().isBadRequest());

        List<Platform> platformList = platformRepository.findAll();
        assertThat(platformList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAccessLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = platformRepository.findAll().size();
        // set the field null
        platform.setAccessLevel(null);

        // Create the Platform, which fails.

        restPlatformMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(platform)))
            .andExpect(status().isBadRequest());

        List<Platform> platformList = platformRepository.findAll();
        assertThat(platformList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPlatforms() throws Exception {
        // Initialize the database
        platformRepository.saveAndFlush(platform);

        // Get all the platformList
        restPlatformMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(platform.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].accessLevel").value(hasItem(DEFAULT_ACCESS_LEVEL.intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].contentsContentType").value(hasItem(DEFAULT_CONTENTS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].contents").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENTS))))
            .andExpect(jsonPath("$.[*].added").value(hasItem(DEFAULT_ADDED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getPlatform() throws Exception {
        // Initialize the database
        platformRepository.saveAndFlush(platform);

        // Get the platform
        restPlatformMockMvc
            .perform(get(ENTITY_API_URL_ID, platform.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(platform.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.accessLevel").value(DEFAULT_ACCESS_LEVEL.intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.contentsContentType").value(DEFAULT_CONTENTS_CONTENT_TYPE))
            .andExpect(jsonPath("$.contents").value(Base64Utils.encodeToString(DEFAULT_CONTENTS)))
            .andExpect(jsonPath("$.added").value(DEFAULT_ADDED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingPlatform() throws Exception {
        // Get the platform
        restPlatformMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlatform() throws Exception {
        // Initialize the database
        platformRepository.saveAndFlush(platform);

        int databaseSizeBeforeUpdate = platformRepository.findAll().size();

        // Update the platform
        Platform updatedPlatform = platformRepository.findById(platform.getId()).get();
        // Disconnect from session so that the updates on updatedPlatform are not directly saved in db
        em.detach(updatedPlatform);
        updatedPlatform
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .accessLevel(UPDATED_ACCESS_LEVEL)
            .version(UPDATED_VERSION)
            .contents(UPDATED_CONTENTS)
            .contentsContentType(UPDATED_CONTENTS_CONTENT_TYPE)
            .added(UPDATED_ADDED)
            .updated(UPDATED_UPDATED)
            .active(UPDATED_ACTIVE);

        restPlatformMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlatform.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPlatform))
            )
            .andExpect(status().isOk());

        // Validate the Platform in the database
        List<Platform> platformList = platformRepository.findAll();
        assertThat(platformList).hasSize(databaseSizeBeforeUpdate);
        Platform testPlatform = platformList.get(platformList.size() - 1);
        assertThat(testPlatform.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlatform.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPlatform.getAccessLevel()).isEqualTo(UPDATED_ACCESS_LEVEL);
        assertThat(testPlatform.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testPlatform.getContents()).isEqualTo(UPDATED_CONTENTS);
        assertThat(testPlatform.getContentsContentType()).isEqualTo(UPDATED_CONTENTS_CONTENT_TYPE);
        assertThat(testPlatform.getAdded()).isEqualTo(UPDATED_ADDED);
        assertThat(testPlatform.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testPlatform.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingPlatform() throws Exception {
        int databaseSizeBeforeUpdate = platformRepository.findAll().size();
        platform.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlatformMockMvc
            .perform(
                put(ENTITY_API_URL_ID, platform.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(platform))
            )
            .andExpect(status().isBadRequest());

        // Validate the Platform in the database
        List<Platform> platformList = platformRepository.findAll();
        assertThat(platformList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlatform() throws Exception {
        int databaseSizeBeforeUpdate = platformRepository.findAll().size();
        platform.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlatformMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(platform))
            )
            .andExpect(status().isBadRequest());

        // Validate the Platform in the database
        List<Platform> platformList = platformRepository.findAll();
        assertThat(platformList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlatform() throws Exception {
        int databaseSizeBeforeUpdate = platformRepository.findAll().size();
        platform.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlatformMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(platform)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Platform in the database
        List<Platform> platformList = platformRepository.findAll();
        assertThat(platformList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlatformWithPatch() throws Exception {
        // Initialize the database
        platformRepository.saveAndFlush(platform);

        int databaseSizeBeforeUpdate = platformRepository.findAll().size();

        // Update the platform using partial update
        Platform partialUpdatedPlatform = new Platform();
        partialUpdatedPlatform.setId(platform.getId());

        partialUpdatedPlatform
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .version(UPDATED_VERSION)
            .contents(UPDATED_CONTENTS)
            .contentsContentType(UPDATED_CONTENTS_CONTENT_TYPE)
            .added(UPDATED_ADDED)
            .updated(UPDATED_UPDATED);

        restPlatformMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlatform.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlatform))
            )
            .andExpect(status().isOk());

        // Validate the Platform in the database
        List<Platform> platformList = platformRepository.findAll();
        assertThat(platformList).hasSize(databaseSizeBeforeUpdate);
        Platform testPlatform = platformList.get(platformList.size() - 1);
        assertThat(testPlatform.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlatform.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPlatform.getAccessLevel()).isEqualTo(DEFAULT_ACCESS_LEVEL);
        assertThat(testPlatform.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testPlatform.getContents()).isEqualTo(UPDATED_CONTENTS);
        assertThat(testPlatform.getContentsContentType()).isEqualTo(UPDATED_CONTENTS_CONTENT_TYPE);
        assertThat(testPlatform.getAdded()).isEqualTo(UPDATED_ADDED);
        assertThat(testPlatform.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testPlatform.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdatePlatformWithPatch() throws Exception {
        // Initialize the database
        platformRepository.saveAndFlush(platform);

        int databaseSizeBeforeUpdate = platformRepository.findAll().size();

        // Update the platform using partial update
        Platform partialUpdatedPlatform = new Platform();
        partialUpdatedPlatform.setId(platform.getId());

        partialUpdatedPlatform
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .accessLevel(UPDATED_ACCESS_LEVEL)
            .version(UPDATED_VERSION)
            .contents(UPDATED_CONTENTS)
            .contentsContentType(UPDATED_CONTENTS_CONTENT_TYPE)
            .added(UPDATED_ADDED)
            .updated(UPDATED_UPDATED)
            .active(UPDATED_ACTIVE);

        restPlatformMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlatform.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlatform))
            )
            .andExpect(status().isOk());

        // Validate the Platform in the database
        List<Platform> platformList = platformRepository.findAll();
        assertThat(platformList).hasSize(databaseSizeBeforeUpdate);
        Platform testPlatform = platformList.get(platformList.size() - 1);
        assertThat(testPlatform.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlatform.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPlatform.getAccessLevel()).isEqualTo(UPDATED_ACCESS_LEVEL);
        assertThat(testPlatform.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testPlatform.getContents()).isEqualTo(UPDATED_CONTENTS);
        assertThat(testPlatform.getContentsContentType()).isEqualTo(UPDATED_CONTENTS_CONTENT_TYPE);
        assertThat(testPlatform.getAdded()).isEqualTo(UPDATED_ADDED);
        assertThat(testPlatform.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testPlatform.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingPlatform() throws Exception {
        int databaseSizeBeforeUpdate = platformRepository.findAll().size();
        platform.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlatformMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, platform.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(platform))
            )
            .andExpect(status().isBadRequest());

        // Validate the Platform in the database
        List<Platform> platformList = platformRepository.findAll();
        assertThat(platformList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlatform() throws Exception {
        int databaseSizeBeforeUpdate = platformRepository.findAll().size();
        platform.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlatformMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(platform))
            )
            .andExpect(status().isBadRequest());

        // Validate the Platform in the database
        List<Platform> platformList = platformRepository.findAll();
        assertThat(platformList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlatform() throws Exception {
        int databaseSizeBeforeUpdate = platformRepository.findAll().size();
        platform.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlatformMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(platform)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Platform in the database
        List<Platform> platformList = platformRepository.findAll();
        assertThat(platformList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlatform() throws Exception {
        // Initialize the database
        platformRepository.saveAndFlush(platform);

        int databaseSizeBeforeDelete = platformRepository.findAll().size();

        // Delete the platform
        restPlatformMockMvc
            .perform(delete(ENTITY_API_URL_ID, platform.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Platform> platformList = platformRepository.findAll();
        assertThat(platformList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
