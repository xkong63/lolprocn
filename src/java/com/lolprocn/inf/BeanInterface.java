/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lolprocn.inf;

import com.lolprocn.entity.ChampionListDto;
import com.lolprocn.entity.MasteryDto;
import com.lolprocn.entity.MasteryPageDto;
import com.lolprocn.entity.MasteryPagesDto;
import com.lolprocn.entity.PlayerStatsSummaryDto;
import com.lolprocn.entity.PlayerStatsSummaryListDto;
import com.lolprocn.entity.RunePageDto;
import com.lolprocn.entity.RunePagesDto;
import com.lolprocn.entity.SummonerDto;
import com.lolprocn.utils.JSONParser;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.swing.text.html.HTMLDocument;
import org.primefaces.json.JSONException;

/**
 *
 * @author Apollowc
 */
@ManagedBean(name = "data")
@SessionScoped
public class BeanInterface implements Serializable {

    /**
     * Creates a new instance of SummonerPopulate
     */
    private String summoner;
    private SummonerDto summonerDto; 
    private Statistics statistic;
    private JSONParser parser;
    private ChampionListDto freeToPlayChampionList;
    private MasteryParentPanel masteryParentPanel;
    private RuneParentPanel runeParentPanel;
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

    public RuneParentPanel getRuneParentPanel() {
        return runeParentPanel;
    }

    public void setRuneParentPanel(RuneParentPanel runeParentPanel) {
        this.runeParentPanel = runeParentPanel;
    }

    public MasteryParentPanel getMasteryParentPanel() {
        return masteryParentPanel;
    }

    public void setMasteryParentPanel(MasteryParentPanel masteryParentPanel) {
        this.masteryParentPanel = masteryParentPanel;
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
            statistic_display();
            mastery_display();
            rune_display();
            return "profile?faces-redirect=true";
        } catch (JSONException ex) {
            Logger.getLogger(BeanInterface.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BeanInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index";
    }

    private void statistic_display() throws JSONException, IOException{
        summonerDto = parser.populateSummonerDto(summoner);
            PlayerStatsSummaryListDto summonerSummarylist;
            summonerSummarylist = parser.populatePlayerStatsSummaryListDto(summonerDto.getId());
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
    }
    private void mastery_display() throws IOException, JSONException {
    
            MasteryPagesDto summoner_masterPagesDto;
            summoner_masterPagesDto = parser.getSummonerMasterPages(summonerDto.getId());
            List<MasteryChildPanel> masteryChildPanelGroup=new ArrayList<MasteryChildPanel>();
            Set<MasteryPageDto> pages = summoner_masterPagesDto.getPages();
            masteryParentPanel =new MasteryParentPanel();
            Iterator pages_itr = pages.iterator();
            for (; pages_itr.hasNext();) {
                MasteryChildPanel masteryChildPanel = new MasteryChildPanel();
                MasteryPageDto page = (MasteryPageDto) pages_itr.next();
                
                List<MasteryDto> masteries = page.getMasteries();
                Iterator mastery_itr = masteries.listIterator();
                
                masteryChildPanel.setPageName(page.getName());
                int[][] masteryId = new int[30][2];
                System.out.println("page:"+page.getName());
                for (int i = 0; mastery_itr.hasNext(); i++) {
                    MasteryDto masteryDto = (MasteryDto) mastery_itr.next();
                    masteryId[i][0] = masteryDto.getId();
                    masteryId[i][1] = masteryDto.getRank();
                    
                    //               System.out.println("id:" + masteryDto.getId() + " rank:" + masteryDto.getRank());
                    
                }
                
                masteryChildPanelValChange(masteryId, masteryChildPanel);
                masteryChildPanelGroup.add(masteryChildPanel);
            }
            masteryParentPanel.setMasteryChildPanelGroup(masteryChildPanelGroup);


            
    }

    private void masteryChildPanelValChange(int[][] masteryId, MasteryChildPanel masteryChildPanel) {
        for (int r = 0; r < masteryId.length; r++) {
            int panelNum, panelR, panelC;
            panelNum = (masteryId[r][0] % 4000) / 100;
            panelR = ((masteryId[r][0] % 4000) % 100) / 10;
            panelC = ((masteryId[r][0] % 4000) % 100) % 10;
           // System.out.println("panelNum:"+panelNum+" R:"+panelR+" C:"+panelC);
  
            switch (panelNum) {
                case 1:
                    masteryChildPanel.getField1_masteryVal()[panelR-1][panelC-1] = masteryChildPanel.getPATH()  + masteryId[r][0] + ".png";
                    break;
                case 2:
                    masteryChildPanel.getField2_masteryVal()[panelR-1][panelC-1] = masteryChildPanel.getPATH()  + masteryId[r][0] + ".png";
                    break;
                case 3:
                    masteryChildPanel.getField3_masteryVal()[panelR-1][panelC-1] = masteryChildPanel.getPATH()  + masteryId[r][0] + ".png";
                    break;
                default:
                    return;
            }
        }
    }
    
    private void rune_display() throws IOException, JSONException{
        

         RunePagesDto runePagesDto=parser.getRunePagesDto(summonerDto.getId());
         Set<RunePageDto> runePageSet=(HashSet<RunePageDto>)runePagesDto.getPages();
         Iterator runePageItr=runePageSet.iterator();
         List<RuneChildPanel> runeChildPanelGroup=new ArrayList<RuneChildPanel>();
         for(;runePageItr.hasNext();){
             RuneChildPanel runeChildPanel=new RuneChildPanel();
             RunePageDto page=(RunePageDto) runePageItr.next();
             runeChildPanel.setPageName(page.getName());
              runeChildPanelGroup.add(runeChildPanel);
         }
        runeParentPanel=new RuneParentPanel();
        runeParentPanel.setRuneChildPanelGroup(runeChildPanelGroup);
        



    }

}
