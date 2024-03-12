package model.board;

public enum Velocity {
    BOMB_VEL(0),
    PLAYER_VEL(2),
    MONSTER_VEL(1);

    private int value;

    Velocity(int value) {
         this.value = value;
    }

    public int getValue() {
        return value;
    }
}
