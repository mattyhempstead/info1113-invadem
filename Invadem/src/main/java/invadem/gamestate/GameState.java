package invadem.gamestate;

import invadem.App;

/**
 * An abstract GameState class
 * This class should enforce the implementation of a draw method for a given game state.
 * GameStates can optionally implement methods listening to key/mouse events
 */
public abstract class GameState {
    public abstract GameState draw(App app);
    public void keyPressed(int keyCode) {};
    public void keyReleased(int keyCode) {};
    public void mouseClicked(int mouseX, int mouseY) {};
}
