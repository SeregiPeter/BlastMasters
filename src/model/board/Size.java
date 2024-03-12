package model.board;

public enum Size {
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
