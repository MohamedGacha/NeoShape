package app.shapes;

import java.awt.*;


// precision
public class Arc extends java.awt.geom.Arc2D.Double implements CanvasTools{

    /**
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
