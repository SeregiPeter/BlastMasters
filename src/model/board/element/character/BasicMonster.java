package model.board.element.character;

import model.board.Board;
import model.board.Direction;
import model.board.element.deposable.Bomb;
import model.board.element.deposable.Box;
import model.board.element.field.Wall;

import java.awt.*;
import java.util.Random;

public class BasicMonster extends Monster {
    Direction currentDirection;
    Random random;
    public BasicMonster(int x, int y, int width, int height, int velocity, Image image, boolean alive, boolean visible, Board board) {
        super(x, y, width, height, velocity, image, alive, visible, board);
        this.currentDirection = Direction.UP;
        this.random = new Random();
    }

    @Override
    public void move() {
        if(!this.isAlive()) return;

        this.moveTowardsDirection(currentDirection);

        boolean needToChangeDirection = false;
        for(Wall wall : board.getWalls()) {
            if(this.collides(wall)) {
                needToChangeDirection = true;
                break;
            }
        }
        if(!needToChangeDirection) {
            for(Box box : board.getBoxes()) {
                if(this.collides(box)) {
                    needToChangeDirection = true;
                    break;
                }
            }
        }
        if(!needToChangeDirection) {
            for(Bomb bomb : board.getBombs()) {
                if(this.collides(bomb)) {
                    needToChangeDirection = true;
                    break;
                }
            }
        }

        if(needToChangeDirection) {
            this.moveTowardsDirection(Direction.getOppositeDirection(this.currentDirection));
            this.currentDirection = Direction.getDirectionExcept(this.currentDirection);
        }

        changeDirectionRandomly();
    }

    public void changeDirectionRandomly() {
        if(random.nextDouble() < 0.1) {
            this.currentDirection = Direction.getDirectionExcept(this.currentDirection);
        }
    }





}
