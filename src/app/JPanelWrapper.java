package app;

import app.shapes.Area;
import app.shapes.CanvasTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Stack;


public class JPanelWrapper extends JPanel {

    private final ArrayList<CanvasTools> ShapesList = new ArrayList<>();
    private final Stack<ArrayList<CanvasTools>> shapeListStack = new Stack<>();

    private int posCurrentlySelectedShape = -1;

    public int getNumberOfShapes(){
        return ShapesList.size();
    }

    public void deleteShape(int index) {
        pushToStack();
        ShapesList.remove(index);
    }

    public void setShapeAtIndex(CanvasTools s, int index){
        ShapesList.set(index,s);
    }

    public CanvasTools getShapeAtIndex(int index){
        return ShapesList.get(index);
    }

    public void deleteShape(CanvasTools shape){
        pushToStack();
        ShapesList.removeIf(object -> object.equals(shape));
    }

    private Color CurrentColor = new Color(0, 0, 0, 228);
    public ArrayList<CanvasTools> getShapesList() {
        return ShapesList;
    }

    public CanvasTools getLastShape() {
        return ShapesList.get(ShapesList.size()-1);
    }


//    public int selectShape(CanvasTools s){ // selects shape and returns its position
//        int shape_idx = ShapesList.indexOf(s);
//        if(shape_idx != -1){ // found the shape
//            ShapesList.get(shape_idx).select();
//        }
//        return shape_idx;
//    }


    public void addShape(CanvasTools newShape) {
        pushToStack();
        ShapesList.add(newShape);
    }




    public void clearShapes() {
        pushToStack();
        ShapesList.clear();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D gg = (Graphics2D) g;
        gg.setPaint(CurrentColor);
        gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (CanvasTools s : ShapesList) {
            s.draw(gg);
        }
        if (posCurrentlySelectedShape != -1) { // a shape is selected
            if (posCurrentlySelectedShape < ShapesList.size()) { // Check if posCurrentlySelectedShape is within bounds
                PathIterator selectedShapePath = ((Shape) getShapeAtIndex(posCurrentlySelectedShape)).getPathIterator(null);
                Path2D.Double contour = new Path2D.Double();
                contour.append(selectedShapePath, true);
                gg.setColor(Color.red);// Contour color
                gg.setStroke(new BasicStroke(2)); // Contour stroke width
                gg.draw(contour);
            }
        }
    }

    public void setAndRepaintShapesList(ArrayList<CanvasTools> newShapesList) {
        // Set the new shape list
        ShapesList.clear();
        ShapesList.addAll(newShapesList);

        // Repaint the panel to reflect the changes
        repaint();
    }

    public void deleteSelectedShape() {
        pushToStack();
        // Get the position of the currently selected shape
        int posCurrentlySelectedShape = getPosCurrentlySelectedShape();

        // Check if a shape is currently selected
        if (posCurrentlySelectedShape != -1) {
            // Delete the selected shape from the ShapesList
            deleteShape(posCurrentlySelectedShape);

            // Clear the selection (optional)
            setPosCurrentlySelectedShape(-1);

            // Repaint the DrawingArea to reflect the changes
            repaint();
        }
    }

    public void setShapesList(ArrayList<CanvasTools> shapesList) {
        this.ShapesList.clear();
        this.ShapesList.addAll(shapesList);
    }


    public Color getCurrentColor() {
        return CurrentColor;
    }

    public void setCurrentColor(Color currentColor) {
        CurrentColor = currentColor;
    }

    public int getPosCurrentlySelectedShape() {
        return posCurrentlySelectedShape;
    }

    public void setPosCurrentlySelectedShape(int posCurrentlySelectedShape) {
        this.posCurrentlySelectedShape = posCurrentlySelectedShape;
    }

    public void undo() {
        if (!shapeListStack.isEmpty()) {
            ShapesList.clear();
            ShapesList.addAll(shapeListStack.pop());
            repaint();
        }
    }

    public void pushToStack() {
        shapeListStack.push(new ArrayList<>(ShapesList));
    }
}
