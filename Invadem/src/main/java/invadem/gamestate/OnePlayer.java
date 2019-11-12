package invadem.gamestate;

import invadem.App;
import invadem.gameobject.Tank;
import invadem.gameobject.invader.Invader;
import invadem.gameobject.Barrier;
import invadem.gameobject.projectile.Projectile;

import processing.core.PApplet;

/**
 * The game state representing a game of an Invadem with one player
 */
public class OnePlayer extends GameState {    
    int tickCount; // The amount of ticks user has been in the current game state

    private boolean movingLeft;
    private boolean movingRight;

    public OnePlayer(App app) {
        Tank.resetTanks(app.getMode());     // Create tanks
        Invader.resetInvaders();    // Create invaders
        Barrier.resetBarriers();    // Create barriers
        Projectile.resetProjectiles();
    }

    public void keyPressed(int keyCode) {
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

    public void keyReleased(int keyCode) {
        switch (keyCode) {
            case 37:
                this.movingLeft = false;
                break;
            case 39:
                this.movingRight = false;
                break;
            case 32:
                Projectile.addProjectile(Tank.getTankA().fire());
                break;
            default:
                break;
        }
    }

    /**
     * Moves tank based on key inputs
     */
    public void handleTankMovement() {
        // Avoiding else ifs allows keys to cancel out when pressed together
        if (this.movingLeft) Tank.getTankA().moveLeft();
        if (this.movingRight) Tank.getTankA().moveRight();
    }

    public GameState draw(App app) {
        tickCount++;

        this.handleTankMovement();

        // Draw and tick tank
        Tank.drawTanks(app);

        // Draw and tick invaders
        Invader.drawInvaders(app);

        // Draw barriers
        Barrier.drawBarriers(app);

        // Draw and tick projectiles
        Projectile.drawProjectiles(app);

        // Add the points sum of all destroyed invaders to the current score
        app.setCurrScore(
            app.getCurrScore() +
            Invader.getInvaders()
                .stream()
                .filter(invader -> invader.isDestroyed())
                .reduce(0, (sum, invader) -> sum + invader.getPoints(), Integer::sum)
        );

        // Remove any projectiles/barriers/invaders which are destroyed
        Projectile.getProjectiles().removeIf(proj -> proj.isDestroyed());
        Barrier.getBarriers().removeIf(barrier -> barrier.isDestroyed());
        Invader.getInvaders().removeIf(invader -> invader.isDestroyed());

        // Check for next level
        if (Invader.getInvaders().size() == 0) {
            return new NextLevel();
        }

        // Check for game over conditions
        if ((Tank.getTankA().isDestroyed() && (Tank.getTankB() == null || Tank.getTankB().isDestroyed())) || 
            Invader.hasReachedBarriers()) {
            return new GameOver();
        }

        // Draw current score in top left
        app.textAlign(PApplet.LEFT, PApplet.TOP);
        app.text(app.getCurrScore(), 10, 10);

        // Draw high score in top right
        app.textAlign(PApplet.RIGHT, PApplet.TOP);
        app.text(app.getHighScore(), 640 - 10, 10);

        return this;
    }
}
