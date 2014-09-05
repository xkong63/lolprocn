/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lolprocn.connection;

import com.lolprocn.utils.JSONParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alan
 */
public class Connection {

    private static final String USER_AGENT = "Mozilla/5.0";

    // HTTP GET request
    public static String sendGet(String url) throws MalformedURLException, ProtocolException, IOException {

		//String url = "https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/xinkong63?api_key=018a4d88-bbb4-4578-aec5-8b3bf049bb12";
              //  String url = "https://na.api.pvp.net/api/lol/na/v1.3/game/by-summoner/52875213/recent?api_key=018a4d88-bbb4-4578-aec5-8b3bf049bb12";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = 0;

        responseCode = con.getResponseCode();


        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        switch (responseCode) {
            case 400:
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, "Bad request from API");
                break;
            case 401:
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, "Unauthorized from API");
                break;
            case 404:
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, "Game not found from API");
                break;
            case 429:
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, "Rate limit exceeded from API");
                break;
            case 500:
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, "Internal server error from API");
                break;
            case 503:
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, "Service unavailable from API");
                break;
            default:
                Logger.getLogger(Connection.class.getName()).log(Level.FINE, "Success respnse from API");
                break;
        }
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
        return response.toString();

    }
}
