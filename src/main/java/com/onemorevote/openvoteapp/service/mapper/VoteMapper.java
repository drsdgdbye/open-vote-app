package com.onemorevote.openvoteapp.service.mapper;


import com.onemorevote.openvoteapp.domain.*;
import com.onemorevote.openvoteapp.service.dto.VoteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vote} and its DTO {@link VoteDTO}.
 */
@Mapper(componentModel = "spring", uses = {CandidateMapper.class, VotingMapper.class})
public interface VoteMapper extends EntityMapper<VoteDTO, Vote> {

    //these fields has added for frontend display
    @Mapping(source = "candidate.cikCandidateId", target = "cikCandidateId")
    @Mapping(source = "candidate.name", target = "candidateName")
    @Mapping(source = "candidate.politicalParty", target = "politicalParty")
    @Mapping(source = "voting.cikVotingId", target = "cikVotingId")
    @Mapping(source = "voting.name", target = "votingName")
    @Mapping(source = "voting.date", target = "votingDate")
    VoteDTO toDto(Vote vote);

    @Mapping(source = "cikCandidateId", target = "candidate")
    @Mapping(source = "cikVotingId", target = "voting")
    Vote toEntity(VoteDTO voteDTO);

    default Vote fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vote vote = new Vote();
        vote.setId(id);
        return vote;
    }
}
