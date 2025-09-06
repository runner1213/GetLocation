package org.cats.core;

import org.cats.recent.GetRecent;

import java.awt.datatransfer.StringSelection;
import java.net.InetAddress;
import javax.swing.*;
import java.awt.*;

public class UI {
    private final String[] recentDomains = GetRecent.getRecent();
    private final JFrame frame = new JFrame("Локация");

    private final JComboBox<String> domainList = new JComboBox<>(recentDomains);

    protected JLabel enterIpLabel = new JLabel("Введите айпи или домен:");
    protected JTextField ipField = new JTextField(20);
    protected JLabel ip = new JLabel("Айпи: ");
    protected JLabel location = new JLabel("Локация: ");
    protected JButton getBtn = new JButton("Получить айпи");
    protected JButton copyIPBtn = new JButton("Копировать айпи");
    protected JButton copyLocBtn = new JButton("Копировать локацию");

    private final GetLocation getLocation = new GetLocation();
    private final GetIP getIP = new GetIP();

    public void createComponents() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(600, 400);

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(enterIpLabel);
        inputPanel.add(ipField);
        inputPanel.add(domainList);
        inputPanel.add(getBtn);

        domainList.addActionListener(e -> {
            String selected = (String) domainList.getSelectedItem();
            if (selected != null && !selected.isEmpty()) {
                if (!selected.equals(domainList.getItemAt(0))) {
                    ipField.setText(selected);
                    ipField.requestFocusInWindow();
                    ipField.setCaretPosition(0);
                } else {
                  ipField.setText("");
                }
            }
        });

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.add(ip);
        resultPanel.add(location);
        resultPanel.add(copyIPBtn);
        resultPanel.add(copyLocBtn);

        getBtn.addActionListener(e -> getIP.getIP(ipField, ip, location, this, getLocation, frame, domainList));
        copyIPBtn.addActionListener(f -> {
            getIP.getIP(ipField, ip, location, this, getLocation, frame, domainList);

            String ipaddress = getAddress().getHostAddress();
            StringSelection selection = new StringSelection(ipaddress);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
            JOptionPane.showMessageDialog(frame, "IP ("+ ipaddress +") скопирован в буфер обмена!");
        });

        copyLocBtn.addActionListener(e -> {
            String locationGet = getLocation.getLocation(frame, ipField, ip, this);
            JOptionPane.showMessageDialog(frame, "Локация ("+ locationGet +") скопирована в буфер обмена!");
        });

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(resultPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
    public InetAddress getAddress() {
        InetAddress address = null;
        String ipText;
        try {
            ipText = ipField.getText().trim();
            address = InetAddress.getByName(ipText);
            address = InetAddress.getByName(address.getHostAddress());
        } catch (Exception f) {
            JOptionPane.showMessageDialog(frame, f.getMessage());
        }
        return address;
    }
}
