/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lolprocn.entity.rankMatch;

/**
 *
 * @author Apollowc
 */
public class Player {
    /*
matchHistoryUri	string	Match history URI
profileIcon	int	Profile icon ID
summonerName	string	Summoner name
    
    */
    
    String matchHistoryUri;
    int profileIcon;
    String summonerName;

    public String getMatchHistoryUri() {
        return matchHistoryUri;
    }

    public void setMatchHistoryUri(String matchHistoryUri) {
        this.matchHistoryUri = matchHistoryUri;
    }

    public int getProfileIcon() {
        return profileIcon;
    }

    public void setProfileIcon(int profileIcon) {
        this.profileIcon = profileIcon;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    
    
    
}
