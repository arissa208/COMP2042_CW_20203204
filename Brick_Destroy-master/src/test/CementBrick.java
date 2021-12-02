package test;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;


public class CementBrick extends Brick {

    private static final Color DEF_INNER = new Color(147, 147, 147);
    private static final Color DEF_BORDER = new Color(217, 199, 175);
    private static final int CEMENT_STRENGTH = 2;

    private final Crack crack;
    private Shape brickFace;

    /** Representing the descriptions Cement Brick
     * @param point x,y coordinates for position of brick
     * @param size size of the brick
     */
    public CementBrick(Point point, Dimension size) {
        super(point, size, DEF_BORDER, DEF_INNER, CEMENT_STRENGTH);
        crack = new Crack(CementBrick.this, DEF_CRACK_DEPTH, DEF_STEPS);
        brickFace = super.brickFace;
    }

    /** Creates cement brick element
     * @param pos x,y coordinates position of brick
     * @param size size of the brick
     * @return shape of the brick
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos, size);
    }

    /** Set impact on brick
     * @param point Position of impact
     * @param dir   Direction of impact
     * @return false if brick is broken
     */
    @Override
    public boolean setImpact(Point2D point, int dir) {
        if (!super.isBroken())
            return false;
        super.impact();
        if (super.isBroken()) {
            crack.makeCrack(point, dir);
            updateBrick();
            return false;
        }
        return true;
    }

    /** Gets information on brick
     * @return shape of Brick
     */
    @Override
    public Shape getBrick() {
        return brickFace;
    }

    /** Updates brick condition
     *
     */
    private void updateBrick() {
        if (super.isBroken()) {
            GeneralPath gp = crack.draw();
            gp.append(super.brickFace, false);
            brickFace = gp;
        }
    }

    /** Returns brick to original condition before impact
     *
     */
    public void repair() {
        super.repair();
        crack.reset();
        brickFace = super.brickFace;
    }
}
