package test;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;

abstract public class Brick {

    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;

    //Impact attributes
    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;
    private boolean broken;

    private static Random rnd;
    private String name;
    Shape brickFace;
    private Color border;
    private Color inner;
    private int fullStrength;
    private int strength;

    protected abstract Shape makeBrickFace(Point pos, Dimension size);
    public abstract Shape getBrick();

    /** Represents the description of brick
     * @param pos Position of bricks
     * @param size Size of brick
     * @param border The border of the brick
     * @param inner Colour of the brick
     * @param strength Strength of brick
     */
    public Brick(Point pos, Dimension size, Color border, Color inner, int strength) {
        rnd = new Random();
        broken = false;
        brickFace = makeBrickFace(pos, size);
        this.border = border;
        this.inner = inner;
        this.fullStrength = this.strength = strength;
    }

    /** Gets the bricks' border color
     * @return Color of the bricks' border
     */
    public Color getBorderColor() {
        return border;
    }

    /** Gets the brick's color
     * @return Color of brick
     */
    public Color getInnerColor() {
        return inner;
    }

    /** Generates a random number
     * @return random number
     */
    public static Random getRnd() {
        return rnd;
    }

    /** Reduces the strength of brick after impact
     *
     */
    public void impact() {
        strength--;
        broken = (strength == 0);
    }

    /** Sets the impact on the brick
     * @param point Position of impact
     * @param dir Direction of impact
     * @return true if broken is false, otherwise false.
     */
    public boolean setImpact(Point2D point, int dir) {
        if (broken)
            return false;
        impact();
        return broken;
    }

    /** Gets condition of brick, whether it's broken or otherwise
     * @return true if broken is true, otherwise false.
     */
    public final boolean isBroken() {
        return broken;
    }

    /** Gets all the impact on the brick
     * @param b Ball
     * @return Integer representing the direction of impact
     */
    public final int findImpact(Ball b) {
        if (broken)
            return 0;
        int out = 0;
        if (brickFace.contains(b.right))
            out = LEFT_IMPACT;
        else if (brickFace.contains(b.left))
            out = RIGHT_IMPACT;
        else if (brickFace.contains(b.up))
            out = DOWN_IMPACT;
        else if (brickFace.contains(b.down))
            out = UP_IMPACT;
        return out;
    }

    /** Resets the condition of the bricks
     *
     */
    public void repair() {
        broken = false;
        strength = fullStrength;
    }
}





