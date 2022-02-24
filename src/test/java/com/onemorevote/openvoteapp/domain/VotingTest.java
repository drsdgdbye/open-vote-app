package com.onemorevote.openvoteapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.onemorevote.openvoteapp.web.rest.TestUtil;

public class VotingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Voting.class);
        Voting voting1 = new Voting();
        voting1.setId(1L);
        Voting voting2 = new Voting();
        voting2.setId(voting1.getId());
        assertThat(voting1).isEqualTo(voting2);
        voting2.setId(2L);
        assertThat(voting1).isNotEqualTo(voting2);
        voting1.setId(null);
        assertThat(voting1).isNotEqualTo(voting2);
    }
}
