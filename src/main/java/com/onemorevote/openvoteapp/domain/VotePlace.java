package com.onemorevote.openvoteapp.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * The VotePlace entity
 */
@Entity
@Table(name = "district")
public class VotePlace implements Serializable {

    private static final long serialVersionUID = 1L;

    public VotePlace() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "vrn")
    private String vrn;

    @Column(name = "name")
    private String name;

    @Column(name = "subj_code")
    private String subjCode;

    @Column(name = "num_ksa")
    private String numKsa;

    @Column(name = "vid")
    private String vid;

    @Column(name = "address")
    private String address;

    @Column(name = "descr")
    private String descr;

    @Column(name = "phone")
    private String phone;

    @Column(name = "lat")
    private String lat;

    @Column(name = "lon")
    private String lon;

    @Column(name = "voting_address")
    private String votingAddress;

    @Column(name = "voting_descr")
    private String votingDescr;

    @Column(name = "voting_phone")
    private String votingPhone;

    @Column(name = "voting_lat")
    private String votingLat;

    @Column(name = "voting_lon")
    private String votingLon;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVrn() {
        return vrn;
    }

    public VotePlace vrn(String vrn) {
        this.vrn = vrn;
        return this;
    }

    public void setVrn(String vrn) {
        this.vrn = vrn;
    }

    public String getName() {
        return name;
    }

    public VotePlace name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjCode() {
        return subjCode;
    }

    public VotePlace subjCode(String subjCode) {
        this.subjCode = subjCode;
        return this;
    }

    public void setSubjCode(String subjCode) {
        this.subjCode = subjCode;
    }

    public String getNumKsa() {
        return numKsa;
    }

    public VotePlace numKsa(String numKsa) {
        this.numKsa = numKsa;
        return this;
    }

    public void setNumKsa(String numKsa) {
        this.numKsa = numKsa;
    }

    public String getVid() {
        return vid;
    }

    public VotePlace vid(String vid) {
        this.vid = vid;
        return this;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getAddress() {
        return address;
    }

    public VotePlace address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescr() {
        return descr;
    }

    public VotePlace descr(String descr) {
        this.descr = descr;
        return this;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getPhone() {
        return phone;
    }

    public VotePlace phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLat() {
        return lat;
    }

    public VotePlace lat(String lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public VotePlace lon(String lon) {
        this.lon = lon;
        return this;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getVotingAddress() {
        return votingAddress;
    }

    public VotePlace votingAddress(String votingAddress) {
        this.votingAddress = votingAddress;
        return this;
    }

    public void setVotingAddress(String votingAddress) {
        this.votingAddress = votingAddress;
    }

    public String getVotingDescr() {
        return votingDescr;
    }

    public VotePlace votingDescr(String votingDescr) {
        this.votingDescr = votingDescr;
        return this;
    }

    public void setVotingDescr(String votingDescr) {
        this.votingDescr = votingDescr;
    }

    public String getVotingPhone() {
        return votingPhone;
    }

    public VotePlace votingPhone(String votingPhone) {
        this.votingPhone = votingPhone;
        return this;
    }

    public void setVotingPhone(String votingPhone) {
        this.votingPhone = votingPhone;
    }

    public String getVotingLat() {
        return votingLat;
    }

    public VotePlace votingLat(String votingLat) {
        this.votingLat = votingLat;
        return this;
    }

    public void setVotingLat(String votingLat) {
        this.votingLat = votingLat;
    }

    public String getVotingLon() {
        return votingLon;
    }

    public VotePlace votingLon(String votingLon) {
        this.votingLon = votingLon;
        return this;
    }

    public void setVotingLon(String votingLon) {
        this.votingLon = votingLon;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VotePlace)) {
            return false;
        }
        return id != null && id.equals(((VotePlace) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VotePlace{" +
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
