package invadem.gamestate;

import invadem.App;
import invadem.gameobject.Button;
import invadem.gamestate.OnePlayer;
import invadem.gamestate.TwoPlayer;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

/**
 * A game state with clickable buttons to play either one or two player modes
 */
public class Menu extends GameState {
    private static PImage img1Player;
    private static PImage img2Player;

    private boolean hasSelectedMode = false;
    private boolean mode = false;

    private ArrayList<Button> buttons = new ArrayList<>();

    public Menu() {
        buttons.add(new Button(
            220 - 150, 
            200, 
            200, 
            50, 
            "1 Player",
            () -> this.selectMode(false)
        ));

        buttons.add(new Button(
            220 + 150, 
            200, 
            200, 
            50, 
            "2 Player",
            () -> this.selectMode(true)
        ));
    }

    public static void loadResources(App app) {
        img1Player = app.loadImage("1player.png");
        img2Player = app.loadImage("2player.png");
    }

    public void mouseClicked(int mouseX, int mouseY) {
        for (Button button : this.buttons) {
            button.mouseClicked(mouseX, mouseY);
        }
    }

    public boolean getHasSelectedMode() {
        return this.hasSelectedMode;
    }

    public boolean getMode() {
        return this.mode;
    }

    public void selectMode(boolean mode) {
        this.mode = mode;
        this.hasSelectedMode = true;
    }

    public GameState draw(App app) {
        app.textAlign(PApplet.CENTER, PApplet.TOP);
        app.text("Invadem", 640/2, 100);

        for (Button button : this.buttons) {
            button.draw(app);
        }

        app.image(img1Player, 50, 320);
        app.image(img2Player, 350, 320 - 32);

        if (hasSelectedMode) {
            if (mode) {
                app.setMode(true);
                return new TwoPlayer(app); 
            } else {
                app.setMode(false);
                return new OnePlayer(app);
            }
        }

        return this;
    }
}
