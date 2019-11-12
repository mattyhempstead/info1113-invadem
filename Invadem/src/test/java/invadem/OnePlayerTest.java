package invadem;

import org.junit.Test;
import static org.junit.Assert.*;

import invadem.App;
import invadem.gameobject.Tank;
import invadem.gameobject.projectile.Projectile;
import invadem.gamestate.OnePlayer;

public class OnePlayerTest {

    // Ensure OnePlayer instance can be correctly constructed
    @Test
    public void testOnePlayerConstruction() {
        OnePlayer onePlayer = new OnePlayer(new App());
        assertNotNull(onePlayer);
    }

    // Ensure pressing left arrow moves tank
    @Test
    public void testOnePlayerMoveLeft() {
        OnePlayer onePlayer = new OnePlayer(new App());
        int startingPosX = Tank.getTankA().getPosX();
        onePlayer.keyPressed(37);
        onePlayer.handleTankMovement();
        assertEquals(startingPosX - 1, Tank.getTankA().getPosX());
    }

    // Ensure pressing right arrow moves tank
    @Test
    public void testOnePlayerMoveRight() {
        OnePlayer onePlayer = new OnePlayer(new App());
        int startingPosX = Tank.getTankA().getPosX();
        onePlayer.keyPressed(39);
        onePlayer.handleTankMovement();
        assertEquals(startingPosX + 1, Tank.getTankA().getPosX());
    }

    // Ensure lifting left arrow stops moving tank
    @Test
    public void testOnePlayerStopMoveLeft() {
        OnePlayer onePlayer = new OnePlayer(new App());
        int startingPosX = Tank.getTankA().getPosX();
        onePlayer.keyPressed(37);
        onePlayer.keyReleased(37);
        onePlayer.handleTankMovement();
        assertEquals(startingPosX, Tank.getTankA().getPosX());
    }

    // Ensure lifting right arrow stops moving tank
    @Test
    public void testOnePlayerStopMoveRight() {
        OnePlayer onePlayer = new OnePlayer(new App());
        int startingPosX = Tank.getTankA().getPosX();
        onePlayer.keyPressed(39);
        onePlayer.keyReleased(39);
        onePlayer.handleTankMovement();
        assertEquals(startingPosX, Tank.getTankA().getPosX());
    }

    // Ensure pressing invalid keys does not move tank
    @Test
    public void testOnePlayerPressedInvalidKeys() {
        OnePlayer onePlayer = new OnePlayer(new App());
        int startingPosX = Tank.getTankA().getPosX();
        onePlayer.keyPressed(1);
        onePlayer.handleTankMovement();
        assertEquals(startingPosX, Tank.getTankA().getPosX());
    }

    // Ensure lifting invalid keys does not move tank or fire projectile
    @Test
    public void testOnePlayerReleasedInvalidKeys() {
        OnePlayer onePlayer = new OnePlayer(new App());
        int startingPosX = Tank.getTankA().getPosX();
        onePlayer.keyReleased(1);
        onePlayer.handleTankMovement();
        assertEquals(startingPosX, Tank.getTankA().getPosX());
        assertEquals(0, Projectile.getProjectiles().size());
    }

    // Ensure lifting space will fire a projectile
    @Test
    public void testOnePlayerFireProjectile() {
        OnePlayer onePlayer = new OnePlayer(new App());
        onePlayer.keyReleased(32);
        assertEquals(1, Projectile.getProjectiles().size());
    }

    // @Test
    // public void testOnePlayerDraw() {
    //     App app = new App();
    //     app.setup();
    //     OnePlayer onePlayer = new OnePlayer(app);
    //     onePlayer.draw(app);
    // }

}
