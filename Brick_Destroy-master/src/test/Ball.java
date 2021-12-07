package test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;


abstract public class Ball {

    private Shape ballFace;

    private final Point2D position;
    Point2D up;
    Point2D down;
    Point2D left;
    Point2D right;

    private final Color border;
    private final Color inner;

    private int speedX;
    private int speedY;


    /** Represents the description of ball
     * @param center Position of the ball
     * @param radiusA Vertical location of the ball
     * @param radiusB Horizontal location of the ball
     * @param inner Color of the ball
     * @param border Colour of ball's border
     */
    public Ball(Point2D center, int radiusA, int radiusB, Color inner, Color border) {
        this.position = center;

        up = new Point2D.Double();
        down = new Point2D.Double();
        left = new Point2D.Double();
        right = new Point2D.Double();

        up.setLocation(center.getX(), center.getY() - (radiusB / 2));
        down.setLocation(center.getX(), center.getY() + (radiusB / 2));

        left.setLocation(center.getX() - (radiusA / 2), center.getY());
        right.setLocation(center.getX() + (radiusA / 2), center.getY());

        ballFace = makeBall(center, radiusA, radiusB);
        this.border = border;
        this.inner = inner;
        speedX = 0;
        speedY = 0;
    }

    /** Creates the ball
     * @param center Position of ball
     * @param radiusA Vertical location of the ball
     * @param radiusB Horizontal location of the ball
     * @return Shape of a ball
     */
    protected abstract Shape makeBall(Point2D center, int radiusA, int radiusB);

    /** Moves ball
     */
    public void move() {
        RectangularShape tmp = (RectangularShape) ballFace;
        position.setLocation((position.getX() + speedX), (position.getY() + speedY));
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((position.getX() - (w / 2)), (position.getY() - (h / 2)), w, h);
        setPosition(w, h);

        ballFace = tmp;
    }
    /** Reverse the horizontal movement of the ball
     */
    public void reverseX() {
        speedX *= -1;
    }

    /** Reverse the vertical movement of ball
     */
    public void reverseY() {
        speedY *= -1;
    }

    /** Moves the ball to a certain point
     * @param p x,y coordinates of a point
     */
    public void moveTo(Point p) {
        position.setLocation(p);

        RectangularShape tmp = (RectangularShape) ballFace;
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((position.getX() - (w / 2)), (position.getY() - (h / 2)), w, h);
        ballFace = tmp;
    }

    /** Set speed for an ball
     * @param x Horizontal speed of ball
     * @param y Vertical speed of ball
     */
    public void setSpeed(int x, int y) {
        speedX = x;
        speedY = y;
    }

    /** Sets the horizontal speed of ball
     * @param s Speed of ball
     */
    public void setSpeedX(int s) {
        speedX = s;
    }

    /** Sets tge vertical speed of ball
     * @param s Speed of ball
     */
    public void setSpeedY(int s) {
        speedY = s;
    }

    /** Set location for an element
     * @param width Width of page
     * @param height Height of page
     */
    private void setPosition(double width, double height) {
        up.setLocation(position.getX(), position.getY() - (height / 2));
        down.setLocation(position.getX(), position.getY() + (height / 2));

        left.setLocation(position.getX() - (width / 2), position.getY());
        right.setLocation(position.getX() + (width / 2), position.getY());
    }

    /** Gets the border color of ball
     * @return Color of ball's border
     */
    public Color getBorderColor() {
        return border;
    }

    /** Gets the inner color of ball
     * @return Inner color of ball
     */
    public Color getInnerColor() {
        return inner;
    }

    /** Get position of ball
     * @return x,y coordinates for position of ball
     */
    public Point2D getPosition() {
        return position;
    }

    /** Gets shape of ball
     * @return shape of ball
     */
    public Shape getBallFace() {
        return ballFace;
    }

    /** Gets horizontal speed of ball
     * @return Integer speed of ball
     */
    public int getSpeedX() {
        return speedX;
    }

    /** Gets vertical speed of ball
     * @return Integer vertical speed of ball
     */
    public int getSpeedY() {
        return speedY;
    }

}
