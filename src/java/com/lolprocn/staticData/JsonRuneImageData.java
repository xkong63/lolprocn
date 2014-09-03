/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lolprocn.staticData;

import com.lolprocn.connection.Connection;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 *
 * @author Apollowc
 */
public class JsonRuneImageData {
        public static void main(String args[]) {
        try {
            String response = Connection.sendGet("https://na.api.pvp.net/api/lol/static-data/na/v1.2/rune?runeListData=image&api_key=81a9f77d-eb19-4581-a58e-afc46b10ed0b");
            JSONObject jObject = new JSONObject(response);
            FileWriter file = new FileWriter("./web/resources/json/rune_image.json");

            file.write(jObject.toString());
            file.flush();
            file.close();

        } catch (IOException ex) {
            Logger.getLogger(JsonChampionData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(JsonChampionData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
