package app.shapes;

import java.awt.*;

public class Square extends Rectangle{

    /**
     * @param upper_left_point
     * @param w
     * @param h
     */
    public Square(Point upper_left_point, double edge) {
        super(upper_left_point, edge, edge);
    }
}
