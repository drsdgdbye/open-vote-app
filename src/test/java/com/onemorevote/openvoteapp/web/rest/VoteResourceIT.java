package com.onemorevote.openvoteapp.web.rest;

import com.onemorevote.openvoteapp.OpenVoteBackApp;
import com.onemorevote.openvoteapp.domain.Vote;
import com.onemorevote.openvoteapp.repository.VoteRepository;
import com.onemorevote.openvoteapp.service.VoteService;
import com.onemorevote.openvoteapp.service.dto.VoteDTO;
import com.onemorevote.openvoteapp.service.mapper.VoteMapper;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link VoteResource} REST controller.
 */
@SpringBootTest(classes = OpenVoteBackApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VoteResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Instant DEFAULT_CREATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VoteMapper voteMapper;

    @Autowired
    private VoteService voteService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVoteMockMvc;

    private Vote vote;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vote createEntity(EntityManager em) {
        Vote vote = new Vote()
            .userId(DEFAULT_USER_ID)
            .createDate(DEFAULT_CREATE_DATE);
        return vote;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vote createUpdatedEntity(EntityManager em) {
        Vote vote = new Vote()
            .userId(UPDATED_USER_ID)
            .createDate(UPDATED_CREATE_DATE);
        return vote;
    }

    @BeforeEach
    public void initTest() {
        vote = createEntity(em);
    }

    @Test
    @Transactional
    public void createVote() throws Exception {
        int databaseSizeBeforeCreate = voteRepository.findAll().size();
        // Create the Vote
        VoteDTO voteDTO = voteMapper.toDto(vote);
        restVoteMockMvc.perform(post("/api/votes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voteDTO)))
            .andExpect(status().isCreated());

        // Validate the Vote in the database
        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeCreate + 1);
        Vote testVote = voteList.get(voteList.size() - 1);
        assertThat(testVote.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testVote.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createVoteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = voteRepository.findAll().size();

        // Create the Vote with an existing ID
        vote.setId(1L);
        VoteDTO voteDTO = voteMapper.toDto(vote);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoteMockMvc.perform(post("/api/votes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vote in the database
        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = voteRepository.findAll().size();
        // set the field null
        vote.setUserId(null);

        // Create the Vote, which fails.
        VoteDTO voteDTO = voteMapper.toDto(vote);


        restVoteMockMvc.perform(post("/api/votes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voteDTO)))
            .andExpect(status().isBadRequest());

        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = voteRepository.findAll().size();
        // set the field null
        vote.setCreateDate(null);

        // Create the Vote, which fails.
        VoteDTO voteDTO = voteMapper.toDto(vote);


        restVoteMockMvc.perform(post("/api/votes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voteDTO)))
            .andExpect(status().isBadRequest());

        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVotes() throws Exception {
        // Initialize the database
        voteRepository.saveAndFlush(vote);

        // Get all the voteList
        restVoteMockMvc.perform(get("/api/votes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vote.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getVote() throws Exception {
        // Initialize the database
        voteRepository.saveAndFlush(vote);

        // Get the vote
        restVoteMockMvc.perform(get("/api/votes/{id}", vote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vote.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingVote() throws Exception {
        // Get the vote
        restVoteMockMvc.perform(get("/api/votes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVote() throws Exception {
        // Initialize the database
        voteRepository.saveAndFlush(vote);

        int databaseSizeBeforeUpdate = voteRepository.findAll().size();

        // Update the vote
        Vote updatedVote = voteRepository.findById(vote.getId()).get();
        // Disconnect from session so that the updates on updatedVote are not directly saved in db
        em.detach(updatedVote);
        updatedVote
            .userId(UPDATED_USER_ID)
            .createDate(UPDATED_CREATE_DATE);
        VoteDTO voteDTO = voteMapper.toDto(updatedVote);

        restVoteMockMvc.perform(put("/api/votes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voteDTO)))
            .andExpect(status().isOk());

        // Validate the Vote in the database
        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeUpdate);
        Vote testVote = voteList.get(voteList.size() - 1);
        assertThat(testVote.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testVote.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingVote() throws Exception {
        int databaseSizeBeforeUpdate = voteRepository.findAll().size();

        // Create the Vote
        VoteDTO voteDTO = voteMapper.toDto(vote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoteMockMvc.perform(put("/api/votes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vote in the database
        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVote() throws Exception {
        // Initialize the database
        voteRepository.saveAndFlush(vote);

        int databaseSizeBeforeDelete = voteRepository.findAll().size();

        // Delete the vote
        restVoteMockMvc.perform(delete("/api/votes/{id}", vote.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
