package model.board;

import java.util.Arrays;
import java.util.List;

/**
 * Enum representing image URLs for various elements in the game.
 */
public enum Image {

    /**
     * Represents the image URL for a bomb.
     */
    BOMB_IMG("src/main/resources/assets/bomb.png"),


    BOMB_EXPLODE_IMG(Arrays.asList(
            "src/main/resources/assets/bombexplode/be1.png",
            "src/main/resources/assets/bombexplode/be2.png",
            "src/main/resources/assets/bombexplode/be3.png",
            "src/main/resources/assets/bombexplode/be4.png",
            "src/main/resources/assets/bombexplode/be5.png",
            "src/main/resources/assets/bombexplode/be6.png",
            "src/main/resources/assets/bombexplode/be7.png",
            "src/main/resources/assets/bombexplode/be8.png",
            "src/main/resources/assets/bombexplode/be9.png",
            "src/main/resources/assets/bombexplode/be10.png",
            "src/main/resources/assets/bombexplode/be11.png",
            "src/main/resources/assets/bombexplode/be12.png",
            "src/main/resources/assets/bombexplode/be13.png",
            "src/main/resources/assets/bombexplode/be14.png",
            "src/main/resources/assets/bombexplode/be15.png"


    )),



    /**
     * Represents the image URL for the first player character.
     */
    PLAYER1_IMG(Arrays.asList(
            "src/main/resources/assets/entities/bomberman1/bomberman1e1.png",
            "src/main/resources/assets/entities/bomberman1/bomberman1e2.png",
            "src/main/resources/assets/entities/bomberman1/bomberman1e3.png",
            "src/main/resources/assets/entities/bomberman1/bomberman1e4.png",
            "src/main/resources/assets/entities/bomberman1/bomberman1n1.png",
            "src/main/resources/assets/entities/bomberman1/bomberman1n2.png",
            "src/main/resources/assets/entities/bomberman1/bomberman1n3.png",
            "src/main/resources/assets/entities/bomberman1/bomberman1s1.png",
            "src/main/resources/assets/entities/bomberman1/bomberman1s2.png",
            "src/main/resources/assets/entities/bomberman1/bomberman1s3.png",
            "src/main/resources/assets/entities/bomberman1/bomberman1w1.png",
            "src/main/resources/assets/entities/bomberman1/bomberman1w2.png",
            "src/main/resources/assets/entities/bomberman1/bomberman1w3.png",
            "src/main/resources/assets/entities/bomberman1/bomberman1w4.png"
    )),

    PLAYER1_IMMORTAL_IMG(Arrays.asList(
            "src/main/resources/assets/entities/bomberman1/immortalityBonusActive/bomberman1e1.png",
            "src/main/resources/assets/entities/bomberman1/immortalityBonusActive/bomberman1e2.png",
            "src/main/resources/assets/entities/bomberman1/immortalityBonusActive/bomberman1e3.png",
            "src/main/resources/assets/entities/bomberman1/immortalityBonusActive/bomberman1e4.png",
            "src/main/resources/assets/entities/bomberman1/immortalityBonusActive/bomberman1n1.png",
            "src/main/resources/assets/entities/bomberman1/immortalityBonusActive/bomberman1n2.png",
            "src/main/resources/assets/entities/bomberman1/immortalityBonusActive/bomberman1n3.png",
            "src/main/resources/assets/entities/bomberman1/immortalityBonusActive/bomberman1s1.png",
            "src/main/resources/assets/entities/bomberman1/immortalityBonusActive/bomberman1s2.png",
            "src/main/resources/assets/entities/bomberman1/immortalityBonusActive/bomberman1s3.png",
            "src/main/resources/assets/entities/bomberman1/immortalityBonusActive/bomberman1w1.png",
            "src/main/resources/assets/entities/bomberman1/immortalityBonusActive/bomberman1w2.png",
            "src/main/resources/assets/entities/bomberman1/immortalityBonusActive/bomberman1w3.png",
            "src/main/resources/assets/entities/bomberman1/immortalityBonusActive/bomberman1w4.png"
    )),

    /**
     * Represents the image URL for the second player character.
     */
    PLAYER2_IMG(Arrays.asList(
            "src/main/resources/assets/entities/bomberman2/bomberman2e1.png",
            "src/main/resources/assets/entities/bomberman2/bomberman2e2.png",
            "src/main/resources/assets/entities/bomberman2/bomberman2e3.png",
            "src/main/resources/assets/entities/bomberman2/bomberman2e4.png",
            "src/main/resources/assets/entities/bomberman2/bomberman2n1.png",
            "src/main/resources/assets/entities/bomberman2/bomberman2n2.png",
            "src/main/resources/assets/entities/bomberman2/bomberman2n3.png",
            "src/main/resources/assets/entities/bomberman2/bomberman2s1.png",
            "src/main/resources/assets/entities/bomberman2/bomberman2s2.png",
            "src/main/resources/assets/entities/bomberman2/bomberman2s3.png",
            "src/main/resources/assets/entities/bomberman2/bomberman2w1.png",
            "src/main/resources/assets/entities/bomberman2/bomberman2w2.png",
            "src/main/resources/assets/entities/bomberman2/bomberman2w3.png",
            "src/main/resources/assets/entities/bomberman2/bomberman2w4.png"
    )),

    PLAYER2_IMMORTAL_IMG(Arrays.asList(
            "src/main/resources/assets/entities/bomberman2/immortalityBonusActive/bomberman2e1.png",
            "src/main/resources/assets/entities/bomberman2/immortalityBonusActive/bomberman2e2.png",
            "src/main/resources/assets/entities/bomberman2/immortalityBonusActive/bomberman2e3.png",
            "src/main/resources/assets/entities/bomberman2/immortalityBonusActive/bomberman2e4.png",
            "src/main/resources/assets/entities/bomberman2/immortalityBonusActive/bomberman2n1.png",
            "src/main/resources/assets/entities/bomberman2/immortalityBonusActive/bomberman2n2.png",
            "src/main/resources/assets/entities/bomberman2/immortalityBonusActive/bomberman2n3.png",
            "src/main/resources/assets/entities/bomberman2/immortalityBonusActive/bomberman2s1.png",
            "src/main/resources/assets/entities/bomberman2/immortalityBonusActive/bomberman2s2.png",
            "src/main/resources/assets/entities/bomberman2/immortalityBonusActive/bomberman2s3.png",
            "src/main/resources/assets/entities/bomberman2/immortalityBonusActive/bomberman2w1.png",
            "src/main/resources/assets/entities/bomberman2/immortalityBonusActive/bomberman2w2.png",
            "src/main/resources/assets/entities/bomberman2/immortalityBonusActive/bomberman2w3.png",
            "src/main/resources/assets/entities/bomberman2/immortalityBonusActive/bomberman2w4.png"
    )),

    /**
     * Represents the image URL for the background of map 1.
     */
    BACKGROUND_IMG_MAP1("src/main/resources/assets/tiles/map1/grass2.png"),

    /**
     * Represents the image URL for a box in map 1.
     */
    BOX_IMG_MAP1("src/main/resources/assets/tiles/map1/box_pixel.png"),

    /**
     * Represents the image URL for a monster in map 1.
     */
    MONSTER_IMG_MAP1(Arrays.asList(
            "src/main/resources/assets/entities/monster1/monstere1.png",
            "src/main/resources/assets/entities/monster1/monstere2.png",
            "src/main/resources/assets/entities/monster1/monstere3.png",
            "src/main/resources/assets/entities/monster1/monstere4.png",
            "src/main/resources/assets/entities/monster1/monstern1.png",
            "src/main/resources/assets/entities/monster1/monstern2.png",
            "src/main/resources/assets/entities/monster1/monstern3.png",
            "src/main/resources/assets/entities/monster1/monsters1.png",
            "src/main/resources/assets/entities/monster1/monsters2.png",
            "src/main/resources/assets/entities/monster1/monsters3.png",
            "src/main/resources/assets/entities/monster1/monsterw1.png",
            "src/main/resources/assets/entities/monster1/monsterw2.png",
            "src/main/resources/assets/entities/monster1/monsterw3.png",
            "src/main/resources/assets/entities/monster1/monsterw4.png"
    )),

    /**
     * Represents the image URL for a wall in map 1.
     */
    WALL_IMG_MAP1("src/main/resources/assets/tiles/map1/wall_pixel.png"),

    /**
     * Represents the image URL for the background of map 2.
     */
    BACKGROUND_IMG_MAP2("src/main/resources/assets/tiles/map2/fire_background.png"),

    /**
     * Represents the image URL for a box in map 2.
     */
    BOX_IMG_MAP2("src/main/resources/assets/tiles/map2/fire_box.png"),

    /**
     * Represents the image URL for a monster in map 2.
     */
    MONSTER_IMG_MAP2(Arrays.asList(
            "src/main/resources/assets/entities/monster2/monstere1.png",
            "src/main/resources/assets/entities/monster2/monstere2.png",
            "src/main/resources/assets/entities/monster2/monstere3.png",
            "src/main/resources/assets/entities/monster2/monstere4.png",
            "src/main/resources/assets/entities/monster2/monstern1.png",
            "src/main/resources/assets/entities/monster2/monstern2.png",
            "src/main/resources/assets/entities/monster2/monstern3.png",
            "src/main/resources/assets/entities/monster2/monsters1.png",
            "src/main/resources/assets/entities/monster2/monsters2.png",
            "src/main/resources/assets/entities/monster2/monsters3.png",
            "src/main/resources/assets/entities/monster2/monsterw1.png",
            "src/main/resources/assets/entities/monster2/monsterw2.png",
            "src/main/resources/assets/entities/monster2/monsterw3.png",
            "src/main/resources/assets/entities/monster2/monsterw4.png"
    )),

    /**
     * Represents the image URL for a wall in map 2.
     */
    WALL_IMG_MAP2("src/main/resources/assets/tiles/map2/fire_wall.png"),

    /**
     * Represents the image URL for the background of map 3.
     */
    BACKGROUND_IMG_MAP3("src/main/resources/assets/tiles/map3/ice_background.png"),

    /**
     * Represents the image URL for a box in map 3.
     */
    BOX_IMG_MAP3("src/main/resources/assets/tiles/map3/ice_box.png"),

    /**
     * Represents the image URL for a monster in map 3.
     */
    MONSTER_IMG_MAP3(Arrays.asList(
            "src/main/resources/assets/entities/monster3/monstere1.png",
            "src/main/resources/assets/entities/monster3/monstere2.png",
            "src/main/resources/assets/entities/monster3/monstere3.png",
            "src/main/resources/assets/entities/monster3/monstere4.png",
            "src/main/resources/assets/entities/monster3/monstern1.png",
            "src/main/resources/assets/entities/monster3/monstern2.png",
            "src/main/resources/assets/entities/monster3/monstern3.png",
            "src/main/resources/assets/entities/monster3/monsters1.png",
            "src/main/resources/assets/entities/monster3/monsters2.png",
            "src/main/resources/assets/entities/monster3/monsters3.png",
            "src/main/resources/assets/entities/monster3/monsterw1.png",
            "src/main/resources/assets/entities/monster3/monsterw2.png",
            "src/main/resources/assets/entities/monster3/monsterw3.png",
            "src/main/resources/assets/entities/monster3/monsterw4.png"
    )),

    /**
     * Represents the image URL for a wall in map 3.
     */
    WALL_IMG_MAP3("src/main/resources/assets/tiles/map3/ice_wall.png"),

    /**
     * Represents the image URL for a flame effect.
     */
    FLAME_IMG("src/main/resources/assets/tiles/map3/ice_monster.png"),

    /**
     * Represents the image URL for the bigger range bonus.
     */
    BIGGER_RANGE_BONUS_IMG("src/main/resources/assets/bonuses/bigger_range.png"),

    /**
     * Represents the image URL for the bomb up bonus.
     */
    BOMB_UP_BONUS_IMG("src/main/resources/assets/bonuses/bomb_up.png"),
    BOX_BONUS_IMG("src/main/resources/assets/bonuses/box_bonus.png"),
    ROLLER_BONUS_IMG("src/main/resources/assets/bonuses/roller.png"),
    SLOW_DOWN_BONUS_IMG("src/main/resources/assets/bonuses/slow_down.png"),
    DETONATOR_BONUS_IMG("src/main/resources/assets/bonuses/detonator.png"),
    GHOST_BONUS_IMG("src/main/resources/assets/bonuses/ghostbonus.png"),
    IMMORTALITY_BONUS_IMG("src/main/resources/assets/bonuses/immortalitybonus.png"),
    FLAME_RIGHT_IMG("src/main/resources/assets/flame_right.png"),
    FLAME_DOWN_IMG("src/main/resources/assets/flame_down.png"),
    FLAME_UP_IMG("src/main/resources/assets/flame_up.png"),
    FLAME_LEFT_IMG("src/main/resources/assets/flame_left.png"),
    IMMEDIATELY_IMG("src/main/resources/assets/bonuses/immediately.png"),
    PACIFIST_IMG("src/main/resources/assets/bonuses/pacifist.png"),
    SMALLERRANGE_IMG("src/main/resources/assets/bonuses/smallerRange.png"),
    GHOST_MONSTER_IMG("src/main/resources/assets/ghost.png"),
    SEMI_INTELLIGENT_MONSTER_IMG("src/main/resources/assets/semi_intelligent_monster.gif"),
    INTELLIGENT_MONSTER_IMG("src/main/resources/assets/intelligent_monster.png");

    private final String url;
    private final List<String> urls;

    Image(List<String> envUrls) {
        this.urls = envUrls;
        this.url = null; // Since this constructor is used only for list URLs, set single URL to null
    }

    Image(String envUrl) {
        this.url = envUrl;
        this.urls = null; // Since this constructor is used only for single URL, set list URLs to null
    }

    public List<String> getImageUrls() {
        return urls;
    }

    public String getImageUrl() {
        return url;
    }
}
