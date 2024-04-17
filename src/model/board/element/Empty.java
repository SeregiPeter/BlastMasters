package model.board.element;

import java.awt.*;

public class Empty extends Entity{
    public Empty(int x, int y, int width, int height) {
        super(x, y, width, height, 0, null, false, false);
    }

    @Override
    public String toString() {
        return "E";
    }
}
