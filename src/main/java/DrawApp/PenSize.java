package DrawApp;

public enum PenSize {
    SMALL(2),
    MEDIUM(10),
    LARGE(20);

    private final int radius;

    PenSize(int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }
}
