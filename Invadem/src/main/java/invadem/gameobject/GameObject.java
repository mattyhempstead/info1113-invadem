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

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
