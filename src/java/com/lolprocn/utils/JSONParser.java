/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lolprocn.utils;

import com.lolprocn.connection.Connection;
import com.lolprocn.entity.ChampionDto;
import com.lolprocn.entity.ChampionListDto;
import com.lolprocn.entity.PlayerStatsSummaryDto;
import com.lolprocn.entity.PlayerStatsSummaryListDto;
import com.lolprocn.entity.SummonerDto;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 *
 * @author xkong63
 */
public class JSONParser {
    SummonerDto populateSummonerDto(String name) throws JSONException, IOException{
        SummonerDto summonerDto = new SummonerDto();
        
        String response = Connection.sendGet("https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/"+name+"?api_key=018a4d88-bbb4-4578-aec5-8b3bf049bb12");
        JSONObject jObject  = new JSONObject(response);
       
        
        summonerDto.setId(jObject.getLong("id"));
        summonerDto.setName(jObject.getString("name"));
        summonerDto.setProfileIconId(jObject.getInt("profileIconId"));
        summonerDto.setSummonerLevel(jObject.getLong("summonerLevel"));
        return summonerDto;
        
    }
    
    PlayerStatsSummaryListDto populatePlayerStatsSummaryListDto(long summonerId) throws IOException, JSONException{
        
        PlayerStatsSummaryListDto playerStatsSummaryListDto = new PlayerStatsSummaryListDto();
        String response = Connection.sendGet("https://na.api.pvp.net/api/lol/na/v1.3/stats/by-summoner/"+summonerId+"/summary?season=SEASON4&api_key=018a4d88-bbb4-4578-aec5-8b3bf049bb12");
        JSONObject jObject  = new JSONObject(response);
        
        List<PlayerStatsSummaryDto> playerStatsSummaryDtoList = new ArrayList<>();
        
        JSONArray jSONArray = jObject.getJSONArray("playerStatSummaries");
        for(int i=0; i<jSONArray.length(); i++){
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            PlayerStatsSummaryDto dto = populatePlayerStatsSummaryDto(jSONObject);
            
            playerStatsSummaryDtoList.add(dto);
        }
        
        playerStatsSummaryListDto.setSummonerId(jObject.getLong("summonerId"));
        playerStatsSummaryListDto.setPlayerStatsSummaryDto(playerStatsSummaryDtoList);
        return playerStatsSummaryListDto;
    }
    
    PlayerStatsSummaryDto populatePlayerStatsSummaryDto(JSONObject jSONObject) throws JSONException{
        PlayerStatsSummaryDto playerStatsSummaryDto = new PlayerStatsSummaryDto();
        playerStatsSummaryDto.setLosses(jSONObject.getInt("losses"));
        playerStatsSummaryDto.setPlayerStatSummaryType(jSONObject.getString("playerStatSummaryType"));
        
        playerStatsSummaryDto.setWins(jSONObject.getInt("wins"));
        return playerStatsSummaryDto;
    }
    
    ChampionListDto getFreeToPlayChampionList() throws IOException, JSONException{
        ChampionListDto championListDto = new ChampionListDto();
              
        String response = Connection.sendGet("https://na.api.pvp.net/api/lol/na/v1.2/champion?freeToPlay=true&api_key=018a4d88-bbb4-4578-aec5-8b3bf049bb12");
        JSONObject jObject  = new JSONObject(response);
        
        JSONArray jSONArray = jObject.getJSONArray("champions");
        for(int i=0; i<jSONArray.length(); i++){
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            
            championListDto.getChampions().add(populateChampionDto(jSONObject));
        }
        return championListDto;
    }
    
    ChampionDto populateChampionDto(JSONObject jObject) throws JSONException{
        ChampionDto championDto = new ChampionDto();
        
        championDto.setActive(jObject.getBoolean("active"));
        championDto.setBotEnabled(jObject.getBoolean("botEnabled"));
        championDto.setBotMmEnabled(jObject.getBoolean("botMmEnabled"));
        championDto.setFreeToPlay(jObject.getBoolean("freeToPlay"));
        championDto.setId(jObject.getLong("id"));
        championDto.setRankedPlayEnabled(jObject.getBoolean("rankedPlayEnabled"));
        return championDto;
    }
}
