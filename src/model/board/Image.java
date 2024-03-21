package model.board;

public enum Image {
    BOMB_IMG("src/resources/assets/bomb.png"),
    PLAYER1_IMG("src/resources/assets/bomberman1_proba.png"),
    PLAYER2_IMG("src/resources/assets/bomberman2_proba.png"),
    BOX_IMG("src/resources/assets/box.png"),
    MONSTER_IMG("src/resources/assets/monster.png"),
    BACKGROUND_IMG("src/resources/assets/background.png"),
    WALL_IMG("src/resources/assets/wall.png"),
    POSITIVE_BONUS_IMG("src/resources/assets/positivebonus.png");

    private final String url;

    Image(String envUrl) {
        this.url = envUrl;
    }

    public String getImageUrl() {
        return url;
    }
}
