package com.onemorevote.openvoteapp.domain;


import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Candidate entity
 */
@Entity
@Table(name = "candidate")
public class Candidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type")
    private Integer type;

    @Column(name = "political_party")
    private String politicalParty;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @NotNull
    @NaturalId
    @Column(name = "cik_candidate_id", nullable = false, unique = true)
    private String cikCandidateId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Candidate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public Candidate type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPoliticalParty() {
        return politicalParty;
    }

    public Candidate politicalParty(String politicalParty) {
        this.politicalParty = politicalParty;
        return this;
    }

    public void setPoliticalParty(String politicalParty) {
        this.politicalParty = politicalParty;
    }

    public String getDescription() {
        return description;
    }

    public Candidate description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Candidate imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCikCandidateId() {
        return cikCandidateId;
    }

    public Candidate cikCandidateId(String cikCandidateId) {
        this.cikCandidateId = cikCandidateId;
        return this;
    }

    public void setCikCandidateId(String cikCandidateId) {
        this.cikCandidateId = cikCandidateId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Candidate)) {
            return false;
        }
        return id != null && id.equals(((Candidate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Candidate{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type=" + getType() +
            ", politicalParty='" + getPoliticalParty() + "'" +
            ", description='" + getDescription() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", cikCandidateId='" + getCikCandidateId() + "'" +
            "}";
    }
}
