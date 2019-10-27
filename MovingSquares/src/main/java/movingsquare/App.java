/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package movingsquare;

import java.util.List;
import java.util.ArrayList;

import processing.core.PApplet;
import movingsquare.squares.RedSquare;
import movingsquare.squares.GreenSquare;
import movingsquare.squares.Square;

public class App extends PApplet {

    List<Square> squares;

    public App() {
        squares = new ArrayList<>();
    }
    
    public void settings() {
        size(640, 480);
    }
    
    public void setup() {
        frameRate(60);
        
        squares.add(new RedSquare(loadImage("red.png"), 0, 0, 50, 50, new int[] {0, 1}));
        squares.add(new GreenSquare(loadImage("green.png"), 50, 50, 20, 20, new int[] {1, 0}));
        squares.add(new GreenSquare(loadImage("green.png"), 50, 50, 30, 30, new int[] {1, 1}));
    }
    
    public void draw() {
        background(0);
        for(Square s : squares) {
            s.draw(this);
        }
    }
    
    public static void main(String[] args) {
        PApplet.main("movingsquare.App");
    }
}
