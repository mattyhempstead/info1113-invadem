package invadem;

import org.junit.Test;
import static org.junit.Assert.*;

import invadem.gamestate.Menu;

public class MenuTest {

    // Ensure Menu instance can be correctly constructed
    @Test
    public void testMenuConstruction() {
        Menu menu = new Menu();
        assertNotNull(menu);
    }

    // Ensures a mode is not selected by default
    @Test
    public void testMenuGetHasSelectedMode() {
        Menu menu = new Menu();
        assertFalse(menu.getHasSelectedMode());
    }

    // Ensures 1 player mode can be selected correctly
    @Test
    public void testMenuSelectModeOne() {
        Menu menu = new Menu();
        menu.selectMode(false);
        assertTrue(menu.getHasSelectedMode());
        assertFalse(menu.getMode());
    }

    // Ensures 2 player mode can be selected correctly
    @Test
    public void testMenuSelectModeTwo() {
        Menu menu = new Menu();
        menu.selectMode(true);
        assertTrue(menu.getHasSelectedMode());
        assertTrue(menu.getMode());
    }

    // Ensures clicking outside both mode buttons will not select a mode
    @Test
    public void testMenuSelectNoButtons() {
        Menu menu = new Menu();
        menu.mouseClicked(10, 10);
        assertFalse(menu.getHasSelectedMode());
    }

    // Ensures clicking on 1 player button will select 1 player mode
    @Test
    public void testMenuSelectOnePlayer() {
        Menu menu = new Menu();
        menu.mouseClicked(80, 210);
        assertTrue(menu.getHasSelectedMode());
        assertFalse(menu.getMode());
    }

    // Ensures clicking on 2 player button will select 2 player mode
    @Test
    public void testMenuSelectTwoPlayer() {
        Menu menu = new Menu();
        menu.mouseClicked(380, 210);
        assertTrue(menu.getHasSelectedMode());
        assertTrue(menu.getMode());
    }
}
