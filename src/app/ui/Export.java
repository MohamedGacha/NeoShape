package app.ui;

import app.JPanelWrapper;

import javax.swing.*;
import java.awt.event.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class Export extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel ipLabel;
    private JLabel portLabel;
    private JPanelWrapper DrawingArea;

    private static final int MAX_ATTEMPTS = 3; // Maximum number of attempts to connect to the RMI registry
    private static final long RETRY_DELAY = 1000; // Delay (in milliseconds) between each connection attempt

    public Export(JPanelWrapper DrawingArea) {
        this.DrawingArea = DrawingArea;
        setTitle("RMI Export Service");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void onOK() {
        String serverAddress = textField1.getText();
        int port = Integer.parseInt(textField2.getText());

        boolean connected = false; // Flag to track if connected successfully

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            try {
                // Attempt to connect to the RMI registry
                Registry registry = LocateRegistry.getRegistry(serverAddress, port);

                // Check if connection is successful before printing
                if (registry != null) {
                    System.out.println("Connected to RMI registry");
                    connected = true; // Set the flag to true if connected
                    dispose(); // Close the dialog if connected
                    return; // Exit the method if connected
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // If not connected, wait for a retry delay before the next attempt
            try {
                Thread.sleep(RETRY_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // If all attempts fail, print failure message
        System.out.println("Connection to RMI registry failed");
    }


    private void onCancel() {
        dispose();
    }
}
