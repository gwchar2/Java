package FourInARow;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/*
    This class handles the game matrix array, and objects for each panel on the grid.
 */
public class GameSlot {

    private final int x;                    // X coordinate

    private final int y;                    // Y coordinate

    private Boundaries boundary;            // The UI (scenebuilder) X,Y coordinates

    private boolean colored;                // Is the square colored?

    private Color color;                    // The color of the circle

    private Circle circle;                  // The circle

    /* Constructor class */
    public GameSlot(int x, int y, Boundaries boundary) {
        this.x = x;
        this.y = y;
        this.boundary = boundary;
        this.colored = false;
        this.color = Color.BLACK; // Default color
        this.circle = new Circle();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Boundaries getBoundary() {
        return boundary;
    }

    public boolean isColored() {
        return colored;
    }

    public void setColored(boolean colored) {
        this.colored = colored;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }



}
