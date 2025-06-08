package org.cats.core;

import org.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.cats.core.UI.*;

public class GetLocation {
    protected static String getLocation() {
        String locationGet = null;
        String urlString = "http://ip-api.com/json/" + getAddress().getHostAddress() + "?fields=status,country,regionName,city,message";

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject json = new JSONObject(response.toString());

            if ("success".equals(json.getString("status"))) {
                locationGet = json.getString("country") + ", " + json.getString("regionName") + ", " + json.getString("city");
            } else {
                JOptionPane.showMessageDialog(frame, "Не удалось определить местоположение IP: " + ip + "\nОшибка: " + json.getString("message"), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Ошибка при запросе к API: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
        return locationGet;
    }
}
