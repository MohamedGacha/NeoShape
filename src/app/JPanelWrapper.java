package app;

import app.shapes.CanvasTools;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class JPanelWrapper extends JPanel {

    private final ArrayList<CanvasTools> ShapesList = new ArrayList<>();

    public int getNumberOfShapes(){
        return ShapesList.size();
    }

    public void deleteShape(int index){
        ShapesList.remove(index);
    }

    public void setShapeAtIndex(CanvasTools s, int index){
        ShapesList.set(index,s);
    }

    public CanvasTools getShapeAtIndex(int index){
        return ShapesList.get(index);
    }

    public void deleteShape(CanvasTools shape){
        ShapesList.removeIf(object -> object.equals(shape));
    }

    private Color CurrentColor = new Color(0, 0, 0, 228);
    public ArrayList<CanvasTools> getShapesList() {
        return ShapesList;
    }

    public CanvasTools getLastShape() {
        return ShapesList.get(ShapesList.size()-1);
    }

    public void addShape(CanvasTools newShape) {
        ShapesList.add(newShape);
    }


    public void clearShapes() {
        ShapesList.clear(); // Clear all shapes from the ShapesList
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D gg = (Graphics2D)g;
        gg.setPaint(CurrentColor);
        gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for(CanvasTools s:ShapesList){
            s.draw((Graphics2D) g);
        }
    }

    public Color getCurrentColor() {
        return CurrentColor;
    }

    public void setCurrentColor(Color currentColor) {
        CurrentColor = currentColor;
    }
}
