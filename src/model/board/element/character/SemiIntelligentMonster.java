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

    @Override
    public String toString() {
        return "Sm";
    }
}
