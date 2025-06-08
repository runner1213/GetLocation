package org.cats.core;

import org.cats.recent.GetRecent;

import java.awt.datatransfer.StringSelection;
import java.net.InetAddress;
import javax.swing.*;
import java.awt.*;

public class UI {
    public static String[] recentDomains = GetRecent.getRecent();
    public static JFrame frame = new JFrame("Локация");

    public static JComboBox<String> domainList = new JComboBox<>(recentDomains);

    protected static JLabel enterIpLabel = new JLabel("Введите айпи или домен:");
    protected static JTextField ipField = new JTextField(20);
    protected static JLabel ip = new JLabel("Айпи: ");
    protected static JLabel location = new JLabel("Локация: ");
    protected static JButton getBtn = new JButton("Получить айпи");
    protected static JButton copyBtn = new JButton("Копировать айпи");

    public static void createComponents() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(600, 300);

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
                } else {
                  ipField.setText("");
                }
            }
        });

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.add(ip);
        resultPanel.add(location);
        resultPanel.add(copyBtn);

        getBtn.addActionListener(e -> GetIP.getIP());
        copyBtn.addActionListener(f -> {
            GetIP.getIP();

            String ipaddress = getAddress().getHostAddress();
            StringSelection selection = new StringSelection(ipaddress);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
            JOptionPane.showMessageDialog(frame, "IP ("+ ipaddress +") скопирован в буфер обмена!");
        });

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(resultPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
    public static InetAddress getAddress() {
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
