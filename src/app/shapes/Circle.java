package app.shapes;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Circle extends Ellipse {

    private static final int MIN_DIAMETER = 4; // Minimum diameter for the circle

    public Circle(Point center, double diameter,Color currentColor) {
        super(center, diameter, diameter,currentColor); // Setting both width and height as diameter
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

}
