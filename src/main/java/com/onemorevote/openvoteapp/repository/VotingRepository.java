package com.onemorevote.openvoteapp.repository;

import com.onemorevote.openvoteapp.domain.Voting;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Voting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VotingRepository extends JpaRepository<Voting, Long> {
    boolean existsByCikVotingId(String cikVotingId);

    Voting findOneByCikVotingId(String cikVotingId);
}
