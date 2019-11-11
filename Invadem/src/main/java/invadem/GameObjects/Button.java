package invadem;

import java.lang.Runnable;

public class Button extends GameObject {
    private String text;
    private Runnable action;

    public Button(int posX, int posY, int width, int height, String text, Runnable action) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.text = text;
        this.action = action;
    }

    public void mouseClicked(int mouseX, int mouseY) {
        if (this.posX <= mouseX && mouseX <= this.posX + width &&
            this.posY <= mouseY && mouseY <= this.posY + height) {
            System.out.println("pressed me" + this.text);
            this.action.run();
        }
    }

    public void draw(App app) {
        app.fill(0);
        app.stroke(255);
        app.rect(this.posX, this.posY, this.width, this.height);
        app.fill(255);

        app.textAlign(app.CENTER, app.CENTER);
        app.text(
            this.text,
            this.posX + this.width/2, 
            this.posY + this.height/2
        );
    }
}
