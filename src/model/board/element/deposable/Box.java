package model.board.element.deposable;

import model.board.element.Entity;
import model.board.element.powerup.Bonus;
import java.awt.*;
public class Box extends Entity {
    private Bonus bonus;

    public Box(int x, int y, int width, int height, double velocity, Image image, boolean alive, boolean visible, Bonus bonus) {
        super(x, y, width, height, velocity, image, alive, visible);
        this.bonus = bonus;
    }

    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        return "\uD83D\uDCE6";
    }
}
