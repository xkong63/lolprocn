/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lolprocn.entity;

import java.util.List;

/**
 *
 * @author alan
 */
public class MasteryPageDto {
    boolean current;
    long id;
    List<MasteryDto> masteries;

    String name;
    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<MasteryDto> getMasteries() {
        return masteries;
    }

    public void setMasteries(List<MasteryDto> masteries) {
        this.masteries = masteries;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
