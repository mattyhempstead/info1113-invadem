package invadem;

/**
 * All objects which can be drawn to the screen must implement a draw method.
 * This method should draw the object to the given App instance
 */
public interface Drawable {
    static void loadResources(App app) {};
    void draw(App app);
}
