package com.onemorevote.openvoteapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class VoteMapperTest {

    private VoteMapper voteMapper;

    @BeforeEach
    public void setUp() {
        voteMapper = new VoteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(voteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(voteMapper.fromId(null)).isNull();
    }
}
