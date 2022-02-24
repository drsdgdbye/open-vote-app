package com.onemorevote.openvoteapp.service.mapper;


import com.onemorevote.openvoteapp.domain.*;
import com.onemorevote.openvoteapp.service.dto.CandidateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Candidate} and its DTO {@link CandidateDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CandidateMapper extends EntityMapper<CandidateDTO, Candidate> {



    default Candidate fromId(Long id) {
        if (id == null) {
            return null;
        }
        Candidate candidate = new Candidate();
        candidate.setId(id);
        return candidate;
    }
}
