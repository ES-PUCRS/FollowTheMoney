package src;

import java.io.File;

import java.util.LinkedList;

public class PathFollower {

    private final String[][] map;
    private final int startIndex;
    private LinkedList<Integer> valueOnTheRoad;

    public PathFollower(String path) {
        File file = new File(path);
        this.map = FileManager.importMap(file);
        this.startIndex = findStartRoadPosition(map);
        this.valueOnTheRoad = new LinkedList<>();
    }

    public static int findStartRoadPosition(String[][] map) {
        for (int i = 0; i < map.length; i++) {
            if (map[i][0].equals("-"))
                return i;
        }
        return -1;
    }

    public void runThroughTheRoads() {
        Coordinate position = new Coordinate(startIndex, 0);
        Direction direction = Direction.RIGHT;
        boolean lastPathWasDigit = false;

        String currentPointContent = map[position.line][position.column];

        while (!currentPointContent.equals("#")) {
            System.out.println(currentPointContent);
            switch (currentPointContent) {
                case "-":
                case "|":
                    lastPathWasDigit = false;
                    break;

                case "/":
                    if (direction == Direction.UP)
                        direction = Direction.RIGHT;

                    else if (direction == Direction.LEFT)
                        direction = Direction.DOWN;

                    else if (direction == Direction.DOWN)
                        direction = Direction.LEFT;

                    else if (direction == Direction.RIGHT)
                        direction = Direction.UP;
                    lastPathWasDigit = false;
                    break;

                case "\\":
                    if (direction == Direction.RIGHT)
                        direction = Direction.DOWN;

                    else if (direction == Direction.UP)
                        direction = Direction.LEFT;

                    else if (direction == Direction.DOWN)
                        direction = Direction.RIGHT;

                    else if (direction == Direction.LEFT)
                        direction = Direction.UP;

                    lastPathWasDigit = false;
                    break;

                default:
                    int value = 0;

                    try {
                        value = Integer.parseInt(currentPointContent);
                    } catch (NumberFormatException ignored) {
                        String[][] mapClone = map.clone();
                        mapClone[position.line][position.column] = "*";

                        try {
                            FileManager.exportMap(mapClone);
                        } catch (Exception e) {
                        }

                        throw new IllegalStateException(
                                "Position content not expected \"" + currentPointContent + "\"\n" +
                                        "Current position: " + position.toString() + " desired direction: "
                                        + direction);
                    }

                    if (lastPathWasDigit) {// parseInt("49")
                        String lastValue = valueOnTheRoad.getLast().toString();
                        valueOnTheRoad.add(valueOnTheRoad.size(), Integer.parseInt(lastValue + value));
                    } else {
                        valueOnTheRoad.add(value);
                    }
                    lastPathWasDigit = true;
            }

            position = move(position, direction);
            currentPointContent = map[position.line][position.column];
        }

        System.out.println("REACH THE END!");
        System.out.println(valueOnTheRoad);
        int sum = 0;
        for (int value : valueOnTheRoad)
            sum += value;

        System.out.println("Result: " + sum);
    }

    public Coordinate move(Coordinate currentPosition, Direction direction) {
        Coordinate nextPosition = null;

        switch (direction) {
            case UP:
                nextPosition = new Coordinate(currentPosition.line - 1, currentPosition.column);
                break;

            case DOWN:
                nextPosition = new Coordinate(currentPosition.line + 1, currentPosition.column);
                break;

            case LEFT:
                nextPosition = new Coordinate(currentPosition.line, currentPosition.column - 1);
                break;

            case RIGHT:
                nextPosition = new Coordinate(currentPosition.line, currentPosition.column + 1);
        }

        if (nextPosition.line < 0 || nextPosition.column < 0 ||
                nextPosition.line > map.length || nextPosition.column > map[0].length)
            throw new IllegalStateException(
                    "We are trying to move outsize of the map.\n" +
                            "Current position: " + currentPosition.toString() + " desired direction: " + direction
                            + "\n" +
                            "Desired position: " + nextPosition);

        return nextPosition;
    }

}
