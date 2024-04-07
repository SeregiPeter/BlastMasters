package model.board;

/**
 * Enum representing various velocities used in the game.
 */
public enum Velocity {

    /**
     * Represents the velocity of a bomb.
     */
    BOMB_VEL(0),

    /**
     * Represents the velocity of a wall.
     */
    WALL_VEL(0),

    /**
     * Represents the velocity of a box.
     */
    BOX_VEL(0),

    /**
     * Represents the velocity of a player character.
     * Currently, only integer values are supported.
     */
    PLAYER_VEL(2),
    PLAYER_WITH_ROLLER_VEL(4),

    /**
     * Represents the velocity of a bonus.
     */
    BONUS_VEL(0),

    /**
     * Represents the velocity of a monster.
     */
    MONSTER_VEL(1);

    private final double velocity;

    /**
     * Constructs a Velocity enum with the specified velocity value.
     *
     * @param velocity the velocity value
     */
    Velocity(double velocity) {
         this.velocity = velocity;
    }

    /**
     * Gets the velocity value associated with this enum constant.
     *
     * @return the velocity value
     */
    public double getVelocity() {
        return velocity;
    }
}
