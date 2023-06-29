package com.cyberpoint.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cyberpoint.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Test class for the CrocResource REST controller.
 *
 * @see CrocResource
 */
@IntegrationTest
class CrocResourceIT {

    private MockMvc restMockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        CrocResource crocResource = new CrocResource();
        restMockMvc = MockMvcBuilders.standaloneSetup(crocResource).build();
    }

    /**
     * Test defaultAction
     */
    @Test
    void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/croc-resource/default-action")).andExpect(status().isOk());
    }
}
