/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lolprocn.inf;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Apollowc
 */
@ManagedBean
public class RuneChildPanel implements Serializable {

    /**
     * Creates a new instance of RuneChildPanel
     */
    private String pageName;
    private final String PATH="./resources/images/bundle/4.12.2/img/rune/";
    private String[] runeVal;
    public RuneChildPanel() {
        runeVal=new String[]{PATH+"8001.png"};
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String[] getRuneVal() {
        return runeVal;
    }

    public void setRuneVal(String[] runeVal) {
        this.runeVal = runeVal;
    }
    
    
    
}
