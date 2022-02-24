package com.onemorevote.openvoteapp.repository.projections;

public interface VoteCounterProjection {
    Long getCandidateId();

    String getCandidateName();

    String getCandidateParty();

    Long getCandidateCount();
}
