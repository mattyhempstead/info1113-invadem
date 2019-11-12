package invadem;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import invadem.App;
import processing.core.PApplet;

public class AppTest {

    // Ensure App instance can be correctly constructed
    @Test
    public void testAppConstruction() {
        App app = new App();
        assertNotNull(app);
    }

    // Ensure we can start One Player game and perform basic movement/shooting
    @Test
    public void testAppDraw() {
        boolean exceptionRaised = false;
        try {
            // Launch App
            App app = new App();
            PApplet.runSketch(new String[] {""}, app);
            app.delay(500);
    
            // Start One Player mode
            app.mouseX = 80;
            app.mouseY = 210;
            app.mouseClicked();
            app.delay(500);
    
            // Shoot barrier directly above Player
            app.keyCode = 32;
            app.keyReleased();
            app.delay(500);
            app.keyReleased();
            app.delay(500);
            app.keyReleased();
            app.delay(500);

            // Shoot 3 projectiles at invaders
            app.keyReleased();
            app.delay(200);
            app.keyReleased();
            app.delay(200);
            app.keyReleased();
            app.delay(1000);
    
            // Move left for 1 seconds
            app.keyCode = 37;
            app.keyPressed();
            app.delay(1000);
            app.keyReleased();
    
            // Move right for 2 seconds
            app.keyCode = 39;
            app.keyPressed();
            app.delay(2000);
            app.keyReleased();
    
            app.delay(1000);
        } catch (Exception e) {
            exceptionRaised = true;
        }
        assertFalse(exceptionRaised);
    }

}
