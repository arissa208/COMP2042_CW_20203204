package test;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.Point2D;

public class ClayBrick extends Brick {

    private static final Color DEF_INNER = new Color(114, 39, 39).darker();
    private static final Color DEF_BORDER = Color.GRAY;
    private static final int CLAY_STRENGTH = 1;

    /** Represents description of Clay Brick
     * @param point position of brick
     * @param size size of brick
     */
    public ClayBrick(Point point, Dimension size) {
        super(point, size, DEF_BORDER, DEF_INNER, CLAY_STRENGTH);
    }

    /** Creates clay brick
     * @param pos position of brick
     * @param size size of brick
     * @return shape of brick
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos, size);
    }

    /** Gets brick shape
     * @return shape of brick
     */
    @Override
    public Shape getBrick() {
        return super.brickFace;
    }


}
