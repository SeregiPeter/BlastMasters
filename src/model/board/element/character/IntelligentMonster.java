package model.board.element.character;

import model.board.Board;

import java.awt.*;
import java.util.List;

/**
 * The IntelligentMonster class represents a monster entity on the game board
 * that exhibits intelligent behavior. It can determine the closest player
 * and make decisions based on that information.
 */
public class IntelligentMonster extends Monster {

    /**
     * Constructs an IntelligentMonster object with the specified parameters.
     *
     * @param x        the x-coordinate of the monster
     * @param y        the y-coordinate of the monster
     * @param width    the width of the monster
     * @param height   the height of the monster
     * @param velocity the velocity of the monster
     * @param images   the image representing the monster
     * @param alive    the status indicating if the monster is alive
     * @param visible  the status indicating if the monster is visible
     * @param board    the game board the monster belongs to
     */
    public IntelligentMonster(int x, int y, int width, int height, double velocity, List<Image> images, boolean alive, boolean visible, Board board) {
        super(x, y, width, height, velocity, images, alive, visible, board);
    }

    /**
     * Gets the closest player to the intelligent monster.
     *
     * @return the closest Player object to the monster, or null if no player is found
     */
    public Player getClosestPlayer() {
        return null;
    }

    /**
     * Moves the intelligent monster on the game board.
     * This method is currently empty and needs to be implemented
     * based on the desired "intelligent behavior".
     */
    @Override
    public void move() {

    }

    /**
     * Returns a string representation of the IntelligentMonster object.
     *
     * @return a string representing the intelligent monster ("Im")
     */
    @Override
    public String toString() {
        return "Im";
    }
}
