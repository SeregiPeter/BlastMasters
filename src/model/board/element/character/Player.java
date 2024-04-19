package model.board.element.character;

import control.Settings;
import model.board.Board;
import model.board.Direction;
import model.board.Velocity;
import model.board.element.Entity;
import model.board.element.deposable.Bomb;
import model.board.element.deposable.Box;
import model.board.element.deposable.Flame;
import model.board.element.field.Wall;
import model.board.element.powerup.Bonus;
import model.board.element.powerup.benefit.*;
import model.board.element.powerup.handicap.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;


import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static model.board.Image.*;
import static model.board.Size.*;
import static model.board.Velocity.BOMB_VEL;
import static model.board.Velocity.BONUS_VEL;

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
    private List<Box> boxes;
    private List<Bonus> bonuses;
    private int maxNumberOfBombs;
    private int numberOfPlaceableBombs;
    private int numberOfPlaceableBoxes;
    private boolean hasDetonator;
    private boolean hasRoller;
    private boolean immortal;
    private boolean ghost;
    private boolean canPlaceObstacles;
    private boolean slowedDown;
    private boolean canPlaceBombs;
    private boolean rangeShrunk;
    private boolean ghostPulsation;
    private int numberOfObstacles;
    private int bombRange;
    private Settings settings;
    private Bomb lastPlantedBomb;
    private boolean onBomb;
    private boolean onBox;
    private ArrayList<Bomb> onBombs;
    private ArrayList<Box> onBoxes;
    private List<Image> images;
    private int imageChangeCounter = 0;
    private int opacityChangeCounter = 0;
    private float currentOpacity;
    // Define a threshold for image change frequency
    private int numberOfSlowDownBonuses = 0;
    private static final int IMAGE_CHANGE_THRESHOLD = 8;
    private javax.swing.Timer callertimer;
    private javax.swing.Timer coolDownTimerImmediately;
    private javax.swing.Timer coolDownTimerPacifist;
    private javax.swing.Timer coolDowmTimerGhost;
    private javax.swing.Timer untilGhostPulsationTimer;
    private final javax.swing.Timer coolDownTimerSmallRange;
    boolean immediatelyHandicapActive;

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
        boxes = new ArrayList<Box>();
        //addBomb();
        bonuses = new ArrayList<Bonus>();
        maxNumberOfBombs = 1;
        numberOfPlaceableBombs = 1;
        numberOfPlaceableBoxes = 0;
        this.hasDetonator = false;
        this.hasRoller = false;
        this.immortal = false;
        this.ghost = false;
        this.canPlaceObstacles = false;
        this.slowedDown = false;
        this.canPlaceBombs = true;
        this.numberOfObstacles = 0;
        this.settings = settings;
        this.immediatelyHandicapActive = false;
        this.rangeShrunk = false;
        this.ghostPulsation = false;
        lastPlantedBomb = null;
        onBomb = false;
        onBox = false;
        bombRange = 2;
        this.explodable = true;
        onBombs = new ArrayList<>();
        onBoxes = new ArrayList<>();
        currentOpacity = 0.4f;
        callertimer = new javax.swing.Timer(100, new Caller());
        coolDownTimerImmediately = new javax.swing.Timer(1000 * 10, new Cooldown());
        coolDownTimerImmediately.setActionCommand("0");
        coolDownTimerImmediately.setRepeats(false);
        coolDownTimerPacifist = new javax.swing.Timer(1000 * 5, new Cooldown());
        coolDownTimerPacifist.setActionCommand("1");
        coolDownTimerPacifist.setRepeats(false);
        coolDownTimerSmallRange = new javax.swing.Timer(1000 * 15, new Cooldown());
        coolDownTimerSmallRange.setActionCommand("2");
        coolDownTimerSmallRange.setRepeats(false);
        coolDowmTimerGhost = new javax.swing.Timer(1000 * 10, new Cooldown());
        coolDowmTimerGhost.setActionCommand("3");
        coolDowmTimerGhost.setRepeats(false);
        untilGhostPulsationTimer = new javax.swing.Timer(1000 * 7, new Cooldown());
        untilGhostPulsationTimer.setActionCommand("4");
        untilGhostPulsationTimer.setRepeats(false);
    }


    /**
     * Gets the position where the bomb will be placed by the player.
     *
     * @return the position as a Point object
     */
    public Point getThePositionOfTheBombToBePlaced() {
        return new Point(BOMB_WIDTH.getSize() * getColumn(), BOMB_HEIGHT.getSize() * getRow());
    }

    public Point getThePositionOfTheBoxToBePlaced() {
        return new Point(TILE_WIDTH.getSize() * getColumn(), TILE_HEIGHT.getSize() * getRow());
    }

    /**
     * Plants a bomb at the player's current position on the game board.
     */
    public void plantBomb() {
        if (!canPlaceBombs) return;
        if (numberOfPlaceableBombs == 0 && hasDetonator) {
            explodeBombs();
            return;
        }
        if (numberOfPlaceableBombs == 0 || !alive) {
            return;
        }
        Bomb bomb;
        if (rangeShrunk) {
            bomb = new Bomb((int) getThePositionOfTheBombToBePlaced().getX(), (int) getThePositionOfTheBombToBePlaced().getY(), BOMB_WIDTH.getSize(), BOMB_HEIGHT.getSize(), BOMB_VEL.getVelocity(), 1, new ImageIcon(BOMB_IMG.getImageUrl()).getImage(), false, false, this, this.board);
        } else {
            bomb = new Bomb((int) getThePositionOfTheBombToBePlaced().getX(), (int) getThePositionOfTheBombToBePlaced().getY(), BOMB_WIDTH.getSize(), BOMB_HEIGHT.getSize(), BOMB_VEL.getVelocity(), bombRange, new ImageIcon(BOMB_IMG.getImageUrl()).getImage(), false, false, this, this.board);
        }

        for (Entity entity : board.getEntities()) {
            if (!(entity.equals(this)) && entity.collides(bomb)) {
                return;
            }
        }
        numberOfPlaceableBombs--;
        bomb.plant();
        onBombs.add(bomb);
        onBomb = true;
        bombs.add(bomb);
    }

    /**
     * Explodes the bombs placed by the player.
     * This method is used when the player has the Detonator bonus.
     */
    public void explodeBombs() {
        ArrayList<Bomb> ownBombs = new ArrayList<>(bombs);
        for (Bomb bomb : ownBombs) {
            if (!bomb.isDetonated()) {
                bomb.explode();
            }
        }
    }

    /**
     * Adds a new bomb to the player's list of bombs.
     */
    public void addBomb() {
        bombs.add(new Bomb(this.x, this.y, BOMB_WIDTH.getSize(), BOMB_HEIGHT.getSize(), BOMB_VEL.getVelocity(), bombRange, new ImageIcon(BOMB_IMG.getImageUrl()).getImage(), false, false, this, this.board));
    }

    public void removeBomb(Bomb bomb) {
        this.bombs.remove(bomb);
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
        double oldVelocity = this.velocity;
        this.velocity = velocity;
        move(d);
        this.velocity = oldVelocity;
    }

    /**
     * Moves the player in the specified direction with the default velocity.
     *
     * @param direction the direction to move
     */
    /* Így most folyamatosan tud lelépni a bombáról. Ha ugrásszerűen akarjuk,
    akkor overloadolni kéne a moveTowardsDirection-t úgy, hogy megkapja a visszalépés mértékét (a bomba méretét).*/
    public void move(Direction direction) {
        if(!alive) return;
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

        boolean shouldBePlacedBack = false;
        this.moveTowardsDirection(direction);
        ArrayList<Entity> entities = new ArrayList<>(board.getEntities());
        if (ghost) {
            if (this.x < TILE_WIDTH.getSize() || this.x + this.width > ((BOARD_WIDTH.getSize()-1) * TILE_WIDTH.getSize())  || this.y < TILE_HEIGHT.getSize() ||
                    this.y + this.height > ((BOARD_HEIGHT.getSize()-1) * TILE_HEIGHT.getSize())) {
                shouldBePlacedBack = true;
            }
            for (Entity entity : entities) {
                if (entity instanceof Bonus && this.collides(entity)) {
                    this.runIntoBonus((Bonus) entity);
                }
            }
        } else {
            // Perform movement logic
            for (Entity entity : entities) {
                if (((entity instanceof Wall) || (entity instanceof Box) || (entity instanceof Bomb && !onBombs.contains(entity))) && this.collides(entity)) {
                    shouldBePlacedBack = true;
                    break;
                }
                if (entity instanceof Bonus && this.collides(entity)) {
                    this.runIntoBonus((Bonus) entity);
                }
                if ((entity instanceof Flame || entity instanceof Monster) && entity.collides(this)) {
                    this.alive = false;
                }
            }
        }

        //if(onBomb && !this.collides(lastPlantedBomb)) onBomb = false;
        ArrayList<Bomb> bombsToBeChecked = new ArrayList<>(onBombs);
        for (Bomb bomb : bombsToBeChecked) {
            if (!this.collides(bomb)) onBombs.remove(bomb);
        }

        ArrayList<Box> boxesToBeChecked = new ArrayList<>(onBoxes);
        for (Box box : boxesToBeChecked) {
            if (!this.collides(box)) onBoxes.remove(box);
        }

        if (shouldBePlacedBack) {
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

    public void incrementNumberOfPlaceableBoxes() {
        this.numberOfPlaceableBoxes++;
        System.out.println("Number of placeable boxes: " + numberOfPlaceableBoxes);
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
        if (bonus.getOwner() == null && bonus.isVisible()) {
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
        points = tempPlayerPoints;
    }

    public void useRollerBonus() {
        this.velocity = Velocity.PLAYER_WITH_ROLLER_VEL.getVelocity();
        this.hasRoller = true;
    }

    public void useSlowDownBonus() {
        this.velocity = Velocity.PLAYER_WITH_SLOWDOWN_VEL.getVelocity();
        this.slowedDown = true;
        int oldNumberOfSlowDownBonuses = ++numberOfSlowDownBonuses;

        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (numberOfSlowDownBonuses == oldNumberOfSlowDownBonuses) {
                    if (hasRoller) {
                        velocity = Velocity.PLAYER_WITH_ROLLER_VEL.getVelocity();
                    } else {
                        velocity = Velocity.PLAYER_VEL.getVelocity();
                    }
                    slowedDown = false;
                    numberOfSlowDownBonuses = 0;
                }
            }
        }, 5000);

    }

    public void pacifist() {
        if (!canPlaceBombs) { //clean code-hoz másik változónevet igényelnék...
            coolDownTimerPacifist.restart();
        } else {
            canPlaceBombs = false;
            coolDownTimerPacifist.start();
        }
    }

    public void plantBombImmediately() {
        if (immediatelyHandicapActive) {
            coolDownTimerImmediately.restart();
        } else {
            immediatelyHandicapActive = true;
            callertimer.start();
            coolDownTimerImmediately.start();
        }

    }

    public Bonus putRandomBonusInBox(Box box) {
        Random random = new Random();
        int randomNumber = random.nextInt(8); // Az eddig elkészült bónuszok száma
        Bonus bonus = null;
        switch (randomNumber) {
            case 0:
                bonus = new BiggerRangeBonus(box.getX(), box.getY(), BONUS_SIZE.getSize(), BONUS_SIZE.getSize(), BONUS_VEL.getVelocity(), new ImageIcon(BIGGER_RANGE_BONUS_IMG.getImageUrl()).getImage(), false, false, null);
                break;
            case 1:
                bonus = new MaxBombsBonus(box.getX(), box.getY(), BONUS_SIZE.getSize(), BONUS_SIZE.getSize(), BONUS_VEL.getVelocity(), new ImageIcon(BOMB_UP_BONUS_IMG.getImageUrl()).getImage(), false, false, null);
                break;
            case 2:
                bonus = new RollerBonus(box.getX(), box.getY(), BONUS_SIZE.getSize(), BONUS_SIZE.getSize(), BONUS_VEL.getVelocity(), new ImageIcon(ROLLER_BONUS_IMG.getImageUrl()).getImage(), false, false, null);
                break;
            case 3:
                bonus = new SlowDownBonus(box.getX(), box.getY(), BONUS_SIZE.getSize(), BONUS_SIZE.getSize(), BONUS_VEL.getVelocity(), new ImageIcon(SLOW_DOWN_BONUS_IMG.getImageUrl()).getImage(), false, false, null);
                break;
            case 4:
                bonus = new DetonatorBonus(box.getX(), box.getY(), BONUS_SIZE.getSize(), BONUS_SIZE.getSize(), BONUS_VEL.getVelocity(), new ImageIcon(DETONATOR_BONUS_IMG.getImageUrl()).getImage(), false, false, null);
                break;
            case 5:
                bonus = new PlaceBombsImmediatelyBonus(box.getX(), box.getY(), BONUS_SIZE.getSize(), BONUS_SIZE.getSize(), BONUS_VEL.getVelocity(), new ImageIcon(IMMEDIATELY_IMG.getImageUrl()).getImage(), false, false, null);
                break;
            case 6:
                bonus = new NoBombsBonus(box.getX(), box.getY(), BONUS_SIZE.getSize(), BONUS_SIZE.getSize(), BONUS_VEL.getVelocity(), new ImageIcon(PACIFIST_IMG.getImageUrl()).getImage(), false, false, null);
                break;
            case 7:
                bonus = new SmallerRangeBonus(box.getX(), box.getY(), BONUS_SIZE.getSize(), BONUS_SIZE.getSize(), BONUS_VEL.getVelocity(), new ImageIcon(SMALLERRANGE_IMG.getImageUrl()).getImage(), false, false, null);
                break;

        }
        return bonus;
    }

    public void plantBox() {

        if (numberOfPlaceableBoxes == 0 || !alive) {
            return;
        }
        Box box;
        ImageIcon boximage=null;
        switch(board.getSelectedMapIndex()){

            case 1:
                boximage = new ImageIcon(BOX_IMG_MAP2.getImageUrl());
                break;
            case 2:
                boximage = new ImageIcon(BOX_IMG_MAP3.getImageUrl());
                break;
            default:
                boximage= new ImageIcon(BOX_IMG_MAP1.getImageUrl());
                break;

        }
        box = new Box((int) getThePositionOfTheBoxToBePlaced().getX(),
                (int) getThePositionOfTheBoxToBePlaced().getY(), TILE_WIDTH.getSize(), TILE_HEIGHT.getSize(),
                BOMB_VEL.getVelocity(), boximage.getImage(),
                false, false, null, board);

        box.setOwner(this);

        for (Entity entity : board.getEntities()) {
            if (!(entity.equals(this)) && entity.collides(box)) {
                return;
            }
        }
        numberOfPlaceableBoxes--;
        box.plant();
        onBoxes.add(box);
        onBox = true;
    }

    public void smallerRange() {
        if (rangeShrunk) {
            coolDownTimerSmallRange.restart();
        } else {
            rangeShrunk = true;
            coolDownTimerSmallRange.start();
        }
    }

    class Caller implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (hasDetonator && numberOfPlaceableBombs == 0) return; // ha detonátor bonus alatt hivogatja egymás után
            plantBomb();                                         // a plantbomb()-ot az biztos halált jelent
        }
    }

    class Cooldown implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            int command = parseInt(ae.getActionCommand());
            switch (command) {
                case 0:                                     //Immediately bonus
                    immediatelyHandicapActive = false;
                    callertimer.stop();
                    break;
                case 1:                                     //Pacifist bonus
                    canPlaceBombs = true;
                    break;
                case 2:                                     //SmallerRange bonus
                    rangeShrunk = false;
                    break;
                case 3:                                     //Ghost bonus
                    ghost = false;
                    ghostPulsation = false;
                    ArrayList<Entity> entities = new ArrayList<>(board.getEntities());
                    for (Entity entity : entities) {
                        if (collides(entity)) {
                            if (entity instanceof Wall || entity instanceof Box) {
                                alive = false;
                            } else if (entity instanceof Bomb) {
                                onBombs.add((Bomb) entity);
                            }
                        }
                    }
                    break;
                case 4:
                    opacityChangeCounter = 0;
                    ghostPulsation = true;
                    break;
            }
        }
    }

    public void useDetonatorBonus() {
        this.hasDetonator = true;
    }

    public boolean hasDetonator() {
        return hasDetonator;
    }

    public void useGhostBonus() {
        if (ghost) {
            ghostPulsation = false;
            coolDowmTimerGhost.restart();
            untilGhostPulsationTimer.restart();
        } else {
            ghost = true;
            coolDowmTimerGhost.start();
            untilGhostPulsationTimer.start();
        }
    }

    @Override
    public void draw(Graphics g) {
        if(this.visible) {
            Graphics2D g2d = (Graphics2D) g.create();
            if (this.ghost && !ghostPulsation) {
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
                opacityChangeCounter++;
            } else if (this.ghost && ghostPulsation) {
                opacityChangeCounter++;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, currentOpacity));
                if (opacityChangeCounter >= 40) {
                    float newOpacity = currentOpacity == 1f ? 0.4f : 1f;
                    currentOpacity = newOpacity;
                    opacityChangeCounter = 0;
                }
            }
            g2d.drawImage(image, x, y, width, height, null);
            g2d.dispose();
        }
    }

}
