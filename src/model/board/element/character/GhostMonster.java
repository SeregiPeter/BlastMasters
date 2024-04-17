package model.board.element.character;
import model.board.Board;
import model.board.Direction;
import model.board.Size;
import model.board.element.Entity;
import model.board.element.deposable.Bomb;
import model.board.element.deposable.Box;
import model.board.element.deposable.Flame;
import model.board.element.field.Wall;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The GhostMonster class represents a ghost monster entity on the game board.
 */
public class GhostMonster extends Monster {
    private boolean hasToGoStraight;

    /**
     * Constructs a GhostMonster object with the specified parameters.
     *
     * @param x        the x-coordinate of the monster
     * @param y        the y-coordinate of the monster
     * @param width    the width of the monster
     * @param height   the height of the monster
     * @param velocity the velocity of the monster (unused for ghost monsters)
     * @param images   the image representing the monster
     * @param alive    the status indicating if the monster is alive
     * @param visible  the status indicating if the monster is visible
     * @param board    the game board the monster belongs to
     */
    public GhostMonster(int x, int y, int width, int height, double velocity, List<Image> images, boolean alive, boolean visible, Board board) {
        super(x, y, width, height, velocity, images, alive, visible, board);
        hasToGoStraight = false;
    }

    /**
     * Moves the ghost monster on the game board.
     * This method is currently empty and needs to be implemented
     * based on the desired "ghost behavior".
     */
    @Override
    public void move() {
        if (!this.isAlive()) return;
        this.moveTowardsDirection(currentDirection);

        ArrayList<Entity> entites = new ArrayList<>(board.getEntities());

        boolean needToChangeDirection = false;
        boolean collidesWithBoxOrWall = false;
        for (Entity entity : entites) {
            if ((entity instanceof Wall || entity instanceof Box) && this.collides(entity)) {
                collidesWithBoxOrWall = true;
                if (isThereEmptyField(currentDirection)) {
                    if(!hasToGoStraight) {
                        hasToGoStraight = true;
                        if(random.nextDouble() < 0.5) {
                            needToChangeDirection = true;
                        }
                    }
                } else {
                    needToChangeDirection = true;
                }
            }
            if (entity instanceof Bomb && this.collides(entity)) {
                needToChangeDirection = true;
            }
            if (entity instanceof Flame && entity.collides(this)) {
                this.alive = false;
                this.removable = true;
            }
            if (entity instanceof Player && entity.collides(this)) {
                entity.setAlive(false);
            }
        }

        if(!collidesWithBoxOrWall) {
            hasToGoStraight = false;
        }

        if (needToChangeDirection) {
            this.moveTowardsDirection(Direction.getOppositeDirection(this.currentDirection));
            this.currentDirection = Direction.getDirectionExcept(this.currentDirection);

        }

        if(!hasToGoStraight) {
            changeDirectionRandomly();
        }


    }

    /**
     * Returns a string representation of the GhostMonster object.
     *
     * @return a string representing the ghost monster ("Gm")
     */

    public boolean isThereEmptyField(Direction direction) {
        int maxX = (Size.BOARD_WIDTH.getSize()-1)* Size.WALL_SIZE.getSize();
        int maxY = (Size.BOARD_HEIGHT.getSize()-1)* Size.WALL_SIZE.getSize();

        int checkPointX = this.x;
        int checkPointY = this.y;

        ArrayList<Entity> entities = new ArrayList<>(board.getEntities());

        boolean emptyField = false;
        while(this.x < maxX && this.x > 0 && this.y < maxY && this.y > 0) {
            this.moveTowardsDirection(direction);
            boolean correct = true;
            for(Entity entity : entities) {
                if((entity instanceof Wall || entity instanceof Box || entity instanceof Bomb) && entity.collides(this)) {
                    correct = false;
                }
            }
            if(correct) {
                emptyField = true;
                break;
            }
        }

        this.x = checkPointX;
        this.y = checkPointY;
        return emptyField;
    }

    @Override
    public String toString() {
        return "Gm";
    }
}