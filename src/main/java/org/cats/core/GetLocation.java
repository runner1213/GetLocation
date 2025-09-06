package org.cats.core;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetLocation {
    private String locationGet = null;
    protected String getLocation(Frame frame, JTextField location, JLabel ip, UI ui) {

        try {
            String urlString = "http://ip-api.com/json/" + ui.getAddress().getHostAddress() +
                    "?fields=status,country,regionName,city,timezone,isp,org,message";
            JSONObject json = getJsonObject(urlString);
            if ("success".equals(json.getString("status"))) {
                locationGet = "<html>" +
                        "Локация: " +
                        json.getString("country") + ", " +
                        json.getString("regionName") + ", " +
                        json.getString("city") + "<br>" +
                        "<div style='margin-left: 20px'>| ISP: " + json.getString("isp") + "</div>" +
                        "<div style='margin-left: 20px'>| Организация: " + json.getString("org") + "</div>" +
                        "<div style='margin-left: 20px'>| Временная зона: " + json.getString("timezone") + "</div>" +
                        "</html>";
            } else if (location.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Сначала напишите айпи в поле!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Не удалось определить местоположение IP: " + ip.getText() + "\nОшибка: " + json.getString("message"), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Ошибка при запросе к API: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
        return locationGet;
    }

    private static JSONObject getJsonObject(String urlString) throws IOException {
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

        return new JSONObject(response.toString());
    }
}
