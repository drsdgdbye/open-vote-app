package com.onemorevote.openvoteapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class VotePlaceMapperTest {

    private VotePlaceMapper votePlaceMapper;

    @BeforeEach
    public void setUp() {
        votePlaceMapper = new VotePlaceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(votePlaceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(votePlaceMapper.fromId(null)).isNull();
    }
}
