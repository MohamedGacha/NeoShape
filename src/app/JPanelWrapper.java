package app;

import app.shapes.CanvasTools;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class JPanelWrapper extends JPanel {

    private final ArrayList<CanvasTools> ShapesList = new ArrayList<>();
    public ArrayList<CanvasTools> getShapesList() {
        return ShapesList;
    }

    public CanvasTools getLastShape() {
        return ShapesList.get(ShapesList.size()-1);
    }

    public void addShape(CanvasTools newShape) {
        ShapesList.add(newShape);
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D gg = (Graphics2D)g;
        gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for(CanvasTools s:ShapesList){
            s.draw((Graphics2D) g);
        }
    }

}
