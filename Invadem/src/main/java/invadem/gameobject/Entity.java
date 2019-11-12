package invadem.gameobject;

import invadem.Collidable;

/**
 * Entities are GameObjects which can collide with other Entities and be destroyed
 * Entities also contain health which when depleted will destroy the Entity
 * By default, Entities have 1 health and will thus be destroyed after a single hit
 */
public abstract class Entity extends GameObject implements Collidable {
    protected boolean destroyed = false;
    protected int health = 1;

    /**
     * Remove one health, and destroy if entity reaches zero health
     */
    public void hit() {
        if (--health <= 0) {
            this.destroyed = true;
        }
    }

    /**
     * Instantly destroy Entity
     */
    public void destroy() {
        this.destroyed = true;
    }

    public boolean isDestroyed() {
        return this.destroyed;
    }
}
