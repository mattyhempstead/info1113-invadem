package invadem;

import processing.core.PImage;

/**
 * A temporary next level screen displayed between levels
 * This screen will exit after 3 seconds to reveal the next level
 */
public class NextLevel extends GameState {
    private static PImage imgNextLevel;

    private int tickCount;    

    public static void loadResources(App app) {
        imgNextLevel = app.loadImage("nextlevel.png");
    }

    public GameState draw(App app) {
        app.image(
            imgNextLevel,
            (640 - 122) / 2,
            (480 - 16) / 2
        );

        // Start next level after 3 seconds
        if (tickCount++ == 3*60) {
            // Decrease shoot cooldown for next level
            Invader.decreaseShootCountdownTickLength();

            if (app.getMode()) {
                return new TwoPlayer(app);
            } else {
                return new OnePlayer(app);
            }
        }

        return this;
    }
}
