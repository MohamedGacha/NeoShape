package app.shapes;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

// precision
public class Rectangle extends java.awt.geom.Rectangle2D.Double implements CanvasTools{

    /**
     *
     */

    private static final int RECTANGLE_MIN_WIDTH=4;
    private static final int RECTANGLE_MIN_HEIGHT=4;

    public Rectangle(Point upper_left_point, double w, double h){
        super(upper_left_point.x,upper_left_point.y,w,h);
    }


    public void updateRectDimensions(java.awt.event.MouseEvent e,int panelWidth,int panelHeight, int strokeWidth){
        double newUpperCornerX = Math.max(Math.min(e.getX(), this.getX()),0);
        double newUpperCornerY = Math.max(Math.min(e.getY(), this.getY()),0);
        double newWidth = Math.abs(e.getX() - this.getX());
        double newHeight = Math.abs(e.getY() - this.getY());

        // the mousedrag event keeps firing until mouse is released, so we need to check if width has changed first
        if(newWidth > RECTANGLE_MIN_WIDTH && newHeight > RECTANGLE_MIN_HEIGHT){




        // make sure the rectangle is within the drawing panel's bounds
        if (newUpperCornerX + newWidth > panelWidth) {
            System.out.println("limited width");
            newWidth = panelWidth - newUpperCornerX - strokeWidth;
        }
        if (newUpperCornerY + newHeight > panelHeight) {
            System.out.println("limited height");
            newHeight = panelHeight - newUpperCornerY - strokeWidth;
        }

        this.setRect(newUpperCornerX, newUpperCornerY, newWidth, newHeight);
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.draw(this);
        System.out.println("drew a rectangle!!");
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
