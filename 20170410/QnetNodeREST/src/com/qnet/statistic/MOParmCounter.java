/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qnet.statistic;

import com.qnet.statistic.entities.MOCountResult;
import com.qnet.statistic.entities.MOConstants;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nguyendl
 */
class MOParmCounter implements IMOParmHandler {

    ConcurrentHashMap<String, Map<String, Map<String, Integer>>> data;

    public MOParmCounter() {

    }

    public void onFinish(String fileName) {
        StringBuilder strOut = new StringBuilder();
        for (String moName : data.keySet()) {
            Map<String, Map<String, Integer>> moObject = data.get(moName);
            for (String parmName : moObject.keySet()) {

                Map<String, Integer> parmObject = moObject.get(parmName);
                for (String value : parmObject.keySet()) {
                    System.out.println(moName + "," + parmName + "," + value + "," + parmObject.get(value));
                    strOut.append(moName + "," + parmName + "," + value + "," + parmObject.get(value) + "\n");
                }
            }
        }
        
        try {
            String dirFile = MOConstants.outputFilePath +  "RESULT-" + fileName;
            printOut(dirFile, strOut);
            
        } catch (IOException ex) {
            Logger.getLogger(MOParmCounter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }


    public void init() {
        data = new ConcurrentHashMap<String, Map<String, Map<String, Integer>>>();
    }

    public synchronized void onMOParm(String moName, String parmName, String value) {
        if (!data.containsKey(moName)) {
            data.put(moName, new ConcurrentHashMap<String, Map<String, Integer>>());
        }
        if (!data.get(moName).containsKey(parmName)) {
            data.get(moName).put(parmName, new ConcurrentHashMap<String, Integer>());
        }

        Map<String, Integer> cachedValues = data.get(moName).get(parmName);
        int count = 0;
        if (cachedValues.containsKey(value)) {
            count = cachedValues.get(value);
        }
        count += 1;
        cachedValues.put(value, count);
    }

    @Override
    public void printOut(String outputFile, StringBuilder str) throws FileNotFoundException, IOException {
        FileWriter fw;
        try {
            fw = new FileWriter(outputFile);
        } catch (IOException exc) {
            System.out.println();
            return;
        }
        fw.write(str.toString());
        fw.close();
    }

}
