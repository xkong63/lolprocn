/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lolprocn.entity.rankMatch;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Apollowc
 */
public class PlayerHistory {
   List<MatchSummary> matches=new ArrayList<MatchSummary>();

   
    public List<MatchSummary> getMatches() {
        return matches;
    }

    public void setMatches(List<MatchSummary> matches) {
        this.matches = matches;
    }
   
   
}
