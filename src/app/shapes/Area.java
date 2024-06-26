package app.shapes;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.io.Serializable;


// this class is created especially for the output of operations that create objects with empty space between them
// i thought of 2 implementations: either modify the rectangle's class draw method behaviour to draw only the shapes needed and keep everything else empty
// or have the output of operations a new class that does that; this will be that class, its the same as the 1st method just with a new class,
// i will try to implement the 1st method, if not successful i ll be using this class
public class Area extends java.awt.geom.Area implements CanvasTools, Serializable {

    protected Color shapeColor;


    public void setShapeColor(Color shapeColor) {
        this.shapeColor = shapeColor;
    }

    public Color getShapeColor() {
        return this.shapeColor;
    }
    public Area(Shape shape, Color mixedColor) {
        super(shape);
        this.shapeColor = mixedColor;
    }

    public Area(Contour c, Color mixedColor) {
        super(c.getPath());
        this.shapeColor = mixedColor;
    }

    /**
     *
     */
    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(shapeColor);
        g2d.fill(this);
        //System.out.println("drew the area!!");
    }



    /**
     * @param e
     * @param panelWidth
     * @param panelHeight
     * @param strokeWidth
     */
    @Override
    public void updateShapeDimensions(MouseEvent e, int panelWidth, int panelHeight, int strokeWidth) {
        // not needed here
    }

    /**
     * @param p
     * @return
     */
    @Override
    public boolean select(Point p) {
        return contains(p.getX(),p.getY());
    }

    /**
     * @return
     */
    @Override
    public boolean unselect() {
        return false;
    }

    @Override
    public int getLayer() {
        return 0;
    }


    @Override
    public String toString() {
        return "Area{" +
                "shapeColor=" + shapeColor +
                '}';
    }

    /**
     * @return
     */
    public Area copy() {
        // Create a new Area with the same shape and color
        return new Area(this, shapeColor);
    }

    /**
     * Moves the shape by the specified offset.
     *
     * @param dx The horizontal offset.
     * @param dy The vertical offset.
     */
    public void move(int dx, int dy) {
        AffineTransform tx = AffineTransform.getTranslateInstance(dx, dy);
        this.transform(tx);
    }



}
