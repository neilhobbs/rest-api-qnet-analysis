/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qnet.statistic;

/**
 *
 * @author nguyendl
 */
public interface IMOParmHandler {

    public void onFinish(String resultFileName);

    public void init();

    public void onMOParm(String moName, String parmName, String value);
    
    public void printOut(String outputFile, StringBuilder str) throws Exception;
    
}
