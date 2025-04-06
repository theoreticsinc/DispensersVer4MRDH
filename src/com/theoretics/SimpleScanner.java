/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theoretics;

import java.util.Scanner;

/**
 *
 * @author Theoretics
 */
public class SimpleScanner {

public static void main(String[] args)  {
    while (true) {
        SimpleScanner.testBlackReader();
    }
}
    private static void testBlackReader() {
        Scanner scan = null;

        String text = null;
        String cardUID = null;
        
        String strUID = "";
            try {                
                scan = new Scanner(System.in);
                if(scan.hasNextLong()){
                    text = scan.nextLine();
                }
            } catch (Exception ex) {
            }
            if (null != text) {
                try {
                    System.out.println("RAW: " + text);
                    //cardUID = Long.toHexString(Long.parseLong(text));
                    cardUID = Long.toHexString(Long.parseLong(text));
                    if (cardUID.length() == 7) {
                        cardUID = "0" + cardUID;
                    } else if (cardUID.length() == 6) {
                        cardUID = "00" + cardUID;
                    } else if (cardUID.length() == 5) {
                        cardUID = "000" + cardUID;
                    } else if (cardUID.length() == 4) {
                        cardUID = "0000" + cardUID;
                    } else if (cardUID.length() == 3) {
                        cardUID = "00000" + cardUID;
                    } else if (cardUID.length() == 2) {
                        cardUID = "000000" + cardUID;
                    }
                    //0892609774
                    System.out.println("RAW CARDUID: " + cardUID);
                    /*
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
                    */
                    //cardUID = Integer.toHexString(Integer.parseInt(text));
                    cardUID = cardUID.toUpperCase();
                    
                    strUID = cardUID.substring(6, 8) + cardUID.substring(4, 6) + cardUID.substring(2, 4) + cardUID.substring(0, 2);
                    try {
                    //comms2POS("DISPENSER,"+strUID+", , ");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    System.out.println("UID: " + cardUID.substring(6, 8) + cardUID.substring(4, 6) + cardUID.substring(2, 4) + cardUID.substring(0, 2));
                } catch (Exception ex) {
                    System.err.println("Card Conversion: " + ex);
                }
                //System.out.println("" + stats);
            }
    }
}
