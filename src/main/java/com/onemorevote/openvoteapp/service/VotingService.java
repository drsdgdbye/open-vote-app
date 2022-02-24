package com.onemorevote.openvoteapp.service;

import com.onemorevote.openvoteapp.domain.Voting;
import com.onemorevote.openvoteapp.repository.VotingRepository;
import com.onemorevote.openvoteapp.service.dto.VotingDTO;
import com.onemorevote.openvoteapp.service.mapper.VotingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Voting}.
 */
@Service
@Transactional
public class VotingService {

    private final Logger log = LoggerFactory.getLogger(VotingService.class);

    private final VotingRepository votingRepository;

    private final VotingMapper votingMapper;

    public VotingService(VotingRepository votingRepository, VotingMapper votingMapper) {
        this.votingRepository = votingRepository;
        this.votingMapper = votingMapper;
    }

    /**
     * Save a voting.
     *
     * @param votingDTO the entity to save.
     * @return the persisted entity.
     */
    public VotingDTO save(VotingDTO votingDTO) {
        log.debug("Request to save Voting : {}", votingDTO);
        Voting voting = votingMapper.toEntity(votingDTO);
        voting = votingRepository.save(voting);
        return votingMapper.toDto(voting);
    }

    /**
     * Get all the votings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VotingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Votings");
        return votingRepository.findAll(pageable)
            .map(votingMapper::toDto);
    }


    /**
     * Get one voting by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VotingDTO> findOne(Long id) {
        log.debug("Request to get Voting : {}", id);
        return votingRepository.findById(id)
            .map(votingMapper::toDto);
    }

    /**
     * Delete the voting by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Voting : {}", id);
        votingRepository.deleteById(id);
    }

    /**
     * Get is the voting exists.
     *
     * @param cikVotingId the cik_id of the Voting entity.
     * @return boolean
     */
    public boolean isVotingNotExists(String cikVotingId) {
        return !votingRepository.existsByCikVotingId(cikVotingId);
    }

    /**
     * Get the voting by cikVoting id.
     *
     * @param cikVotingId the cikVoting id of the entity.
     * @return the Voting entity.
     */
    public Voting getVotingByCikVotingId(String cikVotingId) {
        return votingRepository.findOneByCikVotingId(cikVotingId);
    }
}
