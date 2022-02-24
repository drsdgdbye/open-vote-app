package com.onemorevote.openvoteapp.service.mapper;


import com.onemorevote.openvoteapp.domain.*;
import com.onemorevote.openvoteapp.service.dto.VotingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Voting} and its DTO {@link VotingDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VotingMapper extends EntityMapper<VotingDTO, Voting> {



    default Voting fromId(Long id) {
        if (id == null) {
            return null;
        }
        Voting voting = new Voting();
        voting.setId(id);
        return voting;
    }
}
