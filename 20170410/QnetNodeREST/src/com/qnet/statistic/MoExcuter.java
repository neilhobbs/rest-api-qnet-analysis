/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qnet.statistic;

import com.google.common.base.Splitter;
import com.qnet.statistic.entities.MOConstants;
import com.qnet.statistic.entities.MOParmMeta;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nguyendl
 */
public class MoExcuter implements Runnable{
    private final String inPutFile;
    IMOParmHandler handler;
    
    public MoExcuter(String inPutFile) {
        this.inPutFile = inPutFile;
    }

    public void run() {
        MoExcuter moex = new MoExcuter(inPutFile);
        moex.handler = new MOParmCounter();
        try {   
            moex.execute();
        } catch (Exception ex) {
            Logger.getLogger(MoExcuter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  public void execute() throws Exception {
        BufferedReader br = null;
        try {
            handler.init();
            System.out.println("input = " + inPutFile);
            File file = new File(inPutFile);
            if (!file.exists()) {
                throw new Exception("not found " + inPutFile);
            }

            //read file ...
            br = new BufferedReader(new FileReader(inPutFile));
            while (true) {

                String data = br.readLine();
                if (data == null) {
                    break;
                }

                String metaData = br.readLine();

                process(data, metaData);
            }
            int indexStr = inPutFile.lastIndexOf("\\");
            String fileName = inPutFile.substring(indexStr + 1);
            handler.onFinish(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void process(String data, String metaData) throws FileNotFoundException, IOException {
        MOParmMeta moParmMeta = createMOParmMeta(metaData);
        String[] elements = data.split(MOConstants.ITEM_SEPARATOR);
        for (int j = 0; j < elements.length; j++) {
            List<String> values = Splitter.on(MOConstants.LIST_SEPARATOR).splitToList(elements[j]);
            for (int i = 0; i < values.size(); i++) {
                handler.onMOParm(moParmMeta.getMoName(), moParmMeta.getParms().get(i), values.get(i));
            }
        }
    }

    private MOParmMeta createMOParmMeta(String metaData) {
        String[] elements = metaData.split(MOConstants.ITEM_SEPARATOR);
        String moName = elements[0];
        String parmStr = elements[3];

        MOParmMeta moParmMeta = new MOParmMeta();
        moParmMeta.setMoName(moName);
        moParmMeta.setParms(Splitter.on(MOConstants.LIST_SEPARATOR).splitToList(parmStr));
        return moParmMeta;
    }
}
