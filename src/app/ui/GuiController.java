package app.ui;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GuiController {

    private JButton New;
    private JButton Open;
    private JButton Save;
    private JButton Server;
    private JButton Exit;
    private JButton ZoomIn;
    private JSpinner spinner1;
    private JButton ZoomOut;
    private JButton Mouse;
    private JButton Form1;
    private JButton Form2;
    private JButton Form3;
    private JButton Form4;
    private JButton Form5;
    private JButton Form6;
    private JButton button1;
    private JSpinner spinner3;
    private JSlider slider1;
    private JRadioButton radioButton1;
    private JToolBar ActionBar;
    private JButton action1;
    private JButton Action2;
    private JButton Action3;
    private JButton Action4;
    private JPanel DrawingArea;
    private JPanel MainPanel;



    public GuiController() {

        DrawingArea.addMouseListener(new MouseAdapter() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("mouse clicked!");
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                //super.mouseDragged(e);
                System.out.println("mouse dragged!");
            }
        });


    }

    public static void init() {
        JFrame frame = new JFrame("GUI");
        frame.setContentPane(new GuiController().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {

    }
}
