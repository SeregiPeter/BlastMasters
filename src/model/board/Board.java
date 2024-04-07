package model.board;

import model.board.element.Entity;
import model.board.element.character.*;
import model.board.element.deposable.Bomb;
import model.board.element.deposable.Box;
import model.board.element.field.Wall;
import model.board.element.powerup.Bonus;
import model.board.element.powerup.benefit.BiggerRangeBonus;
import model.board.element.powerup.benefit.MaxBombsBonus;
import model.board.element.powerup.benefit.RollerBonus;
import view.state.GameState;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static model.board.Image.*;
import static model.board.Size.*;
import static model.board.Velocity.*;
import static view.state.GameState.*;

/**
 * Represents the game board, containing various elements such as players, monsters, walls, boxes, bonuses, and bombs.
 */
public class Board {

    public int selectedMapIndex;
    private final int boardSize;
    private int numberOfRound;
    private boolean onlyOneAlive;
    private boolean player1Check;
    private boolean player2Check;
    private String path;
    private GameState finalState;
    private Timer afterDeathTimer;
    private GameState state;
    private Player player1;
    private Player player2;
    private ArrayList<Entity> boardElements;
    private ArrayList<Monster> monsters;
    private ArrayList<Wall> walls;
    private ArrayList<Box> boxes;
    private ArrayList<Bonus> bonuses;
    private ArrayList<Bomb> bombs;

    /**
     * Constructs a game board with the specified size, path to the map file, and selected map index.
     *
     * @param boardSize        the size of the game board
     * @param path             the path to the map file
     * @param selectedMapIndex the index of the selected map
     * @throws IOException if an I/O error occurs
     */
    public Board(int boardSize, String path, int selectedMapIndex) throws IOException {
        monsters = new ArrayList<>();
        walls = new ArrayList<>();
        boxes = new ArrayList<>();
        bonuses = new ArrayList<>();
        bombs = new ArrayList<>();
        this.boardSize = boardSize;
        this.selectedMapIndex = selectedMapIndex;
        this.path=path;
        this.numberOfRound=3;                       //temporary initialization!!!!
        onlyOneAlive=false;
        player1Check=false;
        player2Check=false;
        finalState= BOTH_ALIVE;
        state=BOTH_ALIVE;
        afterDeathTimer = new javax.swing.Timer(3*1000, new timerListener());
        initialize(path, selectedMapIndex);
        putBonusesInBoxes();

    }

    /**
     * Initializes the game board based on the map file.
     *
     * @param path             the path to the map file
     * @param selectedMapIndex the index of the selected map
     */
    public void initialize(String path, int selectedMapIndex) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            boardElements = new ArrayList<>();
            int row = 0;
            String line;
            while ((line = br.readLine()) != null) {
                int col = 0;
                for (char entityType : line.toCharArray()) {
                    int x = col * TILE_WIDTH.getSize();
                    int y = row * TILE_HEIGHT.getSize();
                    switch (entityType) {
                        case 'W':
                            Wall wall = new Wall(x, y, WALL_SIZE.getSize(), WALL_SIZE.getSize(), WALL_VEL.getVelocity(),
                                    getWallImage(selectedMapIndex).getImage(), false, true);
                            boardElements.add(wall);
                            walls.add(wall);
                            break;
                        case 'B':
                            Box box = new Box(x, y, BOX_SIZE.getSize(), BOX_SIZE.getSize(), BOX_VEL.getVelocity(),
                                    getBoxImage(selectedMapIndex).getImage(), false, true, null, this);
                            boardElements.add(box);
                            boxes.add(box);
                            break;
                        case 'M':
                            BasicMonster basicMonster = new BasicMonster(x, y, MONSTER_SIZE.getSize(), MONSTER_SIZE.getSize(),
                                    MONSTER_VEL.getVelocity(), getMonsterImage(selectedMapIndex), true, true, this);
                            boardElements.add(basicMonster);
                            monsters.add(basicMonster);
                            break;
                            /*
                        case 'G':
                            GhostMonster ghostMonster = new GhostMonster(x, y, MONSTER_SIZE.getSize(), MONSTER_SIZE.getSize(),
                                    MONSTER_VEL.getVelocity(), getMonsterImage(selectedMapIndex).getImage(), true, true, this);
                            boardElements.add(ghostMonster);
                            monsters.add(ghostMonster);
                            break;
                        case 'S':
                            SemiIntelligentMonster semiIntelligentMonster = new SemiIntelligentMonster(x, y, MONSTER_SIZE.getSize(),
                                    MONSTER_SIZE.getSize(), MONSTER_VEL.getVelocity(), getMonsterImage(selectedMapIndex).getImage(), true, true, this);
                            boardElements.add(semiIntelligentMonster);
                            monsters.add(semiIntelligentMonster);
                            break;
                        case 'I':
                            IntelligentMonster intelligentMonster = new IntelligentMonster(x, y, MONSTER_SIZE.getSize(), MONSTER_SIZE.getSize(),
                                    MONSTER_VEL.getVelocity(), getMonsterImage(selectedMapIndex).getImage(), true, true, this);
                            boardElements.add(intelligentMonster);
                            monsters.add(intelligentMonster);
                            break;

                             */
                        case '1':
                            List<String> player1ImageUrls = Image.PLAYER1_IMG.getImageUrls();
                            List<java.awt.Image> player1Images = new ArrayList<>();
                            for (String url : player1ImageUrls) {
                                player1Images.add(new ImageIcon(url).getImage());
                            }
                            player1 = new Player(x, y, PLAYER_WIDTH.getSize(), PLAYER_HEIGHT.getSize(), PLAYER_VEL.getVelocity(),
                                    player1Images, true, true, "Player1", this, null);
                            boardElements.add(player1);
                            break;
                        case '2':
                            List<String> player2ImageUrls = Image.PLAYER2_IMG.getImageUrls();
                            List<java.awt.Image> player2Images = new ArrayList<>();
                            for (String url : player2ImageUrls) {
                                player2Images.add(new ImageIcon(url).getImage());
                            }
                            player2 = new Player(x, y, PLAYER_WIDTH.getSize(), PLAYER_HEIGHT.getSize(), PLAYER_VEL.getVelocity(),
                                    player2Images, true, true, "Player2", this, null);
                            boardElements.add(player2);
                            break;
                    }
                    col++;
                }
                row++;
            }
            br.close();
        }catch (IOException e){
            System.err.println("File not found");
        }
    }

    /**
     * Gets the ImageIcon for the wall based on the selected map index.
     *
     * @param mapIndex the index of the selected map
     * @return the ImageIcon for the wall
     */
    public ImageIcon getWallImage(int mapIndex) {
        return switch (mapIndex) {
            case 1 -> new ImageIcon(WALL_IMG_MAP2.getImageUrl());
            case 2 -> new ImageIcon(WALL_IMG_MAP3.getImageUrl());
            default ->
                    new ImageIcon(WALL_IMG_MAP1.getImageUrl());
        };
    }

    /**
     * Gets the ImageIcon for the box based on the selected map index.
     *
     * @param mapIndex the index of the selected map
     * @return the ImageIcon for the box
     */
    private ImageIcon getBoxImage(int mapIndex) {
        return switch (mapIndex) {
            case 1 -> new ImageIcon(BOX_IMG_MAP2.getImageUrl());
            case 2 -> new ImageIcon(BOX_IMG_MAP3.getImageUrl());
            default ->
                    new ImageIcon(BOX_IMG_MAP1.getImageUrl());
        };
    }

    /**
     * Gets the ImageIcon for the monster based on the selected map index.
     *
     * @param mapIndex the index of the selected map
     * @return the ImageIcon for the monster
     */
    private List<java.awt.Image> getMonsterImage(int mapIndex) {
        return switch (mapIndex) {
            case 1 -> {
                List<String> monster2ImageUrls = Image.MONSTER_IMG_MAP2.getImageUrls();
                List<java.awt.Image> monster2Images = new ArrayList<>();
                for (String url : monster2ImageUrls) {
                    monster2Images.add(new ImageIcon(url).getImage());
                }
                yield monster2Images;
            }
            case 2 -> {
                List<String> monster3ImageUrls = Image.MONSTER_IMG_MAP3.getImageUrls();
                List<java.awt.Image> monster3Images = new ArrayList<>();
                for (String url : monster3ImageUrls) {
                    monster3Images.add(new ImageIcon(url).getImage());
                }
                yield monster3Images;
            }
            default -> {
                List<String> monster1ImageUrls = Image.MONSTER_IMG_MAP1.getImageUrls();
                List<java.awt.Image> monster1Images = new ArrayList<>();
                for (String url : monster1ImageUrls) {
                    monster1Images.add(new ImageIcon(url).getImage());
                }
                yield monster1Images;
            }
        };
    }

    /**
     * Gets the selected map index.
     *
     * @return the selected map index
     */
    public int getSelectedMapIndex() {
        return selectedMapIndex;
    }

    /**
     * Moves player 1 in the specified direction on the game board.
     * This method delegates the movement action to the player 1 instance.
     *
     * @param d the direction in which player 1 should move
     */
    public void movePlayer1(Direction d) {
        player1.move(d);
    }

    /**
     * Moves player 1 in the specified direction with a custom velocity on the game board.
     * This method delegates the movement action to the player 1 instance.
     *
     * @param d        the direction in which player 1 should move
     * @param velocity the velocity at which player 1 should move
     */
    public void movePlayer1(Direction d, double velocity) {
        player1.move(d,velocity);
    }

    /**
     * Moves player 2 in the specified direction on the game board.
     * This method delegates the movement action to the player 2 instance.
     *
     * @param d the direction in which player 2 should move
     */
    public void movePlayer2(Direction d) {
        player2.move(d);
    }

    /**
     * Moves player 2 in the specified direction with a custom velocity on the game board.
     * This method delegates the movement action to the player 2 instance.
     *
     * @param d        the direction in which player 2 should move
     * @param velocity the velocity at which player 2 should move
     */
    public void movePlayer2(Direction d, double velocity) {
        player2.move(d,velocity);
    }

    /**
     * Moves all monsters on the game board.
     */
    public void moveMonsters() {
        for(Monster monster : monsters) {
            if(monster.isAlive()) {
                monster.move();
            }
        }
    }

    /**
     * Initiates the action of player 1 planting a bomb on the game board.
     * This method delegates the bomb planting action to the player 1 instance.
     */
    public void player1PlantsBomb() {
        this.player1.plantBomb();
    }

    /**
     * Initiates the action of player 2 planting a bomb on the game board.
     * This method delegates the bomb planting action to the player 2 instance.
     */
    public void player2PlantsBomb() {
        this.player2.plantBomb();
    }

    /**
     * Gets the winner of the game.
     * This method is currently not implemented and returns null.
     *
     * @return the winning player, or null if there is no winner yet
     */
    public Player getWinner(){
        return null;
    }

    /**
     * Gets the player 1 instance.
     *
     * @return the player 1 instance
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Gets the player 2 instance.
     *
     * @return the player 2 instance
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * Gets the list of monsters on the game board.
     *
     * @return the list of monsters
     */
    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    /**
     * Gets the list of walls on the game board.
     *
     * @return the list of walls
     */
    public ArrayList<Wall> getWalls() {
        return walls;
    }

    /**
     * Gets the list of boxes on the game board.
     *
     * @return the list of boxes
     */
    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    /**
     * Gets the list of bonuses on the game board.
     *
     * @return the list of bonuses
     */
    public ArrayList<Bonus> getBonuses() {
        return bonuses;
    }

    /**
     * Gets the list of bombs on the game board.
     *
     * @return the list of bombs
     */
    public ArrayList<Bomb> getBombs() {
        return bombs;
    }

    /**
     * Gets the list of all entities on the game board.
     *
     * @return the list of entities
     */
    public ArrayList<Entity> getEntities() {
        return boardElements;
    }

    /**
     * Adds a bomb to the list of bombs on the game board.
     *
     * @param bomb the bomb to add
     */
    public void addBomb(Bomb bomb) {
        bombs.add(bomb);
    }

    /**
     * Adds an entity to the list of entities on the game board.
     *
     * @param entity the entity to add
     */
    public void addEntity(Entity entity) {
        boardElements.add(entity);
    }

    /**
     * Puts bonuses into random boxes on the game board.
     */
    public void putBonusesInBoxes() {
        Random random = new Random();
        int numberOfBonuses = boxes.size() / 2;
        Collections.shuffle(boxes);
        ArrayList<Box> boxesWithBonuses = new ArrayList<>(boxes.subList(0, numberOfBonuses));
        for(Box box : boxesWithBonuses) {
            putRandomBonusInBox(box);
        }
    }

    public void putRandomBonusInBox(Box box) {
        Random random = new Random();
        int randomNumber = random.nextInt(3); // Az eddig elkészült bónuszok száma
        Bonus bonus = null;
        switch(randomNumber) {
            case 0:
                bonus =  new BiggerRangeBonus(box.getX(), box.getY(), BONUS_SIZE.getSize(), BONUS_SIZE.getSize(), BONUS_VEL.getVelocity(), new ImageIcon(BIGGER_RANGE_BONUS_IMG.getImageUrl()).getImage(), false, false, null);
                break;
            case 1:
                bonus = new MaxBombsBonus(box.getX(), box.getY(), BONUS_SIZE.getSize(), BONUS_SIZE.getSize(), BONUS_VEL.getVelocity(), new ImageIcon(BOMB_UP_BONUS_IMG.getImageUrl()).getImage(), false, false, null);
                break;
            case 2:
                bonus = new RollerBonus(box.getX(), box.getY(), BONUS_SIZE.getSize(), BONUS_SIZE.getSize(), BONUS_VEL.getVelocity(), new ImageIcon(ROLLER_BONUS_IMG.getImageUrl()).getImage(), false, false, null);
                break;
        }
        box.setBonus(bonus);
        boardElements.add(bonus);
        bonuses.add(bonus);
    }

    /**
     * Removes entities marked as removable from the game board.
     */
    public void removeRemovableEntities() {
        ArrayList<Entity> removables = new ArrayList<>();
        ArrayList<Entity> elements = new ArrayList<>(boardElements);
        for(Entity entity : elements) {
            if(entity.isRemovable()) removables.add(entity);
        }
        boardElements.removeAll(removables);
    }

    /**
     * Checks the current status of the game, updating the state accordingly.
     */
    public void statusCheck() {
        if(state==PLAYER1_FINAL_WIN||state==PLAYER2_FINAL_WIN)return;
        if (finalState!=BOTH_ALIVE){
            state=finalState;
            return;
        }
        if (player1.isAlive() && player2.isAlive()) {
            state=BOTH_ALIVE;
        }else if (!player1.isAlive()) {
            if (!onlyOneAlive){
                player1Check=true;
                onlyOneAlive = true;
                afterDeathTimer.start();
            }
        }else if(!player2.isAlive()){
            if (!onlyOneAlive){
                player2Check=true;
                onlyOneAlive = true;
                afterDeathTimer.start();
            }
        }
    }

    /**
     * Handles the end of a round, updating player points and determining the final winner.
     */
    public void roundEnd(){
        if(state==PLAYER1_WON)player1.incrementPoints();
        else player2.incrementPoints();

        if(player1.getPoints()>numberOfRound/2 || player2.getPoints()>numberOfRound/2){
            if(player1.getPoints()>player2.getPoints()) state=PLAYER1_FINAL_WIN;
            else state=PLAYER2_FINAL_WIN;
        }
    }

    /**
     * Represents an ActionListener for the after death timer.
     */
     class timerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if(player1Check&&!player2Check){
                if(player2.isAlive()){
                    finalState=PLAYER2_WON;
                }else{
                    finalState=DRAW;
                }
            }else if(!player1Check&&player2Check){
                if(player1.isAlive()){
                    finalState=PLAYER1_WON;
                }else{
                    finalState=DRAW;
                }
            }
        }
    }

    /**
     * Returns a string representation of the game board, including its size, players, monsters, and other elements.
     *
     * @return a string representation of the game board
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Board Information:\n");
        sb.append("Size: ").append(boardSize).append("\n");

        // Players
        sb.append("Player 1: ").append(player1.getName()).append("\n");
        sb.append("Player 2: ").append(player2.getName()).append("\n");

        // Monsters
        sb.append("Number of Monsters: ").append(monsters.size()).append("\n");

        // Board Elements
        sb.append("Number of Board Elements: ").append(boardElements.size()).append("\n");
        sb.append("Walls: ").append(walls.size()).append("\n");
        sb.append("Boxes: ").append(boxes.size()).append("\n");
        sb.append("Bonuses: ").append(bonuses.size()).append("\n");
        sb.append("Bombs: ").append(bombs.size()).append("\n");

        return sb.toString();
    }

    /**
     * Gets the current game state.
     *
     * @return the current game state
     */
    public GameState getGameState() {
        return state;
    }

    /**
     * Resets the game board to its initial state, including resetting elements and scores.
     */
    public void reset() {
        if(state!=PLAYER1_FINAL_WIN &&state!=PLAYER2_FINAL_WIN) state=BOTH_ALIVE;
        monsters = new ArrayList<>();
        walls = new ArrayList<>();
        boxes = new ArrayList<>();
        bonuses = new ArrayList<>();
        bombs = new ArrayList<>();
        onlyOneAlive=false;
        player1Check=false;
        player2Check=false;
        finalState= BOTH_ALIVE;
        afterDeathTimer = new javax.swing.Timer(3*1000, new timerListener());
        int tempPlayer1Points=player1.getPoints();
        int tempPlayer2Points=player2.getPoints();
        initialize(path, selectedMapIndex);
        putBonusesInBoxes();
        player1.setPoints(tempPlayer1Points);
        player2.setPoints(tempPlayer2Points);
    }
}
