/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lolprocn.dbentities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Apollowc
 */
@Entity
@Table(name = "summoner")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Summoner.findAll", query = "SELECT s FROM Summoner s"),
    @NamedQuery(name = "Summoner.findBySummonerId", query = "SELECT s FROM Summoner s WHERE s.summonerId = :summonerId"),
    @NamedQuery(name = "Summoner.findByProfileIcon", query = "SELECT s FROM Summoner s WHERE s.profileIcon = :profileIcon"),
    @NamedQuery(name = "Summoner.findBySummonerName", query = "SELECT s FROM Summoner s WHERE s.summonerName = :summonerName"),
    @NamedQuery(name = "Summoner.findByLevel", query = "SELECT s FROM Summoner s WHERE s.level = :level")})
public class Summoner implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "summonerId")
    private Long summonerId;
    @Column(name = "profileIcon")
    private Integer profileIcon;
    @Size(max = 30)
    @Column(name = "summonerName")
    private String summonerName;
    @Column(name = "level")
    private Short level;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "summoner")
    private Collection<MatchrawStats> matchrawStatsCollection;

    public Summoner() {
    }

    public Summoner(Long summonerId) {
        this.summonerId = summonerId;
    }

    public Long getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(Long summonerId) {
        this.summonerId = summonerId;
    }

    public Integer getProfileIcon() {
        return profileIcon;
    }

    public void setProfileIcon(Integer profileIcon) {
        this.profileIcon = profileIcon;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public Short getLevel() {
        return level;
    }

    public void setLevel(Short level) {
        this.level = level;
    }

    @XmlTransient
    public Collection<MatchrawStats> getMatchrawStatsCollection() {
        return matchrawStatsCollection;
    }

    public void setMatchrawStatsCollection(Collection<MatchrawStats> matchrawStatsCollection) {
        this.matchrawStatsCollection = matchrawStatsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (summonerId != null ? summonerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Summoner)) {
            return false;
        }
        Summoner other = (Summoner) object;
        if ((this.summonerId == null && other.summonerId != null) || (this.summonerId != null && !this.summonerId.equals(other.summonerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lolprocn.dbentities.Summoner[ summonerId=" + summonerId + " ]";
    }
    
}
