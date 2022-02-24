package com.onemorevote.openvoteapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class VotingMapperTest {

    private VotingMapper votingMapper;

    @BeforeEach
    public void setUp() {
        votingMapper = new VotingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(votingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(votingMapper.fromId(null)).isNull();
    }
}
