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
import com.lolprocn.entity.rankMatch.ParticipantIdentity;
import com.lolprocn.entity.rankMatch.ParticipantStats;
import com.lolprocn.entity.rankMatch.Player;
import com.lolprocn.entity.rankMatch.PlayerHistory;
import com.lolprocn.jpaController.MatchesJpaController;
import com.lolprocn.jpaController.SummonerJpaController;
import com.lolprocn.jpaController.exceptions.PreexistingEntityException;
import com.lolprocn.jpaController.exceptions.RollbackFailureException;
import com.lolprocn.utils.JSONParser;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
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
    private MatchPanel matchPanel;
    private List<Matches> matches;//database
    private PlayerHistory playerHistory;//from api
    private List<SummonerGameData> summonerGameDatas;//for jsf
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

    public PlayerHistory getPlayerHistory() {
        return playerHistory;
    }

    public void setPlayerHistory(PlayerHistory playerHistory) {
        this.playerHistory = playerHistory;
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

    public MatchPanel getMatchPanel() {
        return matchPanel;
    }

    public void setMatchPanel(MatchPanel matchPanel) {
        this.matchPanel = matchPanel;
    }

    public String profileRedirect() {

        try {
            statistic_display();
            /*database insert*/
            if (!dbFindSummoner(summonerDto.getId())) {
                dbSummonerDtoInsert();
            }
            /*matches and rawdata find from database*/
            if (!matchQueryFromDatabase()) {
                matchQueryFromApi();
            }
            match_display();
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

    private boolean dbFindMatchBySummonerName(String name) {
        return database.dbFindMatchBySummonerName(name)!=null;
    }

    private boolean dbFindSummoner(long summonerId) {
       
        return database.dbFindSummoner(summonerId)!=null;
    }

    private void dbSummonerDtoInsert() {
        try {
            database.dbSummonerDtoInsert(summonerDto);
        }catch(PreexistingEntityException pex){
            Logger.getLogger(BeanInterface.class.getName()).log(Level.WARNING, "summonerId already exists in Summoner table!");
        }       catch (Exception ex) {
            System.out.println("BeanInterfase:dbSummonerDtoInsert()");
            Logger.getLogger(BeanInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean matchQueryFromDatabase() throws JSONException, IOException{
//        playerHistory=new PlayerHistory();
        List<MatchSummary> matchSummarys=new ArrayList<>();
        List<MatchrawStats> matchrawStatses=database.dbFindMatchBySummonerName(summonerDto.getName());
        
        if(matchrawStatses==null)
            return false;
        summonerGameDatas=new LinkedList<>();
        for(MatchrawStats stats:matchrawStatses){
            SummonerGameData data=new SummonerGameData();
            data.setSummonerName(stats.getMatchrawStatsPK().getSummonerName());
            data.setMatchId(stats.getMatches().getGameId());
            data.setAssists(stats.getAssists());  
            data.setChampionName(JSONParser.convertFromIdToName((long)stats.getChampionId()));
                data.setAssists(stats.getAssists());
                data.setChampLevel(stats.getLevel());
                data.setDeaths(stats.getDeaths());
                data.setGoldEarned(stats.getGoldEarned());
                data.setItem0(stats.getItem0());
                data.setItem1(stats.getItem1());
                data.setItem2(stats.getItem2());
                data.setItem3(stats.getItem3());
                data.setItem4(stats.getItem4());
                data.setItem5(stats.getItem5());
                data.setItem6(stats.getItem6());
                data.setKillSprees(stats.getKillSprees());
                data.setKills(stats.getKills());
                data.setMinionsKilled(stats.getMinionsKilled());
                data.setTotalDamageDealtToChampions(stats.getTotalDamageDealtToChampions());
                data.setTotalDamageTaken(stats.getTotalDamageTaken());
                if(stats.getWin())
                    data.setWinner("WIN");
                else
                    data.setWinner("LOSS");
                summonerGameDatas.add(data);
        }
//        for(MatchrawStats stats:matchrawStatses){
//            Matches m=stats.getMatches();
//            MatchSummary matchSummary=new MatchSummary();
//            matchSummary.setMatchId(m.getGameId());
//            matchSummary.setMatchCreation(m.getCreateDate());
//            matchSummary.setMatchDuration(m.getMatchDuration());
//            matchSummary.setMapId(m.getMapId());
//            matchSummary.setQueueType(m.getGameMode());
//               List<MatchrawStats> eachGame=database.dbFindMatchRawStatsByMatchId(m.getGameId());
//            List<Participant> list=new ArrayList<>();
//            List<ParticipantIdentity> listIdentity=new ArrayList<>();
//
//            for(MatchrawStats s:eachGame){
//                ParticipantIdentity pi=new ParticipantIdentity();
//                Player player=new Player();
//                player.setSummonerName(s.getMatchrawStatsPK().getSummonerName());
//                pi.setPlayer(player);
//                listIdentity.add(pi);
//                Participant p=new Participant();
//                p.setChampionId(s.getChampionId());
//                p.setSpell1Id(s.getSpell1Id());
//                p.setSpell2Id(s.getSpell2Id());
//                p.setTeamId(s.getTeamId());
//                ParticipantStats participantStats=new ParticipantStats();
//                participantStats.setAssists(s.getAssists());
//                participantStats.setChampLevel(s.getLevel());
//                participantStats.setDeaths(s.getDeaths());
//                participantStats.setGoldEarned(s.getGoldEarned());
//                participantStats.setItem0(s.getItem0());
//                participantStats.setItem1(s.getItem1());
//                participantStats.setItem2(s.getItem2());
//                participantStats.setItem3(s.getItem3());
//                participantStats.setItem4(s.getItem4());
//                participantStats.setItem5(s.getItem5());
//                participantStats.setItem6(s.getItem6());
//                participantStats.setKillSprees(s.getKillSprees());
//                participantStats.setKills(s.getKills());
//                participantStats.setMinionsKilled(s.getMinionsKilled());
//                participantStats.setTotalDamageDealtToChampions(s.getTotalDamageDealtToChampions());
//                participantStats.setTotalDamageTaken(s.getTotalDamageTaken());
//                participantStats.setWinner(s.getWin());
//                p.setStats(participantStats);
//               list.add(p);
//            }
//            matchSummary.setParticipantIdentities(listIdentity);
//            matchSummary.setParticipants(list);
//            matchSummarys.add(matchSummary);
//        }
//        playerHistory.setMatches(matchSummarys);
//        System.out.println("playerHistory:"+playerHistory.getMatches());
//        for(MatchSummary su:playerHistory.getMatches()){
//            System.out.println(su.getMatchId());
//        }
        return true;

    }
    private void matchQueryFromApi() throws IOException, JSONException {
        playerHistory = parser.getPlayerHistory(summonerDto.getId());
        List<MatchSummary> matchSummarys = playerHistory.getMatches();
        for (MatchSummary matchSummary : matchSummarys) {
                        System.out.println("matchQueryFromApi:" + matchSummary.getMatchId());
            Matches match = new Matches(matchSummary.getMatchId());
            match.setCreateDate(matchSummary.getMatchCreation());
            match.setMapId(matchSummary.getMapId());
            match.setMatchDuration(matchSummary.getMatchDuration());
            match.setGameMode(matchSummary.getQueueType());
            dbMatchInsert(match);
            
            
            List<Participant> p=matchSummary.getParticipants();
            List<ParticipantIdentity> identities=matchSummary.getParticipantIdentities();
            
            for(Participant participant:p){
                MatchrawStats stats = new MatchrawStats(matchSummary.getMatchId(), identities.get(participant.getParticipantId()).getPlayer().getSummonerName());
                Matches matches = database.dbFindMatches(matchSummary.getMatchId());  
                stats.setMatches(matches);   
                
            stats.setAssists((int)participant.getStats().getAssists());
            stats.setChampionId(participant.getChampionId());
            stats.setDeaths((int)participant.getStats().getDeaths());
            stats.setSpell1Id(participant.getSpell1Id());
            stats.setSpell2Id(participant.getSpell2Id());
            stats.setGoldEarned((int)participant.getStats().getGoldEarned());
            stats.setItem0((int)participant.getStats().getItem0());
            stats.setItem1((int)participant.getStats().getItem1());
            stats.setItem2((int)participant.getStats().getItem2());
            stats.setItem3((int)participant.getStats().getItem3());
            stats.setItem4((int)participant.getStats().getItem4());
            stats.setItem5((int)participant.getStats().getItem5());
            stats.setItem6((int)participant.getStats().getItem6());
            stats.setKillSprees((int)participant.getStats().getKillSprees());
            stats.setKills((int)participant.getStats().getKills());
            stats.setLevel((short)participant.getStats().getChampLevel());
            stats.setMinionsKilled((int)participant.getStats().getMinionsKilled());
            stats.setTeamId(participant.getTeamId());
            stats.setTotalDamageDealtToChampions((int)participant.getStats().getTotalDamageDealtToChampions());
            stats.setTotalDamageTaken((int)participant.getStats().getTotalDamageTaken());
            stats.setWin(participant.getStats().isWinner());
            dbMatchrawStatsInsert(stats);
            }       
        }
    }

    private void dbMatchrawStatsInsert(MatchrawStats stats) {
        try {
            database.dbMatchRawStatsInsert(stats);
        }catch(PreexistingEntityException pex){
            Logger.getLogger(BeanInterface.class.getName()).log(Level.WARNING, stats.getMatches().getGameId()+" already exists in MatchrawStats table!");
        }  
        catch (Exception ex) {
            Logger.getLogger(BeanInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void dbMatchInsert(Matches match) {
        try {
            database.dbMatchInsert(match);
        }catch(PreexistingEntityException pex){
            Logger.getLogger(BeanInterface.class.getName()).log(Level.WARNING, match.getGameId()+" already exists in Match table!");
        }   
        catch (Exception ex) {
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
    private void match_display(){
        matchPanel=new MatchPanel();
        matchPanel.setSummonerGameDatas(summonerGameDatas);
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
//                System.out.println("slotId:" + slot.getRuneSlotId());
//                System.out.println("runeId:" + slot.getRuneId());
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
