package invadem;

/**
 * Both projectiles, and all objects which projectiles can collide with should implement Collidable
 */
public interface Collidable {
    public int getWidth();
    public int getHeight();
    public int getPosX();
    public int getPosY();
    public static boolean isColliding(Collidable a, Collidable b) {
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
