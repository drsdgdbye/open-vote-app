package com.onemorevote.openvoteapp.service.mapper;


import com.onemorevote.openvoteapp.domain.*;
import com.onemorevote.openvoteapp.service.dto.VotePlaceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link VotePlace} and its DTO {@link VotePlaceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VotePlaceMapper extends EntityMapper<VotePlaceDTO, VotePlace> {



    default VotePlace fromId(Long id) {
        if (id == null) {
            return null;
        }
        VotePlace votePlace = new VotePlace();
        votePlace.setId(id);
        return votePlace;
    }
}
