package com.onemorevote.openvoteapp.service.dto;

import java.io.Serializable;
import java.util.List;

public class VoteCounterWithMyCandidateDTO implements Serializable {
    private static final long serialVersionUID = -3938265337871964429L;

    private String myCandidateCikId;
    private List<VoteCounterDTO> results;

    public VoteCounterWithMyCandidateDTO() {
        // Empty constructor needed for Jackson.
    }

    public VoteCounterWithMyCandidateDTO(String myCandidateCikId, List<VoteCounterDTO> voteCounterDTO) {
        this.myCandidateCikId = myCandidateCikId;
        this.results = voteCounterDTO;
    }

    public List<VoteCounterDTO> getResults() {
        return results;
    }

    public void setResults(List<VoteCounterDTO> voteCounterDTO) {
        this.results = voteCounterDTO;
    }

    public String getMyCandidateCikId() {
        return myCandidateCikId;
    }

    public void setMyCandidateCikId(String myCandidateCikId) {
        this.myCandidateCikId = myCandidateCikId;
    }
}
