package app.shapes;

import app.JPanelWrapper;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface CanvasTools {


    public abstract void draw(Graphics2D g2d);

    public abstract void updateShapeDimensions(MouseEvent e, int panelWidth,int panelHeight,int strokeWidth);

    public abstract boolean select(Point p);

    public abstract boolean unselect();


    public abstract int getLayer();

}
