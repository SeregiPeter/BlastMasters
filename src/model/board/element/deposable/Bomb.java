package model.board.element.deposable;

import model.board.Board;
import model.board.element.Entity;
import model.board.element.character.Player;
import model.board.element.field.Wall;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static model.board.Size.*;

public class Bomb extends Entity {
    private static int bombCounter;
    private int id;
    private boolean detonated = false;
    private Player owner;
    private Board board;

    public Bomb(int x, int y, int width, int height, int velocity, Image image, boolean alive, boolean visible, Player owner, Board board) {
        super(x, y, BOMB_WIDTH.getSize(), BOMB_HEIGHT.getSize(), velocity, image, alive, visible);
        this.owner = owner;
        this.board = board;
        id = ++bombCounter;
    }

    public void plant() {
        board.getEntities().add(this);
        board.getBombs().add(this);

        Timer Fuse = new Timer();
        Fuse.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!detonated) {
                    explode();
                }

            }
        }, 4 * 1000);                     // after 4 sec the bomb explodes


    }

    public Entity getEntFromXY(int x, int y) {
        ArrayList<Entity> Ents = new ArrayList<>();
        for (Entity entity : board.getEntities()) {
            if (entity.getX() == x && entity.getY() == y) {
                Ents.add(entity);
            }
        }
        return Ents.get(Ents.size() - 1);

    }

    public void explode() {
        detonated = true;
        boolean right = contact(getEntFromXY(this.x + TILE_WIDTH.getSize(), this.y));
        boolean left = contact(getEntFromXY(this.x - TILE_WIDTH.getSize(), this.y));
        boolean down = contact(getEntFromXY(this.x, this.y + TILE_HEIGHT.getSize()));
        boolean up = contact(getEntFromXY(this.x, this.y - TILE_HEIGHT.getSize()));

        Timer Timer = new Timer();
        Timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (right) contact(getEntFromXY(x + 2 * TILE_WIDTH.getSize(), y));
                if (left) contact(getEntFromXY(x - 2 * TILE_WIDTH.getSize(), y));
                if (up) contact(getEntFromXY(x, y + 2 * TILE_HEIGHT.getSize()));
                if (down) contact(getEntFromXY(x, y - 2 * TILE_HEIGHT.getSize()));
            }
        }, 300);

    }

    private boolean contact(Entity entity) {         //returns true if the explosion can continue in that direction
        if (entity instanceof Wall) return false;    //and generate fragments on the entity
        if (entity instanceof Box) {
            board.getEntities().add(                //generate Bomb fragments (temporary solution)
                    new Bomb(
                            entity.getX(),
                            entity.getY(),
                            BOMB_WIDTH.getSize(),
                            BOMB_HEIGHT.getSize(),
                            0,
                            null,
                            false,
                            true,
                            null,
                            this.board));
            //entity.brake()                        // Box gets destroyed
            return false;
        } else if (entity instanceof Bomb) {
            if (!((Bomb) entity).getDetonated()) ((Bomb) entity).explode();

        } else {
            board.getEntities().add(                //generate Bomb fragments (temporary solution)
                    new Bomb(
                            entity.getX(),
                            entity.getY(),
                            BOMB_WIDTH.getSize(),
                            BOMB_HEIGHT.getSize(),
                            0,
                            null,
                            false,
                            true,
                            null,
                            this.board));
            return true;
        }
        return false;
    }

    private boolean getDetonated() {
        return detonated;
    }

    public int getId() {
        return id;
    }

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

    @Override
    public String toString() {
        return Character.toString(128_163);
    }
}
