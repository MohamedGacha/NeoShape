package app.shapes;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Circle extends Ellipse {

    private static final int MIN_DIAMETER = 4; // Minimum diameter for the circle

    public Circle(Point center, double diameter) {
        super(center, diameter, diameter); // Setting both width and height as diameter
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.draw(this);
        System.out.println("drew a circle!!");
    }

    @Override
    public void updateShapeDimensions(MouseEvent e, int panelWidth, int panelHeight, int strokeWidth) {
        // Calculate the diameter using the square root of the sum of squares of width and height
        double newDiameter = Math.sqrt(Math.pow(Math.abs(e.getX() - this.getCenterX()) * 2, 2)
                + Math.pow(Math.abs(e.getY() - this.getCenterY()) * 2, 2));

        // Ensure the diameter is within the specified limit
        newDiameter = Math.max(newDiameter, MIN_DIAMETER);

        double newX = this.getCenterX() - newDiameter / 2;
        double newY = this.getCenterY() - newDiameter / 2;

        // Ensure the circle remains within the panel bounds
        if (newX < 0) {
            newX = 0;
        }
        if (newY < 0) {
            newY = 0;
        }

        if (newX + newDiameter > panelWidth) {
            newX = panelWidth - newDiameter;
        }
        if (newY + newDiameter > panelHeight) {
            newY = panelHeight - newDiameter;
        }

        this.setFrame(newX, newY, newDiameter, newDiameter);
    }

    @Override
    public boolean select(Point p) {
        double centerX = this.getCenterX();
        double centerY = this.getCenterY();
        double radius = this.getWidth() / 2;
        double distance = Math.sqrt(Math.pow(p.getX() - centerX, 2) + Math.pow(p.getY() - centerY, 2));
        return distance <= radius;
    }
}
