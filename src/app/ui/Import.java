package app.ui;

import app.JPanelWrapper;
import app.ShapeListService;
import app.shapes.CanvasTools;

import javax.swing.*;
import java.awt.event.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

public class Import extends JDialog implements ShapeListService {
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
    private Registry registry;

    public Import(JPanelWrapper DrawingArea) {
        this.DrawingArea = DrawingArea;
        setTitle("RMI Import Service");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (NotBoundException ex) {
                    throw new RuntimeException(ex);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void onOK() throws NotBoundException, RemoteException {
        String serverAddress = textField1.getText();
        int port = Integer.parseInt(textField2.getText());

        boolean connected = false; // Flag to track if connected successfully

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            try {
                // Attempt to connect to the RMI registry
                registry = LocateRegistry.getRegistry(serverAddress, port);

                // Check if connection is successful before printing
                if (registry != null) {
                    System.out.println("Connected to RMI registry");
                    connected = true; // Set the flag to true if connected
                    break; // Exit the loop if connected
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

        // If connected flag is still false after all attempts, print failure message
        if (!connected) {
            System.out.println("Connection to RMI registry failed");
            ShapeListService shapeListService = (ShapeListService) registry.lookup("ShapeListService");
            shapeListService.simple();
        } else {
            // Print "connected successfully"
            System.out.println("Connected successfully");
        }
    }




    private void onCancel() {
        dispose();
    }

    @Override
    public ArrayList<CanvasTools> getShapeList() throws RemoteException {
        return null;
    }

    @Override
    public void updateShapeList(JPanelWrapper newArea) throws RemoteException {
        System.out.println("newArea");
        //DrawingArea.setAndRepaintShapesList(newArea.getShapesList());
    }

    @Override
    public void simple() {
        System.out.println("simple");
    }


}
