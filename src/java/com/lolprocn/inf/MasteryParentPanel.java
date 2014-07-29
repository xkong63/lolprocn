/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lolprocn.inf;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

/**
 *
 * @author Apollowc
 */
@ManagedBean
@Named(value = "masteryParentPanel")
@SessionScoped
public class MasteryParentPanel implements Serializable {

    /**
     * Creates a new instance of MasteryParentPanel
     */
    
    private List<MasteryChildPanel> masteryChildPanelGroup;
    public MasteryParentPanel() {
    }

    public List<MasteryChildPanel> getMasteryChildPanelGroup() {
        return masteryChildPanelGroup;
    }

    public void setMasteryChildPanelGroup(List<MasteryChildPanel> masteryChildPanelGroup) {
        this.masteryChildPanelGroup = masteryChildPanelGroup;
    }



    
    
}
