package model.board.element.character;

import model.board.Board;
import model.board.Direction;
import model.board.element.Entity;
import model.board.element.deposable.Bomb;
import model.board.element.deposable.Box;
import model.board.element.deposable.Flame;
import model.board.element.field.Wall;

import java.awt.*;
import java.util.Random;

public class BasicMonster extends Monster {
    Direction currentDirection;
    //komment
    Random random;
    public BasicMonster(int x, int y, int width, int height, double velocity, Image image, boolean alive, boolean visible, Board board) {
        super(x, y, width, height, velocity, image, alive, visible, board);
        this.currentDirection = Direction.UP;
        this.random = new Random();
    }

    @Override
    public void move() {
        if(!this.isAlive()) return;

        this.moveTowardsDirection(currentDirection);

        boolean needToChangeDirection = false;
        for(Entity entity : board.getEntities()) {
            if(((entity instanceof Wall) || (entity instanceof Box) || (entity instanceof Bomb)) && this.collides(entity)) {
                needToChangeDirection = true;
                break;
            }
            if(entity instanceof Flame && entity.collides(this)) {
                this.alive = false;
            }
        }

        if(needToChangeDirection) {
            this.moveTowardsDirection(Direction.getOppositeDirection(this.currentDirection));
            this.currentDirection = Direction.getDirectionExcept(this.currentDirection);
        }

        changeDirectionRandomly();
    }

    public void changeDirectionRandomly() {
        if(random.nextDouble() < 0.005) {
            this.currentDirection = Direction.getDirectionExcept(this.currentDirection);
        }
    }

    @Override
    public String toString() {
        return "Bm";
    }

}
