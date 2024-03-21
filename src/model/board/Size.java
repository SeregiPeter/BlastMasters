package model.board;

public enum Size {
    BOARD_SIZE(200),
    PLAYER_HEIGHT(30),
    PLAYER_WIDTH(17),
    MONSTER_SIZE(40),
    WALL_SIZE(40),
    BOX_SIZE(40),
    BONUS_SIZE(20),
    BOMB_HEIGHT(40),
    BOMB_WIDTH(40),
    TILE_WIDTH(40),
    TILE_HEIGHT(40),
    FLAME_HEIGHT(40),
    FLAME_WIDTH(40);
    private final int size;

    Size(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

}
