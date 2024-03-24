package model.board.element.character;

import model.board.Board;
import model.board.element.Entity;
import java.awt.*;

/**
 * The Monster class represents an abstract monster entity in the game board.
 * It defines the common characteristics and behaviors of all monsters.
 */
public abstract class Monster extends Entity {

    /**
     * The game board where the monster resides.
     */
    protected Board board;

    /**
     * Constructs a Monster object with the specified parameters.
     *
     * @param x        the x-coordinate of the monster
     * @param y        the y-coordinate of the monster
     * @param width    the width of the monster
     * @param height   the height of the monster
     * @param velocity the velocity of the monster
     * @param image    the image representing the monster
     * @param alive    the status indicating if the monster is alive
     * @param visible  the status indicating if the monster is visible
     * @param board    the game board the monster belongs to
     */
    public Monster(int x, int y, int width, int height, double velocity, Image image, boolean alive, boolean visible, Board board) {
        super(x, y, width, height, velocity, image, alive, visible);
        this.board = board;
        this.explodable = true;
    }

    /**
     * Abstract method that defines the movement behavior of the monster.
     * Subclasses must implement this method to specify the movement logic.
     */
    public abstract void move();
}
