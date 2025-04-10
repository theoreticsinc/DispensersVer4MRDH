/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theoretics;

import com.theoretics.DataBaseHandler;
import com.theoretics.SystemStatus;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Theoretics Inc
 */
public class NetworkClock implements Runnable {

    ArrayList<String> cards;
    //String serverIP = "192.168.1.10";
    DataBaseHandler dbh = new DataBaseHandler(CONSTANTS.serverIP);
    static Logger log = LogManager.getLogger(NetworkClock.class.getName());
    //String entranceID = "Entry Zone 2";
    SystemStatus ss = new SystemStatus();
    String serverTime = "";

    public NetworkClock(ArrayList<String> cards) {
        this.cards = cards;
    }

    private void comms2POS(String messageOut) {

        //System.out.println( "Loading contents of URL: " + POSserver );
        try {
            // Connect to the server
            Socket socket = new Socket(CONSTANTS.POSserver, CONSTANTS.port);

            // Create input and output streams to read from and write to the server
            PrintStream out = new PrintStream(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Follow the HTTP protocol of GET <path> HTTP/1.0 followed by an empty line
            out.println(messageOut);
            out.println();

            // Read data from the server until we finish reading the document
            String line = in.readLine();
            while (line != null && in != null) {
                            System.out.println(line);
                line = in.readLine();
            }
            // Close our streams
            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
////            System.out.println("No POS Server available to receive messages");
//            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                boolean online = ss.checkPING(CONSTANTS.serverIP);//LINUX USE ONLY - also check your root password
                //cards.add("ABCD123");
                if (cards.isEmpty() == false) {
                    String cardFromReader = cards.get(0);
                    if (online == true) {
                        //System.out.print("ONLINE ");
                        //System.out.print("`/ ");
                        //System.out.println("✓");
                        //SAVE Card to DATABASE
                        boolean isValid = false;
                        boolean isUpdated = false;
                        //boolean alreadyExists = dbh.findCGHCard(cardFromReader);
                        boolean alreadyExists = dbh.findEntranceCard(cardFromReader);
                        if (alreadyExists) {
                            isUpdated = dbh.updateParkerDB(cardFromReader, "");
                            //isUpdated = dbh.updateParkerDB(cardFromReader, "");
//                            isUpdated = dbh.updateParkerDB(cardFromReader, "");
                            System.out.println(cardFromReader + " isUpdated " + isUpdated);
                            System.out.println(cardFromReader + " isUpdated " + isUpdated);
                            //comms2POS("Dispenser, card number:" + cardFromReader + " , ");
                            dbh.eraseEXTCRD(cardFromReader);
                            cards.remove(0);
                        } else {
                            //isValid = dbh.writeCGHEntryWithPix(CONSTANTS.entranceID, cardFromReader, "R", "");
                            isValid = dbh.saveParkerDB(CONSTANTS.serverIP, "P01", "EN01", cardFromReader, "", "R", false);
                            System.out.println(cardFromReader + " isValid:" + isValid);
                            System.out.println(cardFromReader + " isValid:" + isValid);
                            if (isValid) {
                                //comms2POS("Dispenser, card number:" + cardFromReader + " , ");
                                cards.remove(0);
                            }
                            serverTime = dbh.getServerTime();

                            try {
                                comms2POS("DISPENSER,card number:"+cardFromReader+",please continue,"+serverTime);
                                //comms2POS("DISPENSER," + cardFromReader + "," + serverTime + ", ");
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            System.out.println("Time On Card*" + cardFromReader + "* :: " + serverTime);
                            dbh.eraseEXTCRD(cardFromReader);
                            
                        }

                    } else if (online == false) {
                        System.out.println("OFFLINE");
                        System.out.print("-");
                    }
                    Thread.sleep(100);
                    //ss.updateTimeOnChip(serverTime);
                    //System.out.println("NETWORK");
                    //resetAdmin();
                    //Thread.sleep(2000);
                } else {
                    Thread.yield();
                    Thread.sleep(200);
                }
                Thread.sleep(200);
                //System.out.println(".");
            } catch (Exception ex) {
                //System.out.println(ex.getMessage());
            }
//            ss.checkTemp();
            
        }

    }

}
