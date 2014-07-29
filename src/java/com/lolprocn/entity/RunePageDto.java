/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lolprocn.entity;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author alan
 */
public class RunePageDto {
    boolean current;
    long id;
    String name;
    Set<RuneSlotDto> slots = new HashSet<>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<RuneSlotDto> getSlots() {
        return slots;
    }

    public void setSlots(Set<RuneSlotDto> slots) {
        this.slots = slots;
    }


    
}
