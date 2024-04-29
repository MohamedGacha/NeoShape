package app.ui;

import app.JPanelWrapper;
import app.ShapeListService;
import app.shapes.CanvasTools;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Export extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel ipLabel;
    private JLabel portLabel;
    private JButton exportButton;
    private JPanelWrapper drawingArea;
    private ServerSocket serverSocket;

    public Export(JPanelWrapper drawingArea) {
        this.drawingArea = drawingArea;
        setTitle("TCP Export Service");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    startServer();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Export.this, "Error starting server", "Error", JOptionPane.ERROR_MESSAGE);
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

    private void startServer() throws IOException {
        // Generate the IP address of the server
        InetAddress localhost = InetAddress.getLocalHost();
        String ipAddress = localhost.getHostAddress();

        // Set the generated IP address in the text field
        textField1.setText(ipAddress);

        // Proceed to start the server as before
        String portText = textField2.getText();
        if (portText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid port number", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit method if port number is empty
        }
        int port = Integer.parseInt(portText);
        serverSocket = new ServerSocket(port);

        new Thread(() -> {
            while (true) {
                try {
                    // Accept client connection
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected: " + clientSocket.getInetAddress());

                    // Activate the export button
                    exportButton.setEnabled(true);

                    // Send the ShapeList to the client
                    ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                    outputStream.writeObject(drawingArea.getShapesList());
                    outputStream.flush();

                    // Close the socket after sending the data
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }




    private void onCancel() {
        // Close the server socket when cancelling
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        dispose();
    }
}
