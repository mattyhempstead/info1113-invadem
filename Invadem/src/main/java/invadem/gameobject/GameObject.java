package invadem.gameobject;

import invadem.Drawable;

/**
 * GameObjects are any drawable objects within Invadem that
 * have a particular size and location on the screen.
 */
public abstract class GameObject implements Drawable {
    protected int posX;
    protected int posY;
    protected int width;
    protected int height;

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
