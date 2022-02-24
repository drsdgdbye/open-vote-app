package com.onemorevote.openvoteapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.onemorevote.openvoteapp.web.rest.TestUtil;

public class CandidateDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CandidateDTO.class);
        CandidateDTO candidateDTO1 = new CandidateDTO();
        candidateDTO1.setId(1L);
        CandidateDTO candidateDTO2 = new CandidateDTO();
        assertThat(candidateDTO1).isNotEqualTo(candidateDTO2);
        candidateDTO2.setId(candidateDTO1.getId());
        assertThat(candidateDTO1).isEqualTo(candidateDTO2);
        candidateDTO2.setId(2L);
        assertThat(candidateDTO1).isNotEqualTo(candidateDTO2);
        candidateDTO1.setId(null);
        assertThat(candidateDTO1).isNotEqualTo(candidateDTO2);
    }
}
