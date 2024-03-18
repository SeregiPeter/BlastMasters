package model.board;

public enum Size {
    BOARD_SIZE(200),
    PLAYER_SIZE(20),
    MONSTER_SIZE(20),
    WALL_SIZE(30),
    BOX_SIZE(20),
    BONUS_SIZE(10),
    BOMB_HEIGHT(20),
    BOMB_WIDTH(20);

    private int value;

    Size(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
