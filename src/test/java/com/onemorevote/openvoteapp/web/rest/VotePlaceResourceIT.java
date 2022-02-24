package com.onemorevote.openvoteapp.web.rest;

import com.onemorevote.openvoteapp.OpenVoteBackApp;
import com.onemorevote.openvoteapp.domain.VotePlace;
import com.onemorevote.openvoteapp.repository.VotePlaceRepository;
import com.onemorevote.openvoteapp.service.VotePlaceService;
import com.onemorevote.openvoteapp.service.dto.VotePlaceDTO;
import com.onemorevote.openvoteapp.service.mapper.VotePlaceMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link VotePlaceResource} REST controller.
 */
@SpringBootTest(classes = OpenVoteBackApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VotePlaceResourceIT {

    private static final String DEFAULT_VRN = "AAAAAAAAAA";
    private static final String UPDATED_VRN = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJ_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SUBJ_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_KSA = "AAAAAAAAAA";
    private static final String UPDATED_NUM_KSA = "BBBBBBBBBB";

    private static final String DEFAULT_VID = "AAAAAAAAAA";
    private static final String UPDATED_VID = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCR = "AAAAAAAAAA";
    private static final String UPDATED_DESCR = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_LAT = "AAAAAAAAAA";
    private static final String UPDATED_LAT = "BBBBBBBBBB";

    private static final String DEFAULT_LON = "AAAAAAAAAA";
    private static final String UPDATED_LON = "BBBBBBBBBB";

    private static final String DEFAULT_VOTING_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_VOTING_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_VOTING_DESCR = "AAAAAAAAAA";
    private static final String UPDATED_VOTING_DESCR = "BBBBBBBBBB";

    private static final String DEFAULT_VOTING_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_VOTING_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_VOTING_LAT = "AAAAAAAAAA";
    private static final String UPDATED_VOTING_LAT = "BBBBBBBBBB";

    private static final String DEFAULT_VOTING_LON = "AAAAAAAAAA";
    private static final String UPDATED_VOTING_LON = "BBBBBBBBBB";

    @Autowired
    private VotePlaceRepository votePlaceRepository;

    @Autowired
    private VotePlaceMapper votePlaceMapper;

    @Autowired
    private VotePlaceService votePlaceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDistrictMockMvc;

    private VotePlace votePlace;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VotePlace createEntity(EntityManager em) {
        VotePlace votePlace = new VotePlace()
            .vrn(DEFAULT_VRN)
            .name(DEFAULT_NAME)
            .subjCode(DEFAULT_SUBJ_CODE)
            .numKsa(DEFAULT_NUM_KSA)
            .vid(DEFAULT_VID)
            .address(DEFAULT_ADDRESS)
            .descr(DEFAULT_DESCR)
            .phone(DEFAULT_PHONE)
            .lat(DEFAULT_LAT)
            .lon(DEFAULT_LON)
            .votingAddress(DEFAULT_VOTING_ADDRESS)
            .votingDescr(DEFAULT_VOTING_DESCR)
            .votingPhone(DEFAULT_VOTING_PHONE)
            .votingLat(DEFAULT_VOTING_LAT)
            .votingLon(DEFAULT_VOTING_LON);
        return votePlace;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VotePlace createUpdatedEntity(EntityManager em) {
        VotePlace votePlace = new VotePlace()
            .vrn(UPDATED_VRN)
            .name(UPDATED_NAME)
            .subjCode(UPDATED_SUBJ_CODE)
            .numKsa(UPDATED_NUM_KSA)
            .vid(UPDATED_VID)
            .address(UPDATED_ADDRESS)
            .descr(UPDATED_DESCR)
            .phone(UPDATED_PHONE)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON)
            .votingAddress(UPDATED_VOTING_ADDRESS)
            .votingDescr(UPDATED_VOTING_DESCR)
            .votingPhone(UPDATED_VOTING_PHONE)
            .votingLat(UPDATED_VOTING_LAT)
            .votingLon(UPDATED_VOTING_LON);
        return votePlace;
    }

    @BeforeEach
    public void initTest() {
        votePlace = createEntity(em);
    }

    @Test
    @Transactional
    public void createVotePlace() throws Exception {
        int databaseSizeBeforeCreate = votePlaceRepository.findAll().size();
        // Create the VotePlace
        VotePlaceDTO votePlaceDTO = votePlaceMapper.toDto(votePlace);
        restDistrictMockMvc.perform(post("/api/districts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(votePlaceDTO)))
            .andExpect(status().isCreated());

        // Validate the VotePlace in the database
        List<VotePlace> votePlaceList = votePlaceRepository.findAll();
        assertThat(votePlaceList).hasSize(databaseSizeBeforeCreate + 1);
        VotePlace testVotePlace = votePlaceList.get(votePlaceList.size() - 1);
        assertThat(testVotePlace.getVrn()).isEqualTo(DEFAULT_VRN);
        assertThat(testVotePlace.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVotePlace.getSubjCode()).isEqualTo(DEFAULT_SUBJ_CODE);
        assertThat(testVotePlace.getNumKsa()).isEqualTo(DEFAULT_NUM_KSA);
        assertThat(testVotePlace.getVid()).isEqualTo(DEFAULT_VID);
        assertThat(testVotePlace.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testVotePlace.getDescr()).isEqualTo(DEFAULT_DESCR);
        assertThat(testVotePlace.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testVotePlace.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testVotePlace.getLon()).isEqualTo(DEFAULT_LON);
        assertThat(testVotePlace.getVotingAddress()).isEqualTo(DEFAULT_VOTING_ADDRESS);
        assertThat(testVotePlace.getVotingDescr()).isEqualTo(DEFAULT_VOTING_DESCR);
        assertThat(testVotePlace.getVotingPhone()).isEqualTo(DEFAULT_VOTING_PHONE);
        assertThat(testVotePlace.getVotingLat()).isEqualTo(DEFAULT_VOTING_LAT);
        assertThat(testVotePlace.getVotingLon()).isEqualTo(DEFAULT_VOTING_LON);
    }

    @Test
    @Transactional
    public void createDistrictWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = votePlaceRepository.findAll().size();

        // Create the VotePlace with an existing ID
        votePlace.setId(1L);
        VotePlaceDTO votePlaceDTO = votePlaceMapper.toDto(votePlace);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDistrictMockMvc.perform(post("/api/districts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(votePlaceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VotePlace in the database
        List<VotePlace> votePlaceList = votePlaceRepository.findAll();
        assertThat(votePlaceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDistricts() throws Exception {
        // Initialize the database
        votePlaceRepository.saveAndFlush(votePlace);

        // Get all the districtList
        restDistrictMockMvc.perform(get("/api/districts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(votePlace.getId().intValue())))
            .andExpect(jsonPath("$.[*].vrn").value(hasItem(DEFAULT_VRN)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].subjCode").value(hasItem(DEFAULT_SUBJ_CODE)))
            .andExpect(jsonPath("$.[*].numKsa").value(hasItem(DEFAULT_NUM_KSA)))
            .andExpect(jsonPath("$.[*].vid").value(hasItem(DEFAULT_VID)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].descr").value(hasItem(DEFAULT_DESCR)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT)))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON)))
            .andExpect(jsonPath("$.[*].votingAddress").value(hasItem(DEFAULT_VOTING_ADDRESS)))
            .andExpect(jsonPath("$.[*].votingDescr").value(hasItem(DEFAULT_VOTING_DESCR)))
            .andExpect(jsonPath("$.[*].votingPhone").value(hasItem(DEFAULT_VOTING_PHONE)))
            .andExpect(jsonPath("$.[*].votingLat").value(hasItem(DEFAULT_VOTING_LAT)))
            .andExpect(jsonPath("$.[*].votingLon").value(hasItem(DEFAULT_VOTING_LON)));
    }

    @Test
    @Transactional
    public void getDistrict() throws Exception {
        // Initialize the database
        votePlaceRepository.saveAndFlush(votePlace);

        // Get the votePlace
        restDistrictMockMvc.perform(get("/api/districts/{id}", votePlace.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(votePlace.getId().intValue()))
            .andExpect(jsonPath("$.vrn").value(DEFAULT_VRN))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.subjCode").value(DEFAULT_SUBJ_CODE))
            .andExpect(jsonPath("$.numKsa").value(DEFAULT_NUM_KSA))
            .andExpect(jsonPath("$.vid").value(DEFAULT_VID))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.descr").value(DEFAULT_DESCR))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT))
            .andExpect(jsonPath("$.lon").value(DEFAULT_LON))
            .andExpect(jsonPath("$.votingAddress").value(DEFAULT_VOTING_ADDRESS))
            .andExpect(jsonPath("$.votingDescr").value(DEFAULT_VOTING_DESCR))
            .andExpect(jsonPath("$.votingPhone").value(DEFAULT_VOTING_PHONE))
            .andExpect(jsonPath("$.votingLat").value(DEFAULT_VOTING_LAT))
            .andExpect(jsonPath("$.votingLon").value(DEFAULT_VOTING_LON));
    }
    @Test
    @Transactional
    public void getNonExistingDistrict() throws Exception {
        // Get the votePlace
        restDistrictMockMvc.perform(get("/api/districts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDistrict() throws Exception {
        // Initialize the database
        votePlaceRepository.saveAndFlush(votePlace);

        int databaseSizeBeforeUpdate = votePlaceRepository.findAll().size();

        // Update the votePlace
        VotePlace updatedVotePlace = votePlaceRepository.findById(votePlace.getId()).get();
        // Disconnect from session so that the updates on updatedVotePlace are not directly saved in db
        em.detach(updatedVotePlace);
        updatedVotePlace
            .vrn(UPDATED_VRN)
            .name(UPDATED_NAME)
            .subjCode(UPDATED_SUBJ_CODE)
            .numKsa(UPDATED_NUM_KSA)
            .vid(UPDATED_VID)
            .address(UPDATED_ADDRESS)
            .descr(UPDATED_DESCR)
            .phone(UPDATED_PHONE)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON)
            .votingAddress(UPDATED_VOTING_ADDRESS)
            .votingDescr(UPDATED_VOTING_DESCR)
            .votingPhone(UPDATED_VOTING_PHONE)
            .votingLat(UPDATED_VOTING_LAT)
            .votingLon(UPDATED_VOTING_LON);
        VotePlaceDTO votePlaceDTO = votePlaceMapper.toDto(updatedVotePlace);

        restDistrictMockMvc.perform(put("/api/districts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(votePlaceDTO)))
            .andExpect(status().isOk());

        // Validate the VotePlace in the database
        List<VotePlace> votePlaceList = votePlaceRepository.findAll();
        assertThat(votePlaceList).hasSize(databaseSizeBeforeUpdate);
        VotePlace testVotePlace = votePlaceList.get(votePlaceList.size() - 1);
        assertThat(testVotePlace.getVrn()).isEqualTo(UPDATED_VRN);
        assertThat(testVotePlace.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVotePlace.getSubjCode()).isEqualTo(UPDATED_SUBJ_CODE);
        assertThat(testVotePlace.getNumKsa()).isEqualTo(UPDATED_NUM_KSA);
        assertThat(testVotePlace.getVid()).isEqualTo(UPDATED_VID);
        assertThat(testVotePlace.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testVotePlace.getDescr()).isEqualTo(UPDATED_DESCR);
        assertThat(testVotePlace.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testVotePlace.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testVotePlace.getLon()).isEqualTo(UPDATED_LON);
        assertThat(testVotePlace.getVotingAddress()).isEqualTo(UPDATED_VOTING_ADDRESS);
        assertThat(testVotePlace.getVotingDescr()).isEqualTo(UPDATED_VOTING_DESCR);
        assertThat(testVotePlace.getVotingPhone()).isEqualTo(UPDATED_VOTING_PHONE);
        assertThat(testVotePlace.getVotingLat()).isEqualTo(UPDATED_VOTING_LAT);
        assertThat(testVotePlace.getVotingLon()).isEqualTo(UPDATED_VOTING_LON);
    }

    @Test
    @Transactional
    public void updateNonExistingDistrict() throws Exception {
        int databaseSizeBeforeUpdate = votePlaceRepository.findAll().size();

        // Create the VotePlace
        VotePlaceDTO votePlaceDTO = votePlaceMapper.toDto(votePlace);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistrictMockMvc.perform(put("/api/districts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(votePlaceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VotePlace in the database
        List<VotePlace> votePlaceList = votePlaceRepository.findAll();
        assertThat(votePlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDistrict() throws Exception {
        // Initialize the database
        votePlaceRepository.saveAndFlush(votePlace);

        int databaseSizeBeforeDelete = votePlaceRepository.findAll().size();

        // Delete the votePlace
        restDistrictMockMvc.perform(delete("/api/districts/{id}", votePlace.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VotePlace> votePlaceList = votePlaceRepository.findAll();
        assertThat(votePlaceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
