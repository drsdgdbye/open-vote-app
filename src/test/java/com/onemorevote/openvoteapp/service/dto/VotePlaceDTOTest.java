package com.onemorevote.openvoteapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.onemorevote.openvoteapp.web.rest.TestUtil;

public class VotePlaceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VotePlaceDTO.class);
        VotePlaceDTO votePlaceDTO1 = new VotePlaceDTO();
        votePlaceDTO1.setId(1L);
        VotePlaceDTO votePlaceDTO2 = new VotePlaceDTO();
        assertThat(votePlaceDTO1).isNotEqualTo(votePlaceDTO2);
        votePlaceDTO2.setId(votePlaceDTO1.getId());
        assertThat(votePlaceDTO1).isEqualTo(votePlaceDTO2);
        votePlaceDTO2.setId(2L);
        assertThat(votePlaceDTO1).isNotEqualTo(votePlaceDTO2);
        votePlaceDTO1.setId(null);
        assertThat(votePlaceDTO1).isNotEqualTo(votePlaceDTO2);
    }
}
