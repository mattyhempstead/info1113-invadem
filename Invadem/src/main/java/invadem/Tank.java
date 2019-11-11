package invadem;

import processing.core.PImage;

public class Tank extends Entity {
    private static Tank tank;

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

    public static void resetTank() {
        Tank.tank = new Tank();
    }

    public static Tank getTank() {
        return Tank.tank;
    }

    public static void checkProjectileCollision(Projectile proj) {
        if (!proj.isFriendly() && Collidable.isColliding(proj, Tank.tank)) {
            proj.hit(Tank.tank);
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
        Tank.tank.draw(app);
    }

    public void draw(App app) {
        // app.rect(this.posX, this.posY, this.width, this.height);
        app.image(
            imgTank,
            this.posX,
            this.posY,
            this.width,
            this.height
        );
    }
}
