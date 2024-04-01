package app.shapes;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Ellipse extends java.awt.geom.Ellipse2D.Double implements CanvasTools {

    private static final int MIN_WIDTH = 4; // Minimum width for the ellipse
    private static final int MIN_HEIGHT = 4; // Minimum height for the ellipse

    public Ellipse(Point center, double width, double height) {
        super(center.x - width / 2, center.y - height / 2, width, height);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.draw(this);
        System.out.println("drew an ellipse!!");
    }

    @Override
    public void updateShapeDimensions(MouseEvent e, int panelWidth, int panelHeight, int strokeWidth) {
        double newWidth = Math.abs(e.getX() - this.getCenterX()) * 2;
        double newHeight = Math.abs(e.getY() - this.getCenterY()) * 2;

        // Ensure the width and height are within the specified limits
        newWidth = Math.max(newWidth, MIN_WIDTH);
        newHeight = Math.max(newHeight, MIN_HEIGHT);

        double newX = this.getCenterX() - newWidth / 2;
        double newY = this.getCenterY() - newHeight / 2;

        if (newX < 0) {
            newWidth += newX;
            newX = 0;
        }
        if (newY < 0) {
            newHeight += newY;
            newY = 0;
        }

        if (newX + newWidth > panelWidth) {
            newWidth = panelWidth - newX - strokeWidth;
        }
        if (newY + newHeight > panelHeight) {
            newHeight = panelHeight - newY - strokeWidth;
        }

        this.setFrame(newX, newY, newWidth, newHeight);
    }

    @Override
    public boolean select(Point p) {
        return contains(p);
    }

    @Override
    public boolean unselect() {
        return false;
    }

    @Override
    public void fill() {
        // Fill the ellipse with color
    }

    @Override
    public int getLayer() {
        return 0;
    }
}
