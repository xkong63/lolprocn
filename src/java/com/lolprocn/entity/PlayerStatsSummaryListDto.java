/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lolprocn.entity;

import java.util.List;

/**
 *
 * @author xkong63
 */
public class PlayerStatsSummaryListDto {
    
    List<PlayerStatsSummaryDto> playerStatsSummaryDto;
    long summonerId;

    public List<PlayerStatsSummaryDto> getPlayerStatsSummaryDto() {
        return playerStatsSummaryDto;
    }

    public void setPlayerStatsSummaryDto(List<PlayerStatsSummaryDto> playerStatsSummaryDto) {
        this.playerStatsSummaryDto = playerStatsSummaryDto;
    }

    public long getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(long summonerId) {
        this.summonerId = summonerId;
    }
        
}
