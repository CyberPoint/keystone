package com.cyberpoint.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cyberpoint.domain.Platform;
import com.cyberpoint.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlatformTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Platform.class);
        Platform platform1 = new Platform();
        platform1.setId(1L);
        Platform platform2 = new Platform();
        platform2.setId(platform1.getId());
        assertThat(platform1).isEqualTo(platform2);
        platform2.setId(2L);
        assertThat(platform1).isNotEqualTo(platform2);
        platform1.setId(null);
        assertThat(platform1).isNotEqualTo(platform2);
    }
}
