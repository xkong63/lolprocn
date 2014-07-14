/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lolprocn.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author alan
 */
public class Connection {
    
    
        private static final String USER_AGENT = "Mozilla/5.0";
    
    // HTTP GET request
	public static  String sendGet(String url) throws MalformedURLException, IOException  {
 
		//String url = "https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/xinkong63?api_key=018a4d88-bbb4-4578-aec5-8b3bf049bb12";
                
              //  String url = "https://na.api.pvp.net/api/lol/na/v1.3/game/by-summoner/52875213/recent?api_key=018a4d88-bbb4-4578-aec5-8b3bf049bb12";
 
		URL obj = new URL(url);
		          HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
 
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
