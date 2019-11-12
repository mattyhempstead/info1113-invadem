package invadem;

import org.junit.Test;
import static org.junit.Assert.*;

import invadem.gameobject.GameObject;

public class GameObjectTest {

    // Ensure GameObject is correctly constructed
    @Test
    public void testGameObjectConstruction() {
        GameObject gameObject = new GameObject() {
            public void draw(App app) {}
        };

        assertNotNull(gameObject);
    }

    // Ensure GameObject properties are correctly set and retrieved
    @Test
    public void testGameObjectProperties() {
        GameObject gameObject = new GameObject() {
            public void draw(App app) {}
        };
        
        gameObject.setPosX(10);
        gameObject.setPosY(20);
        gameObject.setWidth(30);
        gameObject.setHeight(40);

        assertEquals(gameObject.getPosX(), 10);
        assertEquals(gameObject.getPosY(), 20);
        assertEquals(gameObject.getWidth(), 30);
        assertEquals(gameObject.getHeight(), 40);
    }
}
