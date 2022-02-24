package com.onemorevote.openvoteapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.onemorevote.openvoteapp.web.rest.TestUtil;

public class VoteDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoteDTO.class);
        VoteDTO voteDTO1 = new VoteDTO();
        voteDTO1.setId(1L);
        VoteDTO voteDTO2 = new VoteDTO();
        assertThat(voteDTO1).isNotEqualTo(voteDTO2);
        voteDTO2.setId(voteDTO1.getId());
        assertThat(voteDTO1).isEqualTo(voteDTO2);
        voteDTO2.setId(2L);
        assertThat(voteDTO1).isNotEqualTo(voteDTO2);
        voteDTO1.setId(null);
        assertThat(voteDTO1).isNotEqualTo(voteDTO2);
    }
}
