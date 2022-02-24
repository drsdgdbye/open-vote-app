package com.onemorevote.openvoteapp.web.rest;

import com.onemorevote.openvoteapp.OpenVoteBackApp;
import com.onemorevote.openvoteapp.domain.Voting;
import com.onemorevote.openvoteapp.repository.VotingRepository;
import com.onemorevote.openvoteapp.service.VotingService;
import com.onemorevote.openvoteapp.service.dto.VotingDTO;
import com.onemorevote.openvoteapp.service.mapper.VotingMapper;

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
 * Integration tests for the {@link VotingResource} REST controller.
 */
@SpringBootTest(classes = OpenVoteBackApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VotingResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_CIK_VOTING_ID = "AAAAAAAAAA";
    private static final String UPDATED_CIK_VOTING_ID = "BBBBBBBBBB";

    @Autowired
    private VotingRepository votingRepository;

    @Autowired
    private VotingMapper votingMapper;

    @Autowired
    private VotingService votingService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVotingMockMvc;

    private Voting voting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voting createEntity(EntityManager em) {
        Voting voting = new Voting()
            .name(DEFAULT_NAME)
            .date(DEFAULT_DATE)
            .cikVotingId(DEFAULT_CIK_VOTING_ID);
        return voting;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voting createUpdatedEntity(EntityManager em) {
        Voting voting = new Voting()
            .name(UPDATED_NAME)
            .date(UPDATED_DATE)
            .cikVotingId(UPDATED_CIK_VOTING_ID);
        return voting;
    }

    @BeforeEach
    public void initTest() {
        voting = createEntity(em);
    }

    @Test
    @Transactional
    public void createVoting() throws Exception {
        int databaseSizeBeforeCreate = votingRepository.findAll().size();
        // Create the Voting
        VotingDTO votingDTO = votingMapper.toDto(voting);
        restVotingMockMvc.perform(post("/api/votings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(votingDTO)))
            .andExpect(status().isCreated());

        // Validate the Voting in the database
        List<Voting> votingList = votingRepository.findAll();
        assertThat(votingList).hasSize(databaseSizeBeforeCreate + 1);
        Voting testVoting = votingList.get(votingList.size() - 1);
        assertThat(testVoting.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVoting.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testVoting.getCikVotingId()).isEqualTo(DEFAULT_CIK_VOTING_ID);
    }

    @Test
    @Transactional
    public void createVotingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = votingRepository.findAll().size();

        // Create the Voting with an existing ID
        voting.setId(1L);
        VotingDTO votingDTO = votingMapper.toDto(voting);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVotingMockMvc.perform(post("/api/votings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(votingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Voting in the database
        List<Voting> votingList = votingRepository.findAll();
        assertThat(votingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = votingRepository.findAll().size();
        // set the field null
        voting.setDate(null);

        // Create the Voting, which fails.
        VotingDTO votingDTO = votingMapper.toDto(voting);


        restVotingMockMvc.perform(post("/api/votings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(votingDTO)))
            .andExpect(status().isBadRequest());

        List<Voting> votingList = votingRepository.findAll();
        assertThat(votingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCikVotingIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = votingRepository.findAll().size();
        // set the field null
        voting.setCikVotingId(null);

        // Create the Voting, which fails.
        VotingDTO votingDTO = votingMapper.toDto(voting);


        restVotingMockMvc.perform(post("/api/votings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(votingDTO)))
            .andExpect(status().isBadRequest());

        List<Voting> votingList = votingRepository.findAll();
        assertThat(votingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVotings() throws Exception {
        // Initialize the database
        votingRepository.saveAndFlush(voting);

        // Get all the votingList
        restVotingMockMvc.perform(get("/api/votings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voting.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE)))
            .andExpect(jsonPath("$.[*].cikVotingId").value(hasItem(DEFAULT_CIK_VOTING_ID)));
    }
    
    @Test
    @Transactional
    public void getVoting() throws Exception {
        // Initialize the database
        votingRepository.saveAndFlush(voting);

        // Get the voting
        restVotingMockMvc.perform(get("/api/votings/{id}", voting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(voting.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE))
            .andExpect(jsonPath("$.cikVotingId").value(DEFAULT_CIK_VOTING_ID));
    }
    @Test
    @Transactional
    public void getNonExistingVoting() throws Exception {
        // Get the voting
        restVotingMockMvc.perform(get("/api/votings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoting() throws Exception {
        // Initialize the database
        votingRepository.saveAndFlush(voting);

        int databaseSizeBeforeUpdate = votingRepository.findAll().size();

        // Update the voting
        Voting updatedVoting = votingRepository.findById(voting.getId()).get();
        // Disconnect from session so that the updates on updatedVoting are not directly saved in db
        em.detach(updatedVoting);
        updatedVoting
            .name(UPDATED_NAME)
            .date(UPDATED_DATE)
            .cikVotingId(UPDATED_CIK_VOTING_ID);
        VotingDTO votingDTO = votingMapper.toDto(updatedVoting);

        restVotingMockMvc.perform(put("/api/votings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(votingDTO)))
            .andExpect(status().isOk());

        // Validate the Voting in the database
        List<Voting> votingList = votingRepository.findAll();
        assertThat(votingList).hasSize(databaseSizeBeforeUpdate);
        Voting testVoting = votingList.get(votingList.size() - 1);
        assertThat(testVoting.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVoting.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testVoting.getCikVotingId()).isEqualTo(UPDATED_CIK_VOTING_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingVoting() throws Exception {
        int databaseSizeBeforeUpdate = votingRepository.findAll().size();

        // Create the Voting
        VotingDTO votingDTO = votingMapper.toDto(voting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVotingMockMvc.perform(put("/api/votings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(votingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Voting in the database
        List<Voting> votingList = votingRepository.findAll();
        assertThat(votingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVoting() throws Exception {
        // Initialize the database
        votingRepository.saveAndFlush(voting);

        int databaseSizeBeforeDelete = votingRepository.findAll().size();

        // Delete the voting
        restVotingMockMvc.perform(delete("/api/votings/{id}", voting.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Voting> votingList = votingRepository.findAll();
        assertThat(votingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
