package model.board.element.powerup.benefit;

import model.board.element.character.Player;
import model.board.element.powerup.Bonus;

import java.awt.*;

/**
 * Represents a power-up bonus that increases the maximum number of bombs a player can place.
 * MaxBombsBonus is a subclass of Bonus and provides a benefit to the player
 * by increasing both the maximum number of bombs they can have and the number of
 * placeable bombs upon use.
 */
public class MaxBombsBonus extends Bonus {

    /**
     * Constructs a MaxBombsBonus with the specified parameters.
     *
     * @param x       the x-coordinate of the bonus
     * @param y       the y-coordinate of the bonus
     * @param width   the width of the bonus
     * @param height  the height of the bonus
     * @param velocity the velocity of the bonus
     * @param image   the image representing the bonus
     * @param alive   the status indicating if the bonus is alive
     * @param visible the visibility status of the bonus
     * @param owner   the player who owns the bonus
     */
    public MaxBombsBonus(double x, double y, int width, int height, double velocity, Image image, boolean alive, boolean visible, Player owner) {
        super(x, y, width, height, velocity, image, alive, visible, owner);
    }

    /**
     * Applies the benefit of the max bombs bonus to the owner player.
     * This method increases both the maximum number of bombs and the number of
     * placeable bombs of the player who owns the bonus.
     */
    @Override
    public void use() {
        this.owner.incrementNumberOfPlaceableBombs();

    }
}
