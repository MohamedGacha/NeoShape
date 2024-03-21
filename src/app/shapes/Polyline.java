package app.shapes;

import java.awt.*;
import java.awt.geom.Path2D;

// precision here
public class Polyline extends Path2D.Double implements CanvasTools{
    /**
     *
     */
    @Override
    public void draw() {

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
