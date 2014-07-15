/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lolprocn.entity;

/**
 *
 * @author xkong63
 */
public class PlayerStatsSummaryDto {
    AggregatedStatsDto aggregatedStatsDto;
        int losses;
     String   playerStatSummaryType;
    int wins;

    public AggregatedStatsDto getAggregatedStatsDto() {
        return aggregatedStatsDto;
    }

    public void setAggregatedStatsDto(AggregatedStatsDto aggregatedStatsDto) {
        this.aggregatedStatsDto = aggregatedStatsDto;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public String getPlayerStatSummaryType() {
        return playerStatSummaryType;
    }

    public void setPlayerStatSummaryType(String playerStatSummaryType) {
        this.playerStatSummaryType = playerStatSummaryType;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }
    
}
