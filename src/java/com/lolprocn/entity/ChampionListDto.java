/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lolprocn.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alan
 */
public class ChampionListDto {
    List<ChampionDto> champions = new ArrayList<ChampionDto>();

    public List<ChampionDto> getChampions() {
        return champions;
    }

    public void setChampions(List<ChampionDto> champions) {
        this.champions = champions;
    }
    
    
}
