package model.board.element.deposable;

import model.board.Board;
import model.board.element.Entity;
import model.board.element.character.Player;

import java.awt.*;
import java.util.Objects;

import static model.board.Size.BOMB_HEIGHT;
import static model.board.Size.BOMB_WIDTH;

public class Bomb extends Entity {
    private static int bombCounter;
    private int id;
    private Player owner;
    private Board board;
    public Bomb(int x, int y, int width, int height, int velocity, Image image, boolean alive, boolean visible, Player owner, Board board) {
        super(x, y, BOMB_WIDTH.getValue(), BOMB_HEIGHT.getValue(), velocity, image, alive, visible);
        this.owner=owner;
        this.board=board;
        id = ++bombCounter;
    }
    public void plant(){}
    public Entity getAllEntitiesInRange(){
        return null;
    }
    public void explode(){}
    public int getId() { return id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bomb bomb = (Bomb) o;
        return id == bomb.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
