package org.cats.core;

import org.cats.recent.SetRecent;

import javax.swing.*;

public class GetIP {
    protected void getIP(JTextField ipField, JLabel ip, JLabel location, UI ui, GetLocation getLocation, JFrame frame, JComboBox<String> domainList) {

        String address = ui.getAddress().getHostAddress();
        String displayAddress;

        switch (address) {
            case "8.8.8.8"       -> displayAddress = address + " (Google DNS)";
            case "1.1.1.1" -> displayAddress = address + " (Cloudflare DNS)";
            default -> displayAddress = address;
        }

        ip.setText("Айпи: " + displayAddress);
        location.setText(getLocation.getLocation(frame, ipField, ip, ui));

        try {
            String ipText = ipField.getText().trim();
            SetRecent.setRecent(ipText, domainList);
        } catch (Exception g) {
            JOptionPane.showMessageDialog(frame, g.getMessage());
        }
    }
}
