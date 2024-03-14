package src;

public class Coordinate {

    public int line;
    public int column;

    public Coordinate(int line, int column) {
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {
        return "[" + line + ", " + column + "]";
    }
}
