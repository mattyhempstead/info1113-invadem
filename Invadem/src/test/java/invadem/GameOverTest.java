package invadem;

import org.junit.Test;
import static org.junit.Assert.*;

import invadem.gamestate.GameOver;

public class GameOverTest {

    // Ensure GameOver instance can be correctly constructed
    @Test
    public void testGameOverConstruction() {
        GameOver gameOver = new GameOver();
        assertNotNull(gameOver);
    }

}
