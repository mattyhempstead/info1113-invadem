package invadem;

import processing.core.PImage;

public class PowerProjectile extends Projectile {
    
    // Static variables to store PowerProjectile resources
    private static PImage imgPowerProj;

    public PowerProjectile(int posX, int posY, boolean friendly) {
        super(posX, posY, friendly);

        // Power projectiles are larger
        this.width = 2;
        this.height = 5;

        // Override image resource reference for PowerProjectile
        this.imgRef = imgPowerProj;
    }

    /**
     * Load sprites statically into the Projectile class
     */
    public static void loadResources(App app) {
        PowerProjectile.imgPowerProj = app.loadImage("projectile_lg.png");
    }

    /**
     * PowerProjectiles will instantly destroy the object they collide with
     * This differs from the default Projectile behaviour which is to just hit the object
     */
    @Override
    public void hit(Collidable collided) {
        this.destroy();
        collided.destroy();
    }
}
