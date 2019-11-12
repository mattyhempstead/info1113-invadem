package invadem;

import org.junit.Test;
import static org.junit.Assert.*;

import invadem.gameobject.invader.Invader;
import invadem.gameobject.projectile.Projectile;

public class InvaderTest {

    // Ensure invader instance can be correctly constructed
    @Test
    public void testInvaderConstruction() {
        Invader invader = new Invader(0, 0);
        assertNotNull(invader);
    }

    // Ensure all Invaders are correctly constructed on level reset
    @Test
    public void testInvaderReset() {
        Invader.resetInvaders();
        assertNotNull(Invader.getInvaders());
        assertEquals(40, Invader.getInvaders().size());
    }

    // Ensure Invaders follow the correct movement pattern
    // Specifically: 30 ticks right -> 8 ticks down -> 30 ticks left -> 8 ticks down
    @Test
    public void testInvaderTickCycle() {
        Invader.resetInvaders();

        Invader invader = Invader.getInvaders().get(0);
        int startingPosX = invader.getPosX();
        int startingPosY = invader.getPosY();

        for (int i=0; i<30; i++) {
            invader.tick();
            assertEquals(startingPosX + i + 1, invader.getPosX());
            assertEquals(startingPosY, invader.getPosY());
        }

        for (int i=0; i<8; i++) {
            invader.tick();
            assertEquals(startingPosX + 30, invader.getPosX());
            assertEquals(startingPosY + i + 1, invader.getPosY());
        }

        for (int i=0; i<30; i++) {
            invader.tick();
            assertEquals(startingPosX + 30 - i - 1, invader.getPosX());
            assertEquals(startingPosY + 8, invader.getPosY());
        }

        for (int i=0; i<8; i++) {
            invader.tick();
            assertEquals(startingPosX, invader.getPosX());
            assertEquals(startingPosY + 8 + i + 1, invader.getPosY());
        }

    }

    // Ensure invader correctly fires projectile
    @Test
    public void testInvaderProjectileFiring() {
        Invader invader = new Invader(0, 0);
        assertNotNull(invader.fire());
    }

    // Ensure a random invader from swarm is selected to fire projectile
    @Test
    public void testInvaderSwarmProjectileFiring() {
        Invader.resetInvaders();
        assertNotNull(Invader.shootFromInvader());
    }

    // Ensure Invader start with 5 second shoot cooldown
    @Test
    public void testInvaderStartingShootCooldown() {
        assertEquals(5*60, Invader.getShootCooldownTickLength());
    }

    // Ensure Invader cooldown decreases down to 1 second correctly
    @Test
    public void testInvaderDecreasingShootCooldown() {
        assertEquals(5*60, Invader.getShootCooldownTickLength());
        Invader.decreaseShootCountdownTickLength();
        assertEquals(4*60, Invader.getShootCooldownTickLength());
        Invader.decreaseShootCountdownTickLength();
        assertEquals(3*60, Invader.getShootCooldownTickLength());
        Invader.decreaseShootCountdownTickLength();
        assertEquals(2*60, Invader.getShootCooldownTickLength());
        Invader.decreaseShootCountdownTickLength();
        assertEquals(1*60, Invader.getShootCooldownTickLength());
        Invader.decreaseShootCountdownTickLength();
        assertEquals(1*60, Invader.getShootCooldownTickLength());
    }

    // Ensure Invader cooldown resets to 5 seconds correctly
    @Test
    public void testInvaderResetShootCooldown() {
        assertEquals(5*60, Invader.getShootCooldownTickLength());
        Invader.decreaseShootCountdownTickLength();
        assertEquals(4*60, Invader.getShootCooldownTickLength());
        Invader.resetShootCountdownTickLength();
        assertEquals(5*60, Invader.getShootCooldownTickLength());
    }

    // Ensure invader swarm shoot cooldown ticks to zero and resets
    @Test
    public void testInvaderTickShootCooldown() {
        // Tick shootCooldown down to zero
        // We should not expect Invaders to shoot during this period
        for (int i=0; i<5*60; i++) {
            Invader.tickShootCountdown();
            assertEquals(5*60 - i, Invader.getShootCooldown());
            assertFalse(Invader.shouldInvadersShoot());
        }

        // Once cooldown reaches zero, invaders should attempt to shoot
        Invader.tickShootCountdown();
        assertEquals(0, Invader.getShootCooldown());
        assertTrue(Invader.shouldInvadersShoot());

        // The next tick should reset the cooldown
        Invader.tickShootCountdown();
        assertEquals(5*60, Invader.getShootCooldown());
        assertFalse(Invader.shouldInvadersShoot());
    }

    // Ensure invader is worth 100 points
    @Test
    public void testInvaderPoints() {
        Invader invader = new Invader(0, 0);
        assertEquals(100, invader.getPoints());
    }

    // Ensure invaders start above the barriers
    @Test
    public void testInvaderAboveBarriers() {
        Invader.resetInvaders();
        assertFalse(Invader.hasReachedBarriers());
    }

    // Ensure invaders are detected correctly on the edge of barriers
    @Test
    public void testInvaderEdgeBarriers() {
        Invader.resetInvaders();
        Invader invader = Invader.getInvaders().get(0);
        invader.setPosY(378 - 1 - invader.getHeight());
        assertFalse(Invader.hasReachedBarriers());
        invader.setPosY(invader.getPosY() + 1);
        assertTrue(Invader.hasReachedBarriers());
    }

    // Ensure invaders are detected when below barriers
    @Test
    public void testInvaderBelowBarriers() {
        Invader.resetInvaders();
        Invader invader = Invader.getInvaders().get(0);
        invader.setPosY(400);
        assertTrue(Invader.hasReachedBarriers());
    }

    // Ensure invaders cannot collide with enemy projectiles
    @Test
    public void testInvaderEnemyProjectileCollision() {
        // Construct invader swarm and isolate health of a single invader
        Invader.resetInvaders();
        Invader invader = Invader.getInvaders().get(0);
        int startingHealth = invader.getHealth();

        // Attempt to collide swarm with an enemy projectile inside the isolated invader
        Invader.checkProjectileCollision(new Projectile(invader.getPosX(), invader.getPosY(), false));

        // Check invader has not lost health
        assertEquals(startingHealth, invader.getHealth());
    }

    // Ensure invaders collide with friendly projectiles
    @Test
    public void testInvaderFriendlyProjectileCollision() {
        // Construct invader swarm and isolate health of a single invader
        Invader.resetInvaders();
        Invader invader = Invader.getInvaders().get(0);
        int startingHealth = invader.getHealth();

        // Attempt to collide swarm with a friendly projectile inside the isolated invader
        Invader.checkProjectileCollision(new Projectile(invader.getPosX(), invader.getPosY(), true));

        // Check invader has not lost health
        assertEquals(startingHealth - 1, invader.getHealth());
    }

    // Ensure invaders are constructed not destroyed
    @Test
    public void testInvaderStartNotDestroyed() {
        Invader invader = new Invader(0, 0);
        assertFalse(invader.isDestroyed());
    }

    // Ensure invaders are destroyed after a hit with a projectile
    @Test
    public void testInvaderProjectileDestroyed() {
        Invader invader = new Invader(0, 0);
        Projectile proj = new Projectile(invader.getPosX(), invader.getPosY(), true);
        proj.hit(invader);
        assertTrue(invader.isDestroyed());
    }

}
