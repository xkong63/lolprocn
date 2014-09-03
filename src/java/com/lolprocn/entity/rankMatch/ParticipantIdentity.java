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
public class ParticipantIdentity {
    /*
    participantId	int	Participant ID
player	Player	Player information
    */
    int participantId;
    Player player;

    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    
}
