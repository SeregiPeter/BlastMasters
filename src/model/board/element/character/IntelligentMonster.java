package model.board.element.character;

import model.board.Board;

import java.awt.*;

public class IntelligentMonster extends Monster {
    public IntelligentMonster(int x, int y, int width, int height, int velocity, Image image, boolean alive, boolean visible, Board board) {
        super(x, y, width, height, velocity, image, alive, visible, board);
    }

    public Player getClosestPlayer() {
        return null;
    }

    @Override
    public void move() {

    }
}
