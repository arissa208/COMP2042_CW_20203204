package test;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BallTest {

    @Test
    void makeBall() {
    }

    @Test
    void move() {
    }

    @Test
    void reverseX() {
    }

    @Test
    void reverseY() {
    }

    @Test
    void moveTo() {
    }

    @Test
    void setSpeed() {
    }

    @Test
    void setSpeedX() {
    }

    @Test
    void setSpeedY() {
    }

    @Test
    void getBorderColor() {
    }

    @Test
    void getInnerColor() {
    }

    @Test
    void getPosition() {
    }

    @Test
    void getBallFace() {
    }

    @Test
    void getSpeedX() {
        int speed = 5;
        int speedX;
        Ball.setSpeedX(speed);
        speedX = Ball.getSpeedX();
        assertEquals(speed, speedX);
    }

    @Test
    void getSpeedY() {
        int speed = -4;
        int speedY;
        Ball.setSpeedY(speed);
        speedY = Ball.getSpeedY();
        assertEquals(speed, speedY);
    }
}