package model.board.element.character;
import model.board.Board;

import java.awt.*;
public class GhostMonster extends Monster {
    public GhostMonster(int x, int y, int width, int height, int velocity, Image image, boolean alive, boolean visible, Board board) {
        super(x, y, width, height, velocity, image, alive, visible, board);
    }

    @Override
    public void move() {
    }
}