package model.board.element.powerup.handicap;

import model.board.element.character.Player;
import model.board.element.powerup.BonusWithTimer;

import java.awt.*;

public class NoBombsBonus extends BonusWithTimer {
    public NoBombsBonus(int x, int y, int width, int height, double velocity, Image image, boolean alive, boolean visible, Player owner) {
        super(x, y, width, height, velocity, image, alive, visible, owner);
    }

    @Override
    public void use() {

    }
}
