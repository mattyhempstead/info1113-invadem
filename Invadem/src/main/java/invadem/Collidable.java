package invadem;

/**
 * Both projectiles, and all objects which projectiles can collide should implement Collidable
 */
public interface Collidable {
    public int getWidth();
    public int getHeight();
    public int getPosX();
    public int getPosY();
    public void hit();
    public boolean isDestroyed();
    public static boolean isColliding(Collidable a, Collidable b) {
        // Don't allow objects which are destroyed to collide with others
        if (a.isDestroyed() || b.isDestroyed()) return false;

        if (a.getPosX() < b.getPosX() + b.getWidth() &&
            a.getPosX() + a.getWidth() > b.getPosX() &&
            a.getPosY() < b.getPosY() + b.getHeight() &&
            a.getHeight() + a.getPosY() > b.getPosY()    
        ) {
            return true;
        } else {
            return false;
        }
    }
}
