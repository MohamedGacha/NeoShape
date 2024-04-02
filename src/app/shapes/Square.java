package app.shapes;

import java.awt.*;

public class Square extends Rectangle{


    public Square(Point upper_left_point, double edge) {
        super(upper_left_point, edge, edge);
    }

    @Override
    public void DraggedUpdateShapeDimensions(Point initialPressedPoint,java.awt.event.MouseEvent e,int panelWidth,int panelHeight, int strokeWidth) {
        //System.out.println("********");
        double startX = Math.min(initialPressedPoint.getX(), e.getX());
        double startY = Math.max(initialPressedPoint.getY(), e.getY());
        double endX = Math.max(initialPressedPoint.getX(), e.getX());
        double endY = Math.min(initialPressedPoint.getY(), e.getY());


        double width = Math.abs(endX - startX);
        double height = Math.abs(endY - startY);
        double size = Math.min(width, height); // edge length

        // adjusting end coordinates
        endX = startX + size;
        endY = startY - size; // Subtract size to move upwards

        this.setFrameFromDiagonal(startX, startY, endX, endY);
    }
}
