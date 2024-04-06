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
import javax.swing.text.AbstractDocument;
import javax.swing.text.StyledDocument;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.io.*;import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class GuiController extends JPanel implements Serializable{

    private JButton New;
    private JButton Open;
    private JButton Save;
    private JButton Server;
    private JButton Exit;
    private JSpinner spinner1;
    private JButton selectionModeButton;
    private JButton rectangleDrawingModeButton;
    private JButton circleDrawingModeButton;
    private JButton ellipseDrawingModeButton;
    private JButton squareDrawingModeButton;
    private JButton heartDrawingModeButton;
    private JButton triangleDrawingModeButton;
    private JFormattedTextField redColorFormattedTF;
    private JToolBar ActionBar;
    private JButton unionOperatorButton;
    private JButton intersectionOperatorButton;
    private JButton xorOperatorButton;
    private JPanel MainPanel;

    private JPanelWrapper DrawingArea;
    private JPanel RightToolboxPanel;
    private JButton substractOperatorButton;
    private JButton colorSetButton;
    private JButton moreColorsButton;
    private JPanel colorSquarePanel;
    private JSpinner redSpinner;
    private JSpinner greenSpinner;
    private JSpinner blueSpinner;
    private JLabel redLabel;
    private JLabel blueLabel;
    private JLabel greenLabel;
    private JLabel colorLabel;
    private JTabbedPane colorTabbedPane;
    private JColorChooser colorChooser1;

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

    public static Color blend(Color c0, Color c1) {

        System.out.println("c0: " +c0 + " | c1: "+ c1);
        int red = (c0.getRed() + c1.getRed())/2;
        int green = (c0.getGreen() + c1.getGreen())/2;
        int blue = (c0.getBlue() + c1.getBlue())/2;

        return new Color(red,green,blue);
    }

    public Color getColorWrapper(int pos) {


        CanvasTools shape = DrawingArea.getShapeAtIndex(pos);

        if (shape instanceof Rectangle) {
            System.out.println("wanted to grab color: "+ ((Rectangle) shape).getShapeColor() + " at index: "+(int)(pos));
            return ((Rectangle) shape).getShapeColor();
        } else if (shape instanceof Circle) {
            return ((Circle) shape).getShapeColor();
        } else if (shape instanceof Ellipse) {
            return ((Ellipse) shape).getShapeColor();
        } else if (shape instanceof Heart) {
            return ((Heart) shape).getShapeColor();
        } else if (shape instanceof Triangle) {
            return ((Triangle) shape).getShapeColor();
        } else if (shape instanceof Area) {
            return ((Area) shape).getShapeColor();
        }else {
            System.out.println("cant grab shape color!!");
            return null; // or some other default value
        }
    }

    public static boolean isValidRGBValue(int value) {
        return value >= 0 && value <= 255;
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


    private Color currentColor = Color.black; // default color when initialising
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
                    posOperationFirstShape = i + 1;

                    operationFirstArea = new Area((Shape) currentShape,getColorWrapper(posOperationFirstShape));
                    break; // stop iterating over the loop
                } else { // first shape already selected
                    if (currentShape != DrawingArea.getShapeAtIndex(posOperationFirstShape)) { // to avoid union and intersection on same shape
                        posOperationSecondShape = i +1;
                        operationSecondArea = new Area((Shape) currentShape,Color.black); // don't need to set color for second area since it will be deleted
                        //set color to resulting shape
                        //System.out.println("miaw checkpoint: area1 color = " + operationFirstArea.getShapeColor());
                        operationFirstArea.setShapeColor(blend(operationFirstArea.getShapeColor(),getColorWrapper(posOperationSecondShape)));
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

        Open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Open File");
                fileChooser.setFileFilter(new FileNameExtensionFilter("Shape Files (*.shp)", "shp"));

                int userSelection = fileChooser.showOpenDialog(GuiController.this);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                    loadShapesFromFile(selectedFilePath);
                }
            }
        });

        // Add action listener to the Server button
        Server.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt user to enter IP address and port
                String ipAddress = JOptionPane.showInputDialog(null, "Enter IP Address:");
                String portString = JOptionPane.showInputDialog(null, "Enter Port Number:");
                int port = Integer.parseInt(portString);

                // Start the server
                startServer(ipAddress, port);
            }
        });

        Save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToFile();
            }
        });

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
                    case DRAWING_TRIANGLE:

                        //System.out.print("mouse dragged from ("+ mousePosition.x + "," + mousePosition.y + ") to (");
                        //updateMousePosition(e);
                        //System.out.println(mousePosition.x + "," + mousePosition.y + ")");


                        Triangle triangle = (Triangle) DrawingArea.getLastShape();

                        updateMousePosition(e);

                        triangle.DraggedUpdateShapeDimensions(FirstMousePressPosition,e,DrawingArea.getWidth(),DrawingArea.getHeight(),strokeCurrentWidth);

                        DrawingArea.repaint();
                        //System.out.println("dragged a rectangle!");

                        break;
                    case DRAWING_HEART:

                        //System.out.print("mouse dragged from ("+ mousePosition.x + "," + mousePosition.y + ") to (");
                        //updateMousePosition(e);
                        //System.out.println(mousePosition.x + "," + mousePosition.y + ")");


                        Heart heart = (Heart) DrawingArea.getLastShape();

                        updateMousePosition(e);

                        heart.DraggedUpdateShapeDimensions(FirstMousePressPosition,e,DrawingArea.getWidth(),DrawingArea.getHeight(),strokeCurrentWidth);

                        DrawingArea.repaint();
                        //System.out.println("dragged a rectangle!");

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

                        Rectangle r = new Rectangle(mousePosition,1,1,getCurrentColor());



                        r.draw(gg);


                        DrawingArea.addShape(r);
                        break;

                    case DRAWING_SQUARE:

                        System.out.println("SQUARE drawing mode!!");
                        updateMousePosition(e);

                        Square square = new Square(mousePosition,1,getCurrentColor());



                        square.draw(gg);


                        DrawingArea.addShape(square);

                        break;
                    case DRAWING_ELLIPSE:
                        // drawing mode
                        System.out.println("ELLIPSE drawing mode!!");
                        updateMousePosition(e);

                        Ellipse eli = new Ellipse(mousePosition,1,1,getCurrentColor());



                        eli.draw(gg);


                        DrawingArea.addShape(eli);
                        break;
                    case DRAWING_CIRCLE:
                        // drawing mode
                        System.out.println("Circle drawing mode!!");
                        updateMousePosition(e);

                        Circle circle = new Circle(mousePosition,1,getCurrentColor());

                        circle.draw(gg);


                        DrawingArea.addShape(circle);
                        break;
                    case DRAWING_HEART:
                        // drawing mode
                        System.out.println("Heart drawing mode!!");
                        updateMousePosition(e);

                        Heart heart = new Heart(mousePosition,1,1,getCurrentColor());

                        heart.draw(gg);


                        DrawingArea.addShape(heart);
                        break;
                    case DRAWING_TRIANGLE:
                        // drawing mode
                        System.out.println("Triangle drawing mode!!");
                        updateMousePosition(e);

                        Triangle triangle = new Triangle(mousePosition,1,1,getCurrentColor());

                        triangle.draw(gg);


                        DrawingArea.addShape(triangle);
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

                    case XOR:
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
                    case DRAWING_TRIANGLE:

                        Triangle triangle = (Triangle) DrawingArea.getLastShape();

                        updateMousePosition(e);

                        triangle.updateShapeDimensions(e,DrawingArea.getWidth(),DrawingArea.getHeight(),strokeCurrentWidth);

                        DrawingArea.repaint();
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
        heartDrawingModeButton.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                resetOperatorVariables();
                setCurrentMode(MouseMode.DRAWING_HEART);
                enableAllDrawingButtonsExcept(heartDrawingModeButton);
                System.out.println("set mode to heart drawing");
            }
        });
        triangleDrawingModeButton.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                resetOperatorVariables();
                setCurrentMode(MouseMode.DRAWING_TRIANGLE);
                enableAllDrawingButtonsExcept(triangleDrawingModeButton);
                System.out.println("set mode to heart drawing");
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

        selectionModeButton.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                resetOperatorVariables();
                CurrentMode = MouseMode.SELECTION;
                enableAllDrawingButtonsExcept(selectionModeButton);
                System.out.println("set mode to SELECTION");
            }
        });

        moreColorsButton.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                setCurrentColor(JColorChooser.showDialog(MainPanel, "select a coloor",java.awt.Color.BLUE,true));



                //System.out.println(getCurrentColor());
            }
        });

        New.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawingArea.clearShapes(); // Clear the DrawingArea
                DrawingArea.repaint(); // Repaint the panel to reflect the changes
            }
        });

        redSpinner.setModel(new SpinnerNumberModel(255,0,255,1));
        greenSpinner.setModel(new SpinnerNumberModel(255,0,255,1));
        blueSpinner.setModel(new SpinnerNumberModel(255,0,255,1));

        redSpinner.setValue(0); // default value is 0 when init
        greenSpinner.setValue(0); // default value is 0 when init
        blueSpinner.setValue(0); // default value is 0 when init


        colorSetButton.addActionListener(new ActionListener() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                int red = (int) redSpinner.getValue();
                int green = (int) greenSpinner.getValue();
                int blue = (int) blueSpinner.getValue();

                if(isValidRGBValue(red) && isValidRGBValue(green) && isValidRGBValue(blue)){
                    setCurrentColor(new Color(red,green,blue));
                }
            }
        });
    }

    private void enableAllDrawingButtonsExcept(JButton buttonToExclude) {
        rectangleDrawingModeButton.setEnabled(true);
        circleDrawingModeButton.setEnabled(true);
        ellipseDrawingModeButton.setEnabled(true);
        squareDrawingModeButton.setEnabled(true);
        heartDrawingModeButton.setEnabled(true);
        triangleDrawingModeButton.setEnabled(true);
        selectionModeButton.setEnabled(true);
        intersectionOperatorButton.setEnabled(true);
        unionOperatorButton.setEnabled(true);
        xorOperatorButton.setEnabled(true);
        substractOperatorButton.setEnabled(true);
        buttonToExclude.setEnabled(false);// Disable the button that activated polyline mode
    }

    public static void init() {
        JFrame frame = new JFrame("NeoShape");
        frame.setContentPane(new GuiController().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {

    }


    public java.awt.Color getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(java.awt.Color currentColor) {
        this.currentColor = currentColor;
        colorSquarePanel.setBackground(currentColor);
    }

    public void saveShapesToFile(String filename) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            // Écrire la liste des formes dans le fichier
            outputStream.writeObject(DrawingArea.getShapesList());
            System.out.println("Formes sauvegardées dans " + filename);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des formes : " + e.getMessage());
        }
    }

    public void loadShapesFromFile(String filename) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            // Read the list of shapes from the file
            List<CanvasTools> shapes = (List<CanvasTools>) inputStream.readObject();
            DrawingArea.getShapesList().clear(); // Clear existing shapes
            DrawingArea.getShapesList().addAll(shapes); // Add shapes from the file
            System.out.println("Shapes loaded from " + filename);
            DrawingArea.repaint(); // Repaint the panel to show the loaded shapes
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading shapes: " + e.getMessage());
        }
    }
    private void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Shapes");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Shape Files", "shp"));
        int userSelection = fileChooser.showSaveDialog(MainPanel);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filename.endsWith(".shp")) {
                filename += ".shp"; // Ajoute l'extension .shp si elle n'est pas déjà présente
            }
            saveShapesToFile(filename);
        }
    }
    private void startServer(String ipAddress, int port) {
        try {
            // Create a ServerSocket object bound to the provided IP address and port
            // Start listening for incoming connections, handle client connections in a separate thread
            // Implementation of this method is provided in the previous answer
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error starting server: " + e.getMessage(), "Server Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}
