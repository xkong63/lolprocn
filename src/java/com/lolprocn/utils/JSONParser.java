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
import java.io.IOException;
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
    

    public ChampionDto populateChampionDto(JSONObject jObject) throws JSONException{

    

        ChampionDto championDto = new ChampionDto();
        
        championDto.setActive(jObject.getBoolean("active"));
        championDto.setBotEnabled(jObject.getBoolean("botEnabled"));
        championDto.setBotMmEnabled(jObject.getBoolean("botMmEnabled"));
        championDto.setFreeToPlay(jObject.getBoolean("freeToPlay"));
        championDto.setId(jObject.getLong("id"));
        championDto.setRankedPlayEnabled(jObject.getBoolean("rankedPlayEnabled"));
        return championDto;
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
        JSONObject jObject = (new JSONObject(response)).getJSONObject("summonerId");
        
        JSONArray jSONArray = jObject.getJSONArray("pages");
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
        
        JSONArray jSONArray = jObject.getJSONArray("slots");
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            runePageDto.getSlots().add(populateRuneSlotDto(jSONObject));
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
