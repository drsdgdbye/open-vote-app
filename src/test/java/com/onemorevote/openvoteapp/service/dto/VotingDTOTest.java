package com.onemorevote.openvoteapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.onemorevote.openvoteapp.web.rest.TestUtil;

public class VotingDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VotingDTO.class);
        VotingDTO votingDTO1 = new VotingDTO();
        votingDTO1.setId(1L);
        VotingDTO votingDTO2 = new VotingDTO();
        assertThat(votingDTO1).isNotEqualTo(votingDTO2);
        votingDTO2.setId(votingDTO1.getId());
        assertThat(votingDTO1).isEqualTo(votingDTO2);
        votingDTO2.setId(2L);
        assertThat(votingDTO1).isNotEqualTo(votingDTO2);
        votingDTO1.setId(null);
        assertThat(votingDTO1).isNotEqualTo(votingDTO2);
    }
}
