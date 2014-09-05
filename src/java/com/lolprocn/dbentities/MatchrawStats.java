/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lolprocn.dbentities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Apollowc
 */
@Entity
@Table(name = "match_rawStats")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MatchrawStats.findAll", query = "SELECT m FROM MatchrawStats m"),
    @NamedQuery(name = "MatchrawStats.findByGameId", query = "SELECT m FROM MatchrawStats m WHERE m.matchrawStatsPK.gameId = :gameId"),
    @NamedQuery(name = "MatchrawStats.findBySummonerName", query = "SELECT m FROM MatchrawStats m WHERE m.matchrawStatsPK.summonerName = :summonerName"),
    @NamedQuery(name = "MatchrawStats.findByChampionId", query = "SELECT m FROM MatchrawStats m WHERE m.championId = :championId"),
    @NamedQuery(name = "MatchrawStats.findBySpell1Id", query = "SELECT m FROM MatchrawStats m WHERE m.spell1Id = :spell1Id"),
    @NamedQuery(name = "MatchrawStats.findBySpell2Id", query = "SELECT m FROM MatchrawStats m WHERE m.spell2Id = :spell2Id"),
    @NamedQuery(name = "MatchrawStats.findByAssists", query = "SELECT m FROM MatchrawStats m WHERE m.assists = :assists"),
    @NamedQuery(name = "MatchrawStats.findByKills", query = "SELECT m FROM MatchrawStats m WHERE m.kills = :kills"),
    @NamedQuery(name = "MatchrawStats.findByMinionsKilled", query = "SELECT m FROM MatchrawStats m WHERE m.minionsKilled = :minionsKilled"),
    @NamedQuery(name = "MatchrawStats.findByDeaths", query = "SELECT m FROM MatchrawStats m WHERE m.deaths = :deaths"),
    @NamedQuery(name = "MatchrawStats.findByKillSprees", query = "SELECT m FROM MatchrawStats m WHERE m.killSprees = :killSprees"),
    @NamedQuery(name = "MatchrawStats.findByTotalDamageDealtToChampions", query = "SELECT m FROM MatchrawStats m WHERE m.totalDamageDealtToChampions = :totalDamageDealtToChampions"),
    @NamedQuery(name = "MatchrawStats.findByTotalDamageTaken", query = "SELECT m FROM MatchrawStats m WHERE m.totalDamageTaken = :totalDamageTaken"),
    @NamedQuery(name = "MatchrawStats.findByItem0", query = "SELECT m FROM MatchrawStats m WHERE m.item0 = :item0"),
    @NamedQuery(name = "MatchrawStats.findByItem1", query = "SELECT m FROM MatchrawStats m WHERE m.item1 = :item1"),
    @NamedQuery(name = "MatchrawStats.findByItem2", query = "SELECT m FROM MatchrawStats m WHERE m.item2 = :item2"),
    @NamedQuery(name = "MatchrawStats.findByItem3", query = "SELECT m FROM MatchrawStats m WHERE m.item3 = :item3"),
    @NamedQuery(name = "MatchrawStats.findByItem4", query = "SELECT m FROM MatchrawStats m WHERE m.item4 = :item4"),
    @NamedQuery(name = "MatchrawStats.findByItem5", query = "SELECT m FROM MatchrawStats m WHERE m.item5 = :item5"),
    @NamedQuery(name = "MatchrawStats.findByItem6", query = "SELECT m FROM MatchrawStats m WHERE m.item6 = :item6"),
    @NamedQuery(name = "MatchrawStats.findByTeamId", query = "SELECT m FROM MatchrawStats m WHERE m.teamId = :teamId"),
    @NamedQuery(name = "MatchrawStats.findByLevel", query = "SELECT m FROM MatchrawStats m WHERE m.level = :level"),
    @NamedQuery(name = "MatchrawStats.findByGoldEarned", query = "SELECT m FROM MatchrawStats m WHERE m.goldEarned = :goldEarned"),
    @NamedQuery(name = "MatchrawStats.findByWin", query = "SELECT m FROM MatchrawStats m WHERE m.win = :win")})
public class MatchrawStats implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MatchrawStatsPK matchrawStatsPK;
    @Column(name = "championId")
    private Integer championId;
    @Column(name = "spell1Id")
    private Integer spell1Id;
    @Column(name = "spell2Id")
    private Integer spell2Id;
    @Column(name = "assists")
    private Integer assists;
    @Column(name = "kills")
    private Integer kills;
    @Column(name = "minionsKilled")
    private Integer minionsKilled;
    @Column(name = "deaths")
    private Integer deaths;
    @Column(name = "killSprees")
    private Integer killSprees;
    @Column(name = "totalDamageDealtToChampions")
    private Integer totalDamageDealtToChampions;
    @Column(name = "totalDamageTaken")
    private Integer totalDamageTaken;
    @Column(name = "item0")
    private Integer item0;
    @Column(name = "item1")
    private Integer item1;
    @Column(name = "item2")
    private Integer item2;
    @Column(name = "item3")
    private Integer item3;
    @Column(name = "item4")
    private Integer item4;
    @Column(name = "item5")
    private Integer item5;
    @Column(name = "item6")
    private Integer item6;
    @Column(name = "teamId")
    private Integer teamId;
    @Column(name = "level")
    private Short level;
    @Column(name = "goldEarned")
    private Integer goldEarned;
    @Column(name = "win")
    private Boolean win;
    @JoinColumn(name = "gameId", referencedColumnName = "gameId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Matches matches;

    public MatchrawStats() {
    }

    public MatchrawStats(MatchrawStatsPK matchrawStatsPK) {
        this.matchrawStatsPK = matchrawStatsPK;
    }

    public MatchrawStats(long gameId, String summonerName) {
        this.matchrawStatsPK = new MatchrawStatsPK(gameId, summonerName);
    }

    public MatchrawStatsPK getMatchrawStatsPK() {
        return matchrawStatsPK;
    }

    public void setMatchrawStatsPK(MatchrawStatsPK matchrawStatsPK) {
        this.matchrawStatsPK = matchrawStatsPK;
    }

    public Integer getChampionId() {
        return championId;
    }

    public void setChampionId(Integer championId) {
        this.championId = championId;
    }

    public Integer getSpell1Id() {
        return spell1Id;
    }

    public void setSpell1Id(Integer spell1Id) {
        this.spell1Id = spell1Id;
    }

    public Integer getSpell2Id() {
        return spell2Id;
    }

    public void setSpell2Id(Integer spell2Id) {
        this.spell2Id = spell2Id;
    }

    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    public Integer getKills() {
        return kills;
    }

    public void setKills(Integer kills) {
        this.kills = kills;
    }

    public Integer getMinionsKilled() {
        return minionsKilled;
    }

    public void setMinionsKilled(Integer minionsKilled) {
        this.minionsKilled = minionsKilled;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public Integer getKillSprees() {
        return killSprees;
    }

    public void setKillSprees(Integer killSprees) {
        this.killSprees = killSprees;
    }

    public Integer getTotalDamageDealtToChampions() {
        return totalDamageDealtToChampions;
    }

    public void setTotalDamageDealtToChampions(Integer totalDamageDealtToChampions) {
        this.totalDamageDealtToChampions = totalDamageDealtToChampions;
    }

    public Integer getTotalDamageTaken() {
        return totalDamageTaken;
    }

    public void setTotalDamageTaken(Integer totalDamageTaken) {
        this.totalDamageTaken = totalDamageTaken;
    }

    public Integer getItem0() {
        return item0;
    }

    public void setItem0(Integer item0) {
        this.item0 = item0;
    }

    public Integer getItem1() {
        return item1;
    }

    public void setItem1(Integer item1) {
        this.item1 = item1;
    }

    public Integer getItem2() {
        return item2;
    }

    public void setItem2(Integer item2) {
        this.item2 = item2;
    }

    public Integer getItem3() {
        return item3;
    }

    public void setItem3(Integer item3) {
        this.item3 = item3;
    }

    public Integer getItem4() {
        return item4;
    }

    public void setItem4(Integer item4) {
        this.item4 = item4;
    }

    public Integer getItem5() {
        return item5;
    }

    public void setItem5(Integer item5) {
        this.item5 = item5;
    }

    public Integer getItem6() {
        return item6;
    }

    public void setItem6(Integer item6) {
        this.item6 = item6;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Short getLevel() {
        return level;
    }

    public void setLevel(Short level) {
        this.level = level;
    }

    public Integer getGoldEarned() {
        return goldEarned;
    }

    public void setGoldEarned(Integer goldEarned) {
        this.goldEarned = goldEarned;
    }

    public Boolean getWin() {
        return win;
    }

    public void setWin(Boolean win) {
        this.win = win;
    }

    public Matches getMatches() {
        return matches;
    }

    public void setMatches(Matches matches) {
        this.matches = matches;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (matchrawStatsPK != null ? matchrawStatsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MatchrawStats)) {
            return false;
        }
        MatchrawStats other = (MatchrawStats) object;
        if ((this.matchrawStatsPK == null && other.matchrawStatsPK != null) || (this.matchrawStatsPK != null && !this.matchrawStatsPK.equals(other.matchrawStatsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lolprocn.dbentities.MatchrawStats[ matchrawStatsPK=" + matchrawStatsPK + " ]";
    }
    
}
