package invadem;

/**
 * GameObjects are any drawable objects within Invadem that
 * have a particular size and location on the screen.
 */
public abstract class GameObject implements Drawable {
    int posX;
    int posY;
    int width;
    int height;

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
