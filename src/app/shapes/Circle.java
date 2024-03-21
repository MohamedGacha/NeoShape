package app.shapes;
import java.awt.Point;

public class Circle {
    private boolean selected = false;
    private  Point center;


    public Circle(Point center) {
        this.center = center;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
