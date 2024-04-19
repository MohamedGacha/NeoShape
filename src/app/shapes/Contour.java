package app.shapes;

import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.io.Serializable;

public class Contour implements CanvasTools, Serializable, Shape{

    public Path2D.Double getPath() {
        return path;
    }

    public void setPath(Path2D.Double path) {
        this.path = path;
    }

    Path2D.Double path = new Path2D.Double();
    protected Color shapeColor;

    public void setShapeColor(Color shapeColor) {
        this.shapeColor = shapeColor;
    }

    public Color getShapeColor() {
        return this.shapeColor;
    }


    public Contour(){
        super();
    }

    public Contour(Area a){ // argument is canvastools but should only be area!!!
        this.shapeColor = a.getShapeColor();
        path.append(a.getPathIterator(null), true);
    }

    @Override
    public String toString() {
        return super.toString() + " | color: " + this.shapeColor;
    }


    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(shapeColor);
        g2d.fill(path);
        //System.out.println("drew a path object!!");
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


    @Override
    public boolean select(Point p) {
        return path.contains(p.getX(), p.getY());
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

    /**
     * @return
     */
    @Override
    public Rectangle getBounds() {
        return path.getBounds();
    }

    /**
     * @return
     */
    @Override
    public Rectangle2D getBounds2D() {
        return path.getBounds2D();
    }

    /**
     * @param x the specified X coordinate to be tested
     * @param y the specified Y coordinate to be tested
     * @return
     */
    @Override
    public boolean contains(double x, double y) {
        return path.contains(x,y);
    }

    /**
     * @param p the specified {@code Point2D} to be tested
     * @return
     */
    @Override
    public boolean contains(Point2D p) {
        return path.contains(p);
    }

    /**
     * @param x the X coordinate of the upper-left corner
     *          of the specified rectangular area
     * @param y the Y coordinate of the upper-left corner
     *          of the specified rectangular area
     * @param w the width of the specified rectangular area
     * @param h the height of the specified rectangular area
     * @return
     */
    @Override
    public boolean intersects(double x, double y, double w, double h) {
        return path.intersects(x,y,w,h);
    }

    /**
     * @param r the specified {@code Rectangle2D}
     * @return
     */
    @Override
    public boolean intersects(Rectangle2D r) {
        return path.intersects(r);
    }

    /**
     * @param x the X coordinate of the upper-left corner
     *          of the specified rectangular area
     * @param y the Y coordinate of the upper-left corner
     *          of the specified rectangular area
     * @param w the width of the specified rectangular area
     * @param h the height of the specified rectangular area
     * @return
     */
    @Override
    public boolean contains(double x, double y, double w, double h) {
        return path.contains(x,y,w,h);
    }

    /**
     * @param r The specified {@code Rectangle2D}
     * @return
     */
    @Override
    public boolean contains(Rectangle2D r) {
        return path.contains(r);
    }

    /**
     * @param at an optional {@code AffineTransform} to be applied to the
     *           coordinates as they are returned in the iteration, or
     *           {@code null} if untransformed coordinates are desired
     * @return
     */
    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        return path.getPathIterator(at);
    }

    /**
     * @param at       an optional {@code AffineTransform} to be applied to the
     *                 coordinates as they are returned in the iteration, or
     *                 {@code null} if untransformed coordinates are desired
     * @param flatness the maximum distance that the line segments used to
     *                 approximate the curved segments are allowed to deviate
     *                 from any point on the original curve
     * @return
     */
    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return path.getPathIterator(at,flatness);
    }
}
