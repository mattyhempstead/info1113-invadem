package invadem.gamestate;

import invadem.App;

public abstract class GameState {
    public abstract GameState draw(App app);
    public void keyPressed(int keyCode) {};
    public void keyReleased(int keyCode) {};
    public void mouseClicked(int mouseX, int mouseY) {};
}
