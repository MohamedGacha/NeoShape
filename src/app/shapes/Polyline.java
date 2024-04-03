package app.shapes;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;

// precision here
public class Polyline extends Path2D.Double implements CanvasTools{
    /**
     *
     */
    @Override
    public void draw(Graphics2D g2d) {

    }

    /**
     * @param e
     * @param panelWidth
     * @param panelHeight
     * @param strokeWidth
     */
    @Override
    public void updateShapeDimensions(MouseEvent e, int panelWidth, int panelHeight, int strokeWidth) {

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
     * @return
     */
    @Override
    public int getLayer() {
        return 0;
    }
}
