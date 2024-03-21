package app.shapes;

import java.awt.*;

public interface CanvasTools {


    public abstract void draw();

    public abstract boolean select(Point p);

    public abstract boolean unselect();

    public abstract void fill();

    public abstract int getLayer();

}
