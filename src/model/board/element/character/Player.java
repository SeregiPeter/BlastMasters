package model.board.element.character;

import control.Settings;
import model.board.Board;
import model.board.Direction;
import model.board.element.Entity;
import model.board.element.deposable.Bomb;
import model.board.element.deposable.Box;
import model.board.element.deposable.Flame;
import model.board.element.field.Wall;
import model.board.element.powerup.Bonus;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static model.board.Image.BOMB_IMG;
import static model.board.Size.*;
import static model.board.Velocity.BOMB_VEL;
import static model.board.Velocity.PLAYER_VEL;

public class Player extends Entity {

    private String name;
    private int points;
    private Board board;
    private List<Bomb> bombs;
    private List<Bonus> bonuses;
    private int maxNumberOfBombs;
    private int numberOfPlaceableBombs;
    private boolean hasDetonator;
    private boolean hasRoller;
    private boolean immortal;
    private boolean ghost;
    private boolean canPlaceObstacles;
    private boolean slowedDown;
    private boolean hasToPlaceImmediately;
    private boolean canPlaceBombs;
    private int numberOfObstacles;
    private int bombRange;
    private Settings settings;
    private Bomb lastPlantedBomb;
    private boolean onBomb;

    public Player(int x, int y, int width, int height, double velocity, Image image, boolean alive, boolean visible, String name, Board board, Settings settings) {
        super(x, y, width, height, velocity, image, alive, visible);
        this.name = name;
        this.board = board;
        points = 0;
        bombs = new ArrayList<Bomb>();
        addBomb();
        bonuses = new ArrayList<Bonus>();
        maxNumberOfBombs = 1;
        numberOfPlaceableBombs = 1;
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
        lastPlantedBomb = null;
        onBomb = false;
        bombRange = 2;
        this.explodable = true;
    }

    public Point getCenterCoordinate() {
        return new Point((x+(width/2)), (y+(height/2)));
    }

    public int getColumn() {
        return (int)getCenterCoordinate().getX()/TILE_WIDTH.getSize();
    }

    public int getRow() {
        return (int)getCenterCoordinate().getY()/TILE_HEIGHT.getSize();
    }

    public Point getThePositionOfTheBombToBePlaced() {
        return new Point(BOMB_WIDTH.getSize() * getColumn(), BOMB_HEIGHT.getSize() * getRow());
    }


    public void plantBomb() {
        if(numberOfPlaceableBombs == 0 || !alive) {
            return;
        }
        Bomb bomb = new Bomb((int)getThePositionOfTheBombToBePlaced().getX(), (int)getThePositionOfTheBombToBePlaced().getY(), BOMB_WIDTH.getSize(), BOMB_HEIGHT.getSize(), BOMB_VEL.getVelocity(),  new ImageIcon(BOMB_IMG.getImageUrl()).getImage(), false, false, this, this.board);
        for(Entity entity : board.getEntities()) {
            if(!(entity.equals(this)) && entity.collides(bomb)) {
                return;
            }
        }
        numberOfPlaceableBombs--;
        bomb.plant();
        lastPlantedBomb = bomb;
        onBomb = true;
    }

    //Detonator bónusz esetén
    public void explodeBombs() {

    }

    public void addBomb() {
        bombs.add(new Bomb(this.x, this.y, BOMB_WIDTH.getSize(), BOMB_HEIGHT.getSize(), BOMB_VEL.getVelocity(), new ImageIcon(BOMB_IMG.getImageUrl()).getImage(), false, false, this, this.board));
    }

    public void addBonus(Bonus b) {
        bonuses.add(b);
    }
    public void move(Direction d, double velocity) {
        this.velocity=velocity;
        move(d);
        this.velocity=PLAYER_VEL.getVelocity();
    }
    /* Így most folyamatosan tud lelépni a bombáról. Ha ugrásszerűen akarjuk, akkor overloadolni kéne a moveTowardsDirection-t úgy, hogy megkapja a visszalépés mértékét (a bomba méretét).*/
    public void move(Direction direction) {
        if(!this.isAlive()) return;

        this.moveTowardsDirection(direction);

        boolean shouldBePlacedBack = false;
        for(Entity entity : board.getEntities()) {
            if(((entity instanceof Wall) || (entity instanceof Box) || (entity instanceof Bomb && (!entity.equals(lastPlantedBomb) || !onBomb))) && this.collides(entity)) {
                shouldBePlacedBack = true;
                break;
            }
            if(entity instanceof Bonus && this.collides(entity)) {
                this.runIntoBonus((Bonus) entity);
            }
            if((entity instanceof Flame || entity instanceof Monster) && entity.collides(this)) {
                this.alive = false;
            }
        }
        if(onBomb && !this.collides(lastPlantedBomb)) onBomb = false;

        if(shouldBePlacedBack) {
            this.moveTowardsDirection(Direction.getOppositeDirection(direction));
        }
    }

    public Settings getSettings() {
        return settings;
    }

    public String getName() {
        return name;
    }
    public void incrementBombRange() {
        this.bombRange++;
    }

    public void incrementMaxNumberOfBombs() {
        this.maxNumberOfBombs++;
    }

    public void incrementNumberOfPlaceableBombs() {
        this.numberOfPlaceableBombs++;
    }

    public int getBombRange() {
        return bombRange;
    }

    public void runIntoBonus(Bonus bonus) {
        if(bonus.getOwner() == null && bonus.isVisible()) {
            bonus.getUsedByPlayer(this);
            System.out.println(this + " felvette: " + bonus);
        }
    }

    @Override
    public String toString() {
        return "P";
    }
}
