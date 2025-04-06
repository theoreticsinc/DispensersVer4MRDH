/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theoretics;

import com.google.gson.Gson;

/**
 *
 * @author Theoretics
 */
public class VIPs {
    public String brand = null;
    public int    doors = 0;
    
    public static void main( String args[]) {
        Gson gson = new Gson();

        String json = "{\"brand\":\"Jeep\", \"doors\": 3}";

        VIPs car = gson.fromJson(json, VIPs.class);
        
        System.out.println(car.brand +"["+ car.doors + "]");
        
        car.brand = "Rover";
        car.doors = 5;

        String jsonOut = gson.toJson(car);
        System.out.println(jsonOut);
    }
}
