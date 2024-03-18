package model.board.element.powerup.benefit;

import model.board.element.character.Player;
import model.board.element.powerup.BonusWithTimer;

import java.awt.*;

public class ImmortalityBonus extends BonusWithTimer {

    public ImmortalityBonus(int x, int y, int width, int height, int velocity, Image image, boolean alive, boolean visible, Player owner) {
        super(x, y, width, height, velocity, image, alive, visible, owner);
    }

    @Override
    public void use() {

    }
}
