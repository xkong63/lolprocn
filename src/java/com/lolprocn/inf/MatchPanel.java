/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lolprocn.inf;

import com.lolprocn.entity.rankMatch.MatchSummary;
import com.lolprocn.entity.rankMatch.Participant;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Apollowc
 */
@ManagedBean(name = "matchPanel")
@SessionScoped
public class MatchPanel implements Serializable {

    /**
     * Creates a new instance of MatchPanel
     */
    private List<SummonerGameData> summonerGameDatas;
    
    public MatchPanel() {
   
    }

    public List<SummonerGameData> getSummonerGameDatas() {
        return summonerGameDatas;
    }

    public void setSummonerGameDatas(List<SummonerGameData> summonerGameDatas) {
        this.summonerGameDatas = summonerGameDatas;
    }




    
    
}
