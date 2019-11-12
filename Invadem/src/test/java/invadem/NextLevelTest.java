package invadem;

import org.junit.Test;
import static org.junit.Assert.*;

import invadem.gamestate.NextLevel;

public class NextLevelTest {

    // Ensure NextLevel instance can be correctly constructed
    @Test
    public void testNextLevelConstruction() {
        NextLevel nextLevel = new NextLevel();
        assertNotNull(nextLevel);
    }

}
