/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lolprocn.inf;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Apollowc
 */
@ManagedBean
@Named(value = "panelUpdate")
@RequestScoped
public class PanelUpdate {

    /**
     * Creates a new instance of PanelUpdate
     */
    boolean render_stat=true;
    boolean render_mastery=false;
    boolean render_rune=false;
    boolean render_match=false;
    public PanelUpdate() {
        
    }

    public boolean isRender_stat() {
        return render_stat;
    }

    public void setRender_stat(boolean render_stat) {
        this.render_stat = render_stat;
    }

    public boolean isRender_mastery() {
        return render_mastery;
    }

    public void setRender_mastery(boolean render_mastery) {
        this.render_mastery = render_mastery;
    }

    public boolean isRender_rune() {
        return render_rune;
    }

    public void setRender_rune(boolean render_rune) {
        this.render_rune = render_rune;
    }

    public boolean isRender_match() {
        return render_match;
    }

    public void setRender_match(boolean render_match) {
        this.render_match = render_match;
    }

    
    public void mastery_display(){
        render_mastery=true;
        render_stat=false;
        render_rune=false;
        render_match=false;
    }
    
      public void rune_display(){
        render_rune=true;
        render_stat=false;
        render_mastery=false;
        render_match=false;
    }
      
            public void stat_display(){
        render_rune=false;
        render_stat=true;
        render_mastery=false;
        render_match=false;
    }
    
                public void match_display(){
        render_rune=false;
        render_stat=false;
        render_mastery=false;
        render_match=true;
    }
}
