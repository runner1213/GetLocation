package org.cats;

import org.cats.core.UI;
import javax.swing.*;

public class Main {
    public Main() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        UI ui = new UI();
        ui.createComponents();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}