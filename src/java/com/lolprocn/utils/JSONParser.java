/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lolprocn.utils;

import com.lolprocn.connection.Connection;
import com.lolprocn.entity.AggregatedStatsDto;
import com.lolprocn.entity.ChampionDto;
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
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

import java.util.Collection;
import java.util.Iterator;

import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 *
 * @author xkong63
 */
public class JSONParser {

    public PlayerHistory getPlayerHistory(long summonerId) throws IOException, JSONException{
        PlayerHistory playerHistory=new PlayerHistory();
        List<MatchSummary> matchList=new ArrayList<MatchSummary>();
        String response=Connection.sendGet("https://na.api.pvp.net/api/lol/na/v2.2/matchhistory/"+summonerId+"?api_key=81a9f77d-eb19-4581-a58e-afc46b10ed0b");
        JSONObject jObject0  = new JSONObject(response);
        JSONArray matches=jObject0.getJSONArray("matches");
        
        for(int i=0;i<matches.length();i++){
            JSONObject match=matches.getJSONObject(i);
            matchList.add(populateMatchSummary(match));
        }
        playerHistory.setMatches(matchList);
        return playerHistory;
    }
    
    public MatchSummary populateMatchSummary(JSONObject match) throws JSONException{
        MatchSummary matchSummary=new MatchSummary();
        matchSummary.setMapId(match.getInt("mapId"));
        matchSummary.setMatchCreation(BigInteger.valueOf(match.getLong("matchCreation")));
        
        matchSummary.setMatchDuration(BigInteger.valueOf(match.getLong("matchDuration")));
        matchSummary.setMatchId(match.getLong("matchId"));
        matchSummary.setMatchVersion(match.getString("matchVersion"));
        matchSummary.setQueueType(match.getString("queueType"));
        matchSummary.setRegion(match.getString("region"));
        matchSummary.setSeason(match.getString("season"));
        
        JSONArray participantIdentities=match.getJSONArray("participantIdentities");
        List<ParticipantIdentity> participantIdentitiesList=new ArrayList<ParticipantIdentity>();
        for(int i=0;i<participantIdentities.length();i++){
            participantIdentitiesList.add(populateParticipantIdentity(participantIdentities.getJSONObject(i)));
        }
        matchSummary.setParticipantIdentities(participantIdentitiesList);
        
        JSONArray participants=match.getJSONArray("participants");
        List<Participant> participantsList=new ArrayList<Participant>();
        for(int i=0;i<participants.length();i++){
            participantsList.add(populateParticipant(participants.getJSONObject(i)));
        }
        matchSummary.setParticipants(participantsList);
        return matchSummary;
        
    }
    public Participant populateParticipant(JSONObject participant) throws JSONException{
        Participant participant1=new Participant();
        participant1.setChampionId(participant.getInt("championId"));
        participant1.setParticipantId(participant.getInt("participantId"));
        participant1.setSpell1Id(participant.getInt("spell1Id"));
        participant1.setSpell2Id(participant.getInt("spell2Id"));
        participant1.setTeamId(participant.getInt("teamId"));
        
        ParticipantStats participantStats=populateParticipantStats(participant.getJSONObject("stats"));
        participant1.setStats(participantStats);
        return participant1;
    }
    
    public ParticipantStats populateParticipantStats(JSONObject participantStats) throws JSONException{
        ParticipantStats stats=new ParticipantStats();
        stats.setAssists(participantStats.getLong("assists"));
        stats.setChampLevel(participantStats.getInt("champLevel"));
        stats.setDeaths(participantStats.getLong("deaths"));
        stats.setGoldEarned(participantStats.getLong("goldEarned"));
        stats.setItem0(participantStats.getLong("item0"));
        stats.setItem1(participantStats.getLong("item1"));
        stats.setItem2(participantStats.getLong("item2"));
        stats.setItem3(participantStats.getLong("item3"));
        stats.setItem4(participantStats.getLong("item4"));
        stats.setItem5(participantStats.getLong("item5"));
        stats.setItem6(participantStats.getLong("item6"));
        stats.setKillSprees(participantStats.getLong("killingSprees"));
        stats.setKills(participantStats.getLong("kills"));
        stats.setMinionsKilled(participantStats.getLong("minionsKilled"));
        stats.setTotalDamageDealtToChampions(participantStats.getLong("totalDamageDealtToChampions"));
        stats.setTotalDamageTaken(participantStats.getLong("totalDamageTaken"));
        return stats;
    }
    public ParticipantIdentity populateParticipantIdentity(JSONObject participantIdentity) throws JSONException{
        ParticipantIdentity identity=new ParticipantIdentity();
        JSONObject jSONObject=participantIdentity.getJSONObject("player");
        
        
        Player player=new Player();
        player.setMatchHistoryUri(jSONObject.getString("matchHistoryUri"));
        player.setProfileIcon(jSONObject.getInt("profileIcon"));
        player.setSummonerName(jSONObject.getString("summonerName"));
        identity.setParticipantId(participantIdentity.getInt("participantId"));
        identity.setPlayer(player);
        return identity;
    }
    
    
    public SummonerDto populateSummonerDto(String name) throws JSONException, IOException{


        SummonerDto summonerDto = new SummonerDto();
        
        String response = Connection.sendGet("https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/"+name+"?api_key=018a4d88-bbb4-4578-aec5-8b3bf049bb12");
        JSONObject jObject0  = new JSONObject(response);
       

        JSONObject jObject = jObject0.getJSONObject(name.toLowerCase());
        summonerDto.setId(jObject.getLong("id"));
        summonerDto.setName(jObject.getString("name"));
        summonerDto.setProfileIconId(jObject.getInt("profileIconId"));
        summonerDto.setSummonerLevel(jObject.getLong("summonerLevel"));
        return summonerDto;
        
    }
    

    public PlayerStatsSummaryListDto populatePlayerStatsSummaryListDto(long summonerId) throws IOException, JSONException{

        
        PlayerStatsSummaryListDto playerStatsSummaryListDto = new PlayerStatsSummaryListDto();
        String response = Connection.sendGet("https://na.api.pvp.net/api/lol/na/v1.3/stats/by-summoner/" + summonerId + "/summary?season=SEASON4&api_key=018a4d88-bbb4-4578-aec5-8b3bf049bb12");
        JSONObject jObject = new JSONObject(response);
        
        List<PlayerStatsSummaryDto> playerStatsSummaryDtoList = new ArrayList<>();
        
        JSONArray jSONArray = jObject.getJSONArray("playerStatSummaries");
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            PlayerStatsSummaryDto dto = populatePlayerStatsSummaryDto(jSONObject);
            
            playerStatsSummaryDtoList.add(dto);
        }
        
        playerStatsSummaryListDto.setSummonerId(jObject.getLong("summonerId"));
        playerStatsSummaryListDto.setPlayerStatsSummaryDto(playerStatsSummaryDtoList);
        return playerStatsSummaryListDto;
    }
    

    public PlayerStatsSummaryDto populatePlayerStatsSummaryDto(JSONObject jSONObject) throws JSONException{

        PlayerStatsSummaryDto playerStatsSummaryDto = new PlayerStatsSummaryDto();
        //playerStatsSummaryDto.setLosses(jSONObject.getInt("losses"));
        playerStatsSummaryDto.setPlayerStatSummaryType(jSONObject.getString("playerStatSummaryType"));

        

        AggregatedStatsDto aggregatedStatsDto = populateAggregatedStatsDto(jSONObject.getJSONObject("aggregatedStats"));
        playerStatsSummaryDto.setAggregatedStatsDto(aggregatedStatsDto);


        playerStatsSummaryDto.setWins(jSONObject.getInt("wins"));
        
        return playerStatsSummaryDto;
    }
    

    public ChampionListDto getFreeToPlayChampionList() throws IOException, JSONException{


        ChampionListDto championListDto = new ChampionListDto();
        
        String response = Connection.sendGet("https://na.api.pvp.net/api/lol/na/v1.2/champion?freeToPlay=true&api_key=018a4d88-bbb4-4578-aec5-8b3bf049bb12");
        JSONObject jObject = new JSONObject(response);
        
        JSONArray jSONArray = jObject.getJSONArray("champions");
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            
            championListDto.getChampions().add(populateChampionDto(jSONObject));
        }
        return championListDto;
    }
    

    public ChampionDto populateChampionDto(JSONObject jObject) throws JSONException, IOException{

        ChampionDto championDto = new ChampionDto();
        
        championDto.setActive(jObject.getBoolean("active"));
        championDto.setBotEnabled(jObject.getBoolean("botEnabled"));
        championDto.setBotMmEnabled(jObject.getBoolean("botMmEnabled"));
        championDto.setFreeToPlay(jObject.getBoolean("freeToPlay"));
        championDto.setId(jObject.getLong("id"));
        championDto.setRankedPlayEnabled(jObject.getBoolean("rankedPlayEnabled"));
        championDto.setName(convertFromIdToName(jObject.getLong("id")));
        System.out.println(championDto.getName());
        return championDto;
    }

    public String convertFromIdToName(Long id) throws IOException, JSONException{
        
        FileReader fr=new FileReader("/Users/Apollowc/NetBeansProjects/LolProCn/web/resources/json/champion.json");
        char[] data=new char[5000000];
        fr.read(data);
         JSONObject jobj=new JSONObject(new String(data));
         JSONObject keys=jobj.getJSONObject("keys");
        return (String)keys.get(Long.toString(id));

    }
 
    public MasteryPagesDto getSummonerMasterPages(long summonerId) throws IOException, JSONException{
        MasteryPagesDto masterPagesDto=new MasteryPagesDto();
       String response = Connection.sendGet("https://na.api.pvp.net/api/lol/na/v1.4/summoner/"+summonerId+"/masteries?api_key=018a4d88-bbb4-4578-aec5-8b3bf049bb12");
        JSONObject jObject  = new JSONObject(response);
        JSONObject summoner=jObject.getJSONObject(Long.toString(summonerId));
        
       
        JSONArray pages=summoner.getJSONArray("pages");
        for(int i=0;i<pages.length();i++){
            JSONObject temp=pages.getJSONObject(i);
            masterPagesDto.getPages().add(populateMasteryPageDto(temp));
        }
        return masterPagesDto;

    }
    
    public MasteryPageDto populateMasteryPageDto(JSONObject jObject) throws JSONException{
        MasteryPageDto masteryPageDto=new MasteryPageDto();
        masteryPageDto.setId(jObject.getLong("id"));
        masteryPageDto.setCurrent(jObject.getBoolean("current"));
        masteryPageDto.setName(jObject.getString("name"));
        masteryPageDto.setMasteries(populateMasteryDto(jObject.getJSONArray("masteries")));
        return masteryPageDto;
    }
    
    public List<MasteryDto> populateMasteryDto(JSONArray masteries) throws JSONException{
        List<MasteryDto> list=new ArrayList<MasteryDto>(); 
        for(int i=0;i<masteries.length();i++){
            JSONObject temp=masteries.getJSONObject(i);
            MasteryDto masteryDto=new MasteryDto();
            masteryDto.setId(temp.getInt("id"));
            masteryDto.setRank(temp.getInt("rank"));
            list.add(masteryDto);
        }
        return list;
    }
    
    
    public AggregatedStatsDto populateAggregatedStatsDto(JSONObject jSONObject) throws JSONException{
        AggregatedStatsDto aggregatedStatsDto = new AggregatedStatsDto();
        if(jSONObject.isNull("averageAssists"))
            aggregatedStatsDto.setAverageAssists(0);
        else
            aggregatedStatsDto.setAverageAssists(jSONObject.getInt("averageAssists"));
        
        if(jSONObject.isNull("averageChampionsKilled"))
            aggregatedStatsDto.setAverageChampionsKilled(0);
        else
            aggregatedStatsDto.setAverageChampionsKilled(jSONObject.getInt("averageChampionsKilled"));
        
                if(jSONObject.isNull("averageCombatPlayerScore"))
            aggregatedStatsDto.setAverageCombatPlayerScore(0);
        else
            aggregatedStatsDto.setAverageCombatPlayerScore(jSONObject.getInt("averageCombatPlayerScore"));
            
                
        if(jSONObject.isNull("averageNumDeaths"))
            aggregatedStatsDto.setAverageNumDeaths(0);
        else
            aggregatedStatsDto.setAverageNumDeaths(jSONObject.getInt("averageNumDeaths"));
            
        if(jSONObject.isNull("maxChampionsKilled"))
            aggregatedStatsDto.setMaxChampionsKilled(0);
        else
            aggregatedStatsDto.setMaxChampionsKilled(jSONObject.getInt("maxChampionsKilled"));
        
        if(jSONObject.isNull("maxLargestKillingSpree"))
            aggregatedStatsDto.setMaxLargestKillingSpree(0);
        else
            aggregatedStatsDto.setMaxLargestKillingSpree(jSONObject.getInt("maxLargestKillingSpree"));
        
        if(jSONObject.isNull("maxNumDeaths"))
            aggregatedStatsDto.setMaxNumDeaths(0);
        else
            aggregatedStatsDto.setMaxNumDeaths(jSONObject.getInt("maxNumDeaths"));
        
        if(jSONObject.isNull("maxNumDeaths"))
            aggregatedStatsDto.setMaxTimePlayed(0);
        else
            aggregatedStatsDto.setMaxTimePlayed(jSONObject.getInt("maxNumDeaths"));
        
        if(jSONObject.isNull("totalAssists"))
            aggregatedStatsDto.setTotalAssists(0);
        else
            aggregatedStatsDto.setTotalAssists(jSONObject.getInt("totalAssists"));
        
        
        if(jSONObject.isNull("totalChampionKills"))
            aggregatedStatsDto.setTotalChampionKills(0);
        else
            aggregatedStatsDto.setTotalChampionKills(jSONObject.getInt("totalChampionKills"));
        
         if(jSONObject.isNull("totalMinionKills"))
            aggregatedStatsDto.setTotalMinionKills(0);
        else
            aggregatedStatsDto.setTotalMinionKills(jSONObject.getInt("totalMinionKills"));
         
                  if(jSONObject.isNull("totalNeutralMinionsKilled"))
            aggregatedStatsDto.setTotalNeutralMinionsKilled(0);
        else
            aggregatedStatsDto.setTotalNeutralMinionsKilled(jSONObject.getInt("totalNeutralMinionsKilled"));
         
                                    if(jSONObject.isNull("totalTurretsKilled"))
            aggregatedStatsDto.setTotalTurretsKilled(0);
        else
            aggregatedStatsDto.setTotalTurretsKilled(jSONObject.getInt("totalTurretsKilled"));
       
        
        return aggregatedStatsDto;
    }

    
    
    public RunePagesDto getRunePagesDto(long summonerId) throws IOException, JSONException {
        RunePagesDto runePagesDto = new RunePagesDto();
        
        String response = Connection.sendGet("https://na.api.pvp.net/api/lol/na/v1.4/summoner/" + summonerId + "/runes?api_key=018a4d88-bbb4-4578-aec5-8b3bf049bb12");
        JSONObject jObject = (new JSONObject(response)).getJSONObject(Long.toString(summonerId));
        
        JSONArray jSONArray = jObject.getJSONArray("pages");
        System.out.println("getrunePagesDto:"+jSONArray.length());
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            
            runePagesDto.getPages().add(populateRunePageDto(jSONObject));
        }
        
        runePagesDto.setSummonerId(jObject.getLong("summonerId"));
        
        return runePagesDto;
    }
    
    public RunePageDto populateRunePageDto(JSONObject jObject) throws JSONException {
        RunePageDto runePageDto = new RunePageDto();
        
        runePageDto.setCurrent(jObject.getBoolean("current"));
        runePageDto.setId(jObject.getLong("id"));
        runePageDto.setName(jObject.getString("name"));
        
        if(jObject.isNull("slots")==false){
            
        
        JSONArray jSONArray = jObject.getJSONArray("slots");
        System.out.println("populaterunePagesDto:"+jSONArray.length());
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            runePageDto.getSlots().add(populateRuneSlotDto(jSONObject));
        }
        }
        
        return runePageDto;
    }
    
    public RuneSlotDto populateRuneSlotDto(JSONObject jObject) throws JSONException {
        RuneSlotDto runeSlotDto = new RuneSlotDto();
        
        runeSlotDto.setRuneId(jObject.getInt("runeId"));
        runeSlotDto.setRuneSlotId(jObject.getInt("runeSlotId"));
        
        return runeSlotDto;
    }
    
}
