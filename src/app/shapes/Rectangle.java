package app.shapes;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

// precision
public class Rectangle extends java.awt.geom.Rectangle2D.Double implements CanvasTools{

    /**
     *
     */

    public Rectangle(Point upper_left_point, double w, double h){
        super(upper_left_point.x,upper_left_point.y,w,h);
    }


    @Override
    public void draw(Graphics2D g2d) {
        g2d.draw(this);
        System.out.println("drew a rectangle!!");
    }

    /**
     * @param p
     * @return
     */
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
