package org.cats.core;

import org.cats.recent.SetRecent;

import javax.swing.*;

import static org.cats.core.UI.*;

public class GetIP {
    protected static void getIP() {
        String address = getAddress().getHostAddress();
        String displayAddress;

        switch (address) {
            case "8.8.8.8"       -> displayAddress = address + " (Google DNS)";
            case "1.1.1.1" -> displayAddress = address + " (Cloudflare DNS)";
            default -> displayAddress = address;
        }

        ip.setText("Айпи: " + displayAddress);
        location.setText("Локация: " + GetLocation.getLocation());

        try {
            String ipText = ipField.getText().trim();
            SetRecent.setRecent(ipText);
        } catch (Exception g) {
            JOptionPane.showMessageDialog(frame, g.getMessage());
        }
    }
}
