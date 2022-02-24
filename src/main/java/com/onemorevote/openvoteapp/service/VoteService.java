package com.onemorevote.openvoteapp.service;

import com.onemorevote.openvoteapp.domain.Candidate;
import com.onemorevote.openvoteapp.domain.User;
import com.onemorevote.openvoteapp.domain.Vote;
import com.onemorevote.openvoteapp.domain.Voting;
import com.onemorevote.openvoteapp.repository.UserRepository;
import com.onemorevote.openvoteapp.repository.projections.VoteCounterProjection;
import com.onemorevote.openvoteapp.repository.VoteRepository;
import com.onemorevote.openvoteapp.service.dto.*;
import com.onemorevote.openvoteapp.service.mapper.VoteMapper;
import com.onemorevote.openvoteapp.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

/**
 * Service Implementation for managing {@link Vote}.
 */
@Service
@Transactional
public class VoteService {

    private final Logger log = LoggerFactory.getLogger(VoteService.class);

    private final VoteRepository voteRepository;

    private final CandidateService candidateService;

    private final VotingService votingService;

    private final VoteMapper voteMapper;

    private final UserRepository userRepository;

    public VoteService(VoteRepository voteRepository, VoteMapper voteMapper, CandidateService candidateService, VotingService votingService, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.voteMapper = voteMapper;
        this.candidateService = candidateService;
        this.votingService = votingService;
        this.userRepository = userRepository;
    }

    /**
     * Save a vote.
     * Save the candidate if not exists.
     * Save the voting if not exists.
     *
     * @param voteDTO the entity to save.
     * @return id by the Vote entity.
     */
    public Long save(VoteDTO voteDTO) {
        log.debug("Request to save Vote : {}", voteDTO);

        CandidateDTO candidateDTO = new CandidateDTO();
        String cikCandidateId = voteDTO.getCikCandidateId();
        if (candidateService.isCandidateNotExists(cikCandidateId)) {
            candidateDTO.setCikCandidateId(cikCandidateId);
            candidateDTO.setName(voteDTO.getCandidateName());
            candidateDTO.setPoliticalParty(voteDTO.getPoliticalParty());
            candidateService.save(candidateDTO);
        }

        String cikVotingId = voteDTO.getCikVotingId();
        if (votingService.isVotingNotExists(cikVotingId)) {
            VotingDTO votingDTO = new VotingDTO();
            votingDTO.setCikVotingId(cikVotingId);
            votingDTO.setName(voteDTO.getVotingName());
            votingDTO.setDate(voteDTO.getVotingDate());
            votingService.save(votingDTO);
        }
        Vote vote = new Vote();
        if (Objects.isNull(voteDTO.getCreateDate())) {
            vote.setCreateDate(Instant.now());
        }
        vote.setUserId(voteDTO.getUserId());
        vote.setCandidate(candidateService.findOneByCikCandidateId(cikCandidateId));
        vote.setVoting(votingService.getVotingByCikVotingId(cikVotingId));
        vote = voteRepository.save(vote);
        return vote.getId();
    }

    /**
     * Get all the votes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VoteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Votes");
        return voteRepository.findAll(pageable)
            .map(voteMapper::toDto);
    }


    /**
     * Get one vote by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VoteDTO> findOne(Long id) {
        log.debug("Request to get Vote : {}", id);
        return voteRepository.findById(id)
            .map(voteMapper::toDto);
    }

    /**
     * Delete the vote by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Vote : {}", id);
        voteRepository.deleteById(id);
    }

    /**
     * Check if the vote exist by user id and cikVoting id.
     *
     * @param userId the id of the User entity.
     * @param cikVotingId the id of the Voting entity.
     * @return boolean existing the vote.
     */
    public boolean isSameVote(Long userId, String cikVotingId) {
        return voteRepository.existsByUserIdAndCikVotingId(userId, cikVotingId);
    }

    /**
     * Get List of candidates with votes count by cikVoting id with myCandidateCikId.
     *
     * @param user the User entity.
     * @param cikVotingId the cikVoting id of the Voting entity.
     * @return VoteCounterWithMyCandidateDTO.
     */
    public VoteCounterWithMyCandidateDTO getCandidatesWithCountVotesByCikVotingId(User user, String cikVotingId) {
        List<Vote> votes = voteRepository.findAllByCiKVotingId(cikVotingId);
        Map<Candidate, Long> countVotesMap = votes.stream().collect(Collectors.groupingBy(Vote::getCandidate, Collectors.counting()));
        List<VoteCounterDTO> candidatesVotesCountList = new ArrayList<>();
        countVotesMap.forEach((k, v) -> candidatesVotesCountList.add(new VoteCounterDTO(k, v)));

        Vote vote = voteRepository.findVoteByUserIdAndCikVotingId(user.getId(), cikVotingId).orElse(null);
        String myCandidateCikId = Objects.isNull(vote) ? null : vote.getCandidate().getCikCandidateId();
        return new VoteCounterWithMyCandidateDTO(myCandidateCikId, candidatesVotesCountList);
    }

    /**
     * Get List of candidates with votes from user's vote place group by voting.
     *
     * @param user current user.
     * @return List VoteCounterByVotePlaceDTO.
     */
    public List<VoteCounterByVotePlaceDTO> getCandidatesWithCountVotesFromVotePlaceGroupByVoting(User user) {
        List<Long> userIdsByVotePlace = userRepository.findAllByVotePlace(user.getVotePlace()).stream()
            .map(User::getId)
            .collect(Collectors.toList());

        List<Vote> votes = voteRepository.findAllByUserIds(userIdsByVotePlace);
        List<Voting> votings = votes.stream()
            .map(Vote::getVoting)
            .distinct()
            .collect(Collectors.toList());

        List<VoteCounterByVotePlaceDTO> candidateVotes = new ArrayList<>();
        for (Voting voting : votings) {
            Map<Candidate, Long> countVotesMap = voteRepository.findAllByVoting(voting).stream().collect(Collectors.groupingBy(Vote::getCandidate, Collectors.counting()));
            List<VoteCounterDTO> candidatesVotesCountList = new ArrayList<>();
            countVotesMap.forEach((k, v) -> candidatesVotesCountList.add(new VoteCounterDTO(k, v)));
            candidateVotes.add(new VoteCounterByVotePlaceDTO(voting, candidatesVotesCountList));
        }

        return candidateVotes;
    }

    /**
     * Get List of candidates with votes count by cikVoting id by page.
     *
     * @param pageable the pagination information.
     * @param cikVotingId the cikVoting id of the Voting entity.
     * @return Page<VoteCounterProjection>.
     */
    public Page<VoteCounterProjection> getCandidatesByCikVotingId(Pageable pageable, String cikVotingId) {
        log.debug("Request to get statistics by voting");
        Voting voting = votingService.getVotingByCikVotingId(cikVotingId);
        if (Objects.isNull(cikVotingId)) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "notexistfromservice");
        }
        return voteRepository.findAllByVoting(voting, pageable);
    }

    public String getAllVotesCountByCikVotingId(String cikVotingId) {
        Voting voting = votingService.getVotingByCikVotingId(cikVotingId);
        return voteRepository.countVotesByVoting(voting).toString();
    }
}
