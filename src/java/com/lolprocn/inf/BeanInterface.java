/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lolprocn.inf;

import com.lolprocn.dbentities.Matches;
import com.lolprocn.dbentities.MatchrawStats;
import com.lolprocn.dbentities.MatchrawStatsPK;
import com.lolprocn.dbentities.Summoner;
import com.lolprocn.entity.ChampionListDto;
import com.lolprocn.entity.MasteryDto;
import com.lolprocn.entity.MasteryPageDto;
import com.lolprocn.entity.MasteryPagesDto;
import com.lolprocn.entity.PlayerStatsSummaryDto;
import com.lolprocn.entity.PlayerStatsSummaryListDto;
import com.lolprocn.entity.RunePageDto;
import com.lolprocn.entity.RunePagesDto;
import com.lolprocn.entity.RuneSlotDto;
import com.lolprocn.entity.SummonerDto;
import com.lolprocn.entity.rankMatch.MatchSummary;
import com.lolprocn.entity.rankMatch.Participant;
import com.lolprocn.entity.rankMatch.PlayerHistory;
import com.lolprocn.jpaController.MatchesJpaController;
import com.lolprocn.jpaController.SummonerJpaController;
import com.lolprocn.jpaController.exceptions.RollbackFailureException;
import com.lolprocn.utils.JSONParser;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.swing.text.html.HTMLDocument;
import javax.transaction.UserTransaction;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 *
 * @author Apollowc
 */
@ManagedBean(name = "data")
@SessionScoped
public class BeanInterface implements Serializable {

    @PersistenceUnit(unitName = "LOLProCNPU") //inject from your application server 
    EntityManagerFactory emf;
    @Resource //inject from your application server 
    UserTransaction utx;
    
    
    @ManagedProperty("#{database}")
    private Database database;
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
    private List<Matches> matches;//database
    private PlayerHistory playerHistory;//from api

    public BeanInterface() {
        try {
            
           
            System.out.println("BeanInterface!");
            parser = new JSONParser();
            freeToPlayChampionList = parser.getFreeToPlayChampionList();

         
        } catch (IOException ex) {
            Logger.getLogger(BeanInterface.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(BeanInterface.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
        @PostConstruct
    public void init() {
        System.out.println("database injected: " + database);
    }

    public void setDatabase(Database database) {
        this.database = database;
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
            /*database insert*/
            dbSummonerDtoInsert();
            /*matches and rawdata find from database*/
            //if(!dbMatchSearch()){
                matchQueryFromApi();
            //};
//            Matches match=new Matches(1489678);
//            database.dbMatchInsert(match);
            mastery_display();
            rune_display();

            return "profile?faces-redirect=true";
        } catch (JSONException ex) {
            Logger.getLogger(BeanInterface.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BeanInterface.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BeanInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index";
    }

    private boolean dbMatchSearch(){
       if(database.dbMatchesFind()!=null)
                return true;
         else
                return false;
    }
    private void dbSummonerDtoInsert()  {
        try {
            database.dbSummonerDtoInsert(summonerDto);
        } catch (Exception ex) {
            System.out.println("BeanInterfase:dbSummonerDtoInsert()");
            Logger.getLogger(BeanInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void matchQueryFromApi() throws IOException, JSONException{
       playerHistory=parser.getPlayerHistory(summonerDto.getId());
       List<MatchSummary> matchSummarys=playerHistory.getMatches();
       List<MatchrawStats> matchrawStats=new ArrayList<MatchrawStats>();
       for(MatchSummary matchSummary:matchSummarys){
           System.out.println("matchQueryFromApi:"+matchSummary.getMatchId());
           Matches match=new Matches(matchSummary.getMatchId());
           match.setCreateDate(matchSummary.getMatchCreation());
           match.setMapId(matchSummary.getMapId());
           match.setMatchDuration(matchSummary.getMatchDuration());
           match.setGameMode(matchSummary.getQueueType());
                      dbMatchInsert(match);
           MatchrawStats stats=new MatchrawStats(matchSummary.getMatchId(),summonerDto.getId());
           Summoner summoner=database.dbFindSummoner(summonerDto.getId());
           Matches matches=database.dbFindMatches(matchSummary.getMatchId());
           stats.setMatches(matches);
           stats.setSummoner(summoner);
           dbMatchrawStatsInsert(stats);

       }
    }

    private void dbMatchrawStatsInsert(MatchrawStats stats){
        try {
            database.dbMatchRawStatsInsert(stats);
        } catch (Exception ex) {
            Logger.getLogger(BeanInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void dbMatchInsert(Matches match){
        try {
            database.dbMatchInsert(match);
        } catch (Exception ex) {
            Logger.getLogger(BeanInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void statistic_display() throws JSONException, IOException, Exception {
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
        List<MasteryChildPanel> masteryChildPanelGroup = new ArrayList<MasteryChildPanel>();
        Set<MasteryPageDto> pages = summoner_masterPagesDto.getPages();
        masteryParentPanel = new MasteryParentPanel();
        Iterator pages_itr = pages.iterator();
        for (; pages_itr.hasNext();) {
            MasteryChildPanel masteryChildPanel = new MasteryChildPanel();
            MasteryPageDto page = (MasteryPageDto) pages_itr.next();

            List<MasteryDto> masteries = page.getMasteries();
            Iterator mastery_itr = masteries.listIterator();

            masteryChildPanel.setPageName(page.getName());
            int[][] masteryId = new int[30][2];
            System.out.println("page:" + page.getName());
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
                    masteryChildPanel.getField1_masteryVal()[panelR - 1][panelC - 1] = masteryChildPanel.getPATH() + masteryId[r][0] + ".png";
                    masteryChildPanel.getField1_masteryImgStyle()[panelR - 1][panelC - 1] = "height:35px;width:35px;";
                    break;
                case 2:
                    masteryChildPanel.getField2_masteryVal()[panelR - 1][panelC - 1] = masteryChildPanel.getPATH() + masteryId[r][0] + ".png";
                    masteryChildPanel.getField2_masteryImgStyle()[panelR - 1][panelC - 1] = "height:35px;width:35px;";
                    break;
                case 3:
                    masteryChildPanel.getField3_masteryVal()[panelR - 1][panelC - 1] = masteryChildPanel.getPATH() + masteryId[r][0] + ".png";
                    masteryChildPanel.getField3_masteryImgStyle()[panelR - 1][panelC - 1] = "height:35px;width:35px;";
                    break;
                default:
                    return;
            }
        }
    }

    private void rune_display() throws IOException, JSONException {

        RunePagesDto runePagesDto = parser.getRunePagesDto(summonerDto.getId());
        Set<RunePageDto> runePageSet = (HashSet<RunePageDto>) runePagesDto.getPages();
        Iterator runePageItr = runePageSet.iterator();
        List<RuneChildPanel> runeChildPanelGroup = new ArrayList<RuneChildPanel>();
        for (; runePageItr.hasNext();) {
            RuneChildPanel runeChildPanel = new RuneChildPanel();
            RunePageDto page = (RunePageDto) runePageItr.next();

            runeChildPanel.setPageName(page.getName());
            Set<RuneSlotDto> slots = (HashSet<RuneSlotDto>) page.getSlots();
            Iterator slotItr = slots.iterator();
            for (; slotItr.hasNext();) {
                RuneSlotDto slot = (RuneSlotDto) slotItr.next();
                System.out.println("slotId:" + slot.getRuneSlotId());
                System.out.println("runeId:" + slot.getRuneId());
                runeChildPanel.getRuneVal()[slot.getRuneSlotId() - 1] = runeChildPanel.getPATH() + setRunePageName(slot.getRuneId());
            }
            runeChildPanelGroup.add(runeChildPanel);
        }
        runeParentPanel = new RuneParentPanel();
        runeParentPanel.setRuneChildPanelGroup(runeChildPanelGroup);

    }

    private String setRunePageName(int runeId) throws IOException, JSONException {

        FileReader fr = new FileReader("/Users/Apollowc/NetBeansProjects/LolProCn/web/resources/json/rune_image.json");
        char[] data = new char[1500000];
        fr.read(data);
        JSONObject jobj = new JSONObject(new String(data));
        JSONObject dat = jobj.getJSONObject("data");
        JSONObject data1 = dat.getJSONObject(Integer.toString(runeId));
        JSONObject image = data1.getJSONObject("image");
        return image.getString("full");

    }

}
