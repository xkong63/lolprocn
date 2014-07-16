/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lolprocn.utils;

import com.lolprocn.entity.ChampionDto;
import com.lolprocn.entity.ChampionListDto;
import com.lolprocn.entity.PlayerStatsSummaryDto;
import com.lolprocn.entity.PlayerStatsSummaryListDto;
import com.lolprocn.entity.SummonerDto;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import org.primefaces.json.JSONException;

/**
 *
 * @author Apollowc
 */
@ManagedBean
@Named(value = "data")
@SessionScoped
public class BeanInterface implements Serializable {

    /**
     * Creates a new instance of SummonerPopulate
     */
    private String summoner;
    private SummonerDto summonerDto;
    private PlayerStatsSummaryListDto summonerSummarylist;
    private PlayerStatsSummaryDto normal5_5;
    private JSONParser parser;
    private ChampionListDto championList;
    
        public BeanInterface() {
        try {
            parser=new JSONParser();
            championList=parser.getFreeToPlayChampionList();
            Iterator itr_champion;
            List<ChampionDto> championDto=championList.getChampions();
            itr_champion=championDto.listIterator();
//            for(;itr_champion.hasNext();){
//                
//            }
        } catch (IOException ex) {
            Logger.getLogger(BeanInterface.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(BeanInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public ChampionListDto getChampionList() {
        return championList;
    }

    public void setChampionList(ChampionListDto championList) {
        this.championList = championList;
    }


        
    public SummonerDto getSummonerDto() {
        return summonerDto;
    }

    public void setSummonerDto(SummonerDto summonerDto) {
        this.summonerDto = summonerDto;
    }
    


    public String getSummoner() {
        return summoner;
    }

    public void setSummoner(String summoner) {
        this.summoner = summoner;
    }

    public PlayerStatsSummaryDto getNormal5_5() {
        return normal5_5;
    }

    public void setNormal5_5(PlayerStatsSummaryDto normal5_5) {
        this.normal5_5 = normal5_5;
    }
    
    public String profileRedirect(){
        
        
        try {
            summonerDto=parser.populateSummonerDto(summoner); 
            summonerSummarylist=parser.populatePlayerStatsSummaryListDto(summonerDto.getId());
            
            Iterator itr_player;
            List<PlayerStatsSummaryDto> playerStatsSummaryDto = summonerSummarylist.getPlayerStatsSummaryDto();
            
            itr_player=playerStatsSummaryDto.listIterator();
            
            for(;itr_player.hasNext();){
                PlayerStatsSummaryDto e = (PlayerStatsSummaryDto)itr_player.next();               
                if(e.getPlayerStatSummaryType().equals("Unranked")){
                    normal5_5=e;
                }                       
            }
            
            
            return "profile";
        } catch (JSONException ex) {
            Logger.getLogger(BeanInterface.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BeanInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index";
    }
      
    
}
