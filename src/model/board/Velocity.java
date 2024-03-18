package model.board;

public enum Velocity {
    BOMB_VEL(0),
    WALL_VEL(0),
    BOX_VEL(0),
    PLAYER_VEL(2),
    MONSTER_VEL(1);

    private final int velocity;

    Velocity(int velocity) {
         this.velocity = velocity;
    }

    public int getVelocity() {
        return velocity;
    }
}
