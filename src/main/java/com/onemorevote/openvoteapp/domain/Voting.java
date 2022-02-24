package com.onemorevote.openvoteapp.domain;


import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Voting.
 */
@Entity
@Table(name = "voting")
public class Voting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "date", nullable = false)
    private String date;

    @NotNull
    @NaturalId
    @Column(name = "cik_voting_id", nullable = false, unique = true)
    private String cikVotingId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Voting name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public Voting date(String date) {
        this.date = date;
        return this;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCikVotingId() {
        return cikVotingId;
    }

    public Voting cikVotingId(String cikVotingId) {
        this.cikVotingId = cikVotingId;
        return this;
    }

    public void setCikVotingId(String cikVotingId) {
        this.cikVotingId = cikVotingId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Voting)) {
            return false;
        }
        return id != null && id.equals(((Voting) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Voting{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", date='" + getDate() + "'" +
            ", cikVotingId='" + getCikVotingId() + "'" +
            "}";
    }
}
