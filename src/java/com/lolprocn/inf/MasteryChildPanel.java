/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lolprocn.inf;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

/**
 *
 * @author Apollowc
 */
@ManagedBean
public class MasteryChildPanel implements Serializable {

    /**
     * Creates a new instance of MasteryPanel
     */

    private final String PATH = "./resources/images/bundle/4.12.2/img/mastery/";
    private String[][] field1_masteryVal;
    private String[][] field2_masteryVal;
    private String[][] field3_masteryVal;

    private String pageName;
    public MasteryChildPanel() {
    field1_masteryVal =  new String[][]{
        {PATH + "gray_4111.png", PATH + "gray_4112.png", PATH + "gray_4113.png", PATH + "gray_4114.png"},
        {PATH + "gray_4121.png", PATH + "gray_4122.png", PATH + "gray_4123.png", PATH + "gray_4124.png"},
        {PATH + "gray_4131.png", PATH + "gray_4132.png", PATH + "gray_4133.png", PATH + "gray_4134.png"},
        {PATH + "gray_4141.png", PATH + "gray_4142.png", PATH + "gray_4143.png", PATH + "gray_4144.png"},
        {PATH + "gray_4151.png", PATH + "gray_4152.png", PATH + "gray_4153.png", PATH + "gray_4154.png"},
        {PATH + "gray_4161.png", PATH + "gray_4162.png", PATH + "gray_4163.png", PATH + "gray_4164.png"}};
    field2_masteryVal =  new String[][]{
        {PATH + "gray_4211.png", PATH + "gray_4212.png", PATH + "gray_4213.png", PATH + "gray_4214.png"},
        {PATH + "gray_4221.png", PATH + "gray_4222.png", PATH + "gray_4223.png", PATH + "gray_4224.png"},
        {PATH + "gray_4231.png", PATH + "gray_4232.png", PATH + "gray_4233.png", PATH + "gray_4234.png"},
        {PATH + "gray_4241.png", PATH + "gray_4242.png", PATH + "gray_4243.png", PATH + "gray_4244.png"},
        {PATH + "gray_4251.png", PATH + "gray_4252.png", PATH + "gray_4253.png", PATH + "gray_4254.png"},
        {PATH + "gray_4261.png", PATH + "gray_4262.png", PATH + "gray_4263.png", PATH + "gray_4264.png"}};
    field3_masteryVal =  new String[][]{
        {PATH + "gray_4311.png", PATH + "gray_4312.png", PATH + "gray_4313.png", PATH + "gray_4314.png"},
        {PATH + "gray_4321.png", PATH + "gray_4322.png", PATH + "gray_4323.png", PATH + "gray_4324.png"},
        {PATH + "gray_4331.png", PATH + "gray_4332.png", PATH + "gray_4333.png", PATH + "gray_4334.png"},
        {PATH + "gray_4341.png", PATH + "gray_4342.png", PATH + "gray_4343.png", PATH + "gray_4344.png"},
        {PATH + "gray_4351.png", PATH + "gray_4352.png", PATH + "gray_4353.png", PATH + "gray_4354.png"},
        {PATH + "gray_4361.png", PATH + "gray_4362.png", PATH + "gray_4363.png", PATH + "gray_4364.png"}};
    }


    public String[][] getField1_masteryVal() {
        return field1_masteryVal;
    }

    public void setField1_masteryVal(String[][] field1_masteryVal) {
        this.field1_masteryVal = field1_masteryVal;
    }

    public String[][] getField2_masteryVal() {
        return field2_masteryVal;
    }

    public void setField2_masteryVal(String[][] field2_masteryVal) {
        this.field2_masteryVal = field2_masteryVal;
    }

    public String[][] getField3_masteryVal() {
        return field3_masteryVal;
    }

    public void setField3_masteryVal(String[][] field3_masteryVal) {
        this.field3_masteryVal = field3_masteryVal;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPATH() {
        return PATH;
    }

    
}
