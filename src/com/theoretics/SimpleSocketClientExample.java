/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theoretics;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class SimpleSocketClientExample
{
    
    private static int port = 5777;
    
    public static void main( String[] args )
    {
        
        String server = "192.168.1.23";
    
        String path = "Test Path";

        System.out.println( "Loading contents of URL: " + server );

        try
        {
            // Connect to the server
            Socket socket = new Socket( server, port );

            // Create input and output streams to read from and write to the server
            PrintStream out = new PrintStream( socket.getOutputStream() );
            BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );

            // Follow the HTTP protocol of GET <path> HTTP/1.0 followed by an empty line
            out.println( "GET " + path + " HTTP/1.0" );
            out.println();

            // Read data from the server until we finish reading the document
            String line = in.readLine();
            while( line != null )
            {
                System.out.println( line );
                line = in.readLine();
            }

            // Close our streams
            in.close();
            out.close();
            socket.close();
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }
}