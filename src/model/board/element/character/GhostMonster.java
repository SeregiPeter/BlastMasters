package model.board.element.character;
import model.board.Board;

import java.awt.*;

/**
 * The GhostMonster class represents a ghost monster entity on the game board.
 */
public class GhostMonster extends Monster {

    /**
     * Constructs a GhostMonster object with the specified parameters.
     *
     * @param x        the x-coordinate of the monster
     * @param y        the y-coordinate of the monster
     * @param width    the width of the monster
     * @param height   the height of the monster
     * @param velocity the velocity of the monster (unused for ghost monsters)
     * @param image    the image representing the monster
     * @param alive    the status indicating if the monster is alive
     * @param visible  the status indicating if the monster is visible
     * @param board    the game board the monster belongs to
     */
    public GhostMonster(int x, int y, int width, int height, double velocity, Image image, boolean alive, boolean visible, Board board) {
        super(x, y, width, height, velocity, image, alive, visible, board);
    }

    /**
     * Moves the ghost monster on the game board.
     * This method is currently empty and needs to be implemented
     * based on the desired "ghost behavior".
     */
    @Override
    public void move() {
    }

    /**
     * Returns a string representation of the GhostMonster object.
     *
     * @return a string representing the ghost monster ("Gm")
     */
    @Override
    public String toString() {
        return "Gm";
    }
}