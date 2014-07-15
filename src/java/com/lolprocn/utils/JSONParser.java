/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lolprocn.utils;

import com.lolprocn.connection.Connection;
import com.lolprocn.entity.PlayerStatsSummaryDto;
import com.lolprocn.entity.PlayerStatsSummaryListDto;
import com.lolprocn.entity.Summoner;
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
    void populateSummonerByName(Summoner summoner) throws JSONException, IOException{
        
        String summonerName="";
        String response = Connection.sendGet("https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/"+summonerName+"?api_key=018a4d88-bbb4-4578-aec5-8b3bf049bb12");
        JSONObject jObject  = new JSONObject(response);
       
        
        summoner.setId(jObject.getLong("id"));
        summoner.setName(jObject.getString("name"));
        summoner.setProfileIconId(jObject.getInt("profileIconId"));
        summoner.setSummonerLevel(jObject.getLong("summonerLevel"));
        
    }
    
    void populatePlayerStatsSummaryListDto(PlayerStatsSummaryListDto playerStatsSummaryListDto) throws IOException, JSONException{
        long summonerId=0;
        String response = Connection.sendGet("https://na.api.pvp.net/api/lol/na/v1.3/stats/by-summoner/"+summonerId+"/summary?season=SEASON4&api_key=018a4d88-bbb4-4578-aec5-8b3bf049bb12");
        JSONObject jObject  = new JSONObject(response);
        
        List<PlayerStatsSummaryDto> playerStatsSummaryDtoList = new ArrayList<>();
        
        JSONArray jSONArray = jObject.getJSONArray("playerStatSummaries");
        for(int i=0; i<jSONArray.length(); i++){
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            PlayerStatsSummaryDto dto = new PlayerStatsSummaryDto();
            populatePlayerStatsSummaryDto(dto, jSONObject);
            playerStatsSummaryDtoList.add(dto);
        }
        
        playerStatsSummaryListDto.setSummonerId(jObject.getLong("summonerId"));
        playerStatsSummaryListDto.setPlayerStatsSummaryDto(playerStatsSummaryDtoList);
    }
    
    void populatePlayerStatsSummaryDto(PlayerStatsSummaryDto playerStatsSummaryDto, JSONObject jSONObject) throws JSONException{
        
        playerStatsSummaryDto.setLosses(jSONObject.getInt("losses"));
        playerStatsSummaryDto.setPlayerStatSummaryType(jSONObject.getString("playerStatSummaryType"));
        
        playerStatsSummaryDto.setWins(jSONObject.getInt("wins"));
    }
}
