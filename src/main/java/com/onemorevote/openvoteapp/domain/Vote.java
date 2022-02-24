package com.onemorevote.openvoteapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Vote.
 */
@Entity
@Table(name = "vote")
public class Vote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "create_date", nullable = false)
    private Instant createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "voting_id",
        referencedColumnName = "cik_voting_id"
    )
    @JsonIgnoreProperties(value = "votes", allowSetters = true)
    private Voting voting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "candidate_id",
        referencedColumnName = "cik_candidate_id"
    )
    @JsonIgnoreProperties(value = "votes", allowSetters = true)
    private Candidate candidate;


    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public Vote userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public Vote createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public Vote candidate(Candidate candidate) {
        this.candidate = candidate;
        return this;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Voting getVoting() {
        return voting;
    }

    public Vote voting(Voting voting) {
        this.voting = voting;
        return this;
    }

    public void setVoting(Voting voting) {
        this.voting = voting;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vote)) {
            return false;
        }
        return id != null && id.equals(((Vote) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vote{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", createDate='" + getCreateDate() + "'" +
            "}";
    }
}
