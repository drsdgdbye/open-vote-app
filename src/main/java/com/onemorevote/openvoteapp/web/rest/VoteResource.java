package com.onemorevote.openvoteapp.web.rest;

import com.onemorevote.openvoteapp.domain.User;
import com.onemorevote.openvoteapp.domain.Voting;
import com.onemorevote.openvoteapp.repository.projections.VoteCounterProjection;
import com.onemorevote.openvoteapp.security.AuthoritiesConstants;
import com.onemorevote.openvoteapp.service.UserService;
import com.onemorevote.openvoteapp.service.VoteService;
import com.onemorevote.openvoteapp.service.dto.VoteCounterByVotePlaceDTO;
import com.onemorevote.openvoteapp.service.dto.VoteCounterWithMyCandidateDTO;
import com.onemorevote.openvoteapp.service.dto.VoteDTO;
import com.onemorevote.openvoteapp.web.rest.errors.BadRequestAlertException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.onemorevote.openvoteapp.domain.Vote}.
 */
@RestController
@RequestMapping("/api")
public class VoteResource {

    private final Logger log = LoggerFactory.getLogger(VoteResource.class);

    private static final String ENTITY_NAME = "vote";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VoteService voteService;
    private final UserService userService;

    public VoteResource(VoteService voteService, UserService userService) {
        this.voteService = voteService;
        this.userService = userService;
    }

    /**
     * {@code POST  /votes} : Create a new vote.
     *
     * @param voteDTO the voteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with id of the new voteDTO,
     * or with status {@code 400 (Bad Request)} if the vote has already an ID or {@code 403 (Forbidden)}
     * if user is already voted.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/votes")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<VoteDTO> createVote(@Valid @RequestBody VoteDTO voteDTO) throws URISyntaxException {
        log.debug("REST request to save Vote : {}", voteDTO);
        if (voteDTO.getId() != null) {
            throw new BadRequestAlertException("A new vote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Long userId = getUserFromCtx().getId();
        String cikVotingId = voteDTO.getCikVotingId();
        if (voteService.isSameVote(userId, cikVotingId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            voteDTO.setUserId(userId);
            Long voteId = voteService.save(voteDTO);
            return ResponseEntity.created(new URI("/api/votes/" + voteId))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, voteId.toString()))
                .build();
        }
    }

    /**
     * {@code GET  /votes/onmyvoteplace} : get the votes count group by user's vote place.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of votes in body.
     */
    @GetMapping("/votes/onmyvoteplace")
    public ResponseEntity<List<VoteCounterByVotePlaceDTO>> getStatisticsFromVotePlace() {
        log.debug("REST request to get a list of vote places with candidates and votes count");
        User currentUser = getUserFromCtx();
        return ResponseEntity.ok(voteService.getCandidatesWithCountVotesFromVotePlaceGroupByVoting(currentUser));
    }

    private User getUserFromCtx() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();
        return userService.getUserWithAuthoritiesByLogin(userDetails.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    /**
     * {@code GET  /votes/by/:cikVotingId} : get candidates with votes count by the cikVoting id, or with status
     * {@code 400 (Bad Request)} if cikVotingId is null.
     *
     * @param cikVotingId id of voting.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of candidates with votes count
     * and myCandidateCikId in body.
     */
    @GetMapping("/votes/by/{cikVotingId}")
    public ResponseEntity<VoteCounterWithMyCandidateDTO> getStatistics(@PathVariable String cikVotingId) {
        log.debug("REST request to get a list of Candidates with votes count");
        if (Objects.isNull(cikVotingId)) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        User current = getUserFromCtx();
        return ResponseEntity.ok(voteService.getCandidatesWithCountVotesByCikVotingId(current, cikVotingId));
    }

    /**
     * {@code PUT  /votes} : Updates an existing vote.
     *
     * @param voteDTO the voteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voteDTO,
     * or with status {@code 400 (Bad Request)} if the voteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the voteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/votes")
    public ResponseEntity<VoteDTO> updateVote(@Valid @RequestBody VoteDTO voteDTO) throws URISyntaxException {
        log.debug("REST request to update Vote : {}", voteDTO);
        if (voteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Long result = voteService.save(voteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.toString()))
            .build();
    }

    /**
     * {@code GET  /votes} : get all the votes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of votes in body.
     */
    @GetMapping("/votes")
    public ResponseEntity<List<VoteDTO>> getAllVotes(Pageable pageable) {
        log.debug("REST request to get a page of Votes");
        Page<VoteDTO> page = voteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /votes} : get statistics on voting.
     *
     * @param pageable the pagination information.
     * @param cikVotingId the voting id.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statistics in body, or with status
     * {@code 400 (Bad Request)} if cikVotingId is null.
     */
    @GetMapping("/votes/on/{cikVotingId}")
    public ResponseEntity<List<VoteCounterProjection>> getStatisticsByPage(Pageable pageable, @PathVariable String cikVotingId) {
        log.debug("REST request to get a page of Votes");
        if (Objects.isNull(cikVotingId)) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Page<VoteCounterProjection> page = voteService.getCandidatesByCikVotingId(pageable, cikVotingId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /votes/count/by/:cikVotingId} : get votes count by voting.
     *
     * @param cikVotingId the voting id.
     * @return the string
     * {@code 400 (Bad Request)} if cikVotingId is null.
     */
    @GetMapping("/votes/count/by/{cikVotingId}")
    public String getAllVotesByVoting(@PathVariable String cikVotingId) {
        if (Objects.isNull(cikVotingId)) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        return voteService.getAllVotesCountByCikVotingId(cikVotingId);
    }

    /**
     * {@code GET  /votes/:id} : get the "id" vote.
     *
     * @param id the id of the voteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the voteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/votes/{id}")
    public ResponseEntity<VoteDTO> getVote(@PathVariable Long id) {
        log.debug("REST request to get Vote : {}", id);
        Optional<VoteDTO> voteDTO = voteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(voteDTO);
    }

    /**
     * {@code DELETE  /votes/:id} : delete the "id" vote.
     *
     * @param id the id of the voteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/votes/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteVote(@PathVariable Long id) {
        log.debug("REST request to delete Vote : {}", id);
        voteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
