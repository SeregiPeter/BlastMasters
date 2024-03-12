package model.board.element.character;

import control.Settings;
import model.board.Board;
import model.board.Direction;
import model.board.element.Entity;
import model.board.element.deposable.Bomb;
import model.board.element.powerup.Bonus;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static model.board.Image.BOMB_IMG;
import static model.board.Size.BOMB_HEIGHT;
import static model.board.Size.BOMB_WIDTH;
import static model.board.Velocity.BOMB_VEL;

public class Player extends Entity {

    private String name;
    private int points;
    private Board board;
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
    private Settings settings;

    public Player(int x, int y, int width, int height, int velocity, Image image, boolean alive, boolean visible, String name, Board board, Settings settings) {
        super(x, y, width, height, velocity, image, alive, visible);
        this.name = name;
        this.board = board;
        points = 0;
        bombs = new ArrayList<Bomb>();
        addBomb();
        bonuses = new ArrayList<Bonus>();
        maxNumberOfBombs = 1;
        this.hasDetonator = false;
        this.hasRoller = false;
        this.immortal = false;
        this.ghost = false;
        this.canPlaceObstacles = false;
        this.slowedDown = false;
        this.hasToPlaceImmediately = false;
        this.canPlaceBombs = true;
        this.numberOfObstacles = 0;
        this.settings = settings;
    }

    public void plantBomb() {

    }

    public void explodeBombs() {

    }

    public void addBomb() {
        bombs.add(new Bomb(this.x, this.y, BOMB_WIDTH.getValue(), BOMB_HEIGHT.getValue(), BOMB_VEL.getValue(), new ImageIcon(BOMB_IMG.getUrl()).getImage(), false, false, this, this.board));
    }

    public void addBonus(Bonus b) {
        bonuses.add(b);
    }

    public void move(Direction d) {

    }

}
