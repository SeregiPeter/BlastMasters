package model.board.element.deposable;

import model.board.Board;
import model.board.element.Entity;
import model.board.element.character.Player;

import java.awt.*;

public class Bomb extends Entity {
    private Player owner;
    private Board board;
    public Bomb(int x, int y, int width, int height, int velocity, Image image, boolean alive, boolean visible, Player owner,Board board) {
        super(x, y, width, height, velocity, image, alive, visible);
        this.owner=owner;
        this.board=board;
    }
    public void plant(){}
    public Entity getAllEntitiesInRange(){
        return null;
    }
    public void explode(){}
}
