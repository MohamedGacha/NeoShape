package app.ui;

import app.JPanelWrapper;
import app.shapes.*;
import app.shapes.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ListIterator;
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
    private JButton rectangleDrawingModeButton;
    private JButton circleDrawingModeButton;
    private JButton ellipseDrawingModeButton;
    private JButton squareDrawingModeButton;
    private JButton Form5;
    private JButton Form6;
    private JSpinner spinner3;
    private JSlider stroke_slider;
    private JToolBar ActionBar;
    private JButton unionOperatorButton;
    private JButton intersectionOperatorButton;
    private JButton xorOperatorButton;
    private JPanel MainPanel;
    private JCheckBox dashed_checkbox;
    private JRadioButton radioButton1;

    private JPanelWrapper DrawingArea;
    private JPanel RightToolboxPanel;
    private JButton substractOperatorButton;

    //int mouseDragdX, mouseDragdY;
    private Point mousePosition; // relative to DrawingArea

    private Point FirstMousePressPosition;

    private Area operationFirstArea = null,operationSecondArea = null;

    private int posOperationFirstShape = -1,posOperationSecondShape = -1; // position of first and second shape for operations

    public void resetOperatorVariables(){ // needs to be called whenever mode is changed
        operationFirstArea = null;
        operationSecondArea = null;
        posOperationFirstShape = -1;
        posOperationSecondShape = -1;

    }
    public Area getoperationFirstArea(){
        return operationFirstArea;
    }

    public Area operationSecondArea(){
        return operationSecondArea;
    }
    private MouseMode CurrentMode;

    {
        CurrentMode = MouseMode.SELECTION; // default mode is selection mode
    }

    int strokeCurrentWidth = 5;


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


    private void shapeSelectionWorkflow(Point mousePosition) {

        CanvasTools currentShape;
        int i = DrawingArea.getNumberOfShapes() - 1;
        // iterator starting from end of shapes list
        ListIterator<CanvasTools> iter = DrawingArea.getShapesList().listIterator(i + 1);

        while (iter.hasPrevious()) {
            currentShape = iter.previous();
            i--;
            if (currentShape.select(mousePosition)) { // selected a shape
                if (operationFirstArea == null) { // first shape not yet defined
                    operationFirstArea = new Area((Shape) currentShape);
                    posOperationFirstShape = i + 1;
                    break; // stop iterating over the loop
                } else { // first shape already selected
                    if (currentShape != DrawingArea.getShapeAtIndex(posOperationFirstShape)) { // to avoid union and intersection on same shape
                        operationSecondArea = new Area((Shape) currentShape);
                        posOperationSecondShape = i + 1;
                        break; // stop iterating over the loop
                    } else {
                        System.out.println("error: operations aren't reflexive!");
                    }
                }

            }
        }

    }

    public void doOperation(){ // automatically checks for current mode and does needed operation
            if(operationFirstArea != null && operationSecondArea != null){ // both shapes defined
                System.out.println("1st shape index: " + posOperationFirstShape + " 2nd shape index: " + posOperationSecondShape);

                // modify first shape
                switch (getCurrentMode()){
                    case INTERSECTION -> operationFirstArea.intersect(operationSecondArea);
                    case XOR -> operationFirstArea.exclusiveOr(operationSecondArea);
                    case UNION -> operationFirstArea.add(operationSecondArea);
                    case SUBSTRACTION -> operationFirstArea.subtract(operationSecondArea);
                }

                // save to array
                DrawingArea.setShapeAtIndex((CanvasTools) operationFirstArea,posOperationFirstShape);

                //delete the second shape
                DrawingArea.deleteShape(posOperationSecondShape);

                // reset variables
                resetOperatorVariables();
            }
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
                    case INTERSECTION: // braces to creeate a new scope to avoid getting compiler errors due to same var names with union case
                        updateMousePosition(e);

                        shapeSelectionWorkflow(e.getPoint());

                        doOperation();

                        DrawingArea.repaint();
                        //System.out.println("finished intersection operation");
                        System.out.println(DrawingArea.getShapesList());
                        //setCurrentMode(MouseMode.SELECTION);
                        break;


                    case UNION:
                        updateMousePosition(e);

                        shapeSelectionWorkflow(e.getPoint());

                        doOperation();


                        DrawingArea.repaint();
                        //System.out.println("finished union operation");
                        System.out.println(DrawingArea.getShapesList());
                        //setCurrentMode(MouseMode.SELECTION);
                        break;

                    case XOR: // braces to creeate a new scope to avoid getting compiler errors due to same var names with union case
                        updateMousePosition(e);

                        shapeSelectionWorkflow(e.getPoint());

                        doOperation();

                        DrawingArea.repaint();
                        //System.out.println("finished intersection operation");
                        System.out.println(DrawingArea.getShapesList());
                        //setCurrentMode(MouseMode.SELECTION);
                        break;

                    case SUBSTRACTION: // braces to creeate a new scope to avoid getting compiler errors due to same var names with union case
                        updateMousePosition(e);

                        shapeSelectionWorkflow(e.getPoint());

                        doOperation();

                        DrawingArea.repaint();
                        //System.out.println("finished intersection operation");
                        System.out.println(DrawingArea.getShapesList());
                        //setCurrentMode(MouseMode.SELECTION);
                        break;

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
                        System.out.println("finished drawing an ELLIPSE, center @ (" + eli.getX() + "," + eli.getY() + ")");
                        System.out.println(DrawingArea.getShapesList());
                        //setCurrentMode(MouseMode.SELECTION);
                        break;
                    case DRAWING_SQUARE:

                        Square square = (Square) DrawingArea.getLastShape();

                        updateMousePosition(e);

                        square.updateShapeDimensions(e,DrawingArea.getWidth(),DrawingArea.getHeight(),strokeCurrentWidth);

                        DrawingArea.repaint();
                        System.out.println("finished drawing a square, up left corner @ (" + square.getX() + "," + square.getY() + ")");
                        System.out.println(DrawingArea.getShapesList());
                        //setCurrentMode(MouseMode.SELECTION);
                        break;
                    case DRAWING_CIRCLE:

                        Circle circle = (Circle) DrawingArea.getLastShape();

                        updateMousePosition(e);

                        circle.updateShapeDimensions(e,DrawingArea.getWidth(),DrawingArea.getHeight(),strokeCurrentWidth);

                        DrawingArea.repaint();
                        System.out.println("finished drawing a CIRCLE, center @ (" + circle.getX() + "," + circle.getY() + ")");
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
                //updateCurrentStroke();
            }
        });

        unionOperatorButton.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                resetOperatorVariables();
                CurrentMode = MouseMode.UNION;
                enableAllDrawingButtonsExcept(unionOperatorButton);
                System.out.println("set mode to union operation");
            }
        });



        Exit.addActionListener(e -> System.exit(0));
        rectangleDrawingModeButton.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                resetOperatorVariables();

                setCurrentMode(MouseMode.DRAWING_RECTANGLE);
                enableAllDrawingButtonsExcept(rectangleDrawingModeButton);
                System.out.println("set mode to rectangle drawing");
            }
        });
        circleDrawingModeButton.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                resetOperatorVariables();
                setCurrentMode(MouseMode.DRAWING_CIRCLE);
                enableAllDrawingButtonsExcept(circleDrawingModeButton);
                System.out.println("set mode to circle drawing");
            }
        });
        ellipseDrawingModeButton.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                resetOperatorVariables();
                setCurrentMode(MouseMode.DRAWING_ELLIPSE);
                enableAllDrawingButtonsExcept(ellipseDrawingModeButton);
                System.out.println("set mode to ellipse drawing");
            }
        });

        squareDrawingModeButton.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                resetOperatorVariables();
                setCurrentMode(MouseMode.DRAWING_SQUARE);
                enableAllDrawingButtonsExcept(squareDrawingModeButton);
                System.out.println("set mode to square drawing");
            }
        });
        intersectionOperatorButton.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                resetOperatorVariables();
                CurrentMode = MouseMode.INTERSECTION;
                enableAllDrawingButtonsExcept(intersectionOperatorButton);
                System.out.println("set mode to intersection operation");
            }
        });
        substractOperatorButton.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                resetOperatorVariables();
                CurrentMode = MouseMode.SUBSTRACTION;
                enableAllDrawingButtonsExcept(substractOperatorButton);
                System.out.println("set mode to SUBSTRACTION operation");
            }
        });
        xorOperatorButton.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                resetOperatorVariables();
                CurrentMode = MouseMode.XOR;
                enableAllDrawingButtonsExcept(xorOperatorButton);
                System.out.println("set mode to XOR operation");
            }
        });
    }

    private void enableAllDrawingButtonsExcept(JButton buttonToExclude) {
        rectangleDrawingModeButton.setEnabled(true);
        circleDrawingModeButton.setEnabled(true);
        ellipseDrawingModeButton.setEnabled(true);
        squareDrawingModeButton.setEnabled(true);
        intersectionOperatorButton.setEnabled(true);
        unionOperatorButton.setEnabled(true);
        xorOperatorButton.setEnabled(true);
        substractOperatorButton.setEnabled(true);
        buttonToExclude.setEnabled(false);// Disable the button that activated polyline mode
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
