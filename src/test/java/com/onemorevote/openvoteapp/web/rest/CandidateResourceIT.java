package com.onemorevote.openvoteapp.web.rest;

import com.onemorevote.openvoteapp.OpenVoteBackApp;
import com.onemorevote.openvoteapp.domain.Candidate;
import com.onemorevote.openvoteapp.repository.CandidateRepository;
import com.onemorevote.openvoteapp.service.CandidateService;
import com.onemorevote.openvoteapp.service.dto.CandidateDTO;
import com.onemorevote.openvoteapp.service.mapper.CandidateMapper;

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
 * Integration tests for the {@link CandidateResource} REST controller.
 */
@SpringBootTest(classes = OpenVoteBackApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CandidateResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final String DEFAULT_POLITICAL_PARTY = "AAAAAAAAAA";
    private static final String UPDATED_POLITICAL_PARTY = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CIK_CANDIDATE_ID = "AAAAAAAAAA";
    private static final String UPDATED_CIK_CANDIDATE_ID = "BBBBBBBBBB";

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private CandidateMapper candidateMapper;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCandidateMockMvc;

    private Candidate candidate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Candidate createEntity(EntityManager em) {
        Candidate candidate = new Candidate()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .politicalParty(DEFAULT_POLITICAL_PARTY)
            .description(DEFAULT_DESCRIPTION)
            .imageUrl(DEFAULT_IMAGE_URL)
            .cikCandidateId(DEFAULT_CIK_CANDIDATE_ID);
        return candidate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Candidate createUpdatedEntity(EntityManager em) {
        Candidate candidate = new Candidate()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .politicalParty(UPDATED_POLITICAL_PARTY)
            .description(UPDATED_DESCRIPTION)
            .imageUrl(UPDATED_IMAGE_URL)
            .cikCandidateId(UPDATED_CIK_CANDIDATE_ID);
        return candidate;
    }

    @BeforeEach
    public void initTest() {
        candidate = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandidate() throws Exception {
        int databaseSizeBeforeCreate = candidateRepository.findAll().size();
        // Create the Candidate
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);
        restCandidateMockMvc.perform(post("/api/candidates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidateDTO)))
            .andExpect(status().isCreated());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeCreate + 1);
        Candidate testCandidate = candidateList.get(candidateList.size() - 1);
        assertThat(testCandidate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCandidate.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCandidate.getPoliticalParty()).isEqualTo(DEFAULT_POLITICAL_PARTY);
        assertThat(testCandidate.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCandidate.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testCandidate.getCikCandidateId()).isEqualTo(DEFAULT_CIK_CANDIDATE_ID);
    }

    @Test
    @Transactional
    public void createCandidateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = candidateRepository.findAll().size();

        // Create the Candidate with an existing ID
        candidate.setId(1L);
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidateMockMvc.perform(post("/api/candidates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidateRepository.findAll().size();
        // set the field null
        candidate.setName(null);

        // Create the Candidate, which fails.
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);


        restCandidateMockMvc.perform(post("/api/candidates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidateDTO)))
            .andExpect(status().isBadRequest());

        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCikCandidateIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidateRepository.findAll().size();
        // set the field null
        candidate.setCikCandidateId(null);

        // Create the Candidate, which fails.
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);


        restCandidateMockMvc.perform(post("/api/candidates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidateDTO)))
            .andExpect(status().isBadRequest());

        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCandidates() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList
        restCandidateMockMvc.perform(get("/api/candidates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].politicalParty").value(hasItem(DEFAULT_POLITICAL_PARTY)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].cikCandidateId").value(hasItem(DEFAULT_CIK_CANDIDATE_ID)));
    }
    
    @Test
    @Transactional
    public void getCandidate() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get the candidate
        restCandidateMockMvc.perform(get("/api/candidates/{id}", candidate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(candidate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.politicalParty").value(DEFAULT_POLITICAL_PARTY))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.cikCandidateId").value(DEFAULT_CIK_CANDIDATE_ID));
    }
    @Test
    @Transactional
    public void getNonExistingCandidate() throws Exception {
        // Get the candidate
        restCandidateMockMvc.perform(get("/api/candidates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandidate() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();

        // Update the candidate
        Candidate updatedCandidate = candidateRepository.findById(candidate.getId()).get();
        // Disconnect from session so that the updates on updatedCandidate are not directly saved in db
        em.detach(updatedCandidate);
        updatedCandidate
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .politicalParty(UPDATED_POLITICAL_PARTY)
            .description(UPDATED_DESCRIPTION)
            .imageUrl(UPDATED_IMAGE_URL)
            .cikCandidateId(UPDATED_CIK_CANDIDATE_ID);
        CandidateDTO candidateDTO = candidateMapper.toDto(updatedCandidate);

        restCandidateMockMvc.perform(put("/api/candidates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidateDTO)))
            .andExpect(status().isOk());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
        Candidate testCandidate = candidateList.get(candidateList.size() - 1);
        assertThat(testCandidate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCandidate.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCandidate.getPoliticalParty()).isEqualTo(UPDATED_POLITICAL_PARTY);
        assertThat(testCandidate.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCandidate.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testCandidate.getCikCandidateId()).isEqualTo(UPDATED_CIK_CANDIDATE_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingCandidate() throws Exception {
        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();

        // Create the Candidate
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidateMockMvc.perform(put("/api/candidates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCandidate() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        int databaseSizeBeforeDelete = candidateRepository.findAll().size();

        // Delete the candidate
        restCandidateMockMvc.perform(delete("/api/candidates/{id}", candidate.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
