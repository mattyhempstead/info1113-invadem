package invadem;

public abstract class GameState {
    public abstract GameState draw(App app);
    public void keyPressed(int keyCode) {};
    public void keyReleased(int keyCode) {};
}
