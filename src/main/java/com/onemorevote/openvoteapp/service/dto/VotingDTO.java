package com.onemorevote.openvoteapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.onemorevote.openvoteapp.domain.Voting} entity.
 */
public class VotingDTO implements Serializable {
    
    private Long id;

    private String name;

    @NotNull
    private String date;

    @NotNull
    private String cikVotingId;

    
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCikVotingId() {
        return cikVotingId;
    }

    public void setCikVotingId(String cikVotingId) {
        this.cikVotingId = cikVotingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VotingDTO)) {
            return false;
        }

        return id != null && id.equals(((VotingDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VotingDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", date='" + getDate() + "'" +
            ", cikVotingId='" + getCikVotingId() + "'" +
            "}";
    }
}
