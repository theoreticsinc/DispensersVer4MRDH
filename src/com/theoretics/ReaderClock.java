/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theoretics;

import com.pi4j.system.SystemInfo;
import com.theoretics.DataBaseHandler;
import com.theoretics.SystemStatus;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Theoretics Inc
 */
public class ReaderClock implements Runnable {

    ArrayList<String> cards;
    //String serverIP = "192.168.1.10";
    DataBaseHandler dbh = new DataBaseHandler(CONSTANTS.serverIP);
    static Logger log = LogManager.getLogger(ReaderClock.class.getName());
    //String entranceID = "Entry Zone 2";
    String cardFromReader = "";
    String strUID = "";
    String prevUID = "0";
    

    String text = null;
    String cardUID = null;

    public ReaderClock(ArrayList<String> cards) {
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
            while (line != null) {
                System.out.println(line);
                line = in.readLine();
            }
            // Close our streams
            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Scanner scan = new Scanner(System.in);
                System.out.println("!reader!");

//            try {
//                comms2POS("DISPENSER,CDF07701, , ");
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
                strUID = "";
                try {
                    if (null == scan) {
                        System.out.println("!scan == null!");
                    } else {
                        text = scan.nextLine();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
if (null == text) {
    System.out.println("!text == null!");
}
                if (null != text) {
                    try {
                        System.out.println("RAW: " + text);
                        cardUID = Long.toHexString(Long.parseLong(text));
                        if (text.startsWith("0")) {
                            cardUID = "0" + cardUID;
                        } else if (text.startsWith("00")) {
                            cardUID = "00" + cardUID;
                        } else if (text.startsWith("000")) {
                            cardUID = "000" + cardUID;
                        } else if (text.startsWith("0000")) {
                            cardUID = "0000" + cardUID;
                        } else if (text.startsWith("00000")) {
                            cardUID = "00000" + cardUID;
                        } else if (text.startsWith("000000")) {
                            cardUID = "000000" + cardUID;
                        }
                        //cardUID = Integer.toHexString(Integer.parseInt(text));
                        cardUID = cardUID.toUpperCase();
                        strUID = cardUID.substring(6, 8) + cardUID.substring(4, 6) + cardUID.substring(2, 4) + cardUID.substring(0, 2);
                        System.out.println("UID: " + cardUID.substring(6, 8) + cardUID.substring(4, 6) + cardUID.substring(2, 4) + cardUID.substring(0, 2));
                    } catch (Exception ex) {
                        System.err.println("Card Conversion: " + ex);
                    }
                    //System.out.println("" + stats);

                    try {
                        if (prevUID.compareToIgnoreCase(strUID) != 0) {
                            //Uncomment Below to disable Read same Card
//                        prevUID = strUID;

                            System.out.println("Card Read UID:" + strUID.substring(0, 8));
                            cardFromReader = strUID.substring(0, 8).toUpperCase();
//
                            if (cardFromReader.compareToIgnoreCase("") != 0) {
                                cards.add(cardFromReader);
//
//                        //byte[] buffer2 = {0x2E};
//                        //comPort.writeBytes(buffer2, 1);
                            }

                            //led1.pulse(1250, true);
                            System.out.println("LED Open!");
                            //led2.pulse(1250, true);

                            // turn on gpio pin1 #01 for 1 second and then off
                            //System.out.println("--> GPIO state should be: ON for only 3 second");
                            // set second argument to 'true' use a blocking call
//                    c.showWelcome(700, false);
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                System.out.println("EOL");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
