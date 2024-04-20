package app.shapes;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

// precision
public class Rectangle extends java.awt.geom.Rectangle2D.Double implements CanvasTools{



    protected Color shapeColor;

    public void setShapeColor(Color shapeColor) {
        this.shapeColor = shapeColor;
    }

    public Color getShapeColor() {
        return this.shapeColor;
    }




    public Rectangle(Point upper_left_point, double w, double h,Color currentColor){
        super(upper_left_point.x,upper_left_point.y,w,h);
        this.shapeColor = currentColor;
    }

    @Override
    public String toString() {
        return super.toString() + " | color: " + this.shapeColor;
    }

    public void DraggedUpdateShapeDimensions(Point initialPressedPoint, java.awt.event.MouseEvent e, int panelWidth, int panelHeight, int strokeWidth) {
        // has an extra argument to handle left dragging when drawing a shape (rectagle/square)

        //double newupper_x,newupper_y,new_w,new_h;

        //System.out.println("********");


        double newUpperX = Math.min(Math.max(0, e.getX()), Math.max(0, initialPressedPoint.getX()));
        double newUpperY = Math.min(Math.max(0, e.getY()), Math.max(0, initialPressedPoint.getY()));
        double newWidth = Math.min(Math.abs(e.getX() - initialPressedPoint.getX()), panelWidth - newUpperX);
        double newHeight = Math.min(Math.abs(e.getY() - initialPressedPoint.getY()), panelHeight - newUpperY);

        newWidth -= strokeWidth;// incase it sticks to edge
        newHeight -= strokeWidth;

        this.setRect(newUpperX, newUpperY, newWidth, newHeight);
    }

        @Override
    public void updateShapeDimensions(java.awt.event.MouseEvent e,int panelWidth,int panelHeight, int strokeWidth){

    // DONT USE CUZ YOU DONT HAVE THE INITIALLY PRESSED POINT

    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(shapeColor);
        g2d.fillRect((int) this.getX(), (int) this.getY(), (int) this.getWidth(), (int) this.getHeight());
        //System.out.println("drew a rectangle!!");
    }




    @Override
    public boolean select(Point p) {
        return this.contains(p.getX(), p.getY());
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

    @Override
    public CanvasTools copy() {
        // Create a new Rectangle with the same parameters as the current one
        Rectangle copiedRectangle = new Rectangle(new Point(100, 100),
                this.getWidth(),
                this.getHeight(),
                this.getShapeColor());
        return copiedRectangle;
    }

}
