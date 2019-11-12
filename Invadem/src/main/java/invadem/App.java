package invadem;

import invadem.gameobject.*;
import invadem.gameobject.invader.*;
import invadem.gameobject.projectile.*;
import invadem.gamestate.*;

import processing.core.PApplet;
import processing.core.PFont;

public class App extends PApplet {
    private GameState gameState;

    private int highScore = 10000;
    private int currScore = 0;
    
    private boolean twoPlayer;

    private PFont font;

    public void setup() {
        frameRate(60);

        this.gameState = new Menu();    // Game starts in the "Menu" state
        
        this.font = createFont("PressStart2P-Regular.ttf", 16);
        textFont(this.font);

        // Resources are loaded and stored in static variables in their respective classes
        // This prevents the need to reload resources each time they are needed by a new object instance
        Tank.loadResources(this);
        Invader.loadResources(this);
        ArmouredInvader.loadResources(this);
        PowerInvader.loadResources(this);
        Projectile.loadResources(this);
        PowerProjectile.loadResources(this);
        Barrier.loadResources(this);
        Menu.loadResources(this);
        NextLevel.loadResources(this);
        GameOver.loadResources(this);
    }

    public void settings() {
        size(640, 480);
    }

    public void draw() { 
        background(0);
        this.gameState = this.gameState.draw(this);
    }

    public void keyPressed() {
        this.gameState.keyPressed(keyCode);
    }

    public void keyReleased() {
        this.gameState.keyReleased(keyCode);
    }

    public void mouseClicked() {
        this.gameState.mouseClicked(mouseX, mouseY);
    }

    public boolean getMode() {
        return this.twoPlayer;
    }

    public void setMode(boolean mode) {
        this.twoPlayer = mode;
    }

    public int getCurrScore() {
        return this.currScore;
    }

    public void setCurrScore(int score) {
        this.currScore = score;
    }

    public int getHighScore() {
        return this.highScore;
    }

    /**
     * Checks if the current score beats highScore and will update highScore accordingly
     * Also resets the current score back to zero
     */
    public void updateHighScore() {
        if (this.currScore > this.highScore) {
            this.highScore = this.currScore;
        }
        this.currScore = 0;
    }

    public static void main(String[] args) {
        PApplet.main("invadem.App");
    }
}
