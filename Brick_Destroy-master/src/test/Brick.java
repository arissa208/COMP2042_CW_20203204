package test;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;

//Brick abstract class
abstract public class Brick {

    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;

    //Impact attributes
    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;
    private boolean broken;

    // getter for rnd
    public static Random getRnd() {
        return rnd;
    }

    //Brick attributes
    private static Random rnd;
    private String name;
    Shape brickFace;
    private Color border;
    private Color inner;
    private int fullStrength;
    private int strength;

    // constructor of brick class
    public Brick(String name, Point pos, Dimension size, Color border, Color inner, int strength) {
        rnd = new Random();
        broken = false;
        this.name = name;
        brickFace = makeBrickFace(pos, size);
        this.border = border;
        this.inner = inner;
        this.fullStrength = this.strength = strength;

    }

    protected abstract Shape makeBrickFace(Point pos, Dimension size);

    public abstract Shape getBrick();

    // getter for borderColor, innerColor
    public Color getBorderColor() {
        return border;
    }

    public Color getInnerColor() {
        return inner;
    }

    // impact method
    public void impact() {
        strength--;
        broken = (strength == 0);
    }

    //setter for impact
    public boolean setImpact(Point2D point, int dir) {
        if (broken)
            return false;
        impact();
        return broken;
    }

    public final boolean isBroken() {
        return broken;
    }

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

    public void repair() {
        broken = false;
        strength = fullStrength;
    }


}





