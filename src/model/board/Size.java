package model.board;

/**
 * Enum representing various sizes used in the game board.
 */
public enum Size {
    /**
     * Represents the size of the game board.
     */
    BOARD_SIZE(15),

    /**
     * Represents the height of a player character.
     */
    PLAYER_HEIGHT(30),

    /**
     * Represents the width of a player character.
     */
    PLAYER_WIDTH(17),

    /**
     * Represents the size of a monster.
     */
    MONSTER_SIZE(40),

    /**
     * Represents the size of a wall.
     */
    WALL_SIZE(40),

    /**
     * Represents the size of a box.
     */
    BOX_SIZE(40),

    /**
     * Represents the size of a bonus.
     */
    BONUS_SIZE(30),

    /**
     * Represents the height of a bomb.
     */
    BOMB_HEIGHT(40),

    /**
     * Represents the width of a bomb.
     */
    BOMB_WIDTH(40),

    /**
     * Represents the width of a tile.
     */
    TILE_WIDTH(40),

    /**
     * Represents the height of a tile.
     */
    TILE_HEIGHT(40),

    /**
     * Represents the height of a flame effect.
     */
    FLAME_HEIGHT(40),

    /**
     * Represents the width of a flame effect.
     */
    FLAME_WIDTH(40);
    private final int size;

    /**
     * Constructs a Size enum with the specified size value.
     *
     * @param size the size value
     */
    Size(int size) {
        this.size = size;
    }

    /**
     * Gets the size value associated with this enum constant.
     *
     * @return the size value
     */
    public int getSize() {
        return size;
    }

}
