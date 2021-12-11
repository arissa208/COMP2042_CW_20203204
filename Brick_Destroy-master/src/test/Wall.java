/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package test;

import java.awt.*;
import java.awt.geom.Point2D;


public class Wall {

    private static final int LEVELS_COUNT = 6;
    private static final int CLAY = 1;
    private static final int STEEL = 2;
    private static final int CEMENT = 3;

    private final Rectangle area;

    Brick[] bricks;
    Ball ball;
    Player player;

    private final Brick[][] levels;
    private int level;
    private int score;

    private final Point startPoint;

    public void setBrickCount(int brickCount) {
        this.brickCount = brickCount;
    }

    private int brickCount;
    private int ballCount;
    private boolean ballLost;

    /** Represents the wall of bricks
     * @param drawArea shape of brick
     * @param brickCount number of bricks
     * @param lineCount number of lines of bricks
     * @param brickDimensionRatio dimension ratio of brick
     * @param ballPos position of ball
     */
    public Wall(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos) {

        this.startPoint = new Point(ballPos);

        levels = makeLevels(drawArea, brickCount, lineCount, brickDimensionRatio);
        level = 0;

        ballCount = 3;
        ballLost = false;

        makeBall(ballPos);
        int speedX, speedY;
        speedX = 4;
        speedY = -3;

        ball.setSpeed(speedX, speedY);

        player = new Player((Point) ballPos.clone(), 150, 10, drawArea);

        area = drawArea;

    }

    /** Builds the bricks of the wall
     * @param drawArea shape of brick
     * @param brickCnt number of bricks
     * @param lineCnt size of brick
     * @param brickSizeRatio size ratio of brick
     * @param typeA type of brick
     * @param typeB type of brick
     * @return current level
     */
    private Brick[] makeChessboardLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB) {
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;

        int centerLeft = brickOnLine / 2 - 1;
        int centerRight = brickOnLine / 2 + 1;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] tmp = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen, (int) brickHgt);
        Point p = new Point();

        int i;
        for (i = 0; i < tmp.length; i++) {
            int line = i / brickOnLine;
            if (line == lineCnt)
                break;
            int posX = i % brickOnLine;
            double x = posX * brickLen;
            x = (line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x, y);

            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
            tmp[i] = b ? makeBrick(p, brickSize, typeA) : makeBrick(p, brickSize, typeB);
        }

        for (double y = brickHgt; i < tmp.length; i++, y += 2 * brickHgt) {
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x, y);
            tmp[i] = makeBrick(p, brickSize, typeA);
        }
        return tmp;
    }

    /** Gets new position of ball
     * @param ballPos position of ball
     */
    private void makeBall(Point2D ballPos) {
        ball = new RubberBall(ballPos);
    }

    /** Creates walls of bricks for different levels
     * @param drawArea shape of brick
     * @param brickCount number of bricks
     * @param lineCount number of lines of bricks
     * @param brickDimensionRatio dimension ratio of bricks
     * @return current level
     */
    private Brick[][] makeLevels(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio) {
        Brick[][] tmp = new Brick[LEVELS_COUNT][];
        tmp[0] = makeChessboardLevel(drawArea, brickCount, lineCount, brickDimensionRatio, CLAY, CLAY);
        tmp[1] = makeChessboardLevel(drawArea, brickCount, lineCount, brickDimensionRatio, STEEL, STEEL);
        tmp[2] = makeChessboardLevel(drawArea, brickCount, lineCount, brickDimensionRatio, CEMENT, CEMENT);
        tmp[3] = makeChessboardLevel(drawArea, brickCount, lineCount, brickDimensionRatio, CLAY, STEEL);
        tmp[4] = makeChessboardLevel(drawArea, brickCount, lineCount, brickDimensionRatio, CLAY, CEMENT);
        tmp[5] = makeChessboardLevel(drawArea, brickCount, lineCount, brickDimensionRatio, STEEL, CEMENT);

        return tmp;
    }

    /** Gets player's score
     * @return An integer representing player's score
     */
    public int getScore() {
        return score;
    }

    /** Set player's score
     * @param score An integer representing player's score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /** Moves the ball and player
     *
     */
    public void move() {
        player.move();
        ball.move();
    }

    /** Determines output of impact
     *
     */
    public void findImpacts() {
        if (player.impact(ball)) {
            ball.reverseY();
        } else if (impactWall()) {
            /*for efficiency reverse is done into method impactWall
             * because for every brick program checks for horizontal and vertical impacts
             */
            brickCount--;
        } else if (impactBorder()) {
            ball.reverseX();
        } else if (ball.getPosition().getY() < area.getY()) {
            ball.reverseY();
        } else if (ball.getPosition().getY() > area.getY() + area.getHeight()) {
            ballCount--;
            ballLost = true;
        }
    }

    /** Determines effect of impact
     * @return false
     */
    private boolean impactWall() {
        for (Brick b : bricks) {
            switch (b.findImpact(ball)) {
                //Vertical Impact
                case Brick.UP_IMPACT:
                    ball.reverseY();
                    score += 5;
                    return b.setImpact(ball.down, Crack.UP);

                case Brick.DOWN_IMPACT:
                    ball.reverseY();
                    score += 5;
                    return b.setImpact(ball.up, Crack.DOWN);

                //Horizontal Impact
                case Brick.LEFT_IMPACT:
                    ball.reverseX();
                    score += 5;
                    return b.setImpact(ball.right, Crack.RIGHT);
                case Brick.RIGHT_IMPACT:
                    ball.reverseX();
                    score += 5;
                    return b.setImpact(ball.left, Crack.LEFT);
            }
        }
        return false;
    }

    private boolean impactBorder() {
        Point2D p = ball.getPosition();
        return ((p.getX() < area.getX()) || (p.getX() > (area.getX() + area.getWidth())));
    }

    public static int getBrickCount() {
        return brickCount;
    }

    public int getBallCount() {
        return ballCount;
    }

    public boolean isBallLost() {
        return ballLost;
    }

    public void ballReset() {
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        int speedX, speedY;
        speedX = 4;
        speedY = -3;

        ball.setSpeed(speedX, speedY);
        ballLost = false;
    }

    public void wallReset() {
        for (Brick b : bricks)
            b.repair();
        brickCount = bricks.length;
        ballCount = 3;
    }

    public boolean ballEnd() {
        return ballCount == 0;
    }

    public boolean isDone() {
        return brickCount == 0;
    }

    public void nextLevel() {
        bricks = levels[level++];
        this.brickCount = bricks.length;
    }

    public boolean hasLevel() {
        return level < levels.length;
    }

    public void setBallXSpeed(int s) {
        ball.setSpeedX(s);
    }

    public void setBallYSpeed(int s) {
        ball.setSpeedY(s);
    }

    public void resetBallCount() {
        ballCount = 3;
    }

    private Brick makeBrick(Point point, Dimension size, int type) {
        Brick out;
        switch (type) {
            case CLAY:
                out = new ClayBrick(point, size);
                break;
            case STEEL:
                out = new SteelBrick(point, size);
                break;
            case CEMENT:
                out = new CementBrick(point, size);
                break;
            default:
                throw new IllegalArgumentException(String.format("Unknown Type:%d\n", type));
        }
        return out;
    }

}
