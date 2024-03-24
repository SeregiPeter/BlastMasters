package model.board;

/**
 * Enum representing image URLs for various elements in the game.
 */
public enum Image {

    /**
     * Represents the image URL for a bomb.
     */
    BOMB_IMG("src/resources/assets/bomb_explode.gif"),

    /**
     * Represents the image URL for the first player character.
     */
    PLAYER1_IMG("src/resources/assets/bomberman1.png"),

    /**
     * Represents the image URL for the second player character.
     */
    PLAYER2_IMG("src/resources/assets/bomberman2.png"),

    /**
     * Represents the image URL for the background of map 1.
     */
    BACKGROUND_IMG_MAP1("src/resources/assets/tiles/map1/background.png"),

    /**
     * Represents the image URL for a box in map 1.
     */
    BOX_IMG_MAP1("src/resources/assets/tiles/map1/box.png"),

    /**
     * Represents the image URL for a monster in map 1.
     */
    MONSTER_IMG_MAP1("src/resources/assets/tiles/map1/monster.png"),

    /**
     * Represents the image URL for a wall in map 1.
     */
    WALL_IMG_MAP1("src/resources/assets/tiles/map1/wall.png"),

    /**
     * Represents the image URL for the background of map 2.
     */
    BACKGROUND_IMG_MAP2("src/resources/assets/tiles/map2/fire_background.png"),

    /**
     * Represents the image URL for a box in map 2.
     */
    BOX_IMG_MAP2("src/resources/assets/tiles/map2/fire_box.png"),

    /**
     * Represents the image URL for a monster in map 2.
     */
    MONSTER_IMG_MAP2("src/resources/assets/tiles/map2/fire_monster.png"),

    /**
     * Represents the image URL for a wall in map 2.
     */
    WALL_IMG_MAP2("src/resources/assets/tiles/map2/fire_wall.png"),

    /**
     * Represents the image URL for the background of map 3.
     */
    BACKGROUND_IMG_MAP3("src/resources/assets/tiles/map3/ice_background.png"),

    /**
     * Represents the image URL for a box in map 3.
     */
    BOX_IMG_MAP3("src/resources/assets/tiles/map3/ice_box.png"),

    /**
     * Represents the image URL for a monster in map 3.
     */
    MONSTER_IMG_MAP3("src/resources/assets/tiles/map3/ice_monster.png"),

    /**
     * Represents the image URL for a wall in map 3.
     */
    WALL_IMG_MAP3("src/resources/assets/tiles/map3/ice_wall.png"),

    /**
     * Represents the image URL for a flame effect.
     */
    FLAME_IMG("src/resources/assets/tiles/map3/ice_monster.png"),

    /**
     * Represents the image URL for the bigger range bonus.
     */
    BIGGER_RANGE_BONUS_IMG("src/resources/assets/bonuses/bigger_range.png"),

    /**
     * Represents the image URL for the bomb up bonus.
     */
    BOMB_UP_BONUS_IMG("src/resources/assets/bomb_up.png"),
    FLAME_RIGHT_IMG("src/resources/assets/flame_right.png"),
    FLAME_DOWN_IMG("src/resources/assets/flame_down.png"),
    FLAME_UP_IMG("src/resources/assets/flame_up.png"),
    FLAME_LEFT_IMG("src/resources/assets/flame_left.png");

    private final String url;

    /**
     * Constructs an Image enum with the specified image URL.
     *
     * @param envUrl the image URL
     */
    Image(String envUrl) {
        this.url = envUrl;
    }

    /**
     * Gets the image URL associated with this enum constant.
     *
     * @return the image URL
     */
    public String getImageUrl() {
        return url;
    }
}
