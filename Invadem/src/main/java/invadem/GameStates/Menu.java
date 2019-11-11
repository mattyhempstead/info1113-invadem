package invadem;

public class Menu extends GameState {
    private boolean one;
    private boolean two;

    public Menu() {

    }

    public void keyReleased(int keyCode) {
        switch (keyCode) {
            case 37:
                this.one = true;
                break;
            case 39:
                this.two = true;
                break;
            default:
                break;
        }
    }

    public GameState draw(App app) {
        app.textAlign(app.CENTER, app.TOP);
        app.text("Invadem", 640/2, 100);

        if (one) {
            app.setMode(false);
            return new OnePlayer(app);
        }

        if (two) {
            app.setMode(true);
            return new TwoPlayer(app);
        }

        return this;
    }
}
