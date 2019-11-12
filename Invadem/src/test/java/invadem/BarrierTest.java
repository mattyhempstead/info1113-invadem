package invadem;

import org.junit.Test;
import static org.junit.Assert.*;

import invadem.gameobject.Barrier;
import invadem.gameobject.projectile.Projectile;

public class BarrierTest {

    // Ensure Barrier can be correctly constructed
    @Test
    public void testBarrierConstruction() {
        Barrier barrier = new Barrier(null, 0, 0);
        assertNotNull(barrier);
    }

    // Ensure all Barriers are correctly constructed on level reset
    @Test
    public void testBarrierReset() {
        Barrier.resetBarriers();
        assertNotNull(Barrier.getBarriers());
        assertEquals(Barrier.getBarriers().size(), 21);
    }

    // Ensure Barriers are constructed with 3 starting health
    @Test
    public void testBarrierHealthMax() {
        Barrier barrier = new Barrier(null, 0, 0);
        assertEquals(barrier.getHealth(), 3);
    }

    // Ensure Barriers are constructed in a non-destroyed state
    @Test
    public void testBarrierNotDestroyed() {
        Barrier barrier = new Barrier(null, 0, 0);
        assertFalse(barrier.isDestroyed());
    }

    // Ensure Barrier loses one health when hit by a projectile
    @Test
    public void testBarrierCollision() {
        Barrier barrier = new Barrier(null, 0, 0);
        Projectile proj = new Projectile(0, 0, true);
        proj.hit(barrier);
        assertEquals(barrier.getHealth(), 2);
    }

    // Ensure Barrier is destroyed after 3 hits with different projectiles
    @Test
    public void testBarrierDestroyed() {
        Barrier barrier = new Barrier(null, 0, 0);

        Projectile proj1 = new Projectile(0, 0, true);
        proj1.hit(barrier);
        Projectile proj2 = new Projectile(0, 0, true);
        proj2.hit(barrier);
        Projectile proj3 = new Projectile(0, 0, true);
        proj3.hit(barrier);

        assertTrue(barrier.isDestroyed());
    }

    // Ensure Barriers collision loop correctly collides Barriers with projectiles
    @Test
    public void testBarrierCollisionLoop() {
        Barrier.resetBarriers();
        Barrier barrier = Barrier.getBarriers().get(0);
        int startingHealth = barrier.getHealth();

        Projectile proj = new Projectile(barrier.getPosX(), barrier.getPosY(), true);
        Barrier.checkProjectileCollision(proj);

        assertEquals(barrier.getHealth(), startingHealth - 1);
    }

}
