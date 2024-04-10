package model.board.element.character;

import model.board.Board;
import model.board.Direction;
import model.board.element.Entity;

import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * The Monster class represents an abstract monster entity in the game board.
 * It defines the common characteristics and behaviors of all monsters.
 */
public abstract class Monster extends Entity {

    Direction currentDirection;
    Random random;

    /**
     * The game board where the monster resides.
     */
    protected Board board;

    /**
     * The list of images representing the monster.
     */
    protected List<Image> images;

    /**
     * Constructs a Monster object with the specified parameters.
     *
     * @param x         the x-coordinate of the monster
     * @param y         the y-coordinate of the monster
     * @param width     the width of the monster
     * @param height    the height of the monster
     * @param velocity  the velocity of the monster
     * @param images    the list of images representing the monster
     * @param alive     the status indicating if the monster is alive
     * @param visible   the status indicating if the monster is visible
     * @param board     the game board the monster belongs to
     */
    public Monster(int x, int y, int width, int height, double velocity, List<Image> images, boolean alive, boolean visible, Board board) {
        super(x, y, width, height, velocity, images.get(0), alive, visible);
        this.images = images;
        this.board = board;
        this.explodable = true;
        this.currentDirection = Direction.UP;
        this.random = new Random();
    }

    /**
     * Changes the direction of the monster randomly with a small probability.
     */
    public void changeDirectionRandomly() {
        if(random.nextDouble() < 0.005) {
            this.currentDirection = Direction.getDirectionExcept(this.currentDirection);
        }
    }

    /**
     * Abstract method that defines the movement behavior of the monster.
     * Subclasses must implement this method to specify the movement logic.
     */
    public abstract void move();
}
