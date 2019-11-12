package invadem;

import org.junit.Test;
import static org.junit.Assert.*;

import invadem.gameobject.Tank;
import invadem.gameobject.projectile.Projectile;

public class TankTest {

    // Ensure a single tank is correctly constructed when resetting for a one player game
    @Test
    public void testOnePlayerTankConstruction() {
        Tank.resetTanks(false);
        assertNotNull(Tank.getTankA());
        assertNull(Tank.getTankB());
    }

    // Ensure both tanks are correctly constructed when resetting for a two player game
    @Test
    public void testTwoPlayerTankConstruction() {
        Tank.resetTanks(true);
        assertNotNull(Tank.getTankA());
        assertNotNull(Tank.getTankB());
    }

    // Ensure tank correctly moves left and right
    @Test
    public void testTankMovement() {
        Tank tank = new Tank(0, 0);

        tank.setPosX(320);
        tank.moveLeft();
        assertEquals(tank.getPosX(), 320 - 1);

        tank.setPosX(320);
        tank.moveRight();
        assertEquals(tank.getPosX(), 320 + 1);
    }

    // Ensure tank movement is blocked by left and right boundaries
    @Test
    public void testTankMovementBoundaries() {
        Tank tank = new Tank(0, 0);

        // Test left boundary
        tank.setPosX(181);
        tank.moveLeft();
        assertEquals(tank.getPosX(), 180);
        tank.moveLeft();
        assertEquals(tank.getPosX(), 180);

        // Test right boundary
        tank.setPosX(437);
        tank.moveRight();
        assertEquals(tank.getPosX(), 438);
        tank.moveRight();
        assertEquals(tank.getPosX(), 438);
    }

    // Ensure tank correctly fires projectile
    @Test
    public void testTankProjectileFiring() {
        Tank tank = new Tank(0, 0);
        assertNotNull(tank.fire());
    }

    // Ensure both tanks cannot collide with friendly projectiles
    @Test
    public void testTankFriendlyProjectileCollision() {
        // Construct both tanks at (0,0) and get initial health
        Tank.resetTanks(true);
        Tank tankA = Tank.getTankA();
        Tank tankB = Tank.getTankB();
        int startingHealthA = tankA.getHealth();
        int startingHealthB = tankB.getHealth();

        // Attempt to collide tanks with friendly projectile
        Tank.checkProjectileCollision(new Projectile(tankA.getPosX(), tankA.getPosY(), true));
        Tank.checkProjectileCollision(new Projectile(tankB.getPosX(), tankB.getPosY(), true));

        // Check both tanks have not lost health
        assertEquals(tankA.getHealth(), startingHealthA);
        assertEquals(tankB.getHealth(), startingHealthB);
    }

    // Ensure both tanks can collide with enemy projectiles
    @Test
    public void testTankEnemyProjectileCollision() {
        // Construct both tanks at (0,0) and get initial health
        Tank.resetTanks(true);
        Tank tankA = Tank.getTankA();
        Tank tankB = Tank.getTankB();
        int startingHealthA = tankA.getHealth();
        int startingHealthB = tankB.getHealth();

        // Attempt to collide tanks with enemy projectile
        Tank.checkProjectileCollision(new Projectile(tankA.getPosX(), tankA.getPosY(), false));
        Tank.checkProjectileCollision(new Projectile(tankB.getPosX(), tankB.getPosY(), false));

        // Check both tanks have not lost health
        assertEquals(tankA.getHealth(), startingHealthA - 1);
        assertEquals(tankB.getHealth(), startingHealthB - 1);
    }

}
