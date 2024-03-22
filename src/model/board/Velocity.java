package model.board;

public enum Velocity {
    BOMB_VEL(0),
    WALL_VEL(0),
    BOX_VEL(0),
    PLAYER_VEL(2), // jelenleg csak egész értékkel működik
    BONUS_VEL(0),
    MONSTER_VEL(0);

    private final double velocity;

    Velocity(double velocity) {
         this.velocity = velocity;
    }

    public double getVelocity() {
        return velocity;
    }
}
