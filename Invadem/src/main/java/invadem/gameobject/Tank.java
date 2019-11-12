package invadem.gameobject;

import invadem.App;
import invadem.Collidable;
import invadem.gameobject.projectile.Projectile;

import processing.core.PImage;

public class Tank extends Entity {
    private static Tank tankA;
    private static Tank tankB;

    private static PImage imgTank;
    
    public Tank(int posX, int posY) {
        this.health = 3;

        this.width = 22;
        this.height = 16;

        // Centre tank on the specified position
        this.posX = posX - (this.width / 2);
        this.posY = posY - (this.height / 2);
    }

    /**
     * Load sprites statically into the Tank class
     */
    public static void loadResources(App app) {
        Tank.imgTank = app.loadImage("tank1.png");
    }

    public static void resetTanks(boolean twoPlayer) {
        if (twoPlayer) {
            Tank.tankA = new Tank(640 / 2 + 50, 480 - 18);
            Tank.tankB = new Tank(640 / 2 - 50, 480 - 18);
        } else {
            Tank.tankA = new Tank(640 / 2, 480 - 18);
            Tank.tankB = null;
        }
    }

    public static Tank getTankA() {
        return Tank.tankA;
    }

    public static Tank getTankB() {
        return Tank.tankB;
    }

    public static void checkProjectileCollision(Projectile proj) {
        if (proj.isFriendly()) return;

        if (Collidable.isColliding(proj, Tank.tankA)) {
            proj.hit(Tank.tankA);
        }

        if (Tank.tankB != null && Collidable.isColliding(proj, Tank.tankB)) {
            proj.hit(Tank.tankB);
        }
    }

    public void moveLeft() {
        if (this.posX > 180) {
            this.posX -= 1;
        }
    }

    public void moveRight() {
        if (this.posX + this.width < 460) {
            this.posX += 1;
        }
    }

    /**
     * Returns a newly spawned tank projectile at the location of tank
     * @return The projectile instance
     */
    public Projectile fire() {
        return new Projectile(
            this.posX + (this.width / 2),
            this.posY,
            true
        );
    }

    public static void drawTanks(App app) {
        if (app.getMode()) {
            // Draw each tank if they are not destroyed

            if (!Tank.tankA.isDestroyed()) {
                app.tint(65, 65, 255);  // Tank B is tinted blue
                Tank.tankA.draw(app);
            }

            if (!Tank.tankB.isDestroyed()) {
                app.tint(255, 65, 65);  // Tank A is tinted red
                Tank.tankB.draw(app);
            }

            app.tint(255);  // Remove tint
        } else {
            Tank.tankA.draw(app);
        }
    }

    public void draw(App app) {
        app.image(
            imgTank,
            this.posX,
            this.posY,
            this.width,
            this.height
        );
    }
}
