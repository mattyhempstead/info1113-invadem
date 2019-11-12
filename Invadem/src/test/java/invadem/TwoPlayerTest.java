package invadem;

import org.junit.Test;
import static org.junit.Assert.*;

import invadem.App;
import invadem.gameobject.Tank;
import invadem.gameobject.projectile.Projectile;
import invadem.gamestate.TwoPlayer;

public class TwoPlayerTest {

    // Ensure OnePlayer instance can be correctly constructed
    @Test
    public void testTwoPlayerConstruction() {
        TwoPlayer twoPlayer = new TwoPlayer(new App());
        assertNotNull(twoPlayer);
    }

    // Ensure pressing left arrow moves tank A left, and pressing A moves tank B left
    @Test
    public void testTwoPlayerMoveLeft() {
        App app = new App();
        app.setMode(true);
        TwoPlayer twoPlayer = new TwoPlayer(app);

        // Tank A
        int startingPosXA = Tank.getTankA().getPosX();
        twoPlayer.keyPressed(37);
        twoPlayer.handleTankMovement();
        assertEquals(startingPosXA - 1, Tank.getTankA().getPosX());

        // Tank B
        int startingPosXB = Tank.getTankB().getPosX();
        twoPlayer.keyPressed(65);
        twoPlayer.handleTankMovement();
        assertEquals(startingPosXB - 1, Tank.getTankB().getPosX());
    }

    // Ensure pressing right arrow moves tank A right, and pressing D moves tank B right
    @Test
    public void testTwoPlayerMoveRight() {
        App app = new App();
        app.setMode(true);
        TwoPlayer twoPlayer = new TwoPlayer(app);

        // Tank A
        int startingPosXA = Tank.getTankA().getPosX();
        twoPlayer.keyPressed(39);
        twoPlayer.handleTankMovement();
        assertEquals(startingPosXA + 1, Tank.getTankA().getPosX());

        // Tank B
        int startingPosXB = Tank.getTankB().getPosX();
        twoPlayer.keyPressed(68);
        twoPlayer.handleTankMovement();
        assertEquals(startingPosXB + 1, Tank.getTankB().getPosX());
    }

    // Ensure lifting left movement keys stops moving either tank left
    @Test
    public void testTwoPlayerStopMoveLeft() {
        App app = new App();
        app.setMode(true);
        TwoPlayer twoPlayer = new TwoPlayer(app);

        // Tank A
        int startingPosXA = Tank.getTankA().getPosX();
        twoPlayer.keyPressed(37);
        twoPlayer.keyReleased(37);
        twoPlayer.handleTankMovement();
        assertEquals(startingPosXA, Tank.getTankA().getPosX());

        // Tank B
        int startingPosXB = Tank.getTankB().getPosX();
        twoPlayer.keyPressed(65);
        twoPlayer.keyReleased(65);
        twoPlayer.handleTankMovement();
        assertEquals(startingPosXB, Tank.getTankB().getPosX());
    }

    // Ensure lifting right movement keys stops moving etiher tank right
    @Test
    public void testTwoPlayerStopMoveRight() {
        App app = new App();
        app.setMode(true);
        TwoPlayer twoPlayer = new TwoPlayer(app);

        // Tank A
        int startingPosXA = Tank.getTankA().getPosX();
        twoPlayer.keyPressed(39);
        twoPlayer.keyReleased(39);
        twoPlayer.handleTankMovement();
        assertEquals(startingPosXA, Tank.getTankA().getPosX());

        // Tank B
        int startingPosXB = Tank.getTankB().getPosX();
        twoPlayer.keyPressed(68);
        twoPlayer.keyReleased(68);
        twoPlayer.handleTankMovement();
        assertEquals(startingPosXB, Tank.getTankB().getPosX());
    }

    // Ensure lifting space/enter will fire a projectile for tankA/tankB
    @Test
    public void testTwoPlayerFireProjectile() {
        App app = new App();
        app.setMode(true);
        TwoPlayer twoPlayer = new TwoPlayer(app);

        // Fire projectile from tank A
        twoPlayer.keyReleased(10);
        assertEquals(1, Projectile.getProjectiles().size());

        // Fire projectile from tank B
        twoPlayer.keyReleased(32);
        assertEquals(2, Projectile.getProjectiles().size());

    }

    // Ensure pressing or lifting invalid keys does not move or shoot projectiles from either tank
    @Test
    public void testTwoPlayerInvalidKeys() {
        App app = new App();
        app.setMode(true);
        TwoPlayer twoPlayer = new TwoPlayer(app);
        int startingPosXA = Tank.getTankA().getPosX();
        int startingPosXB = Tank.getTankB().getPosX();

        // Press and Release invalid keys
        twoPlayer.keyPressed(1);
        twoPlayer.handleTankMovement();
        twoPlayer.keyReleased(1);
        twoPlayer.handleTankMovement();

        assertEquals(startingPosXA, Tank.getTankA().getPosX());
        assertEquals(startingPosXB, Tank.getTankB().getPosX());
        assertEquals(0, Projectile.getProjectiles().size());
    }
    
    // Ensure movement/shooting is disabled if tanks are destroyed
    @Test
    public void testTwoPlayerActionsWhileDestroyed() {
        App app = new App();
        app.setMode(true);
        TwoPlayer twoPlayer = new TwoPlayer(app);

        // Ensure lack of movement/shooting from tank A
        Tank.getTankA().destroy();
        int startingPosXA = Tank.getTankA().getPosX();
        twoPlayer.keyPressed(37);
        twoPlayer.handleTankMovement();
        assertEquals(startingPosXA, Tank.getTankA().getPosX());
        twoPlayer.keyPressed(39);
        twoPlayer.handleTankMovement();
        assertEquals(startingPosXA, Tank.getTankA().getPosX());
        twoPlayer.keyReleased(10);
        twoPlayer.handleTankMovement();
        assertEquals(0, Projectile.getProjectiles().size());

        // Ensure lack of movement/shooting from tank B
        Tank.getTankB().destroy();
        int startingPosXB = Tank.getTankB().getPosX();
        twoPlayer.keyPressed(65);
        twoPlayer.handleTankMovement();
        assertEquals(startingPosXB, Tank.getTankB().getPosX());
        twoPlayer.keyPressed(68);
        twoPlayer.handleTankMovement();
        assertEquals(startingPosXB, Tank.getTankB().getPosX());
        twoPlayer.keyReleased(32);
        twoPlayer.handleTankMovement();
        assertEquals(0, Projectile.getProjectiles().size());
    }

}
