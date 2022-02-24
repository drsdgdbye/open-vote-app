package com.onemorevote.openvoteapp.web.rest;

import com.onemorevote.openvoteapp.service.VotingService;
import com.onemorevote.openvoteapp.web.rest.errors.BadRequestAlertException;
import com.onemorevote.openvoteapp.service.dto.VotingDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.onemorevote.openvoteapp.domain.Voting}.
 */
@RestController
@RequestMapping("/api")
public class VotingResource {

    private final Logger log = LoggerFactory.getLogger(VotingResource.class);

    private static final String ENTITY_NAME = "voting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VotingService votingService;

    public VotingResource(VotingService votingService) {
        this.votingService = votingService;
    }

    /**
     * {@code POST  /votings} : Create a new voting.
     *
     * @param votingDTO the votingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new votingDTO, or with status {@code 400 (Bad Request)} if the voting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/votings")
    public ResponseEntity<VotingDTO> createVoting(@Valid @RequestBody VotingDTO votingDTO) throws URISyntaxException {
        log.debug("REST request to save Voting : {}", votingDTO);
        if (votingDTO.getId() != null) {
            throw new BadRequestAlertException("A new voting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VotingDTO result = votingService.save(votingDTO);
        return ResponseEntity.created(new URI("/api/votings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /votings} : Updates an existing voting.
     *
     * @param votingDTO the votingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated votingDTO,
     * or with status {@code 400 (Bad Request)} if the votingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the votingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/votings")
    public ResponseEntity<VotingDTO> updateVoting(@Valid @RequestBody VotingDTO votingDTO) throws URISyntaxException {
        log.debug("REST request to update Voting : {}", votingDTO);
        if (votingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VotingDTO result = votingService.save(votingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, votingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /votings} : get all the votings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of votings in body.
     */
    @GetMapping("/votings")
    public ResponseEntity<List<VotingDTO>> getAllVotings(Pageable pageable) {
        log.debug("REST request to get a page of Votings");
        Page<VotingDTO> page = votingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /votings/:id} : get the "id" voting.
     *
     * @param id the id of the votingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the votingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/votings/{id}")
    public ResponseEntity<VotingDTO> getVoting(@PathVariable Long id) {
        log.debug("REST request to get Voting : {}", id);
        Optional<VotingDTO> votingDTO = votingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(votingDTO);
    }

    /**
     * {@code DELETE  /votings/:id} : delete the "id" voting.
     *
     * @param id the id of the votingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/votings/{id}")
    public ResponseEntity<Void> deleteVoting(@PathVariable Long id) {
        log.debug("REST request to delete Voting : {}", id);
        votingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
