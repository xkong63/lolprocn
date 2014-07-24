/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lolprocn.entity;

import java.util.Set;

/**
 *
 * @author alan
 */
public class MasteryPagesDto {
    
    Set<MasteryPageDto> pages;
    long summonerId;

    public Set<MasteryPageDto> getPages() {
        return pages;
    }

    public void setPages(Set<MasteryPageDto> pages) {
        this.pages = pages;
    }

    public long getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(long summonerId) {
        this.summonerId = summonerId;
    }
    
    
}
