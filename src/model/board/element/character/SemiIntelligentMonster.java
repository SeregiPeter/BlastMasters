package model.board.element.character;

import model.board.Board;
import model.board.Direction;
import model.board.Size;
import model.board.element.Empty;
import model.board.element.Entity;
import model.board.element.deposable.Bomb;
import model.board.element.deposable.Box;
import model.board.element.deposable.Flame;
import model.board.element.field.Wall;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A type of Monster that exhibits semi-intelligent movement behavior.
 * SemiIntelligentMonster is a subclass of Monster and represents a monster in the game that moves with semi-intelligent behavior.
 */
public class SemiIntelligentMonster extends Monster {

    /**
     * Constructs a SemiIntelligentMonster with the specified parameters.
     *
     * @param x       the x-coordinate of the monster
     * @param y       the y-coordinate of the monster
     * @param width   the width of the monster
     * @param height  the height of the monster
     * @param velocity the velocity of the monster
     * @param images   the image representing the monster
     * @param alive   the status indicating if the monster is alive
     * @param visible the visibility status of the monster
     * @param board   the game board the monster belongs to
     */
    public SemiIntelligentMonster(int x, int y, int width, int height, double velocity, List<Image> images, boolean alive, boolean visible, Board board) {
        super(x, y, width, height, velocity, images, alive, visible, board);
    }
    public Player getClosestPlayer(){ return null;}

    /**
     * Moves the semi-intelligent monster.
     * This method defines the movement behavior of the semi-intelligent monster.
     */
    @Override
    public void move() {
        this.moveTowardsDirection(currentDirection);
        ArrayList<Entity> entites = new ArrayList<>(board.getEntities());

        boolean needToChangeDirection = false;
        for(Entity entity : entites) {
            if(((entity instanceof Wall) || (entity instanceof Box) || (entity instanceof Bomb)) && this.collides(entity)) {
                needToChangeDirection = true;
                break;
            }
            if(entity instanceof Flame && entity.collides(this)) {
                this.alive = false;
                this.removable=true;
            }
            if(entity instanceof Player && entity.collides(this)) {
                entity.setAlive(false);                                     //may violate the oop
            }
        }

        if(needToChangeDirection) {
            this.moveTowardsDirection(Direction.getOppositeDirection(this.currentDirection));
            Direction closest = getClosestPlayerDirection();
            this.currentDirection = closest != null ? closest : Direction.getDirectionExcept(this.currentDirection);
        }
    }

    /**
     * Returns a string representation of the SemiIntelligentMonster.
     *
     * @return a string representation of the SemiIntelligentMonster ("Sm")
     */

    public Direction getClosestPlayerDirection() {
        boolean[][] visited = new boolean[Size.BOARD_HEIGHT.getSize()][Size.BOARD_WIDTH.getSize()];
        for(boolean[] row : visited) {
            for(boolean val : row) {
                val = false;
            }
        }

        int ownRow = this.getRow();
        int ownColumn = this.getColumn();

        LinkedList<Entity> queue = new LinkedList<>();
        queue.add(this);
        visited[ownRow][ownColumn] = true;

        Entity[][] staticElements = board.getStaticElements();

        boolean firstIteration = true;
        int i = 0;
        while(!queue.isEmpty()) {
            Entity entity = queue.removeFirst();
            int row = entity.getRow();
            int column = entity.getColumn();

            Entity upEntity = (row-1 >= 0) && !(visited[row-1][column]) && (staticElements[row-1][column] instanceof Empty) ? staticElements[row-1][column] : null;
            if(upEntity != null) {
                upEntity.setDir(firstIteration ? Direction.UP : entity.getDir());
                if(upEntity.collides(board.getPlayer1()) || upEntity.collides(board.getPlayer2())) {
                    return upEntity.getDir();
                }
                queue.addLast(upEntity);
                visited[row-1][column] = true;
            }

            Entity downEntity = (row+1 <= Size.BOARD_HEIGHT.getSize()-1) && !(visited[row+1][column]) && (staticElements[row+1][column] instanceof Empty) ? staticElements[row+1][column] : null;
            if(downEntity != null) {
                downEntity.setDir(firstIteration ? Direction.DOWN : entity.getDir());
                if(downEntity.collides(board.getPlayer1()) || downEntity.collides(board.getPlayer2())) {
                    return downEntity.getDir();
                }
                queue.addLast(downEntity);
                visited[row+1][column] = true;
            }

            Entity leftEntity = (column-1 >= 0) && !(visited[row][column-1]) && (staticElements[row][column-1] instanceof Empty) ? staticElements[row][column-1] : null;
            if(leftEntity != null) {
                leftEntity.setDir(firstIteration ? Direction.LEFT : entity.getDir());
                if(leftEntity.collides(board.getPlayer1()) || leftEntity.collides(board.getPlayer2())) {
                    return leftEntity.getDir();
                }
                queue.addLast(leftEntity);
                visited[row][column-1] = true;
            }

            Entity rightEntity = (column+1 <= Size.BOARD_WIDTH.getSize()-1) && !(visited[row][column+1]) && (staticElements[row][column+1] instanceof Empty) ? staticElements[row][column+1] : null;
            if(rightEntity != null) {
                rightEntity.setDir(firstIteration ? Direction.RIGHT : entity.getDir());
                if(rightEntity.collides(board.getPlayer1()) || rightEntity.collides(board.getPlayer2())) {
                    return rightEntity.getDir();
                }
                queue.addLast(rightEntity);
                visited[row][column+1] = true;
            }
            System.out.println(++i);
            firstIteration = false;
        }

        return null;
    }

    @Override
    public String toString() {
        return "Sm";
    }
}
