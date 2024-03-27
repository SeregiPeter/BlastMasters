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

/**
 * The Player class represents a player character on the game board.
 * Players can move, plant bombs, collect bonuses, and interact with
 * other elements on the board.
 */
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
    private ArrayList<Bomb> onBombs;
    private List<Image> images;
    private int imageChangeCounter = 0;
    // Define a threshold for image change frequency
    private static final int IMAGE_CHANGE_THRESHOLD = 8;

    /**
     * Constructs a Player object with the specified parameters.
     *
     * @param x        the x-coordinate of the player
     * @param y        the y-coordinate of the player
     * @param width    the width of the player
     * @param height   the height of the player
     * @param velocity the velocity of the player
     * @param alive    the status indicating if the player is alive
     * @param visible  the status indicating if the player is visible
     * @param name     the name of the player
     * @param board    the game board the player belongs to
     * @param settings the game settings
     */
    public Player(int x, int y, int width, int height, double velocity, List<Image> images, boolean alive, boolean visible, String name, Board board, Settings settings) {
        super(x, y, width, height, velocity, images.get(0), alive, visible);
        this.images = images;
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
        onBombs = new ArrayList<>();


    }

    /**
     * Gets the center coordinate of the player.
     *
     * @return the center coordinate as a Point object
     */
    public Point getCenterCoordinate() {
        return new Point((x+(width/2)), (y+(height/2)));
    }

    /**
     * Gets the column index of the player on the game board.
     *
     * @return the column index of the player
     */
    public int getColumn() {
        return (int)getCenterCoordinate().getX()/TILE_WIDTH.getSize();
    }

    /**
     * Gets the row index of the player on the game board.
     *
     * @return the row index of the player
     */
    public int getRow() {
        return (int)getCenterCoordinate().getY()/TILE_HEIGHT.getSize();
    }

    /**
     * Gets the position where the bomb will be placed by the player.
     *
     * @return the position as a Point object
     */
    public Point getThePositionOfTheBombToBePlaced() {
        return new Point(BOMB_WIDTH.getSize() * getColumn(), BOMB_HEIGHT.getSize() * getRow());
    }

    /**
     * Plants a bomb at the player's current position on the game board.
     */
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
        //lastPlantedBomb = bomb;
        onBombs.add(bomb);
        onBomb = true;
    }

    /**
     * Explodes the bombs placed by the player.
     * This method is used when the player has the Detonator bonus.
     */
    public void explodeBombs() {

    }

    /**
     * Adds a new bomb to the player's list of bombs.
     */
    public void addBomb() {
        bombs.add(new Bomb(this.x, this.y, BOMB_WIDTH.getSize(), BOMB_HEIGHT.getSize(), BOMB_VEL.getVelocity(), new ImageIcon(BOMB_IMG.getImageUrl()).getImage(), false, false, this, this.board));
    }

    /**
     * Adds a bonus to the player's list of bonuses.
     *
     * @param b the bonus to be added
     */
    public void addBonus(Bonus b) {
        bonuses.add(b);
    }

    /**
     * Moves the player in the specified direction.
     *
     * @param d        the direction to move
     * @param velocity the velocity of the movement
     */
    public void move(Direction d, double velocity) {
        this.velocity=velocity;
        move(d);
        this.velocity=PLAYER_VEL.getVelocity();
    }

    /**
     * Moves the player in the specified direction with the default velocity.
     *
     * @param direction the direction to move
     */
    /* Így most folyamatosan tud lelépni a bombáról. Ha ugrásszerűen akarjuk,
    akkor overloadolni kéne a moveTowardsDirection-t úgy, hogy megkapja a visszalépés mértékét (a bomba méretét).*/



    public void move(Direction direction) {
        imageChangeCounter++;

        if (imageChangeCounter >= IMAGE_CHANGE_THRESHOLD) {
            imageChangeCounter = 0;

            switch (direction) {
                case UP:
                    int currentIndex = images.indexOf(this.image);
                    int nextIndex = (currentIndex + 1) % 3;
                    this.image = images.get(nextIndex + 4);
                    break;
                case DOWN:
                    currentIndex = images.indexOf(this.image);
                    nextIndex = (currentIndex + 1) % 3;
                    this.image = images.get(nextIndex + 7);
                    break;
                case LEFT:
                    currentIndex = images.indexOf(this.image);
                    nextIndex = (currentIndex + 1) % 4;
                    this.image = images.get(nextIndex + 10);
                    break;
                case RIGHT:
                    currentIndex = images.indexOf(this.image);
                    nextIndex = (currentIndex + 1) % 4;
                    this.image = images.get(nextIndex);
                    break;
                default:
                    break;
            }
        }

        // Perform movement logic
        this.moveTowardsDirection(direction);

        boolean shouldBePlacedBack = false;
        ArrayList<Entity> entities = new ArrayList<>(board.getEntities());
        for(Entity entity : entities) {
            if(((entity instanceof Wall) || (entity instanceof Box) || (entity instanceof Bomb && !onBombs.contains(entity))) && this.collides(entity)) {
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
        //if(onBomb && !this.collides(lastPlantedBomb)) onBomb = false;
        ArrayList<Bomb> bombsToBeChecked = new ArrayList<>(onBombs);
        for(Bomb bomb : bombsToBeChecked) {
            if(!this.collides(bomb)) onBombs.remove(bomb);
        }

        if(shouldBePlacedBack) {
            this.moveTowardsDirection(Direction.getOppositeDirection(direction));
        }
    }

    /**
     * Gets the game settings associated with the player.
     *
     * @return the game settings
     */
    public Settings getSettings() {
        return settings;
    }

    /**
     * Gets the name of the player.
     *
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Increments the bomb range of the player.
     */
    public void incrementBombRange() {
        this.bombRange++;
    }

    /**
     * Increments the maximum number of bombs the player can place.
     */
    public void incrementMaxNumberOfBombs() {
        this.maxNumberOfBombs++;
    }

    /**
     * Increments the number of placeable bombs the player has.
     */
    public void incrementNumberOfPlaceableBombs() {
        this.numberOfPlaceableBombs++;
    }

    /**
     * Gets the bomb range of the player.
     *
     * @return the bomb range
     */
    public int getBombRange() {
        return bombRange;
    }

    /**
     * Gets the list of bombs that the player is currently on.
     *
     * @return the list of bombs
     */
    public ArrayList<Bomb> getOnBombs() {
        return onBombs;
    }

    /**
     * Handles the action when the player runs into a bonus item.
     *
     * @param bonus the bonus item the player has run into
     */
    public void runIntoBonus(Bonus bonus) {
        if(bonus.getOwner() == null && bonus.isVisible()) {
            bonus.getUsedByPlayer(this);
            System.out.println(this + " felvette: " + bonus);
        }
    }

    /**
     * Returns a string representation of the Player.
     *
     * @return a string representation of the Player ("P")
     */
    @Override
    public String toString() {
        return "P";
    }

    /**
     * Increments the points of the player.
     */
    public void incrementPoints() {
        points++;
    }

    /**
     * Gets the points of the player.
     *
     * @return the player's points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Sets the points of the player.
     *
     * @param tempPlayerPoints the points to set
     */
    public void setPoints(int tempPlayerPoints) {
        points=tempPlayerPoints;
    }
}
