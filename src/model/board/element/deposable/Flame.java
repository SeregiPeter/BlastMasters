package model.board.element.deposable;

import model.board.Board;
import model.board.Direction;
import model.board.element.Entity;
import model.board.element.character.Player;
import model.board.element.field.Wall;
import model.board.element.powerup.Bonus;

import java.awt.*;
import java.util.ArrayList;
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
        Bonus bonus = null;
        ArrayList<Entity> entities = new ArrayList<>(board.getEntities());
        for(Entity entity : entities) {
            if(entity.collides(this)) {
                if(entity.isExplodable()) {
                    entity.setRemovable(true);
                    System.out.println(entity);
                    if(entity instanceof Box) {
                        if(((Box) entity).getBonus() != null) {
                            bonus = ((Box) entity).getBonus();
                            bonus.setExplodable(true);
                            bonus.setVisible(true);
                            board.addEntity(bonus);
                        }
                        return false;
                    }
                    if(entity instanceof Player) {
                        entity.setAlive(false);
                    }
                }
                if(entity instanceof Wall) return false;
                if(entity instanceof Bomb) {
                    ((Bomb) entity).explode();
                }

            }
        }
        //expandOneTile();
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
        boolean needToBePlacedBack = false;
        ArrayList<Entity> entities = new ArrayList<>(board.getEntities());
        for(Entity entity : entities) {
            if(entity instanceof Wall && entity.collides(this)) needToBePlacedBack = true;
        }
        if(needToBePlacedBack) {
            switch(direction) {
                case UP:
                    y += TILE_HEIGHT.getSize();
                    height -= TILE_HEIGHT.getSize();
                    break;
                case DOWN:
                    height -= TILE_HEIGHT.getSize();
                    break;
                case LEFT:
                    x += TILE_WIDTH.getSize();
                    width -= TILE_WIDTH.getSize();
                    break;
                case RIGHT:
                    width -= TILE_WIDTH.getSize();
                    break;
            }
        }
    }

    public void expand() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int expansions = 0;
            boolean mainConditionMet = false;

            @Override
            public void run() {
                if (!mainConditionMet) {
                    if (expansions < numberOfExpansions && markEntitiesRemovable()) {
                        expansions++;
                        expandOneTile();
                    } else {
                        markEntitiesRemovable();
                        mainConditionMet = true; // Set the flag to true
                        // Schedule the else branch to run after 5 milliseconds
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                removable = true;
                                timer.cancel();
                                timer.purge();
                            }
                        }, 1500);
                    }
                }
            }
        };
        timer.schedule(task, 0, 500);
    }


}
