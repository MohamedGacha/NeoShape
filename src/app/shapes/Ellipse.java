package app.shapes;

import java.awt.*;

// precision
public class Ellipse extends java.awt.geom.Ellipse2D.Double implements CanvasTools{
    /** Ellipse class with double precision
     *
     */
    @Override
    public void draw(Graphics2D g2d) {

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
