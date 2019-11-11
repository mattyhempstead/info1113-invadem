package invadem;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;

public class App extends PApplet {
    private int gameState;          // The current game state (0=playing game, 1=next level, 2=game over)
    private int gameStateTickCount; // The amount of ticks user has been in the current game state

    private int highScore = 10000;
    private int currScore = 0;
    
    private PFont font;

    private PImage imgNextLevel;
    private PImage imgGameOver;

    private boolean movingLeft;
    private boolean movingRight;

    public void setup() {
        frameRate(60);

        this.gameState = 0;
        this.gameStateTickCount = 0;
        
        this.font = createFont("PressStart2P-Regular.ttf", 16);
        textFont(this.font);

        this.imgNextLevel = loadImage("nextlevel.png");
        this.imgGameOver = loadImage("gameover.png");

        // Some resources are loaded and stored in static variables in their respective classes
        // This prevents the need to reload resources each time they are needed by a new object
        Tank.loadResources(this);
        Invader.loadResources(this);
        ArmouredInvader.loadResources(this);
        PowerInvader.loadResources(this);
        Projectile.loadResources(this);
        PowerProjectile.loadResources(this);
        Barrier.loadResources(this);

        this.movingRight = false;
        this.movingLeft = false;
        
        // Create tanks
        Tank.resetTank();

        // Create invaders
        Invader.resetInvaders();

        // Create barriers
        Barrier.resetBarriers();

    }

    public void settings() {
        size(640, 480);
    }

    public void draw() { 
        background(0);

        // Increment gameStateTickCount at each draw frame
        this.gameStateTickCount++;

        // Run the correct draw function for the game state
        switch (this.gameState) {
            case 0:
                this.drawGame();
                break;
            case 1:
                this.drawNextLevel();
                break;
            case 2:
                this.drawGameOver();
                break;
            default:
                break;
        }
    }

    /**
     * Changes the game state and resets the gameStateTickCount
     * @param gameState The new game state
     */
    public void setGameState(int gameState) {
        this.gameState = gameState;
        this.gameStateTickCount = 0;
    }

    public void drawGame() {

        if (this.movingLeft) Tank.getTank().moveLeft();
        if (this.movingRight) Tank.getTank().moveRight();

        // Draw and tick tank
        Tank.drawTanks(this);

        // Draw and tick invaders
        Invader.drawInvaders(this);   
        
        // Draw barriers
        Barrier.drawBarriers(this);

        // Draw and tick projectiles
        Projectile.drawProjectiles(this);

        // Add the points sum of all destroyed invaders
        currScore += Invader.getInvaders()
            .stream()
            .filter(invader -> invader.isDestroyed())
            .reduce(0, (sum, invader) -> sum + invader.getPoints(), Integer::sum);

        // Remove any projectiles/barriers/invaders which are destroyed
        Projectile.getProjectiles().removeIf(proj -> proj.isDestroyed());
        Barrier.getBarriers().removeIf(barrier -> barrier.isDestroyed());
        Invader.getInvaders().removeIf(invader -> invader.isDestroyed());
        
        // Check for next level
        if (Invader.getInvaders().size() == 0) {
            this.setGameState(1);
        }
        
        // Check for game over conditions
        if (Tank.getTank().isDestroyed() || Invader.hasReachedBarriers()) {
            this.setGameState(2);
        }
 
        // Draw current score in top left
        textAlign(LEFT, TOP);
        text(this.currScore, 10, 10);

        // Draw high score in top right
        textAlign(RIGHT, TOP);
        text(this.highScore, 640 - 10, 10);

    }

    /**
     * A temporary next level screen displayed between levels
     */
    public void drawNextLevel() {
        image(
            imgNextLevel,
            (640 - 122) / 2,
            (480 - 16) / 2
        );

        // Display next level message for 3 seconds
        if (this.gameStateTickCount == 60 * 3) {
            this.setGameState(0);

            // Reset game for next level
            Tank.resetTank();
            Invader.resetInvaders();
            Barrier.resetBarriers();
            Projectile.resetProjectiles();
            Invader.decreaseShootCountdownTickLength();
        }
    }

    /**
     * A permanent game over state displayed when game is lost
     * User must exit game to leave this state
     */
    public void drawGameOver() {
        image(
            imgGameOver,
            (640 - 112) / 2,
            (480 - 16) / 2
        );

        // Display game over message for 3 seconds
        if (this.gameStateTickCount == 60 * 3) {
            this.setGameState(0);

            // Reset for new game
            Tank.resetTank();
            Invader.resetInvaders();
            Barrier.resetBarriers();
            Projectile.resetProjectiles();
            Invader.resetShootCountdownTickLength();

            if (currScore > highScore) highScore = currScore;
            currScore = 0;
        }
    }

    public void keyPressed() {
        switch (keyCode) {
            case 37:
                this.movingLeft = true;
                break;
            case 39:
                this.movingRight = true;
                break;
            default:
                break;
        }
    }

    public void keyReleased() {
        switch (keyCode) {
            case 37:
                this.movingLeft = false;
                break;
            case 39:
                this.movingRight = false;
                break;
            case 32:
                Projectile.addProjectile(Tank.getTank().fire());
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        PApplet.main("invadem.App");
    }

}
