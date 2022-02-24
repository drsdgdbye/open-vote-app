package com.onemorevote.openvoteapp.service.dto;

import com.onemorevote.openvoteapp.domain.VotePlace;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;

/**
 * A DTO for the {@link VotePlace} entity.
 */
@ApiModel(description = "The VotePlace entity")
public class VotePlaceDTO implements Serializable {

    private Long id;

    private String vrn;

    private String name;

    private String subjCode;

    private String numKsa;

    private String vid;

    private String address;

    private String descr;

    private String phone;

    private String lat;

    private String lon;

    private String votingAddress;

    private String votingDescr;

    private String votingPhone;

    private String votingLat;

    private String votingLon;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVrn() {
        return vrn;
    }

    public void setVrn(String vrn) {
        this.vrn = vrn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjCode() {
        return subjCode;
    }

    public void setSubjCode(String subjCode) {
        this.subjCode = subjCode;
    }

    public String getNumKsa() {
        return numKsa;
    }

    public void setNumKsa(String numKsa) {
        this.numKsa = numKsa;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getVotingAddress() {
        return votingAddress;
    }

    public void setVotingAddress(String votingAddress) {
        this.votingAddress = votingAddress;
    }

    public String getVotingDescr() {
        return votingDescr;
    }

    public void setVotingDescr(String votingDescr) {
        this.votingDescr = votingDescr;
    }

    public String getVotingPhone() {
        return votingPhone;
    }

    public void setVotingPhone(String votingPhone) {
        this.votingPhone = votingPhone;
    }

    public String getVotingLat() {
        return votingLat;
    }

    public void setVotingLat(String votingLat) {
        this.votingLat = votingLat;
    }

    public String getVotingLon() {
        return votingLon;
    }

    public void setVotingLon(String votingLon) {
        this.votingLon = votingLon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VotePlaceDTO)) {
            return false;
        }

        return id != null && id.equals(((VotePlaceDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VotePlaceDTO{" +
            "id=" + getId() +
            ", vrn='" + getVrn() + "'" +
            ", name='" + getName() + "'" +
            ", subjCode='" + getSubjCode() + "'" +
            ", numKsa='" + getNumKsa() + "'" +
            ", vid='" + getVid() + "'" +
            ", address='" + getAddress() + "'" +
            ", descr='" + getDescr() + "'" +
            ", phone='" + getPhone() + "'" +
            ", lat='" + getLat() + "'" +
            ", lon='" + getLon() + "'" +
            ", votingAddress='" + getVotingAddress() + "'" +
            ", votingDescr='" + getVotingDescr() + "'" +
            ", votingPhone='" + getVotingPhone() + "'" +
            ", votingLat='" + getVotingLat() + "'" +
            ", votingLon='" + getVotingLon() + "'" +
            "}";
    }
}
