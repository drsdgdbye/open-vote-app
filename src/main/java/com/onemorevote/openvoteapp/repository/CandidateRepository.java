package com.onemorevote.openvoteapp.repository;

import com.onemorevote.openvoteapp.domain.Candidate;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Candidate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    boolean existsByCikCandidateId(String cikCandidateId);

    @Query("select c from Candidate c where c.cikCandidateId = :cikCandidateId")
    Candidate findOneByCikCandidateId(@Param("cikCandidateId") String cikCandidateId);
}
