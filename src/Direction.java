package src;

public enum Direction {
    UP(0),
    DOWN(1),
    LEFT(2),
    RIGHT(3);

    public int direction;

    Direction(int direction) {
        this.direction = direction;
    }

    public static Direction fromValue(int value) throws IllegalArgumentException {
        try {
            return Direction.values()[value];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Unknown enum value :" + value);
        }
    }

}