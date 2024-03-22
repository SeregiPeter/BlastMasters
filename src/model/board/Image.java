package model.board;

public enum Image {
    BOMB_IMG("src/resources/assets/bomb_explode.gif"),
    PLAYER1_IMG("src/resources/assets/bomberman1.png"),
    PLAYER2_IMG("src/resources/assets/bomberman2.png"),
    BOX_IMG("src/resources/assets/ice_box.png"),
    MONSTER_IMG("src/resources/assets/ice_monster.png"),
    BACKGROUND_IMG("src/resources/assets/ice_background.png"),
    WALL_IMG("src/resources/assets/ice_wall.png"),
    POSITIVE_BONUS_IMG("src/resources/assets/positivebonus.png"),
    FLAME_IMG("src/resources/assets/ice_monster.png"),
    BIGGER_RANGE_BONUS_IMG("src/resources/assets/bigger_range.png"),
    BOMB_UP_BONUS_IMG("src/resources/assets/bomb_up.png");



    private final String url;

    Image(String envUrl) {
        this.url = envUrl;
    }

    public String getImageUrl() {
        return url;
    }
}
