package model.board;

public enum Image {
    BOMB_IMG("src/resources/assets/bomb_explode.gif"),
    PLAYER1_IMG("src/resources/assets/bomberman1.png"),
    PLAYER2_IMG("src/resources/assets/bomberman2.png"),
    BACKGROUND_IMG_MAP1("src/resources/assets/tiles/map1/background.png"),
    BOX_IMG_MAP1("src/resources/assets/tiles/map1/box.png"),
    MONSTER_IMG_MAP1("src/resources/assets/tiles/map1/monster.png"),
    WALL_IMG_MAP1("src/resources/assets/tiles/map1/wall.png"),
    BACKGROUND_IMG_MAP2("src/resources/assets/tiles/map2/fire_background.png"),
    BOX_IMG_MAP2("src/resources/assets/tiles/map2/fire_box.png"),
    MONSTER_IMG_MAP2("src/resources/assets/tiles/map2/fire_monster.png"),
    WALL_IMG_MAP2("src/resources/assets/tiles/map2/fire_wall.png"),
    BACKGROUND_IMG_MAP3("src/resources/assets/tiles/map3/ice_background.png"),
    BOX_IMG_MAP3("src/resources/assets/tiles/map3/ice_box.png"),
    MONSTER_IMG_MAP3("src/resources/assets/tiles/map3/ice_monster.png"),
    WALL_IMG_MAP3("src/resources/assets/tiles/map3/ice_wall.png"),
    FLAME_IMG("src/resources/assets/tiles/map3/ice_monster.png"),
    BIGGER_RANGE_BONUS_IMG("src/resources/assets/bonuses/bigger_range.png"),
    BOMB_UP_BONUS_IMG("src/resources/assets/bonuses/bomb_up.png");

    private final String url;

    Image(String envUrl) {
        this.url = envUrl;
    }

    public String getImageUrl() {
        return url;
    }
}
