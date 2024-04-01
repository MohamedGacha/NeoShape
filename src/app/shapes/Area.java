package app.shapes;

import java.awt.*;


// this class is created especially for the output of operations that create objects with empty space between them
// i thought of 2 implementations: either modify the rectangle's class draw method behaviour to draw only the shapes needed and keep everything else empty
// or have the output of operations a new class that does that; this will be that class, its the same as the 1st method just with a new class,
// i will try to implement the 1st method, if not successful i ll be using this class
public class Area implements CanvasTools{
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
