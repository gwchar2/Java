package FourInARow;

/*
    This function handles the X-Y coordinates on the grid panel for every "square" in the game grid.
 */
public class Boundaries {

    private double topH;

    private double topW;

    private double botH;

    private double botW;

    private double centerX;

    private double centerY;

    private final double radius =25; /* Constant radius of our circles */

    public Boundaries(double topH, double topW, double botH, double botW, double centerX, double centerY) {
        this.topH = topH;
        this.topW = topW;
        this.botH = botH;
        this.botW = botW;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public double getTopH() {
        return topH;
    }

    public double getTopW() {
        return topW;
    }

    public double getBotH() {
        return botH;
    }

    public double getBotW() {
        return botW;
    }

    public double getRadius(){
        return this.radius;
    }


}
