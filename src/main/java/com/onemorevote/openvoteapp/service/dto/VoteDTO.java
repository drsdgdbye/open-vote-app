package com.onemorevote.openvoteapp.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.onemorevote.openvoteapp.domain.Vote} entity.
 */
public class VoteDTO implements Serializable {

    private Long id;

    private Long userId;

    private Instant createDate;

    //candidate fields
    @NotNull
    private String cikCandidateId;

    private String candidateName;

    private String politicalParty;

    //voting fields
    @NotNull
    private String cikVotingId;

    private String votingName;

    private String votingDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public String getVotingDate() {
        return votingDate;
    }

    public void setVotingDate(String votingDate) {
        this.votingDate = votingDate;
    }

    public String getPoliticalParty() {
        return politicalParty;
    }

    public void setPoliticalParty(String politicalParty) {
        this.politicalParty = politicalParty;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getCikCandidateId() {
        return cikCandidateId;
    }

    public void setCikCandidateId(String cikCandidateId) {
        this.cikCandidateId = cikCandidateId;
    }

    public String getCikVotingId() {
        return cikVotingId;
    }

    public void setCikVotingId(String cikVotingId) {
        this.cikVotingId = cikVotingId;
    }

    public String getVotingName() {
        return votingName;
    }

    public void setVotingName(String votingName) {
        this.votingName = votingName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VoteDTO)) {
            return false;
        }

        return id != null && id.equals(((VoteDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VoteDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", createDate='" + getCreateDate() + "'" +
            ", candidateId=" + cikCandidateId +
            ", votingId=" + cikVotingId +
            ", votingName='" + getVotingName() + "'" +
            "}";
    }
}
