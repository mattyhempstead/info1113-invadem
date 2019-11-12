package invadem.gamestate;

import invadem.App;
import invadem.gameobject.invader.Invader;

import processing.core.PImage;

/**
 * A temporary game over screen displayed when game is lost
 * This screen will exit after 3 seconds to reveal a new game
 */
public class GameOver extends GameState {
    private static PImage imgGameOver;
    
    private int tickCount;

    public static void loadResources(App app) {
        imgGameOver = app.loadImage("gameover.png");
    }

    public GameState draw(App app) {
        app.image(
            imgGameOver,
            (640 - 112) / 2,
            (480 - 16) / 2
        );

        // Display game over message for 3 seconds
        if (tickCount++ == 3*60) {
            // Reset shoot cooldown for new game
            Invader.resetShootCountdownTickLength();

            app.updateHighScore();

            return new Menu();
        }

        return this;
    }
}
