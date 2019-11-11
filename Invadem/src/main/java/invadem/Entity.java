package invadem;

/**
 * Entities are GameObjects which can collide with other Entities
 */
public abstract class Entity extends GameObject implements Collidable {
    boolean destroyed = false;

    public void hit() {
        this.destroyed = true;
    }

    public boolean isDestroyed() {
        return this.destroyed;
    }
}
