package com.cyberpoint.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cyberpoint.IntegrationTest;
import com.cyberpoint.domain.Agent;
import com.cyberpoint.repository.AgentRepository;
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
 * Integration tests for the {@link AgentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AgentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CLASSIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_CLASSIFICATION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_INSTALLED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INSTALLED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UNINSTALL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UNINSTALL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Boolean DEFAULT_DEACTIVATE = false;
    private static final Boolean UPDATED_DEACTIVATE = true;

    private static final Boolean DEFAULT_AUTO_REGISTERED = false;
    private static final Boolean UPDATED_AUTO_REGISTERED = true;

    private static final Boolean DEFAULT_APPROVED = false;
    private static final Boolean UPDATED_APPROVED = true;

    private static final String ENTITY_API_URL = "/api/agents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAgentMockMvc;

    private Agent agent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agent createEntity(EntityManager em) {
        Agent agent = new Agent()
            .name(DEFAULT_NAME)
            .classification(DEFAULT_CLASSIFICATION)
            .description(DEFAULT_DESCRIPTION)
            .installedOn(DEFAULT_INSTALLED_ON)
            .uninstallDate(DEFAULT_UNINSTALL_DATE)
            .active(DEFAULT_ACTIVE)
            .deactivate(DEFAULT_DEACTIVATE)
            .autoRegistered(DEFAULT_AUTO_REGISTERED)
            .approved(DEFAULT_APPROVED);
        return agent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agent createUpdatedEntity(EntityManager em) {
        Agent agent = new Agent()
            .name(UPDATED_NAME)
            .classification(UPDATED_CLASSIFICATION)
            .description(UPDATED_DESCRIPTION)
            .installedOn(UPDATED_INSTALLED_ON)
            .uninstallDate(UPDATED_UNINSTALL_DATE)
            .active(UPDATED_ACTIVE)
            .deactivate(UPDATED_DEACTIVATE)
            .autoRegistered(UPDATED_AUTO_REGISTERED)
            .approved(UPDATED_APPROVED);
        return agent;
    }

    @BeforeEach
    public void initTest() {
        agent = createEntity(em);
    }

    @Test
    @Transactional
    void createAgent() throws Exception {
        int databaseSizeBeforeCreate = agentRepository.findAll().size();
        // Create the Agent
        restAgentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(agent)))
            .andExpect(status().isCreated());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeCreate + 1);
        Agent testAgent = agentList.get(agentList.size() - 1);
        assertThat(testAgent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAgent.getClassification()).isEqualTo(DEFAULT_CLASSIFICATION);
        assertThat(testAgent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAgent.getInstalledOn()).isEqualTo(DEFAULT_INSTALLED_ON);
        assertThat(testAgent.getUninstallDate()).isEqualTo(DEFAULT_UNINSTALL_DATE);
        assertThat(testAgent.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testAgent.getDeactivate()).isEqualTo(DEFAULT_DEACTIVATE);
        assertThat(testAgent.getAutoRegistered()).isEqualTo(DEFAULT_AUTO_REGISTERED);
        assertThat(testAgent.getApproved()).isEqualTo(DEFAULT_APPROVED);
    }

    @Test
    @Transactional
    void createAgentWithExistingId() throws Exception {
        // Create the Agent with an existing ID
        agent.setId(1L);

        int databaseSizeBeforeCreate = agentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(agent)))
            .andExpect(status().isBadRequest());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = agentRepository.findAll().size();
        // set the field null
        agent.setName(null);

        // Create the Agent, which fails.

        restAgentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(agent)))
            .andExpect(status().isBadRequest());

        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAgents() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);

        // Get all the agentList
        restAgentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agent.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].classification").value(hasItem(DEFAULT_CLASSIFICATION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].installedOn").value(hasItem(DEFAULT_INSTALLED_ON.toString())))
            .andExpect(jsonPath("$.[*].uninstallDate").value(hasItem(DEFAULT_UNINSTALL_DATE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].deactivate").value(hasItem(DEFAULT_DEACTIVATE.booleanValue())))
            .andExpect(jsonPath("$.[*].autoRegistered").value(hasItem(DEFAULT_AUTO_REGISTERED.booleanValue())))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())));
    }

    @Test
    @Transactional
    void getAgent() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);

        // Get the agent
        restAgentMockMvc
            .perform(get(ENTITY_API_URL_ID, agent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(agent.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.classification").value(DEFAULT_CLASSIFICATION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.installedOn").value(DEFAULT_INSTALLED_ON.toString()))
            .andExpect(jsonPath("$.uninstallDate").value(DEFAULT_UNINSTALL_DATE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.deactivate").value(DEFAULT_DEACTIVATE.booleanValue()))
            .andExpect(jsonPath("$.autoRegistered").value(DEFAULT_AUTO_REGISTERED.booleanValue()))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingAgent() throws Exception {
        // Get the agent
        restAgentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAgent() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);

        int databaseSizeBeforeUpdate = agentRepository.findAll().size();

        // Update the agent
        Agent updatedAgent = agentRepository.findById(agent.getId()).get();
        // Disconnect from session so that the updates on updatedAgent are not directly saved in db
        em.detach(updatedAgent);
        updatedAgent
            .name(UPDATED_NAME)
            .classification(UPDATED_CLASSIFICATION)
            .description(UPDATED_DESCRIPTION)
            .installedOn(UPDATED_INSTALLED_ON)
            .uninstallDate(UPDATED_UNINSTALL_DATE)
            .active(UPDATED_ACTIVE)
            .deactivate(UPDATED_DEACTIVATE)
            .autoRegistered(UPDATED_AUTO_REGISTERED)
            .approved(UPDATED_APPROVED);

        restAgentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAgent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAgent))
            )
            .andExpect(status().isOk());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
        Agent testAgent = agentList.get(agentList.size() - 1);
        assertThat(testAgent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAgent.getClassification()).isEqualTo(UPDATED_CLASSIFICATION);
        assertThat(testAgent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAgent.getInstalledOn()).isEqualTo(UPDATED_INSTALLED_ON);
        assertThat(testAgent.getUninstallDate()).isEqualTo(UPDATED_UNINSTALL_DATE);
        assertThat(testAgent.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testAgent.getDeactivate()).isEqualTo(UPDATED_DEACTIVATE);
        assertThat(testAgent.getAutoRegistered()).isEqualTo(UPDATED_AUTO_REGISTERED);
        assertThat(testAgent.getApproved()).isEqualTo(UPDATED_APPROVED);
    }

    @Test
    @Transactional
    void putNonExistingAgent() throws Exception {
        int databaseSizeBeforeUpdate = agentRepository.findAll().size();
        agent.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, agent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(agent))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAgent() throws Exception {
        int databaseSizeBeforeUpdate = agentRepository.findAll().size();
        agent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(agent))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAgent() throws Exception {
        int databaseSizeBeforeUpdate = agentRepository.findAll().size();
        agent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(agent)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAgentWithPatch() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);

        int databaseSizeBeforeUpdate = agentRepository.findAll().size();

        // Update the agent using partial update
        Agent partialUpdatedAgent = new Agent();
        partialUpdatedAgent.setId(agent.getId());

        partialUpdatedAgent.installedOn(UPDATED_INSTALLED_ON).active(UPDATED_ACTIVE).approved(UPDATED_APPROVED);

        restAgentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAgent))
            )
            .andExpect(status().isOk());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
        Agent testAgent = agentList.get(agentList.size() - 1);
        assertThat(testAgent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAgent.getClassification()).isEqualTo(DEFAULT_CLASSIFICATION);
        assertThat(testAgent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAgent.getInstalledOn()).isEqualTo(UPDATED_INSTALLED_ON);
        assertThat(testAgent.getUninstallDate()).isEqualTo(DEFAULT_UNINSTALL_DATE);
        assertThat(testAgent.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testAgent.getDeactivate()).isEqualTo(DEFAULT_DEACTIVATE);
        assertThat(testAgent.getAutoRegistered()).isEqualTo(DEFAULT_AUTO_REGISTERED);
        assertThat(testAgent.getApproved()).isEqualTo(UPDATED_APPROVED);
    }

    @Test
    @Transactional
    void fullUpdateAgentWithPatch() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);

        int databaseSizeBeforeUpdate = agentRepository.findAll().size();

        // Update the agent using partial update
        Agent partialUpdatedAgent = new Agent();
        partialUpdatedAgent.setId(agent.getId());

        partialUpdatedAgent
            .name(UPDATED_NAME)
            .classification(UPDATED_CLASSIFICATION)
            .description(UPDATED_DESCRIPTION)
            .installedOn(UPDATED_INSTALLED_ON)
            .uninstallDate(UPDATED_UNINSTALL_DATE)
            .active(UPDATED_ACTIVE)
            .deactivate(UPDATED_DEACTIVATE)
            .autoRegistered(UPDATED_AUTO_REGISTERED)
            .approved(UPDATED_APPROVED);

        restAgentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAgent))
            )
            .andExpect(status().isOk());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
        Agent testAgent = agentList.get(agentList.size() - 1);
        assertThat(testAgent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAgent.getClassification()).isEqualTo(UPDATED_CLASSIFICATION);
        assertThat(testAgent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAgent.getInstalledOn()).isEqualTo(UPDATED_INSTALLED_ON);
        assertThat(testAgent.getUninstallDate()).isEqualTo(UPDATED_UNINSTALL_DATE);
        assertThat(testAgent.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testAgent.getDeactivate()).isEqualTo(UPDATED_DEACTIVATE);
        assertThat(testAgent.getAutoRegistered()).isEqualTo(UPDATED_AUTO_REGISTERED);
        assertThat(testAgent.getApproved()).isEqualTo(UPDATED_APPROVED);
    }

    @Test
    @Transactional
    void patchNonExistingAgent() throws Exception {
        int databaseSizeBeforeUpdate = agentRepository.findAll().size();
        agent.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, agent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(agent))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAgent() throws Exception {
        int databaseSizeBeforeUpdate = agentRepository.findAll().size();
        agent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(agent))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAgent() throws Exception {
        int databaseSizeBeforeUpdate = agentRepository.findAll().size();
        agent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(agent)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAgent() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);

        int databaseSizeBeforeDelete = agentRepository.findAll().size();

        // Delete the agent
        restAgentMockMvc
            .perform(delete(ENTITY_API_URL_ID, agent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
