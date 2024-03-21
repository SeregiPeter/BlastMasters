package model.board.element.powerup.benefit;

import model.board.element.character.Player;
import model.board.element.powerup.Bonus;

import java.awt.*;

public class RollerBonus extends Bonus {
    public RollerBonus(int x, int y, int width, int height, double velocity, Image image, boolean alive, boolean visible, Player owner) {
        super(x, y, width, height, velocity, image, alive, visible, owner);
    }

    @Override
    public void use() {

    }
}
