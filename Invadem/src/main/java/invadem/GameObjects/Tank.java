package invadem;

import processing.core.PImage;

public class Tank extends Entity {
    private static Tank tankA;
    private static Tank tankB;

    private static PImage imgTank;
    
    public Tank() {
        this.health = 3;

        this.width = 22;
        this.height = 16;
        this.posX = (640 / 2) - (this.width / 2);
        this.posY = 480 - this.height - 10;
    }

    /**
     * Load sprites statically into the Tank class
     */
    public static void loadResources(App app) {
        Tank.imgTank = app.loadImage("tank1.png");
    }

    public static void resetTanks(boolean twoPlayer) {
        Tank.tankA = new Tank();
        if (twoPlayer) Tank.tankB = new Tank();
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
        if (this.posX < 460) {
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
        if (!Tank.tankA.isDestroyed()) Tank.tankA.draw(app);
        if (Tank.tankB != null && !Tank.tankB.isDestroyed()) Tank.tankB.draw(app);
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
