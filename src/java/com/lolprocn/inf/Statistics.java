/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lolprocn.inf;

import com.lolprocn.entity.PlayerStatsSummaryDto;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Apollowc
 */
@ManagedBean
@Named(value = "statistics")
@SessionScoped
public class Statistics implements Serializable {

    /**
     * Creates a new instance of Statistics
     */
    private String title;
    private int wins;
    private int kills;
    private int assists;
    private int loses;
    private int Minion_kills;
    private int Neutral_minion_Kills;
    private int Turret_destroyed;
    private PlayerStatsSummaryDto normal5_5;
    private PlayerStatsSummaryDto rank5_5;

    public Statistics() {
        title="Normal 5V5";
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    public int getMinion_kills() {
        return Minion_kills;
    }

    public void setMinion_kills(int Minion_kills) {
        this.Minion_kills = Minion_kills;
    }

    public int getNeutral_minion_Kills() {
        return Neutral_minion_Kills;
    }

    public void setNeutral_minion_Kills(int Neutral_minion_Kills) {
        this.Neutral_minion_Kills = Neutral_minion_Kills;
    }

    public int getTurret_destroyed() {
        return Turret_destroyed;
    }

    public void setTurret_destroyed(int Turret_destroyed) {
        this.Turret_destroyed = Turret_destroyed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PlayerStatsSummaryDto getNormal5_5() {
        return normal5_5;
    }

    public void setNormal5_5(PlayerStatsSummaryDto normal5_5) {
        this.normal5_5 = normal5_5;
        this.wins = normal5_5.getWins();
        this.kills = normal5_5.getAggregatedStatsDto().getTotalChampionKills();
        this.assists = normal5_5.getAggregatedStatsDto().getTotalAssists();
        this.Minion_kills = normal5_5.getAggregatedStatsDto().getTotalMinionKills();
        this.Neutral_minion_Kills = normal5_5.getAggregatedStatsDto().getTotalNeutralMinionsKilled();
        this.Turret_destroyed = normal5_5.getAggregatedStatsDto().getTotalTurretsKilled();
    }

    public PlayerStatsSummaryDto getRank5_5() {
        return rank5_5;
    }

    public void setRank5_5(PlayerStatsSummaryDto rank5_5) {
        this.rank5_5 = rank5_5;
    }

    public void normal_rank_update() {
        title = "Rank 5V5";
        this.wins = rank5_5.getWins();
        this.kills = rank5_5.getAggregatedStatsDto().getTotalChampionKills();
        this.assists = rank5_5.getAggregatedStatsDto().getTotalAssists();
        this.Minion_kills = rank5_5.getAggregatedStatsDto().getTotalMinionKills();
        this.Neutral_minion_Kills = rank5_5.getAggregatedStatsDto().getTotalNeutralMinionsKilled();
        this.Turret_destroyed = rank5_5.getAggregatedStatsDto().getTotalTurretsKilled();
        this.loses=rank5_5.getLosses();
    }

    public void rank_normal_update() {
        title = "Normal 5V5";
        this.wins = normal5_5.getWins();
        this.kills = normal5_5.getAggregatedStatsDto().getTotalChampionKills();
        this.assists = normal5_5.getAggregatedStatsDto().getTotalAssists();
        this.Minion_kills = normal5_5.getAggregatedStatsDto().getTotalMinionKills();
        this.Neutral_minion_Kills = normal5_5.getAggregatedStatsDto().getTotalNeutralMinionsKilled();
        this.Turret_destroyed = normal5_5.getAggregatedStatsDto().getTotalTurretsKilled();
    }

}
