package model.board.element.character;

import model.board.Board;
import model.board.element.Entity;
import java.awt.*;


public abstract class Monster extends Entity {
    protected Board board;

    public Monster(int x, int y, int width, int height, int velocity, Image image, boolean alive, boolean visible, Board board) {
        super(x, y, width, height, velocity, image, alive, visible);
        this.board = board;
    }

    public abstract void move();
}
