package invadem.gamestate;

import invadem.App;
import invadem.gameobject.Tank;
import invadem.gameobject.projectile.Projectile;

public class TwoPlayer extends OnePlayer {
    private boolean movingLeftA;
    private boolean movingRightA;
    private boolean movingLeftB;
    private boolean movingRightB;

    public TwoPlayer(App app) {
        super(app);
    }

    @Override
    public void keyPressed(int keyCode) {
        switch (keyCode) {
            case 37:
                this.movingLeftA = true;
                break;
            case 39:
                this.movingRightA = true;
                break;
            case 65:
                this.movingLeftB = true;
                break;
            case 68:
                this.movingRightB = true;
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(int keyCode) {
        switch (keyCode) {
            case 37:
                this.movingLeftA = false;
                break;
            case 39:
                this.movingRightA = false;
                break;
            case 65:
                this.movingLeftB = false;
                break;
            case 68:
                this.movingRightB = false;
                break;
            case 10:
            if (!Tank.getTankA().isDestroyed()) Projectile.addProjectile(Tank.getTankA().fire());
                break;
            case 32:
            if (!Tank.getTankB().isDestroyed()) Projectile.addProjectile(Tank.getTankB().fire());
                break;
            default:
                break;
        }
    }

    @Override
    public void handleTankMovement() {
        // Only allow movement if given tank is not destroyed
        if (!Tank.getTankA().isDestroyed()) {
            if (this.movingLeftA) Tank.getTankA().moveLeft();
            if (this.movingRightA) Tank.getTankA().moveRight();
        }
        if (!Tank.getTankB().isDestroyed()) {
            if (this.movingLeftB) Tank.getTankB().moveLeft();
            if (this.movingRightB) Tank.getTankB().moveRight();
        }
    }
}
