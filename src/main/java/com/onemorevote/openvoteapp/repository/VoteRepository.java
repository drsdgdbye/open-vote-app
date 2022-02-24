package com.onemorevote.openvoteapp.repository;

import com.onemorevote.openvoteapp.domain.Candidate;
import com.onemorevote.openvoteapp.domain.Vote;

import com.onemorevote.openvoteapp.domain.Voting;
import com.onemorevote.openvoteapp.repository.projections.VoteCounterProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Vote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByUserIdAndVoting(Long userId, Voting voting);

    @Query("select case when count(v) > 0 then true else false end from Vote v where v.userId = :userId and v.voting.cikVotingId = :votingId")
    boolean existsByUserIdAndCikVotingId(@Param("userId")Long userId, @Param("votingId") String cikVotingId);

    @Query("select v from Vote v where v.userId = :userId and v.voting.cikVotingId = :votingId")
    Optional<Vote> findVoteByUserIdAndCikVotingId(@Param("userId")Long userId, @Param("votingId") String cikVotingId);

    List<Vote> findAllByVoting(Voting voting);

    Long countVotesByCandidate(Candidate candidate);

    @Query("select v from Vote v where v.voting.cikVotingId = :id")
    List<Vote> findAllByCiKVotingId(@Param("id") String cikVotingId);

    @Query("select v.candidate.id as candidateId, v.candidate.name as candidateName, v.candidate.politicalParty as candidateParty, count(v.candidate) as candidateCount from Vote v where v.voting = :voting group by v.candidate.id")
    Page<VoteCounterProjection> findAllByVoting(@Param("voting") Voting voting, Pageable pageable);

    @Query("select v from Vote v where v.userId in :ids")
    List<Vote> findAllByUserIds(@Param("ids") List<Long> userIds);

    Long countVotesByVoting(Voting voting);
}
