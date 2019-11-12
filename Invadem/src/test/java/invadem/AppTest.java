package invadem;

import org.junit.Test;
import static org.junit.Assert.*;

import invadem.App;
import invadem.gameobject.Tank;
import invadem.gameobject.invader.Invader;
import processing.core.PApplet;

public class AppTest {

    // Ensure App instance can be correctly constructed
    @Test
    public void testAppConstruction() {
        App app = new App();
        assertNotNull(app);
    }

    // Ensure we can start and draw an Invadem instance through the various game states
    @Test
    public void testAppNextLevel() {
        boolean exceptionRaised = false;
        try {
            // Launch App
            App app = new App();
            app.delay(1000);
            PApplet.runSketch(new String[] {""}, app);
            app.delay(1000);
    
            // Start One Player mode
            app.mouseX = 80;
            app.mouseY = 210;
            app.mouseClicked();
            app.delay(1000);

            // Shoot a single projectile
            for (int i=0; i<3; i++) {
                app.keyCode = 32;
                app.keyReleased();
                app.delay(500);
            }
            app.delay(3000);

            // Remove all invaders to trigger next level transision
            Invader.getInvaders().clear();
            app.delay(5000);

            // Destroy tank to trigger game over condition
            Tank.getTankA().destroy();
            app.delay(5000);

            // Start Two Player mode
            app.mouseX = 380;
            app.mouseY = 210;
            app.mouseClicked();

            app.delay(1000);

        } catch (Exception e) {
            exceptionRaised = true;
        }
        assertFalse(exceptionRaised);
    }

}
