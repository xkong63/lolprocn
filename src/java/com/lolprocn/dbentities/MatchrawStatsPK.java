/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lolprocn.dbentities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Apollowc
 */
@Embeddable
public class MatchrawStatsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "gameId")
    private long gameId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "summonerName")
    private String summonerName;

    public MatchrawStatsPK() {
    }

    public MatchrawStatsPK(long gameId, String summonerName) {
        this.gameId = gameId;
        this.summonerName = summonerName;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) gameId;
        hash += (summonerName != null ? summonerName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MatchrawStatsPK)) {
            return false;
        }
        MatchrawStatsPK other = (MatchrawStatsPK) object;
        if (this.gameId != other.gameId) {
            return false;
        }
        if ((this.summonerName == null && other.summonerName != null) || (this.summonerName != null && !this.summonerName.equals(other.summonerName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lolprocn.dbentities.MatchrawStatsPK[ gameId=" + gameId + ", summonerName=" + summonerName + " ]";
    }
    
}
