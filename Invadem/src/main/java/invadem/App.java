package invadem;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;

public class App extends PApplet {
    private int gameState;          // The current game state (0=playing game, 1=next level, 2=game over)
    private int gameStateTickCount; // The amount of ticks user has been in the current game state

    private PImage imgNextLevel;
    private PImage imgGameOver;

    private Tank tank;
    private boolean movingLeft;
    private boolean movingRight;

    private ArrayList<Projectile> projectiles;

    public void setup() {
        frameRate(60);

        this.gameState = 0;
        this.gameStateTickCount = 0;

        this.imgNextLevel = loadImage("nextlevel.png");
        this.imgGameOver = loadImage("gameover.png");

        // Some resources are loaded and stored in static variables in their respective classes
        // This prevents the need to reload resources each time they are needed by a new object
        Tank.loadResources( loadImage("tank1.png") );
        Invader.loadResources( new PImage[] { loadImage("invader1.png"), loadImage("invader2.png") } );
        Projectile.loadResources( loadImage("projectile.png") );
        Barrier.loadResources(
            new PImage[] { loadImage("barrier_left1.png"),  loadImage("barrier_left2.png"),  loadImage("barrier_left3.png")  },
            new PImage[] { loadImage("barrier_right1.png"), loadImage("barrier_right2.png"), loadImage("barrier_right3.png") },
            new PImage[] { loadImage("barrier_solid1.png"), loadImage("barrier_solid2.png"), loadImage("barrier_solid3.png") },
            new PImage[] { loadImage("barrier_top1.png"),   loadImage("barrier_top2.png"),   loadImage("barrier_top3.png")   }
        );


        this.tank = new Tank();
        this.movingLeft = false;
        this.movingRight = false;

        this.projectiles = new ArrayList<Projectile>();

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
    public void changeGameState(int gameState) {
        this.gameState = gameState;
        this.gameStateTickCount = 0;
    }

    public void drawGame() {

        if (this.movingLeft) this.tank.moveLeft();
        if (this.movingRight) this.tank.moveRight();

        // If invader shoot countdown has finished, add a new invader projectile to the projectile list
        Invader.tickShootCountdown();
        if (Invader.shouldInvadersShoot()) {
            this.projectiles.add(Invader.shootFromInvader());
        }


        // Draw and tick tank
        tank.draw(this);

        // Draw and tick invaders
        Invader.drawInvaders(this);   

        // Game over if invaders have reached barriers
        if (Invader.hasReachedBarriers()) {
            this.changeGameState(2);
            return;
        }

        // Draw barriers
        Barrier.drawBarriers(this);

        // Draw and tick projectiles
        for (Projectile proj : this.projectiles) {
            proj.draw(this);

            // Check if projectile has collided with any objects
            // We are allowing projectiles to collide with two objects at once,
            // assuming both objects are simultaneously colliding with the projectile.

            // Check barrier collision
            Barrier.checkProjectileCollision(proj); 

            // Check invader collision from friendly projectiles
            if (proj.isFriendly()) {
                Invader.checkProjectileCollision(proj);
            }

            // Check invader collision with tank
            if (!proj.isFriendly() && Collidable.isColliding(proj, this.tank)) {
                proj.hit();
                this.tank.hit();

                // Check for game over from destruction by projectiles
                if (this.tank.isDestroyed()) {
                    this.changeGameState(2);
                    return;
                }
            }

        }

        // Remove any projectiles/barriers/invaders which are destroyed
        this.projectiles.removeIf(proj -> proj.isDestroyed());
        Barrier.getBarriers().removeIf(barrier -> barrier.isDestroyed());
        Invader.getInvaders().removeIf(invader -> invader.isDestroyed());
        
        // Check for next level
        if (Invader.getInvaders().size() == 0) {
            this.changeGameState(1);
        }

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
            this.changeGameState(0);

            // Reset game for next level
            Invader.resetInvaders();
            Barrier.resetBarriers();
            this.projectiles.clear();
            this.tank = new Tank();
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
                this.projectiles.add(tank.fire());
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        PApplet.main("invadem.App");
    }

}
