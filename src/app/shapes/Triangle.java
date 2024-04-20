package app.shapes;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.io.Serializable;

public class Triangle extends Path2D.Double implements CanvasTools, Serializable {

    protected Color shapeColor;
    protected double width;
    protected double height;

    public void setShapeColor(Color shapeColor) {
        this.shapeColor = shapeColor;
    }

    public Color getShapeColor() {
        return this.shapeColor;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public Triangle(Point startPoint, double width, double height, Color currentColor) {
        double x = startPoint.getX();
        double y = startPoint.getY();

        // Calculate the points for the triangle
        double x1 = x + width / 2;
        double y1 = y;
        double x2 = x;
        double y2 = y + height;
        double x3 = x + width;
        double y3 = y + height;

        // Move to the starting point
        moveTo(x1, y1);

        // Draw the triangle
        lineTo(x2, y2);
        lineTo(x3, y3);

        // Close the path to complete the shape
        closePath();

        this.width = width;
        this.height = height;
        setShapeColor(currentColor);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(shapeColor);
        g2d.fill(this);
    }

    @Override
    public void updateShapeDimensions(MouseEvent e, int panelWidth, int panelHeight, int strokeWidth) {
        // Not applicable for Triangle shape
    }

    public void DraggedUpdateShapeDimensions(Point initialPressedPoint, MouseEvent e, int panelWidth, int panelHeight, int strokeWidth) {
        double newWidth = e.getX() - initialPressedPoint.getX();
        double newHeight = e.getY() - initialPressedPoint.getY();

        double x = initialPressedPoint.getX();
        double y = initialPressedPoint.getY();

        // Recreate the triangle shape using the new dimensions
        reset(); // Clear the current path

        double x1 = x + newWidth / 2;
        double y1 = y;
        double x2 = x;
        double y2 = y + newHeight;
        double x3 = x + newWidth;
        double y3 = y + newHeight;

        // Move to the starting point
        moveTo(x1, y1);

        // Draw the triangle
        lineTo(x2, y2);
        lineTo(x3, y3);

        // Close the path to complete the shape
        closePath();

        this.width = newWidth;
        this.height = newHeight;
    }

    @Override
    public boolean select(Point p) {
        return contains(p.getX(), p.getY());
    }

    @Override
    public boolean unselect() {
        return false;
    }

    @Override
    public int getLayer() {
        return 0;
    }

    @Override
    public CanvasTools copy() {
        // Create a new Triangle with the same parameters as the current one
        Triangle copiedTriangle = new Triangle(new Point(100, 100),
                getWidth(), getHeight(), getShapeColor());
        return copiedTriangle;
    }
}
