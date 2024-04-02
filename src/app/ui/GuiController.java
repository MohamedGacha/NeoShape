package app.ui;

import app.JPanelWrapper;
import app.shapes.Ellipse;
import app.shapes.Circle;
import app.shapes.Rectangle;
import app.shapes.Square;

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

    private Point FirstMousePressPosition;
    private MouseMode CurrentMode;

    {
        CurrentMode = MouseMode.DRAWING_SQUARE; // default mode is selection mode
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
                super.mouseDragged(e);
                switch (getCurrentMode()) {
                    case DRAWING_RECTANGLE:
                        //System.out.print("mouse dragged from ("+ mousePosition.x + "," + mousePosition.y + ") to (");
                        //updateMousePosition(e);
                        //System.out.println(mousePosition.x + "," + mousePosition.y + ")");


                        Rectangle r = (Rectangle) DrawingArea.getLastShape();

                        updateMousePosition(e);

                        r.DraggedUpdateShapeDimensions(FirstMousePressPosition,e,DrawingArea.getWidth(),DrawingArea.getHeight(),strokeCurrentWidth);

                        DrawingArea.repaint();
                        //System.out.println("dragged a rectangle!");

                        break;
                    case DRAWING_ELLIPSE:
                        //System.out.print("mouse dragged from ("+ mousePosition.x + "," + mousePosition.y + ") to (");
                        //updateMousePosition(e);
                        //System.out.println(mousePosition.x + "," + mousePosition.y + ")");


                        Ellipse eli = (Ellipse) DrawingArea.getLastShape();

                        updateMousePosition(e);

                        eli.updateShapeDimensions(e,DrawingArea.getWidth(),DrawingArea.getHeight(),strokeCurrentWidth);

                        DrawingArea.repaint();
                        //System.out.println("dragged a rectangle!");

                        break;
                    case DRAWING_CIRCLE:
                        //System.out.print("mouse dragged from ("+ mousePosition.x + "," + mousePosition.y + ") to (");
                        //updateMousePosition(e);
                        //System.out.println(mousePosition.x + "," + mousePosition.y + ")");


                        Circle circle= (Circle) DrawingArea.getLastShape();

                        updateMousePosition(e);

                        circle.updateShapeDimensions(e,DrawingArea.getWidth(),DrawingArea.getHeight(),strokeCurrentWidth);

                        DrawingArea.repaint();
                        //System.out.println("dragged a rectangle!");

                        break;
                    case DRAWING_SQUARE:

                        //System.out.print("mouse dragged from ("+ mousePosition.x + "," + mousePosition.y + ") to (");
                        //updateMousePosition(e);
                        //System.out.println(mousePosition.x + "," + mousePosition.y + ")");


                        Square square = (Square) DrawingArea.getLastShape();

                        updateMousePosition(e);

                        square.DraggedUpdateShapeDimensions(FirstMousePressPosition,e,DrawingArea.getWidth(),DrawingArea.getHeight(),strokeCurrentWidth);

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

                //updateMousePosition(e);



                // select object
            }

            @Override
            public void  mousePressed(MouseEvent e) {
                super.mousePressed(e);
                Graphics2D gg = GetDrawingAreaGraphics2D();
                gg.setStroke(CurrentStroke);
                gg.setPaint(CurrentPaint);

                FirstMousePressPosition = e.getPoint();
                SwingUtilities.convertPoint(e.getComponent(),FirstMousePressPosition,DrawingArea);
                //SwingUtilities.convertPointFromScreen(FirstMousePressPosition,DrawingArea); // DONT USE, WILL GIVE FAULTY COORDINATES

                switch (getCurrentMode()) {
                    case DRAWING_RECTANGLE:
                        // drawing mode
                        System.out.println("RECTANGLE drawing mode!!");
                        updateMousePosition(e);

                        Rectangle r = new Rectangle(mousePosition,1,1);



                        r.draw(gg);


                        DrawingArea.addShape(r);
                        break;

                    case DRAWING_SQUARE:

                        System.out.println("SQUARE drawing mode!!");
                        updateMousePosition(e);

                        Square square = new Square(mousePosition,1);



                        square.draw(gg);


                        DrawingArea.addShape(square);

                        break;
                    case DRAWING_ELLIPSE:
                        // drawing mode
                        System.out.println("ELLIPSE drawing mode!!");
                        updateMousePosition(e);

                        Ellipse eli = new Ellipse(mousePosition,1,1);



                        eli.draw(gg);


                        DrawingArea.addShape(eli);
                        break;
                    case DRAWING_CIRCLE:
                        // drawing mode
                        System.out.println("Circle drawing mode!!");
                        updateMousePosition(e);

                        Circle circle = new Circle(mousePosition,1);



                        circle.draw(gg);


                        DrawingArea.addShape(circle);
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
                super.mouseReleased(e);
                switch (getCurrentMode()) {
                    case DRAWING_RECTANGLE:


                        Rectangle r = (Rectangle) DrawingArea.getLastShape();

                        updateMousePosition(e);

                        r.updateShapeDimensions(e,DrawingArea.getWidth(),DrawingArea.getHeight(),strokeCurrentWidth);

                        DrawingArea.repaint();
                        System.out.println("finished drawing a rectangle, up left corner @ (" + r.getX() + "," + r.getY() + ")");
                        System.out.println(DrawingArea.getShapesList());
                        //setCurrentMode(MouseMode.SELECTION);
                        break;
                    case DRAWING_ELLIPSE:

                        Ellipse eli = (Ellipse) DrawingArea.getLastShape();

                        updateMousePosition(e);

                        eli.updateShapeDimensions(e,DrawingArea.getWidth(),DrawingArea.getHeight(),strokeCurrentWidth);

                        DrawingArea.repaint();
                        System.out.println("finished drawing a rectangle, up left corner @ (" + eli.getX() + "," + eli.getY() + ")");
                        System.out.println(DrawingArea.getShapesList());
                        //setCurrentMode(MouseMode.SELECTION);
                        break;
                    case DRAWING_SQUARE:

                        Square square = (Square) DrawingArea.getLastShape();

                        updateMousePosition(e);

                        square.updateShapeDimensions(e,DrawingArea.getWidth(),DrawingArea.getHeight(),strokeCurrentWidth);

                        DrawingArea.repaint();
                        System.out.println("finished drawing a rectangle, up left corner @ (" + square.getX() + "," + square.getY() + ")");
                        System.out.println(DrawingArea.getShapesList());
                        //setCurrentMode(MouseMode.SELECTION);
                        break;
                    case DRAWING_CIRCLE:

                        Circle circle = (Circle) DrawingArea.getLastShape();

                        updateMousePosition(e);

                        circle.updateShapeDimensions(e,DrawingArea.getWidth(),DrawingArea.getHeight(),strokeCurrentWidth);

                        DrawingArea.repaint();
                        System.out.println("finished drawing a rectangle, up left corner @ (" + circle.getX() + "," + circle.getY() + ")");
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
