package model.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public static Direction getDirectionExcept(Direction d) {
        ArrayList<Direction> directions = new ArrayList<>(Arrays.asList(Direction.values()));
        Collections.shuffle(directions);
        Direction result = directions.get(0);
        for(Direction direction : directions) {
            if(direction != d) {
                result = direction;
                break;
            }
        }
        return result;
    }

    public static Direction getOppositeDirection(Direction d) {
        return switch (d) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            default -> null;
        };

    }

}
