package com.onemorevote.openvoteapp.service.dto;

import com.onemorevote.openvoteapp.domain.Voting;

import java.io.Serializable;
import java.util.List;

public class VoteCounterByVotePlaceDTO implements Serializable {
    private static final long serialVersionUID = 1158526224829320420L;

    private Voting voting;
    private List<VoteCounterDTO> results;

    public VoteCounterByVotePlaceDTO() {
        // Empty constructor needed for Jackson.
    }

    public VoteCounterByVotePlaceDTO(Voting voting, List<VoteCounterDTO> voteCounterDTO) {
        this.voting = voting;
        this.results = voteCounterDTO;
    }

    public List<VoteCounterDTO> getResults() {
        return results;
    }

    public void setResults(List<VoteCounterDTO> voteCounterDTO) {
        this.results = voteCounterDTO;
    }

    public Voting getVoting() {
        return voting;
    }

    public void setVoting(Voting voting) {
        this.voting = voting;
    }
}
