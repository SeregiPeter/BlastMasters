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
import view.ui.PlayerDataPanel;

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
    private boolean immortalityPulsation;
    private int numberOfObstacles;
    private int bombRange;
    private Settings settings;
    private Bomb lastPlantedBomb;
    private boolean onBomb;
    private boolean onBox;
    private ArrayList<Bomb> onBombs;
    private ArrayList<Box> onBoxes;
    private List<Image> images;
    private List<Image> immortalImages;
    private List<Image> usedImages;
    private int imageChangeCounter = 0;
    private int opacityChangeCounter = 0;
    private int changeListOfImagesCounter = 0;
    private int currentIndexOfImageForPulsation = 0;
    private float currentOpacity;
    // Define a threshold for image change frequency
    private int numberOfSlowDownBonuses = 0;
    private static final int IMAGE_CHANGE_THRESHOLD = 8;
    private javax.swing.Timer callertimer;
    private javax.swing.Timer coolDownTimerImmediately;
    private javax.swing.Timer coolDownTimerPacifist;
    private javax.swing.Timer coolDowmTimerGhost;
    private javax.swing.Timer untilGhostPulsationTimer;
    private javax.swing.Timer coolDowmTimerImmortality;
    private javax.swing.Timer untilImmortalityPulsationTimer;
    private javax.swing.Timer coolDownTimerSmallRange;
    private javax.swing.Timer coolDownTimerSlowDown;
    boolean immediatelyHandicapActive;
    private PlayerDataPanel panel;
    private HashMap<javax.swing.Timer,Boolean> timers=new HashMap<>();
    private HashMap<javax.swing.Timer,Integer> delayedTimers=new HashMap<>();

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
    public Player(double x, double y, int width, int height, double velocity, List<Image> images, List<Image> immortalImages, boolean alive, boolean visible, String name, Board board, Settings settings) {
        super(x, y, width, height, velocity, images.get(0), alive, visible);
        this.images = images;
        this.immortalImages = immortalImages;
        this.usedImages = images;
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
        this.immortalityPulsation = false;
        lastPlantedBomb = null;
        onBomb = false;
        onBox = false;
        bombRange = 2;
        this.explodable = true;
        onBombs = new ArrayList<>();
        onBoxes = new ArrayList<>();
        currentOpacity = 0.4f;
        callertimer = new javax.swing.Timer(100, new Caller());
        
        initializeTimers();
    }

    private void initializeTimers() {
        coolDownTimerImmediately = new javax.swing.Timer(1000 * 10, new Cooldown());
        coolDownTimerImmediately.setActionCommand("0");
        coolDownTimerImmediately.setRepeats(false);
        timers.put(coolDownTimerImmediately,false);
        
        coolDownTimerPacifist = new javax.swing.Timer(1000 * 5, new Cooldown());
        coolDownTimerPacifist.setActionCommand("1");
        coolDownTimerPacifist.setRepeats(false);
        timers.put(coolDownTimerPacifist,false);
        
        coolDownTimerSmallRange = new javax.swing.Timer(1000 * 15, new Cooldown());
        coolDownTimerSmallRange.setActionCommand("2");
        coolDownTimerSmallRange.setRepeats(false);
        timers.put(coolDownTimerSmallRange,false);
        
        coolDowmTimerGhost = new javax.swing.Timer(1000 * 10, new Cooldown());
        coolDowmTimerGhost.setActionCommand("3");
        coolDowmTimerGhost.setRepeats(false);
        timers.put(coolDowmTimerGhost,false);
        
        untilGhostPulsationTimer = new javax.swing.Timer(1000 * 7, new Cooldown());
        untilGhostPulsationTimer.setActionCommand("4");
        untilGhostPulsationTimer.setRepeats(false);
        timers.put(untilGhostPulsationTimer,false);
        
        coolDowmTimerImmortality = new javax.swing.Timer(1000 * 10, new Cooldown());
        coolDowmTimerImmortality.setActionCommand("5");
        coolDowmTimerImmortality.setRepeats(false);
        timers.put(coolDowmTimerImmortality,false);
        
        untilImmortalityPulsationTimer = new javax.swing.Timer(1000 * 7, new Cooldown());
        untilImmortalityPulsationTimer.setActionCommand("6");
        untilImmortalityPulsationTimer.setRepeats(false);
        timers.put(untilImmortalityPulsationTimer,false);

        coolDownTimerSlowDown = new javax.swing.Timer(1000 * 5, new Cooldown());
        coolDownTimerSlowDown.setActionCommand("7");
        coolDownTimerSlowDown.setRepeats(false);
        timers.put(coolDownTimerSlowDown,false);
    }

    @Override
    public void setAlive(boolean alive) {
        if (!immortal) {
            super.setAlive(alive);
        }
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

        List<String> bombImageUrls = model.board.Image.BOMB_EXPLODE_IMG.getImageUrls();

        List<java.awt.Image> bombImages = new ArrayList<>();

        for (String url : bombImageUrls) {
            bombImages.add(new ImageIcon(url).getImage());
        }


        if (rangeShrunk) {
            bomb = new Bomb((int) getThePositionOfTheBombToBePlaced().getX(), (int) getThePositionOfTheBombToBePlaced().getY(), BOMB_WIDTH.getSize(), BOMB_HEIGHT.getSize(), BOMB_VEL.getVelocity(), 1, bombImages, false, false, this, this.board);
        } else {
            bomb = new Bomb((int) getThePositionOfTheBombToBePlaced().getX(), (int) getThePositionOfTheBombToBePlaced().getY(), BOMB_WIDTH.getSize(), BOMB_HEIGHT.getSize(), BOMB_VEL.getVelocity(), bombRange, bombImages, false, false, this, this.board);
        }

        for (Entity entity : board.getEntities()) {
            if (!(entity.equals(this)) && entity.collides(bomb)) {
                return;
            }
        }
        numberOfPlaceableBombs--;
        panel.refreshBombLabel(numberOfPlaceableBombs);
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
        List<Image> images = usedImages;

        if(!alive){
            this.image=images.get(14);

            return;
        }
        imageChangeCounter++;

        if (imageChangeCounter >= IMAGE_CHANGE_THRESHOLD) {
            imageChangeCounter = 0;

            switch (direction) {
                case UP:
                    int currentIndex = images.indexOf(this.image);
                    int nextIndex = (currentIndex + 1) % 3;
                    this.image = images.get(nextIndex + 4);
                    currentIndexOfImageForPulsation = nextIndex + 4;
                    break;
                case DOWN:
                    currentIndex = images.indexOf(this.image);
                    nextIndex = (currentIndex + 1) % 3;
                    this.image = images.get(nextIndex + 7);
                    currentIndexOfImageForPulsation = nextIndex + 7;
                    break;
                case LEFT:
                    currentIndex = images.indexOf(this.image);
                    nextIndex = (currentIndex + 1) % 4;
                    this.image = images.get(nextIndex + 10);
                    currentIndexOfImageForPulsation = nextIndex + 10;
                    break;
                case RIGHT:
                    currentIndex = images.indexOf(this.image);
                    nextIndex = (currentIndex + 1) % 4;
                    this.image = images.get(nextIndex);
                    currentIndexOfImageForPulsation = nextIndex;
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
                if (((entity instanceof Wall) || (entity instanceof Box && !onBoxes.contains(entity))  || (entity instanceof Bomb && !onBombs.contains(entity))) && this.collides(entity)) {
                    shouldBePlacedBack = true;
                    break;
                }
                if (entity instanceof Bonus && this.collides(entity)) {
                    this.runIntoBonus((Bonus) entity);
                }
                if ((entity instanceof Flame || entity instanceof Monster) && entity.collides(this)) {
                    setAlive(false);
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
        panel.refreshRangeLabel(bombRange);
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
        panel.refreshBombLabel(numberOfPlaceableBombs);
    }

    public void incrementNumberOfPlaceableBoxes() {
        this.numberOfPlaceableBoxes++;
        System.out.println("Number of placeable boxes: " + numberOfPlaceableBoxes);
        panel.refreshBoxLabel(numberOfPlaceableBoxes);
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
        panel.showBonus("Roller");
    }

    public void useSlowDownBonus() {
        if(slowedDown){
            coolDownTimerSlowDown.restart();
            panel.startLineTimer("SlowDown");
        }else {
            this.velocity = Velocity.PLAYER_WITH_SLOWDOWN_VEL.getVelocity();
            this.slowedDown = true;
            coolDownTimerSlowDown.start();
            panel.startLineTimer("SlowDown");
        }

    }

    public void pacifist() {
        if (!canPlaceBombs) { //clean code-hoz másik változónevet igényelnék...
            coolDownTimerPacifist.restart();
            panel.startLineTimer("Pacifist");
        } else {
            canPlaceBombs = false;
            coolDownTimerPacifist.start();
            panel.startLineTimer("Pacifist");
        }
    }

    public boolean isImmortal() {
        return immortal;
    }

    public void plantBombImmediately() {
        if (immediatelyHandicapActive) {
            coolDownTimerImmediately.restart();
            panel.startLineTimer("Immediately");
        } else {
            immediatelyHandicapActive = true;
            callertimer.start();
            coolDownTimerImmediately.start();
            panel.startLineTimer("Immediately");
        }

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
        panel.refreshBoxLabel(numberOfPlaceableBoxes);
        box.plant();
        onBoxes.add(box);
        onBox = true;
    }

    public void smallerRange() {
        if (rangeShrunk) {
            coolDownTimerSmallRange.restart();
            panel.startLineTimer("SmallRange");
        } else {
            rangeShrunk = true;
            coolDownTimerSmallRange.start();
            panel.startLineTimer("SmallRange");
        }
    }

    public void setPlayerDataPanel(PlayerDataPanel panel) {
        this.panel=panel;
    }

    public void stopTimers() {
        for (Map.Entry<Timer, Boolean> timer : timers.entrySet()){
            if (timer.getKey().isRunning()){
                //int delay=timer.getKey().getDelay();
                timer.getKey().stop();
                //System.out.println(timer.getKey().getDelay()+"remaining");
               //delayedTimers.put(timer.getKey(),delay);
                timer.setValue(true);
            }
        }

    }
    public void startTimers(){
        for (Map.Entry<Timer, Boolean> timer : timers.entrySet()){
            if(timer.getValue()){
                timer.getKey().start();
            }
            /*timer.getKey().start();
            timer.getKey().stop();
            System.out.println(timer.getKey().getDelay()-timer.getValue()+"start with delay");
            timer.getKey().setInitialDelay(timer.getKey().getDelay()-timer.getValue());
            timer.getKey().start();*/
        }
        delayedTimers.clear();
    }

    public int getNumberOfPlaceableBombs() {
        return numberOfPlaceableBombs;
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
                case 5:
                    image = images.get(currentIndexOfImageForPulsation);
                    usedImages = images;
                    immortal = false;
                    immortalityPulsation = false;
                    break;
                case 6:
                    changeListOfImagesCounter = 0;
                    immortalityPulsation = true;
                    break;
                case 7:                                 //slowdown
                    slowedDown=false;
                    if (hasRoller) {
                        velocity = Velocity.PLAYER_WITH_ROLLER_VEL.getVelocity();
                    } else {
                        velocity = Velocity.PLAYER_VEL.getVelocity();
                    }
            }
        }
    }

    public void useDetonatorBonus() {

        this.hasDetonator = true;
        panel.showBonus("Detonator");
    }

    public boolean hasDetonator() {
        return hasDetonator;
    }

    public void useGhostBonus() {
        if (ghost) {
            ghostPulsation = false;
            coolDowmTimerGhost.restart();
            untilGhostPulsationTimer.restart();
            panel.startLineTimer("Ghost");
        } else {
            panel.startLineTimer("Ghost");
            ghost = true;
            coolDowmTimerGhost.start();
            untilGhostPulsationTimer.start();
        }
    }

    public void useImmortalityBonus() {
        if (immortal) {
            usedImages = immortalImages;
            immortalityPulsation = false;
            coolDowmTimerImmortality.restart();
            untilImmortalityPulsationTimer.restart();
            panel.startLineTimer("Immortality");
        } else {
            panel.startLineTimer("Immortality");
            immortal = true;
            coolDowmTimerImmortality.start();
            untilImmortalityPulsationTimer.start();
        }
    }

    public int getNumberOfPlaceableBoxes() {
        return numberOfPlaceableBoxes;
    }

    public boolean isCanPlaceObstacles() {
        return canPlaceObstacles;
    }

    @Override
    public void draw(Graphics g) {
        if(this.visible) {
            Graphics2D g2d = (Graphics2D) g.create();


            if(!alive){
                image=usedImages.get(14);
            }

            if (this.immortal && !immortalityPulsation) {
                usedImages = immortalImages;
                changeListOfImagesCounter++;
            } else if (this.immortalityPulsation && immortalityPulsation) {
                changeListOfImagesCounter++;
                if (changeListOfImagesCounter >= 30) {
                    usedImages = usedImages == immortalImages ? images : immortalImages;
                    image = usedImages.get(currentIndexOfImageForPulsation);
                    changeListOfImagesCounter = 0;
                }
            }
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
            int drawX = (int) Math.round(x);
            int drawY = (int) Math.round(y);
            g2d.drawImage(image, drawX, drawY + TILE_HEIGHT.getSize(), width, height, null);
            g2d.dispose();
        }


    }

}
