package invadem;

import org.junit.Test;
import static org.junit.Assert.*;

import invadem.App;
import invadem.gameobject.projectile.Projectile;

public class ProjectileTest {

    // Ensure projectile instance can be correctly constructed
    @Test
    public void testProjectileConstruction() {
        Projectile proj = new Projectile(0, 0, true);
        assertNotNull(proj);
    }

    // Ensure friendly projectile instance can be constructed
    @Test
    public void testProjectileIsFriendly() {
        Projectile proj = new Projectile(0, 0, true);
        assertTrue(proj.isFriendly());
    }

    // Ensure enemy projectile instance can be constructed
    @Test
    public void testProjectileIsEnemy() {
        Projectile proj = new Projectile(0, 0, false);
        assertFalse(proj.isFriendly());
    }

    // Ensure new projectiles can be added
    @Test
    public void testProjectileAdd() {
        Projectile.resetProjectiles();
        assertEquals(0, Projectile.getProjectiles().size());
        Projectile.addProjectile(new Projectile(0, 0, false));
        assertEquals(1, Projectile.getProjectiles().size());
    }

    // Ensure projectiles can be reset
    @Test
    public void testProjectileReset() {
        Projectile.addProjectile(new Projectile(0, 0, false));
        Projectile.resetProjectiles();
        assertEquals(0, Projectile.getProjectiles().size());
    }

    // Ensure friendly projectiles move up every tick
    @Test
    public void testProjectileFriendlyMovement() {
        Projectile proj = new Projectile(100, 100, true);
        proj.tick(new App());
        assertEquals(99, proj.getPosY());
    }

    // Ensure enemy projectiles move down every tick
    @Test
    public void testProjectileEnemyMovement() {
        Projectile proj = new Projectile(100, 100, false);
        proj.tick(new App());
        assertEquals(101, proj.getPosY());
    }

    // Ensure projectiles are not destroyed when inside screen
    @Test
    public void testProjectileDestructionInsideScreen() {
        Projectile proj = new Projectile(100, 100, false);
        proj.tick(new App());
        assertFalse(proj.isDestroyed());
    }

    // Ensure projectiles are destroyed when above screen
    @Test
    public void testProjectileDestructionAboveScreen() {
        Projectile proj = new Projectile(100, -100, false);
        proj.tick(new App());
        assertTrue(proj.isDestroyed());
    }

    // Ensure projectiles are destroyed when below screen
    @Test
    public void testProjectileDestructionBelowScreen() {
        Projectile proj = new Projectile(100, 1000, false);
        proj.tick(new App());
        assertTrue(proj.isDestroyed());
    }

    // Ensure friendly projectiles are destroyed correctly near top edge of screen
    @Test
    public void testProjectileDestructionNearTopEdge() {
        Projectile proj = new Projectile(100, 1, true);
        proj.tick(new App());
        assertFalse(proj.isDestroyed());
        proj.tick(new App());
        assertTrue(proj.isDestroyed());
    }

    // Ensure enemy projectiles are destroyed correctly near bottom edge of screen
    @Test
    public void testProjectileDestructionNearBottomEdge() {
        Projectile proj = new Projectile(100, 479, false);
        proj.tick(new App());
        assertFalse(proj.isDestroyed());
        proj.tick(new App());
        assertTrue(proj.isDestroyed());
    }
}
