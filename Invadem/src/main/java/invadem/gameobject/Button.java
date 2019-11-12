package invadem.gameobject;

import invadem.App;

import processing.core.PApplet;
import java.lang.Runnable;

public class Button extends GameObject {
    private String text;
    private Runnable action;

    public Button(int posX, int posY, int width, int height, String text, Runnable action) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.text = text;
        this.action = action;
    }

    /**
     * Executes the Button action if a given set of coordinates lies within the buttons dimentions
     * @param mouseX The x coordinate of the mouse
     * @param mouseY The y coordinate of the mouse
     */
    public void mouseClicked(int mouseX, int mouseY) {
        if (this.posX <= mouseX && mouseX <= this.posX + width &&
            this.posY <= mouseY && mouseY <= this.posY + height) {
            this.action.run();
        }
    }

    public void draw(App app) {
        app.fill(0);
        app.stroke(255);
        app.rect(this.posX, this.posY, this.width, this.height);
        app.fill(255);

        app.textAlign(PApplet.CENTER, PApplet.CENTER);
        app.text(
            this.text,
            this.posX + this.width/2, 
            this.posY + this.height/2
        );
    }
}
