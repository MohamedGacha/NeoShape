package app.ui;

import app.JPanelWrapper;
import app.ShapeListService;
import app.shapes.CanvasTools;

import javax.swing.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

public class Export extends JDialog implements ShapeListService {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel ipLabel;
    private JLabel portLabel;
    private JButton exportButton;
    private JPanelWrapper DrawingArea;
    private static final int MAX_ATTEMPTS = 3; // Maximum number of attempts to connect to the RMI registry
    private static final long RETRY_DELAY = 1000; // Delay (in milliseconds) between each connection attempt
    private Registry registry;

    public Export(JPanelWrapper DrawingArea) {
        this.DrawingArea = DrawingArea;
        setTitle("RMI Export Service");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
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

        // Add action listener to the exportButton
        exportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Define the behavior when the export button is clicked
                // For example, you can call the onOK() method here if needed

                try {
                    ShapeListService shapeListService = new ShapeListServiceImpl();
                    registry.rebind("shapeListService", shapeListService);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void onOK() throws RemoteException {
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
                    exportButton.setEnabled(true);

                    // Get the server's IP address and set it to the textField1
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    textField1.setText(inetAddress.getHostAddress());

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

        // Call updateShapeList method of Import to update the shape list in DrawingArea
    }




    private void onCancel() {
        dispose();
    }


    @Override
    public ArrayList<CanvasTools> getShapeList() throws RemoteException {
        return null;
    }

    @Override
    public void updateShapeList(JPanelWrapper DrawingArea) throws RemoteException {

    }

    @Override
    public void simple() throws RemoteException {

    }
}
