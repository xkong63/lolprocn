/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lolprocn.dbentities;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "matches")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Matches.findAll", query = "SELECT m FROM Matches m"),
    @NamedQuery(name = "Matches.findByGameId", query = "SELECT m FROM Matches m WHERE m.gameId = :gameId"),
    @NamedQuery(name = "Matches.findByCreateDate", query = "SELECT m FROM Matches m WHERE m.createDate = :createDate"),
    @NamedQuery(name = "Matches.findByMatchDuration", query = "SELECT m FROM Matches m WHERE m.matchDuration = :matchDuration"),
    @NamedQuery(name = "Matches.findByGameMode", query = "SELECT m FROM Matches m WHERE m.gameMode = :gameMode"),
    @NamedQuery(name = "Matches.findByGameType", query = "SELECT m FROM Matches m WHERE m.gameType = :gameType"),
    @NamedQuery(name = "Matches.findByInvald", query = "SELECT m FROM Matches m WHERE m.invald = :invald"),
    @NamedQuery(name = "Matches.findByMapId", query = "SELECT m FROM Matches m WHERE m.mapId = :mapId"),
    @NamedQuery(name = "Matches.findByIpEarned", query = "SELECT m FROM Matches m WHERE m.ipEarned = :ipEarned"),
    @NamedQuery(name = "Matches.findBySubType", query = "SELECT m FROM Matches m WHERE m.subType = :subType")})
public class Matches implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "gameId")
    private Long gameId;
    @Column(name = "createDate")
    private BigInteger createDate;
    @Column(name = "matchDuration")
    private BigInteger matchDuration;
    @Size(max = 20)
    @Column(name = "gameMode")
    private String gameMode;
    @Size(max = 20)
    @Column(name = "gameType")
    private String gameType;
    @Column(name = "invald")
    private Boolean invald;
    @Column(name = "mapId")
    private Integer mapId;
    @Column(name = "ipEarned")
    private Integer ipEarned;
    @Size(max = 20)
    @Column(name = "subType")
    private String subType;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "matches")
    private Collection<MatchrawStats> matchrawStatsCollection;

    public Matches() {
    }

    public Matches(Long gameId) {
        this.gameId = gameId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public BigInteger getCreateDate() {
        return createDate;
    }

    public void setCreateDate(BigInteger createDate) {
        this.createDate = createDate;
    }

    public BigInteger getMatchDuration() {
        return matchDuration;
    }

    public void setMatchDuration(BigInteger matchDuration) {
        this.matchDuration = matchDuration;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public Boolean getInvald() {
        return invald;
    }

    public void setInvald(Boolean invald) {
        this.invald = invald;
    }

    public Integer getMapId() {
        return mapId;
    }

    public void setMapId(Integer mapId) {
        this.mapId = mapId;
    }

    public Integer getIpEarned() {
        return ipEarned;
    }

    public void setIpEarned(Integer ipEarned) {
        this.ipEarned = ipEarned;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
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
        hash += (gameId != null ? gameId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Matches)) {
            return false;
        }
        Matches other = (Matches) object;
        if ((this.gameId == null && other.gameId != null) || (this.gameId != null && !this.gameId.equals(other.gameId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lolprocn.dbentities.Matches[ gameId=" + gameId + " ]";
    }
    
}
