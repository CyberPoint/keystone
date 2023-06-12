package com.cyberpoint.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cyberpoint.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaskResultTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskResult.class);
        TaskResult taskResult1 = new TaskResult();
        taskResult1.setId(1L);
        TaskResult taskResult2 = new TaskResult();
        taskResult2.setId(taskResult1.getId());
        assertThat(taskResult1).isEqualTo(taskResult2);
        taskResult2.setId(2L);
        assertThat(taskResult1).isNotEqualTo(taskResult2);
        taskResult1.setId(null);
        assertThat(taskResult1).isNotEqualTo(taskResult2);
    }
}
