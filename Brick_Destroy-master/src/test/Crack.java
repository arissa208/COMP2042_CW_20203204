package test;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

public class Crack {

    private static final int CRACK_SECTIONS = 3;
    private static final double JUMP_PROBABILITY = 0.7;

    public static final int LEFT = 10;
    public static final int RIGHT = 20;
    public static final int UP = 30;
    public static final int DOWN = 40;
    public static final int VERTICAL = 100;
    public static final int HORIZONTAL = 200;

    private final Brick brick;
    private final GeneralPath crack;

    private final int crackDepth;
    private final int steps;

    /** Creates a crack
     * @param brick Brick element
     * @param crackDepth Depth of crack
     * @param steps Crack steps
     */
    public Crack(Brick brick, int crackDepth, int steps) {

        this.brick = brick;
        crack = new GeneralPath();
        this.crackDepth = crackDepth;
        this.steps = steps;

    }

    /** Drawing crack on brick
     * @return crack on brick
     */
    public GeneralPath draw() {
        return crack;
    }

    /** Reset crack
     *
     */
    public void reset() {
        crack.reset();
    }

    /** Makes a crack
     * @param point position of crack
     * @param direction direction of crack
     */
    protected void makeCrack(Point2D point, int direction) {
        Rectangle bounds = brick.brickFace.getBounds();

        Point impact = new Point((int) point.getX(), (int) point.getY());
        Point start = new Point();
        Point end = new Point();

        switch (direction) {
            case LEFT:
                start.setLocation(bounds.x + bounds.width, bounds.y);
                end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                Point tmp = makeRandomPoint(start, end, VERTICAL);
                makeCrack(impact, tmp);

                break;
            case RIGHT:
                start.setLocation(bounds.getLocation());
                end.setLocation(bounds.x, bounds.y + bounds.height);
                tmp = makeRandomPoint(start, end, VERTICAL);
                makeCrack(impact, tmp);

                break;
            case UP:
                start.setLocation(bounds.x, bounds.y + bounds.height);
                end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                tmp = makeRandomPoint(start, end, HORIZONTAL);
                makeCrack(impact, tmp);
                break;
            case DOWN:
                start.setLocation(bounds.getLocation());
                end.setLocation(bounds.x + bounds.width, bounds.y);
                tmp = makeRandomPoint(start, end, HORIZONTAL);
                makeCrack(impact, tmp);

                break;
        }
    }

    /** Determines where crack starts and ends
     * @param start point where crack starts
     * @param end point where crack ends
     */
    protected void makeCrack(Point start, Point end) {

        GeneralPath path = new GeneralPath();

        path.moveTo(start.x, start.y);

        double w = (end.x - start.x) / (double) steps;
        double h = (end.y - start.y) / (double) steps;

        int bound = crackDepth;
        int jump = bound * 5;

        double x, y;

        for (int i = 1; i < steps; i++) {

            x = (i * w) + start.x;
            y = (i * h) + start.y + randomInBounds(bound);

            if (inMiddle(i, steps))
                y += jumps(jump);

            path.lineTo(x, y);

        }

        path.lineTo(end.x, end.y);
        crack.append(path, true);
    }

    private int randomInBounds(int bound) {
        int n = (bound * 2) + 1;
        return Brick.getRnd().nextInt(n) - bound;
    }

    private boolean inMiddle(int i, int divisions) {
        int low = (Crack.CRACK_SECTIONS / divisions);
        int up = low * (divisions - 1);

        return (i > low) && (i < up);
    }

    private int jumps(int bound) {

        if (Brick.getRnd().nextDouble() > Crack.JUMP_PROBABILITY)
            return randomInBounds(bound);
        return 0;

    }

    private Point makeRandomPoint(Point from, Point to, int direction) {

        Point out = new Point();
        int pos;

        switch (direction) {
            case HORIZONTAL:
                pos = Brick.getRnd().nextInt(to.x - from.x) + from.x;
                out.setLocation(pos, to.y);
                break;
            case VERTICAL:
                pos = Brick.getRnd().nextInt(to.y - from.y) + from.y;
                out.setLocation(to.x, pos);
                break;
        }
        return out;
    }

}
