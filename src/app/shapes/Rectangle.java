package app.shapes;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

// precision
public class Rectangle extends java.awt.geom.Rectangle2D.Double implements CanvasTools{

    /**
     *
     */

    private static final int RECTANGLE_MIN_WIDTH=4;
    private static final int RECTANGLE_MIN_HEIGHT=4;

    public Rectangle(Point upper_left_point, double w, double h){
        super(upper_left_point.x,upper_left_point.y,w,h);
    }


    public void DraggedUpdateShapeDimensions(Point initialPressedPoint,java.awt.event.MouseEvent e,int panelWidth,int panelHeight, int strokeWidth) {
        // has an extra argument to handle left dragging when drawing a shape (rectagle/square)

        //double newupper_x,newupper_y,new_w,new_h;

        System.out.println("********");

        double newUpperX = Math.min(e.getX(), initialPressedPoint.getX());
        double newUpperY = Math.min(e.getY(), initialPressedPoint.getY());
        double newWidth = Math.abs(e.getX() - initialPressedPoint.getX());
        double newHeight = Math.abs(e.getY() - initialPressedPoint.getY());

        this.setRect(newUpperX, newUpperY, newWidth, newHeight);
    }

        @Override
    public void updateShapeDimensions(java.awt.event.MouseEvent e,int panelWidth,int panelHeight, int strokeWidth){

    // DONT USE CUZ YOU DONT HAVE THE INITIALLY PRESSED POINT

    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.draw(this);
        //System.out.println("drew a rectangle!!");
    }




    @Override
    public boolean select(Point p) {
        return false;
    }

    /**
     * @return
     */
    @Override
    public boolean unselect() {
        return false;
    }

    /**
     *
     */
    @Override
    public void fill() {

    }

    /**
     * @return
     */
    @Override
    public int getLayer() {
        return 0;
    }
}
