/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lolprocn.entity.rankMatch;

import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Apollowc
 */
public class MatchSummary {
    /*
    mapId	int	Match map ID
matchCreation	long	Match creation time
matchDuration	long	Match duration
matchId	long	ID of the match
matchVersion	string	Match version
participantIdentities	List[ParticipantIdentity]	Participant identity information
participants	List[Participant]	Participant information
queueType	string	Match queue type (legal values: CUSTOM, NORMAL_5x5_BLIND, RANKED_SOLO_5x5, RANKED_PREMADE_5x5, BOT_5x5, NORMAL_3x3, RANKED_PREMADE_3x3, NORMAL_5x5_DRAFT, ODIN_5x5_BLIND, ODIN_5x5_DRAFT, BOT_ODIN_5x5, BOT_5x5_INTRO, BOT_5x5_BEGINNER, BOT_5x5_INTERMEDIATE, RANKED_TEAM_3x3, RANKED_TEAM_5x5, BOT_TT_3x3, GROUP_FINDER_5x5, ARAM_5x5, ONEFORALL_5x5, FIRSTBLOOD_1x1, FIRSTBLOOD_2x2, SR_6x6, URF_5x5, BOT_URF_5x5, NIGHTMARE_BOT_5x5_RANK1, NIGHTMARE_BOT_5x5_RANK2, NIGHTMARE_BOT_5x5_RANK5)
region	string	Region where the match was played
season	string	Season match was played (legal values: PRESEASON3, SEASON3, PRESEASON2014, SEASON2014)
    */
    int mapId;
    BigInteger matchCreation;
    BigInteger matchDuration;
    long matchId;
    String matchVersion;
    List<ParticipantIdentity> participantIdentities;
    List<Participant> participants;
    String region;
    String season;
    String queueType;

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public BigInteger getMatchCreation() {
        return matchCreation;
    }

    public void setMatchCreation(BigInteger matchCreation) {
        this.matchCreation = matchCreation;
    }

    public BigInteger getMatchDuration() {
        return matchDuration;
    }

    public void setMatchDuration(BigInteger matchDuration) {
        this.matchDuration = matchDuration;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public String getMatchVersion() {
        return matchVersion;
    }

    public void setMatchVersion(String matchVersion) {
        this.matchVersion = matchVersion;
    }

    public List<ParticipantIdentity> getParticipantIdentities() {
        return participantIdentities;
    }

    public void setParticipantIdentities(List<ParticipantIdentity> participantIdentities) {
        this.participantIdentities = participantIdentities;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }
    
    
    
}
