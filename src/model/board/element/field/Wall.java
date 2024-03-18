package model.board.element.field;

import model.board.element.Entity;


import java.awt.*;

public class Wall extends Entity {

    public Wall(int x, int y, int width, int height, int velocity, Image image, boolean alive, boolean visible) {
        super(x, y, width, height, velocity, image, alive, visible);
    }

    @Override
    public String toString() {
        return "\uD83E\uDDF1";
    }
}
