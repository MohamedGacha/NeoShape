package app.shapes;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.io.Serializable;

public class Heart extends Path2D.Double implements CanvasTools, Serializable {
    protected Color shapeColor;

    public void setShapeColor(Color shapeColor) {
        this.shapeColor = shapeColor;
    }
    private static final long serialVersionUID = "app.shapes.Heart".hashCode();

    public Color getShapeColor() {
        return this.shapeColor;
    }

    // No-argument constructor required for deserialization
    public Heart() {
        super();
    }
    public Heart(Point startPoint, double width, double height, Color currentColor) {
        super();
        double x = startPoint.getX();
        double y = startPoint.getY();

        // Calculate the control points and endpoints based on the given width and height
        double xCtrl1 = x + width * 0.5;
        double yCtrl1 = y - height * 0.2;
        double xCtrl2 = x + width * 0.2;
        double yCtrl2 = y + height * 0.5;
        double xEnd1 = x;
        double yEnd1 = y + height * 0.6;
        double xEnd2 = x + width * 0.5;
        double yEnd2 = y + height;

        // Move to the starting point
        moveTo(xEnd1, yEnd1);

        // Draw the left side of the heart
        quadTo(xCtrl1, yCtrl1, x, y);

        // Draw the right side of the heart
        quadTo(xCtrl2, yCtrl2, xEnd2, yEnd2);

        // Close the path to complete the shape
        closePath();

        setShapeColor(currentColor);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(shapeColor);
        g2d.fill(this);
    }

    @Override
    public void updateShapeDimensions(MouseEvent e, int panelWidth, int panelHeight, int strokeWidth) {
        // Not applicable for Heart shape
    }


    public void DraggedUpdateShapeDimensions(Point initialPressedPoint, MouseEvent e, int panelWidth, int panelHeight, int strokeWidth) {
        double newWidth = e.getX() - initialPressedPoint.getX();
        double newHeight = e.getY() - initialPressedPoint.getY();

        double x = initialPressedPoint.getX();
        double y = initialPressedPoint.getY();

        // Recreate the heart shape using the new dimensions
        reset(); // Clear the current path

        double beX = x + newWidth / 2;  // bottom endpoint X
        double beY = y + newHeight;     // bottom endpoint Y

        double c1DX = newWidth * 0.35;  // delta X of control point 1
        double c1DY = newHeight * 0.45;  // delta Y of control point 1
        double c2DX = newWidth * 0.15;  // delta X of control point 2
        double c2DY = newHeight * 0.8;  // delta Y of control point 2
        double teDY = newHeight * 0.7;  // delta Y of top endpoint

        moveTo(beX, beY);       // bottom endpoint
        // left side of heart
        curveTo(
                beX - c1DX, beY - c1DY,   // control point 1
                beX - c2DX, beY - c2DY,   // control point 2
                beX, beY - teDY);  // top endpoint
        // right side of heart
        curveTo(
                beX + c2DX, beY - c2DY,   // control point 2
                beX + c1DX, beY - c1DY,   // control point 1
                beX, beY);         // bottom endpoint
        closePath(); // Close the path to complete the shape
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
}
