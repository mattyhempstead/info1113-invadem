package invadem;

import org.junit.Test;
import static org.junit.Assert.*;

import invadem.gameobject.Button;
import java.util.concurrent.atomic.AtomicBoolean;

public class ButtonTest {

    // Ensure buttons are correctly constructed
    @Test
    public void testButtonConstruction() {
        Button button = new Button(0, 0, 10, 10, "Text", () -> {});
        assertNotNull(button);
    }

    // Ensure buttons can be clicked and act correctly
    @Test
    public void testButtonClick() {
        AtomicBoolean bool = new AtomicBoolean(false);
        Button button = new Button(10, 10, 20, 20, "Text", () -> bool.set(true));
        button.mouseClicked(20, 20);
        assertTrue(bool.get());
    }

    // Ensure buttons will ignore clicks entirely outside of rectangle
    @Test
    public void testButtonClickOutside() {
        AtomicBoolean bool = new AtomicBoolean(false);
        Button button = new Button(10, 10, 20, 20, "Text", () -> bool.set(true));
        button.mouseClicked(5, 5);
        assertFalse(bool.get());
    }

    // Ensure buttons will ignore clicks when x position is to the left of button
    @Test
    public void testButtonClickOutsideLeft() {
        AtomicBoolean bool = new AtomicBoolean(false);
        Button button = new Button(10, 10, 20, 20, "Text", () -> bool.set(true));
        button.mouseClicked(5, 20);
        assertFalse(bool.get());
    }

    // Ensure buttons will ignore clicks when x position is to the right of button
    @Test
    public void testButtonClickOutsideRight() {
        AtomicBoolean bool = new AtomicBoolean(false);
        Button button = new Button(10, 10, 20, 20, "Text", () -> bool.set(true));
        button.mouseClicked(35, 20);
        assertFalse(bool.get());
    }

    // Ensure buttons will ignore clicks when y position is above button
    @Test
    public void testButtonClickOutsideTop() {
        AtomicBoolean bool = new AtomicBoolean(false);
        Button button = new Button(10, 10, 20, 20, "Text", () -> bool.set(true));
        button.mouseClicked(20, 5);
        assertFalse(bool.get());
    }

    // Ensure buttons will ignore clicks when y position is below button
    @Test
    public void testButtonClickOutsideBottom() {
        AtomicBoolean bool = new AtomicBoolean(false);
        Button button = new Button(10, 10, 20, 20, "Text", () -> bool.set(true));
        button.mouseClicked(20, 35);
        assertFalse(bool.get());
    }
}
