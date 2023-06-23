package com.cyberpoint.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cyberpoint.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RegistrationEventTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegistrationEvent.class);
        RegistrationEvent registrationEvent1 = new RegistrationEvent();
        registrationEvent1.setId(1L);
        RegistrationEvent registrationEvent2 = new RegistrationEvent();
        registrationEvent2.setId(registrationEvent1.getId());
        assertThat(registrationEvent1).isEqualTo(registrationEvent2);
        registrationEvent2.setId(2L);
        assertThat(registrationEvent1).isNotEqualTo(registrationEvent2);
        registrationEvent1.setId(null);
        assertThat(registrationEvent1).isNotEqualTo(registrationEvent2);
    }
}
