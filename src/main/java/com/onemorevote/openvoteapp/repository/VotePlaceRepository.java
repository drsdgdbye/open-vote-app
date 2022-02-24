package com.onemorevote.openvoteapp.repository;

import com.onemorevote.openvoteapp.domain.VotePlace;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the VotePlace entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VotePlaceRepository extends JpaRepository<VotePlace, Long> {
    Optional<VotePlace> findVotePlaceByVrn(String vrn);
}
