package model.board.element.deposable;

import model.board.Board;
import model.board.Direction;
import model.board.element.Entity;
import model.board.element.character.Player;
import model.board.element.field.Wall;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static model.board.Image.*;
import static model.board.Size.*;

/**
 * The Bomb class represents a bomb entity in the game.
 * Bombs can be planted by players and explode after a set duration.
 * When a bomb explodes, it creates flame entities in various directions.
 */
public class Bomb extends Entity {
    private boolean detonated = false;
    private Player owner;
    private Board board;
    private int range;
    private List<Image> images;
    private int imageChangeCounter = 0;

    private static final int IMAGE_CHANGE_THRESHOLD = 8;

    /**
     * Creates a new Bomb instance.
     *
     * @param x       The x-coordinate of the bomb.
     * @param y       The y-coordinate of the bomb.
     * @param width   The width of the bomb.
     * @param height  The height of the bomb.
     * @param velocity The velocity of the bomb.
     * @param images   The image of the bomb.
     * @param alive   The status of the bomb's existence.
     * @param visible The visibility of the bomb.
     * @param owner   The player who owns the bomb.
     * @param board   The game board where the bomb exists.
     */
    public Bomb(int x, int y, int width, int height, double velocity, int range, List<Image> images, boolean alive, boolean visible, Player owner, Board board) {
        super(x, y, BOMB_WIDTH.getSize(), BOMB_HEIGHT.getSize(), velocity, images.get(0), alive, visible);
        this.images=images;
        this.owner = owner;
        this.board = board;
        this.explodable = true;
        this.range = range;
    }

    /**
     * Plants the bomb on the game board.
     * Initiates a timer for the bomb to explode after a set duration.
     */


    public void plant() {
        this.setVisible(true);
        board.addEntity(this);
        board.addStaticElement(this, this.getRow(), this.getColumn());
        board.addBomb(this);

        if(owner.hasDetonator()) {
            return;
        }

        Timer fuse = new Timer();
        fuse.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!detonated) {
                    explode();
                }
            }
        }, 4 * 1000); // 4 másodperc után a bomba felrobban

        // Kép változtatásának időzítése
        Timer imageTimer = new Timer();
        imageTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Ellenőrizze, hogy az imageChangeCounter a képek számának megfelelően van-e beállítva
                if (imageChangeCounter >= images.size()) {
                    imageChangeCounter = 0; // Visszaállítjuk az imageChangeCounter értékét 0-ra, ha elértük a határt
                }

                // Beállítjuk az aktuális képet az imageChangeCounter alapján
                image = images.get(imageChangeCounter);
                imageChangeCounter++; // Növeljük az imageChangeCounter értékét a következő képre való váltáshoz
            }
        }, 0, 4000 / images.size()); // Kép váltásának időzítése, úgy hogy 4 másodperc alatt vegye végig az összes képet
    }


    /**
     * Retrieves the entity at the specified coordinates on the board.
     *
     * @param x The x-coordinate of the entity.
     * @param y The y-coordinate of the entity.
     * @return The entity at the given coordinates.
     */
    public Entity getEntFromXY(int x, int y) {
        ArrayList<Entity> entities = new ArrayList<>();
        for (Entity entity : board.getEntities()) {
            if (entity.getX() == x && entity.getY() == y) {
                entities.add(entity);
            }
        }
        return entities.get(entities.size() - 1);
    }

    /**
     * Explodes the bomb, creating flame entities in all directions.
     * The range of the explosion is determined by the bomb's range.
     */
    public void explode() {
        if(this.detonated) return;
        this.detonated = true;
        Flame flameUp = new Flame(this.x, this.y, FLAME_WIDTH.getSize(), FLAME_HEIGHT.getSize(), 0, new ImageIcon(FLAME_UP_IMG.getImageUrl()).getImage(), false, true, this.board, Direction.UP, this.range, this);

        Flame flameDown = new Flame(this.x, this.y, FLAME_WIDTH.getSize(), FLAME_HEIGHT.getSize(), 0, new ImageIcon(FLAME_DOWN_IMG.getImageUrl()).getImage(), false, true, this.board, Direction.DOWN, this.range, this);

        Flame flameLeft = new Flame(this.x , this.y, FLAME_WIDTH.getSize(), FLAME_HEIGHT.getSize(), 0, new ImageIcon(FLAME_LEFT_IMG.getImageUrl()).getImage(), false, true, this.board, Direction.LEFT, this.range, this);

        Flame flameRight = new Flame(this.x , this.y, FLAME_WIDTH.getSize(), FLAME_HEIGHT.getSize(), 0, new ImageIcon(FLAME_RIGHT_IMG.getImageUrl()).getImage(), false, true, this.board, Direction.RIGHT, this.range, this);

        this.removable = true;
        flameUp.expand();
        flameDown.expand();
        flameLeft.expand();
        flameRight.expand();

        owner.incrementNumberOfPlaceableBombs();
        owner.removeBomb(this);

        /*detonated = true;
        boolean right = contact(getEntFromXY(this.x + TILE_WIDTH.getSize(), this.y));
        boolean left = contact(getEntFromXY(this.x - TILE_WIDTH.getSize(), this.y));
        boolean down = contact(getEntFromXY(this.x, this.y + TILE_HEIGHT.getSize()));
        boolean up = contact(getEntFromXY(this.x, this.y - TILE_HEIGHT.getSize()));

        Timer Timer = new Timer();
        Timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (right) contact(getEntFromXY(x + 2 * TILE_WIDTH.getSize(), y));
                if (left) contact(getEntFromXY(x - 2 * TILE_WIDTH.getSize(), y));
                if (up) contact(getEntFromXY(x, y + 2 * TILE_HEIGHT.getSize()));
                if (down) contact(getEntFromXY(x, y - 2 * TILE_HEIGHT.getSize()));
            }
        }, 300);

        this.owner.incrementNumberOfPlaceableBombs();
    }

    private boolean contact(Entity entity) {         //returns true if the explosion can continue in that direction
        if (entity instanceof Wall) return false;    //and generate fragments on the entity
        if (entity instanceof Box) {
            board.getEntities().add(                //generate Bomb fragments (temporary solution)
                    new Bomb(
                            entity.getX(),
                            entity.getY(),
                            BOMB_WIDTH.getSize(),
                            BOMB_HEIGHT.getSize(),
                            0,
                            null,
                            false,
                            true,
                            null,
                            this.board));
            //entity.brake()                        // Box gets destroyed
            return false;
        } else if (entity instanceof Bomb) {
            if (!((Bomb) entity).getDetonated()) ((Bomb) entity).explode();

        } else {
            board.getEntities().add(                //generate Bomb fragments (temporary solution)
                    new Bomb(
                            entity.getX(),
                            entity.getY(),
                            BOMB_WIDTH.getSize(),
                            BOMB_HEIGHT.getSize(),
                            0,
                            null,
                            false,
                            true,
                            null,
                            this.board));
            return true;
        }
        return false;*/
    }

    /**
     * Checks if the bomb has been detonated.
     *
     * @return true if the bomb has been detonated, false otherwise.
     */
    public boolean isDetonated() {
        return detonated;
    }

    /**
     * Returns a string representation of the bomb.
     *
     * @return The bomb represented as a string.
     */
    @Override
    public String toString() {
        return Character.toString(128_163);
    }
}
