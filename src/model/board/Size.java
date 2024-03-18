package model.board;

import javax.management.ValueExp;

public enum Size {
    BOARD_SIZE(200),
    PLAYER_SIZE(20),
    MONSTER_SIZE(20),
    WALL_SIZE(30),
    BOX_SIZE(20),
    BONUS_SIZE(10),
    BOMB_HEIGHT(20),
    BOMB_WIDTH(20),
    TILE_WIDTH(30),
    TILE_HEIGHT(30);
    private int value;

    Size(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
