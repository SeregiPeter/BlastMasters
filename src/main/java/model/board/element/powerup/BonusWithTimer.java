package model.board.element.powerup;

import model.board.element.character.Player;

import java.awt.*;

public abstract class BonusWithTimer extends Bonus {
    public BonusWithTimer(int x, int y, int width, int height, double velocity, Image image, boolean alive, boolean visible, Player owner) {
        super(x, y, width, height, velocity, image, alive, visible, owner);
    }
}
