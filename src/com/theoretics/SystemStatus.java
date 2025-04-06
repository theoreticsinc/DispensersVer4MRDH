/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theoretics;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Administrator Combine Exit with Entrance - must have ping and online
 * checking
 *
 */
public class SystemStatus {

    static Logger log = LogManager.getLogger(SystemStatus.class.getName());

    public boolean checkPING(String ip)
    {
        return true;
    }
    
    public boolean checkPING2(String ip) {
        //System.out.println(inputLine);        
        boolean status;
        try {
            String pingCmd = "ping " + ip + "";
            System.out.println(pingCmd);
            Runtime r = Runtime.getRuntime();
            Process p = r.exec(pingCmd);

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String inputLine;
            inputLine = in.readLine(); //LINUX ONLY - needs the second line as the result of the ping
            //System.out.println(inputLine);
            inputLine = in.readLine();
            //System.out.println(inputLine);
            inputLine = in.readLine();
            //System.out.println(inputLine);
            if (inputLine != null) {
                //System.out.println(inputLine);
                if (inputLine.compareTo("") == 0) {
                    //System.out.println(inputLine);
                    //System.out.println("Offline");
                    System.out.print("X");
                    in.close();
                    status = false;
                    return status;
                } else if (inputLine != null && inputLine.contains("Unreachable")) {
                    //System.out.println(inputLine);
                    //System.out.println("Offline");
                    System.out.print("ip::" + ip + " is Unreachable  ");
                    in.close();
                    status = false;
                    return status;

                } else if (inputLine != null && inputLine.compareToIgnoreCase("Request timed out.") == 0) {
                    //System.out.println(inputLine);
                    //System.out.println("Offline");
                    System.out.print("X");
                    in.close();
                    status = false;
                    return status;
                } else if (inputLine != null && inputLine.substring(0, 1).compareToIgnoreCase("-") == 0) {
                    //System.out.println(inputLine);
                    //System.out.println("Offline");
                    System.out.print("X");
                    in.close();
                    status = false;
                    return status;
                }
                System.out.println("ONLINE");
                //System.out.println(inputLine);
                System.out.println("✓");
                in.close();
                status = true;
                return status;
            }
            in.close();
            status = false;
            return status;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;

    }

    public boolean checkTemp() {
        //System.out.println(inputLine);        
        boolean status;
        try {
            String pingCmd = "vcgencmd measure_temp";

            Runtime r = Runtime.getRuntime();
            Process p = r.exec(pingCmd);

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String inputLine;
            inputLine = in.readLine(); //LINUX ONLY - needs the second line as the result of the ping
            System.out.println(inputLine);

            in.close();
            status = false;
            return status;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;

    }

    public boolean checkOnline() {
        //boolean found = rfh.FindFileFolder("/SYSTEMS/", "online.aaa");
        return true;
    }

    public static void main(String args[]) throws Exception {
        SystemStatus ss = new SystemStatus();
        ss.checkPING("192.168.1.80");
        ss.checkTemp();
        ss.updateTimeOnChip("2021-09-29 10:00:50");

        while(true) {
            
        }
        //System.out.println(ss.checkOnline());
        //ss.updateServerCRDPLT();
    }

    public boolean updateTimeOnChip(String serverTime) {
        boolean status = false;
        try {
            if (serverTime.length() >= 19) {
            System.out.println("serverTime = " + serverTime.substring(0, 19));        
            String pingCmd = "/home/pi/clocker.py " + serverTime.substring(0, 19);
            //String pingCmd = "date";

            Runtime r = Runtime.getRuntime();
            Process p = r.exec(pingCmd);

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String inputLine;
            inputLine = in.readLine(); //LINUX ONLY - needs the second line as the result of the ping
            System.out.println("RES:" + inputLine);

            in.close();
            status = false;
            }
            return status;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;

    }

}
