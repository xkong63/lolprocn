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
@Named(value = "runeParentPanel")
@SessionScoped
public class RuneParentPanel implements Serializable {

    /**
     * Creates a new instance of runeParentPanel
     */
    
    private List<RuneChildPanel> runeChildPanelGroup;
    public RuneParentPanel() {
    }

    public List<RuneChildPanel> getRuneChildPanelGroup() {
        return runeChildPanelGroup;
    }

    public void setRuneChildPanelGroup(List<RuneChildPanel> runeChildPanelGroup) {
        this.runeChildPanelGroup = runeChildPanelGroup;
    }
    
    
    
}
