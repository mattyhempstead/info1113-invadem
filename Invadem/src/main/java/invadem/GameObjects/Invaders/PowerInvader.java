package invadem;

import processing.core.PImage;

public class PowerInvader extends Invader {
    // Static variables to store PowerInvader resources
    private static PImage[] imgPowerInvader;

    public PowerInvader(int posX, int posY) {
        super(posX, posY);

        this.points = 250;

        // Override image resource reference for PowerInvader
        this.imgRef = PowerInvader.imgPowerInvader;
    }

    /**
     * Load sprites statically into the PowerInvader class
     */
    public static void loadResources(App app) {
        PowerInvader.imgPowerInvader = new PImage[] { 
            app.loadImage("invader1_power.png"),
            app.loadImage("invader2_power.png")
        };
    }

    /**
     * Override firing for PowerInvader to user PowerProjectile
     * @return The power projectile instance
     */
    @Override
    public Projectile fire() {
        return new PowerProjectile(
            this.posX + (this.width / 2),
            this.posY + (this.height / 2),
            false
        );
    }
}
