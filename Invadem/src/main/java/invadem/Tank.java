package invadem;

// import processing.core.PApplet;
import processing.core.PImage;

public class Tank implements Drawable {
    private int posX;
    private int posY;
    private int width;
    private int height;

    // private static PImage image = PApplet.loadImage("../resources/tank1.png");
    
    public Tank() {
        this.width = 22;
        this.height = 16;
        this.posX = (640 / 2) - (this.width / 2);
        this.posY = 480 - this.height - 10;
    }

    public void moveLeft() {
        if (this.posX > 0) {
            this.posX -= 1;
        }
    }

    public void moveRight() {
        if (this.posX < 640) {
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
            this.posY + (this.height / 2),
            true
        );
    }

    public void draw(App app, PImage img) {
        // app.rect(this.posX, this.posY, this.width, this.height);
        app.image(
            img, 
            this.posX, 
            this.posY,
            this.width, 
            this.height
        );
    }
}
