package com.onemorevote.openvoteapp.web.rest;

import com.onemorevote.openvoteapp.domain.VotePlace;
import com.onemorevote.openvoteapp.service.VotePlaceService;
import com.onemorevote.openvoteapp.service.dto.VotePlaceDTO;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link VotePlace}.
 */
@RestController
@RequestMapping("/api")
public class VotePlaceResource {

    private final Logger log = LoggerFactory.getLogger(VotePlaceResource.class);

    private static final String ENTITY_NAME = "votePlace";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VotePlaceService votePlaceService;

    public VotePlaceResource(VotePlaceService votePlaceService) {
        this.votePlaceService = votePlaceService;
    }

    /**
     * {@code POST  /voteplaces} : Create a new vote place.
     *
     * @param votePlaceDTO the votePlaceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new votePlaceDTO, or with status {@code 400 (Bad Request)} if the vote place has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/voteplaces")
    public ResponseEntity<VotePlaceDTO> createVotePlace(@RequestBody VotePlaceDTO votePlaceDTO) throws URISyntaxException {
        log.debug("REST request to save VotePlace : {}", votePlaceDTO);
        if (votePlaceDTO.getId() != null) {
            throw new BadRequestAlertException("A new vote place cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VotePlaceDTO result = votePlaceService.save(votePlaceDTO);
        return ResponseEntity.created(new URI("/api/voteplaces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    /* * {@code PUT  /districts} : Updates an existing votePlace.
     *
     * @param districtDTO the districtDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated districtDTO,
     * or with status {@code 400 (Bad Request)} if the districtDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the districtDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.*/

    /*@PutMapping("/districts")
    public ResponseEntity<VotePlaceDTO> updateDistrict(@RequestBody VotePlaceDTO districtDTO) throws URISyntaxException {
        log.debug("REST request to update VotePlace : {}", districtDTO);
        if (districtDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VotePlaceDTO result = districtService.save(districtDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, districtDTO.getId().toString()))
            .body(result);
    }*/

    /**
     * {@code GET  /voteplaces} : get all the vote places.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vote places in body.
     */
    @GetMapping("/voteplaces")
    public ResponseEntity<List<VotePlaceDTO>> getAllVotePlaces(Pageable pageable) {
        log.debug("REST request to get a page of vote places");
        Page<VotePlaceDTO> page = votePlaceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /voteplaces/:id} : get the "id" of vote place.
     *
     * @param id the id of the votePlaceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the votePlaceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/voteplaces/{id}")
    public ResponseEntity<VotePlaceDTO> getVotePlace(@PathVariable Long id) {
        log.debug("REST request to get VotePlace : {}", id);
        Optional<VotePlaceDTO> votePlaceDTO = votePlaceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(votePlaceDTO);
    }

    /*
     * {@code DELETE  /districts/:id} : delete the "id" votePlace.
     *
     * @param id the id of the districtDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    /*@DeleteMapping("/districts/{id}")
    public ResponseEntity<Void> deleteDistrict(@PathVariable Long id) {
        log.debug("REST request to delete VotePlace : {}", id);
        districtService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }*/
}
