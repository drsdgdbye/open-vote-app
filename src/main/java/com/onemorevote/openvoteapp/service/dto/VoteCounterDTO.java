package com.onemorevote.openvoteapp.service.dto;

import com.onemorevote.openvoteapp.domain.Candidate;

import java.io.Serializable;

/**
 * A DTO for the transfer candidates with votes count.
 */
public class VoteCounterDTO implements Serializable {
    private static final long serialVersionUID = 452718589152703910L;

    private Candidate candidate;
    private Long votesCount;

    public VoteCounterDTO() {
        // Empty constructor needed for Jackson.
    }

    public VoteCounterDTO(Candidate candidate, Long votesCount) {
        this.candidate = candidate;
        this.votesCount = votesCount;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Long getVotesCount() {
        return votesCount;
    }

    public void setVotesCount(Long votesCount) {
        this.votesCount = votesCount;
    }
}
