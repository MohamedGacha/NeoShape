package app.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerDemo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Server Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JButton serverButton = new JButton("Start Server");
            serverButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String ipAddress = JOptionPane.showInputDialog(frame, "Enter IP Address:");
                    String portStr = JOptionPane.showInputDialog(frame, "Enter Port Number:");
                    int port = Integer.parseInt(portStr);
                    startServer(ipAddress, port);
                }
            });

            frame.getContentPane().add(serverButton);
            frame.pack();
            frame.setVisible(true);
        });
    }

    private static void startServer(String ipAddress, int port) {
        // Simulate server startup
        System.out.println("Starting server with IP: " + ipAddress + " and Port: " + port);
        // Add your server startup logic here
        // For demonstration, we'll just print the server details
    }
}
