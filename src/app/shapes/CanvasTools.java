package app.shapes;

import java.awt.*;

public interface CanvasTools {


    public abstract void draw(Graphics2D g2d);

    public abstract boolean select(Point p);

    public abstract boolean unselect();

    public abstract void fill();

    public abstract int getLayer();

}
