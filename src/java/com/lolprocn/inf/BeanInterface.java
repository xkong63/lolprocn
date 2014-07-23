/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lolprocn.inf;

import com.lolprocn.entity.ChampionListDto;
import com.lolprocn.entity.PlayerStatsSummaryDto;
import com.lolprocn.entity.PlayerStatsSummaryListDto;
import com.lolprocn.entity.SummonerDto;
import com.lolprocn.utils.JSONParser;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import org.primefaces.json.JSONException;

/**
 *
 * @author Apollowc
 */
@ManagedBean(name = "data")
//@RequestScoped
@SessionScoped
public class BeanInterface implements Serializable {

    /**
     * Creates a new instance of SummonerPopulate
     */
    private String summoner;
    private SummonerDto summonerDto;
    private PlayerStatsSummaryListDto summonerSummarylist;
    private Statistics statistic;
    private JSONParser parser;
    private ChampionListDto freeToPlayChampionList;

    public BeanInterface() {
        try {
            parser = new JSONParser();
            freeToPlayChampionList = parser.getFreeToPlayChampionList();
        } catch (IOException ex) {
            Logger.getLogger(BeanInterface.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(BeanInterface.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ChampionListDto getFreeToPlayChampionList() {
        return freeToPlayChampionList;
    }

    public void setFreeToPlayChampionList(ChampionListDto freeToPlayChampionList) {
        this.freeToPlayChampionList = freeToPlayChampionList;
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

    public Statistics getStatistic() {
        return statistic;
    }

    public void setStatistic(Statistics statistic) {
        this.statistic = statistic;
    }

    public String profileRedirect() {

        try {
            /*
            summoner details:such as name,level,normal rank status
            */
            summonerDto = parser.populateSummonerDto(summoner);
            summonerSummarylist = parser.populatePlayerStatsSummaryListDto(summonerDto.getId());
            System.out.println(summonerDto.getId());
            this.statistic = new Statistics();
            Iterator itr_player;
            List<PlayerStatsSummaryDto> playerStatsSummaryDto = summonerSummarylist.getPlayerStatsSummaryDto();

            itr_player = playerStatsSummaryDto.listIterator();

            for (; itr_player.hasNext();) {
                PlayerStatsSummaryDto e = (PlayerStatsSummaryDto) itr_player.next();
                if (e.getPlayerStatSummaryType().equals("Unranked")) {
                    statistic.setNormal5_5(e);
                }
                if (e.getPlayerStatSummaryType().equals("RankedSolo5x5")) {
                    statistic.setRank5_5(e);
                }
            }

            /*
            summoner mastery info
            */
            parser.getSummonerMasterPages(summonerDto.getId());
            return "profile?faces-redirect=true";
        } catch (JSONException ex) {
            Logger.getLogger(BeanInterface.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BeanInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index";
    }

}
