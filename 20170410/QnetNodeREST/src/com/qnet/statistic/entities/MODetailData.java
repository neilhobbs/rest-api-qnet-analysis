/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qnet.statistic.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nguyendl
 */
public class MODetailData {
public ArrayList<List> listDetailData = new ArrayList<List>();
    public ArrayList<List> getListDetailData() {
        return listDetailData;
    }

    public void setListDetailData(ArrayList<List> listDetailData) {
        this.listDetailData = listDetailData;
    }
    
}
