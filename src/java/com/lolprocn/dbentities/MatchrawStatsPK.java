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
    @Column(name = "summonerId")
    private long summonerId;

    public MatchrawStatsPK() {
    }

    public MatchrawStatsPK(long gameId, long summonerId) {
        this.gameId = gameId;
        this.summonerId = summonerId;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public long getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(long summonerId) {
        this.summonerId = summonerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) gameId;
        hash += (int) summonerId;
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
        if (this.summonerId != other.summonerId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lolprocn.dbentities.MatchrawStatsPK[ gameId=" + gameId + ", summonerId=" + summonerId + " ]";
    }
    
}
