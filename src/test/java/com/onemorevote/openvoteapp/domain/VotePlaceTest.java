package com.onemorevote.openvoteapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.onemorevote.openvoteapp.web.rest.TestUtil;

public class VotePlaceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VotePlace.class);
        VotePlace votePlace1 = new VotePlace();
        votePlace1.setId(1L);
        VotePlace votePlace2 = new VotePlace();
        votePlace2.setId(votePlace1.getId());
        assertThat(votePlace1).isEqualTo(votePlace2);
        votePlace2.setId(2L);
        assertThat(votePlace1).isNotEqualTo(votePlace2);
        votePlace1.setId(null);
        assertThat(votePlace1).isNotEqualTo(votePlace2);
    }
}
