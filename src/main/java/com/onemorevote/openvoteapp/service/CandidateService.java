package com.onemorevote.openvoteapp.service;

import com.onemorevote.openvoteapp.domain.Candidate;
import com.onemorevote.openvoteapp.repository.CandidateRepository;
import com.onemorevote.openvoteapp.service.dto.CandidateDTO;
import com.onemorevote.openvoteapp.service.mapper.CandidateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Candidate}.
 */
@Service
@Transactional
public class CandidateService {

    private final Logger log = LoggerFactory.getLogger(CandidateService.class);

    private final CandidateRepository candidateRepository;

    private final CandidateMapper candidateMapper;

    public CandidateService(CandidateRepository candidateRepository, CandidateMapper candidateMapper) {
        this.candidateRepository = candidateRepository;
        this.candidateMapper = candidateMapper;
    }

    /**
     * Save a candidate.
     *
     * @param candidateDTO the entity to save.
     * @return the persisted entity.
     */
    public CandidateDTO save(CandidateDTO candidateDTO) {
        log.debug("Request to save Candidate : {}", candidateDTO);
        Candidate candidate = candidateMapper.toEntity(candidateDTO);
        candidate = candidateRepository.save(candidate);
        return candidateMapper.toDto(candidate);
    }

    /**
     * Get all the candidates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CandidateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Candidates");
        return candidateRepository.findAll(pageable)
            .map(candidateMapper::toDto);
    }


    /**
     * Get one candidate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CandidateDTO> findOne(Long id) {
        log.debug("Request to get Candidate : {}", id);
        return candidateRepository.findById(id)
            .map(candidateMapper::toDto);
    }

    public Candidate findOneByCikCandidateId(String cikCandidateId) {
        log.debug("Request to get Candidate by cikCandidateId : {}", cikCandidateId);
        return candidateRepository.findOneByCikCandidateId(cikCandidateId);
    }

    /**
     * Delete the candidate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Candidate : {}", id);
        candidateRepository.deleteById(id);
    }

    /**
     * Get is the candidate exists.
     *
     * @param cikCandidateId the cik_id of the Candidate entity.
     * @return boolean
     */
    public boolean isCandidateNotExists(String cikCandidateId) {
        return !candidateRepository.existsByCikCandidateId(cikCandidateId);
    }
}
