package DrawApp;

public enum Shape {
    LINE(1),
    RECTANGLE(2),
    TRIANGLE(3),
    CIRCLE(4),
    DEFAULT(5);

    private final int shape;

    Shape(int shape) {
        this.shape = shape;
    }

    public int getShape() {
        return shape;
    }

}
