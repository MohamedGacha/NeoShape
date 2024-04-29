package app.ui;

import app.JPanelWrapper;
import app.ShapeListService;
import app.shapes.Area;
import app.shapes.CanvasTools;
import app.shapes.Contour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Import extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel ipLabel;
    private JLabel portLabel;
    private JPanelWrapper DrawingArea;

    public Import(JPanelWrapper DrawingArea) {
        this.DrawingArea = DrawingArea;
        setTitle("TCP Import Service");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    connectToServer();
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Import.this, "Error connecting to server", "Error", JOptionPane.ERROR_MESSAGE);
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

    private void connectToServer() throws IOException, ClassNotFoundException {
        String serverAddress = textField1.getText();
        int port = Integer.parseInt(textField2.getText());

        // Connect to the server
        try (Socket socket = new Socket(serverAddress, port)) {
            // Receive the ShapeList from the server
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ArrayList<CanvasTools> shapeList = (ArrayList<CanvasTools>) inputStream.readObject();

            // Process received shapes (if necessary)
            processReceivedShapes(shapeList);

            // Update the DrawingArea with the received ShapeList
            DrawingArea.setAndRepaintShapesList(shapeList);
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(Import.this, "Error connecting to server", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void processReceivedShapes(ArrayList<CanvasTools> shapes) {
        // Process the received shapes here if needed, for example:
        for (int i = 0; i < shapes.size(); i++) {
            CanvasTools shape = shapes.get(i);
            if (shape instanceof Contour) {
                shapes.set(i, new Area((Shape) shape, ((Contour) shape).getShapeColor()));
            }
        }
    }



    private void onCancel() {
        dispose();
    }
}
