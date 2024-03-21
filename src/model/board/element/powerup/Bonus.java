package model.board.element.powerup;

import model.board.element.Entity;
import model.board.element.character.Player;

import java.awt.*;

public abstract class Bonus extends Entity {
    protected Player owner;

    public Bonus(int x, int y, int width, int height, double velocity, Image image, boolean alive, boolean visible, Player owner) {
        super(x, y, width, height, velocity, image, alive, visible);
        this.owner = owner;
        this.explodable = false;
    }

    public abstract void use();

    public Player getOwner() {
        return this.owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
    public void getUsedByPlayer(Player player) {
        this.owner = player;
        this.visible = false;
        this.use();
    }
}
