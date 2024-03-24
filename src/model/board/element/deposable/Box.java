package model.board.element.deposable;

import model.board.Board;
import model.board.element.Entity;
import model.board.element.powerup.Bonus;
import java.awt.*;

/**
 * Represents a box on the game board that can contain bonuses.
 */
public class Box extends Entity {
    private Bonus bonus;
    Board board;

    /**
     * Constructs a Box object with the specified parameters.
     *
     * @param x       The x-coordinate of the box.
     * @param y       The y-coordinate of the box.
     * @param width   The width of the box.
     * @param height  The height of the box.
     * @param velocity The velocity of the box.
     * @param image   The image of the box.
     * @param alive   The status indicating if the box is alive.
     * @param visible The visibility status of the box.
     * @param bonus   The bonus contained in the box.
     * @param board   The game board the box belongs to.
     */
    public Box(int x, int y, int width, int height, double velocity, Image image, boolean alive, boolean visible, Bonus bonus, Board board) {
        super(x, y, width, height, velocity, image, alive, visible);
        this.bonus = bonus;
        this.explodable = true;
        this.board = board;
    }

    /**
     * Sets the bonus contained in the box.
     *
     * @param bonus The bonus to be set.
     */
    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
    }

    /**
     * Activates when the box is exploded by a bomb.
     * If the box contains a bonus, it becomes visible on the board.
     */
    public void explodedByBomb() {
        if(this.bonus != null) {
            this.bonus.setVisible(true);
            this.bonus.setExplodable(true);
            board.addEntity(bonus);
        }
    }

    /**
     * Gets the bonus contained in the box.
     *
     * @return The bonus contained in the box.
     */
    public Bonus getBonus() {
        return bonus;
    }

    /**
     * Returns a string representation of the Box, which is a Unicode symbol for a box.
     *
     * @return The Unicode symbol for a box, representing the Box object.
     */
    @Override
    public String toString() {
        return "\uD83D\uDCE6";
    }
}
