package com.cyberpoint.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cyberpoint.domain.CallBack;
import com.cyberpoint.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CallBackTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CallBack.class);
        CallBack callBack1 = new CallBack();
        callBack1.setId(1L);
        CallBack callBack2 = new CallBack();
        callBack2.setId(callBack1.getId());
        assertThat(callBack1).isEqualTo(callBack2);
        callBack2.setId(2L);
        assertThat(callBack1).isNotEqualTo(callBack2);
        callBack1.setId(null);
        assertThat(callBack1).isNotEqualTo(callBack2);
    }
}
