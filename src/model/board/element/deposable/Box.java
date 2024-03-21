package model.board.element.deposable;

import model.board.Board;
import model.board.element.Entity;
import model.board.element.powerup.Bonus;
import java.awt.*;
public class Box extends Entity {
    private Bonus bonus;
    Board board;

    public Box(int x, int y, int width, int height, double velocity, Image image, boolean alive, boolean visible, Bonus bonus, Board board) {
        super(x, y, width, height, velocity, image, alive, visible);
        this.bonus = bonus;
        this.explodable = true;
        this.board = board;
    }

    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
    }

    public void explodedByBomb() {
        if(this.bonus != null) {
            this.bonus.setVisible(true);
            this.bonus.setExplodable(true);
            board.addEntity(bonus);
        }
    }

    @Override
    public String toString() {
        return "\uD83D\uDCE6";
    }
}
