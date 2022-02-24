package com.onemorevote.openvoteapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CandidateMapperTest {

    private CandidateMapper candidateMapper;

    @BeforeEach
    public void setUp() {
        candidateMapper = new CandidateMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(candidateMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(candidateMapper.fromId(null)).isNull();
    }
}
