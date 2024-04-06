package app.shapes;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Ellipse extends java.awt.geom.Ellipse2D.Double implements CanvasTools {

    protected Color shapeColor;

    public void setShapeColor(Color shapeColor) {
        this.shapeColor = shapeColor;
    }

    public Color getShapeColor() {
        return this.shapeColor;
    }
    private static final int MIN_WIDTH = 4; // Minimum width for the ellipse
    private static final int MIN_HEIGHT = 4; // Minimum height for the ellipse

    public Ellipse(Point center, double width, double height,Color currentColor) {
        super(center.x - width / 2, center.y - height / 2, width, height);
        setShapeColor(currentColor);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(shapeColor);
        g2d.fillOval((int) this.getX(), (int) this.getY(), (int) this.getWidth(), (int) this.getHeight());
        //System.out.println("drew an oval!!");
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
    public int getLayer() {
        return 0;
    }


}
