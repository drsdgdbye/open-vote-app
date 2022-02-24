package com.onemorevote.openvoteapp.service;

import com.onemorevote.openvoteapp.domain.VotePlace;
import com.onemorevote.openvoteapp.repository.VotePlaceRepository;
import com.onemorevote.openvoteapp.service.dto.VotePlaceDTO;
import com.onemorevote.openvoteapp.service.mapper.VotePlaceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * Service Implementation for managing {@link VotePlace}.
 */
@Service
@Transactional
public class VotePlaceService {

    private final Logger log = LoggerFactory.getLogger(VotePlaceService.class);

    private final VotePlaceRepository votePlaceRepository;

    private final VotePlaceMapper votePlaceMapper;

    public VotePlaceService(VotePlaceRepository votePlaceRepository, VotePlaceMapper votePlaceMapper) {
        this.votePlaceRepository = votePlaceRepository;
        this.votePlaceMapper = votePlaceMapper;
    }

    /**
     * Save a votePlace if vrn not exist.
     *
     * @param votePlaceDTO the entity to save.
     * @return the persisted entity.
     */
    public VotePlaceDTO save(VotePlaceDTO votePlaceDTO) {
        log.debug("Request to save VotePlace : {}", votePlaceDTO);
        VotePlace current = votePlaceRepository.findVotePlaceByVrn(votePlaceDTO.getVrn())
            .orElseGet(() -> votePlaceMapper.toEntity(votePlaceDTO));

        return votePlaceMapper.toDto(votePlaceRepository.save(current));
    }

    /**
     * Get all the districts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VotePlaceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Districts");
        return votePlaceRepository.findAll(pageable)
            .map(votePlaceMapper::toDto);
    }


    /**
     * Get one votePlace by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VotePlaceDTO> findOne(Long id) {
        log.debug("Request to get VotePlace : {}", id);
        return votePlaceRepository.findById(id)
            .map(votePlaceMapper::toDto);
    }

    /**
     * Delete the votePlace by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VotePlace : {}", id);
        votePlaceRepository.deleteById(id);
    }
}
