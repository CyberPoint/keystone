package com.cyberpoint.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cyberpoint.domain.RegistrationSecret;
import com.cyberpoint.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RegistrationSecretTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegistrationSecret.class);
        RegistrationSecret registrationSecret1 = new RegistrationSecret();
        registrationSecret1.setId(1L);
        RegistrationSecret registrationSecret2 = new RegistrationSecret();
        registrationSecret2.setId(registrationSecret1.getId());
        assertThat(registrationSecret1).isEqualTo(registrationSecret2);
        registrationSecret2.setId(2L);
        assertThat(registrationSecret1).isNotEqualTo(registrationSecret2);
        registrationSecret1.setId(null);
        assertThat(registrationSecret1).isNotEqualTo(registrationSecret2);
    }
}
