package model.board.element.character;

import control.Settings;
import model.board.Board;
import model.board.Direction;
import model.board.element.Entity;
import model.board.element.deposable.Bomb;
import model.board.element.powerup.Bonus;

import java.awt.*;
import java.util.List;

public class Player extends Entity {

    private String name;
    private int points;
    private List<Bomb> bombs;
    private List<Bonus> bonuses;
    private int maxNumberOfBombs;
    private boolean hasDetonator;
    private boolean hasRoller;
    private boolean immortal;
    private boolean ghost;
    private boolean canPlaceObstacles;
    private boolean slowedDown;
    private boolean hasToPlaceImmediately;
    private boolean canPlaceBombs;
    private int numberOfObstacles;
    private int MaxNumberOfBombs;
    private Board board;
    private Settings settings;

    public Player(int x, int y, int width, int height, int velocity, Image image, boolean alive, boolean visible, String name, int points, List<Bomb> bombs, List<Bonus> bonuses, int maxNumberOfBombs, boolean hasDetonator, boolean hasRoller, boolean immortal, boolean ghost, boolean canPlaceObstacles, boolean slowedDown, boolean hasToPlaceImmediately, boolean canPlaceBombs, int numberOfObstacles, int maxNumberOfBombs1, Board board, Settings settings) {
        super(x, y, width, height, velocity, image, alive, visible);
        this.name = name;
        this.points = points;
        this.bombs = bombs;
        this.bonuses = bonuses;
        this.maxNumberOfBombs = maxNumberOfBombs;
        this.hasDetonator = hasDetonator;
        this.hasRoller = hasRoller;
        this.immortal = immortal;
        this.ghost = ghost;
        this.canPlaceObstacles = canPlaceObstacles;
        this.slowedDown = slowedDown;
        this.hasToPlaceImmediately = hasToPlaceImmediately;
        this.canPlaceBombs = canPlaceBombs;
        this.numberOfObstacles = numberOfObstacles;
        MaxNumberOfBombs = maxNumberOfBombs1;
        this.board = board;
        this.settings = settings;
    }

    public void plantBomb() {

    }

    public void explodeBombs() {

    }

    public void addBomb(Bomb b) {
        bombs.add(b);
    }

    public void addBonus(Bonus b) {
        bonuses.add(b);
    }

    public void move(Direction d) {

    }

}
