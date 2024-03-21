package model.board.element.deposable;

import model.board.Board;
import model.board.Direction;
import model.board.element.Entity;
import model.board.element.field.Wall;

import java.awt.*;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import static model.board.Size.TILE_HEIGHT;
import static model.board.Size.TILE_WIDTH;

public class Flame extends Entity {
    private Board board;
    private Direction direction;
    private int numberOfExpansions;
    private Bomb bomb;

    public Flame(int x, int y, int width, int height, double velocity, Image image, boolean alive, boolean visible, Board board, Direction direction, int numberOfExpansions, Bomb bomb) {
        super(x, y, width, height, velocity, image, alive, visible);
        this.explodable = false;
        this.board = board;
        board.addEntity(this);
        this.direction = direction;
        this.numberOfExpansions = numberOfExpansions;
        this.bomb = bomb;
    }

    public boolean markEntitiesRemovable() {
        for(Entity entity : board.getEntities()) {
            if(entity.collides(this)) {
                if(entity.isExplodable()) {
                    entity.setRemovable(true);
                }
                if(entity instanceof Box) {
                    ((Box) entity).explodedByBomb();
                    return false;
                }
                if(entity instanceof Wall) return false;
                if(entity instanceof Bomb) {
                    ((Bomb) entity).explode();
                }

            }
        }
        return true;
    }

    public void expandOneTile() {
        switch(direction) {
            case UP:
                y -= TILE_HEIGHT.getSize();
                height += TILE_HEIGHT.getSize();
                break;
            case DOWN:
                height += TILE_HEIGHT.getSize();
                break;
            case LEFT:
                x -= TILE_WIDTH.getSize();
                width += TILE_WIDTH.getSize();
                break;
            case RIGHT:
                width += TILE_WIDTH.getSize();
                break;
        }
    }

    public void expand() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(markEntitiesRemovable()) {
                    expandOneTile();
                } else {
                    //timer.cancel();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            removable = true;
                        }
                    }, 1000);
                }
            }
        }, 1000, numberOfExpansions);

    }

}
