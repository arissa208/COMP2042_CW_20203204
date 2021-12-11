package test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WallTest {

    @Test
    void getScore() {
    }

    @Test
    void getBrickCount() {
        int count = 6;
        int brickCount;
        Wall.setBrickCount(count);
        brickCount = Wall.getBrickCount();
        assertEquals(count, brickCount);
    }

    @Test
    void getBallCount() {
    }
}