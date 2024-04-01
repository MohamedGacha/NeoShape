package app.ui;

import app.JPanelWrapper;
import app.shapes.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GuiController extends JPanel{

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
    private JSpinner spinner3;
    private JSlider stroke_slider;
    private JToolBar ActionBar;
    private JButton action1;
    private JButton Action2;
    private JButton Action3;
    private JButton Action4;
    private JPanel MainPanel;
    private JCheckBox dashed_checkbox;
    private JRadioButton radioButton1;

    private JPanelWrapper DrawingArea;
    private JPanel RightToolboxPanel;

    //int mouseDragdX, mouseDragdY;
    private Point mousePosition; // relative to DrawingArea
    private MouseMode CurrentMode;

    {
        CurrentMode = MouseMode.DRAWING_RECTANGLE; // default mode is selection mode
    }

    private Paint CurrentPaint = new Color(0, 255, 255);

    int strokeCurrentWidth = 5;
    private Stroke CurrentStroke = new BasicStroke(strokeCurrentWidth);

    private boolean DashedLineButton = false;

    //private ArrayList<CanvasTools> ShapesList = new ArrayList<CanvasTools>();

    public void setCurrentMode(MouseMode mode) {
        this.CurrentMode = mode;
    }

    public MouseMode getCurrentMode() {
        return CurrentMode;
    }

    public void updateMousePosition(MouseEvent e){
        mousePosition = e.getLocationOnScreen();
        SwingUtilities.convertPointFromScreen(mousePosition,DrawingArea);
    }


    private Graphics2D GetDrawingAreaGraphics2D(){
        return (Graphics2D) DrawingArea.getGraphics();
    }

    private void updateCurrentStroke(){
        CurrentStroke = new BasicStroke(strokeCurrentWidth);
    }



    public GuiController() {


        DrawingArea.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                switch (getCurrentMode()) {
                    case DRAWING_RECTANGLE:
                        super.mouseDragged(e);
                        //System.out.print("mouse dragged from ("+ mousePosition.x + "," + mousePosition.y + ") to (");
                        //updateMousePosition(e);
                        //System.out.println(mousePosition.x + "," + mousePosition.y + ")");


                        Rectangle r = (Rectangle) DrawingArea.getLastShape();

                        updateMousePosition(e);

                        double new_upper_corner_x = Math.min(e.getX(), r.getX());
                        double new_upper_corner_y = Math.min(e.getY(), r.getY());
                        double new_width = Math.abs(e.getX() - r.getX());
                        double new_height = Math.abs(e.getY() - r.getY());

                        r.setRect(new_upper_corner_x,new_upper_corner_y,new_width,new_height);


                        DrawingArea.repaint();
                        //System.out.println("dragged a rectangle!");

                        break;
                    case SELECTION:
                        // Handle selection mode
                        System.out.println("selection mode!!");
                        updateMousePosition(e);
                        break;
                    case PAN:
                        // Handle panning mode
                        break;
                    // Add cases for other modes
                }


            }
        });

        DrawingArea.addMouseListener(new MouseAdapter() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("mouse clicked!");
                // select object
            }

            @Override
            public void  mousePressed(MouseEvent e) {
                switch (getCurrentMode()) {
                    case DRAWING_RECTANGLE:
                        // drawing mode
                        System.out.println("RECTANGLE drawing mode!!");
                        updateMousePosition(e);

                        Rectangle r = new Rectangle(mousePosition,1,1);

                        Graphics2D gg = GetDrawingAreaGraphics2D();
                        gg.setStroke(CurrentStroke);
                        gg.setPaint(CurrentPaint);

                        r.draw(gg);


                        DrawingArea.addShape(r);
                        break;
                    case SELECTION:
                        // Handle selection mode
                        System.out.println("selection mode!!");
                        updateMousePosition(e);
                        break;
                    case PAN:
                        // Handle panning mode
                        break;
                    // Add cases for other modes
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                switch (getCurrentMode()) {
                    case DRAWING_RECTANGLE:
                        super.mouseReleased(e);

                        Rectangle r = (Rectangle) DrawingArea.getLastShape();

                        updateMousePosition(e);

                        double newUpperCornerX = Math.max(Math.min(e.getX(), r.getX()),0);
                        double newUpperCornerY = Math.max(Math.min(e.getY(), r.getY()),0);
                        double newWidth = Math.abs(e.getX() - r.getX());
                        double newHeight = Math.abs(e.getY() - r.getY());

                        //  size of drawing panel
                        int panelWidth = DrawingArea.getSize().width;
                        int panelHeight = DrawingArea.getSize().height;

                        // make sure the rectangle is within the drawing panel's bounds
                        if (newUpperCornerX + newWidth > panelWidth) {
                            newWidth = panelWidth - newUpperCornerX;
                        }
                        if (newUpperCornerY + newHeight > panelHeight) {
                            newHeight = panelHeight - newUpperCornerY;
                        }

                        r.setRect(newUpperCornerX, newUpperCornerY, newWidth, newHeight);

//                        Graphics2D gg = GetDrawingAreaGraphics2D();
//                        gg.setStroke(CurrentStroke);
//                        gg.setPaint(CurrentPaint);
//
//                        r.draw(gg);
                        DrawingArea.repaint();
                        System.out.println("finished drawing a rectangle, up left corner @ (" + newUpperCornerX + "," + newUpperCornerY + ")");
                        System.out.println(DrawingArea.getShapesList());
                        //setCurrentMode(MouseMode.SELECTION);
                        break;
                    case SELECTION:
                        // Handle selection mode
                        //System.out.println("selection mode!!");
                        updateMousePosition(e);
                        break;
                    case PAN:
                        // Handle panning mode
                        break;
                    // Add cases for other modes
                }

            }


        });
        // listener for slider when value is changed, need to change the stroke
        stroke_slider.addChangeListener(new ChangeListener() {
            /**
             * @param e a ChangeEvent object
             */
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!stroke_slider.getValueIsAdjusting()) {
                    strokeCurrentWidth = (int)stroke_slider.getValue();
                }
                System.out.println("changed stroke slider");
                // update the stroke
                updateCurrentStroke();
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
