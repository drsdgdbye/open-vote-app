package com.onemorevote.openvoteapp.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.onemorevote.openvoteapp.domain.Candidate} entity.
 */
@ApiModel(description = "A Candidate entity")
public class CandidateDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private Integer type;

    private String politicalParty;

    private String description;

    private String imageUrl;

    @NotNull
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

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPoliticalParty() {
        return politicalParty;
    }

    public void setPoliticalParty(String politicalParty) {
        this.politicalParty = politicalParty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCikCandidateId() {
        return cikCandidateId;
    }

    public void setCikCandidateId(String cikCandidateId) {
        this.cikCandidateId = cikCandidateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CandidateDTO)) {
            return false;
        }

        return id != null && id.equals(((CandidateDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CandidateDTO{" +
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
