package app.ui;

import app.JPanelWrapper;
import app.ShapeListService;
import app.shapes.CanvasTools;
import app.ui.ShapeListServiceImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
    private JPanelWrapper drawingArea;
    private static final int MAX_ATTEMPTS = 3; // Maximum number of attempts to connect to the RMI registry
    private static final long RETRY_DELAY = 1000; // Delay (in milliseconds) between each connection attempt
    private Registry registry;

    public Export(JPanelWrapper drawingArea) {
        this.drawingArea = drawingArea;
        setTitle("RMI Export Service");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Export.this, "Error connecting to RMI registry", "Error", JOptionPane.ERROR_MESSAGE);
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
                    initializeServer(); // Start RMI server
                    exportButton.setEnabled(false); // Disable export button after successful export
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Export.this, "Error starting RMI server", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initializeServer() throws RemoteException {
        int port = Integer.parseInt(textField2.getText());

        try {
            // Attempt to locate an existing registry
            registry = LocateRegistry.getRegistry(port);
            // If the registry exists, it means it's already created
            System.out.println("RMI registry already exists on port " + port);
        } catch (RemoteException e) {
            // If RemoteException is thrown, it means the registry doesn't exist, so create it
            System.out.println("Creating RMI registry on port " + port);
            registry = LocateRegistry.createRegistry(port); // Create RMI registry
        }

        ShapeListService shapeListService = new ShapeListServiceImpl(); // Create instance of service implementation
        try {
            registry.rebind("shapeListService", shapeListService); // Bind service implementation to registry
        } catch (Exception e) {
            throw new RemoteException("Failed to bind service to registry", e);
        }
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
        JOptionPane.showMessageDialog(this, "Connection to RMI registry failed", "Error", JOptionPane.ERROR_MESSAGE);
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

    }

    @Override
    public void simple() throws RemoteException {

    }

    @Override
    public void sendShape(CanvasTools shape) throws RemoteException {
        try {
            // Lookup for the ShapeListService instance from the RMI registry
            ShapeListService shapeListService = (ShapeListService) registry.lookup("shapeListService");
            // Call the sendShape method on the service instance to send the shape
            shapeListService.sendShape(shape);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error sending shape", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public CanvasTools receiveShape() throws RemoteException {
        return null;
    }
}
