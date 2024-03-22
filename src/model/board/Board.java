package model.board;

import model.board.element.Entity;
import model.board.element.character.*;
import model.board.element.deposable.Bomb;
import model.board.element.deposable.Box;
import model.board.element.field.Wall;
import model.board.element.powerup.Bonus;
import model.board.element.powerup.benefit.BiggerRangeBonus;
import model.board.element.powerup.benefit.MaxBombsBonus;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import static model.board.Image.*;
import static model.board.Size.*;
import static model.board.Velocity.*;

public class Board {

    private final int boardSize;
    private Player player1;
    private Player player2;
    private ArrayList<Entity> boardElements;
    private ArrayList<Monster> monsters;
    private ArrayList<Wall> walls;
    private ArrayList<Box> boxes;
    private ArrayList<Bonus> bonuses;
    private ArrayList<Bomb> bombs;

    public Board(int boardSize, String path) throws IOException {
        monsters = new ArrayList<>();
        walls = new ArrayList<>();
        boxes = new ArrayList<>();
        bonuses = new ArrayList<>();
        bombs = new ArrayList<>();
        this.boardSize = boardSize;
        initialize(path);
        putBonusesInBoxes();
    }

    public void initialize(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        boardElements = new ArrayList<>();
        int row = 0;
        String line;
        while((line = br.readLine()) != null) {
            int col = 0;
            for(char entityType : line.toCharArray()) {
                int x = col * TILE_WIDTH.getSize();
                int y = row * TILE_HEIGHT.getSize();
                switch (entityType) {
                    case 'W':
                        Wall wall = new Wall(x, y, WALL_SIZE.getSize(), WALL_SIZE.getSize(), WALL_VEL.getVelocity(),
                                new ImageIcon(WALL_IMG.getImageUrl()).getImage(), false, true);
                        boardElements.add(wall);
                        walls.add(wall);
                        break;
                    case 'B':
                        Box box = new Box(x, y, BOX_SIZE.getSize(), BOX_SIZE.getSize(), BOX_VEL.getVelocity(),
                                new ImageIcon(BOX_IMG.getImageUrl()).getImage(), false, true, null, this);
                        boardElements.add(box);
                        boxes.add(box);
                        break;
                    case 'M':
                        BasicMonster basicMonster = new BasicMonster(x, y, MONSTER_SIZE.getSize(), MONSTER_SIZE.getSize(),
                                MONSTER_VEL.getVelocity(), new ImageIcon(MONSTER_IMG.getImageUrl()).getImage(), true, true, this);
                        boardElements.add(basicMonster);
                        monsters.add(basicMonster);
                        break;
                    case 'G':
                        GhostMonster ghostMonster = new GhostMonster(x, y, MONSTER_SIZE.getSize(), MONSTER_SIZE.getSize(),
                                MONSTER_VEL.getVelocity(), new ImageIcon(MONSTER_IMG.getImageUrl()).getImage(), true, true, this);
                        boardElements.add(ghostMonster);
                        monsters.add(ghostMonster);
                        break;
                    case 'S':
                        SemiIntelligentMonster semiIntelligentMonster = new SemiIntelligentMonster(x, y, MONSTER_SIZE.getSize(),
                                MONSTER_SIZE.getSize(), MONSTER_VEL.getVelocity(), new ImageIcon(MONSTER_IMG.getImageUrl()).getImage(), true, true, this);
                        boardElements.add(semiIntelligentMonster);
                        monsters.add(semiIntelligentMonster);
                        break;
                    case 'I':
                        IntelligentMonster intelligentMonster = new IntelligentMonster(x, y, MONSTER_SIZE.getSize(), MONSTER_SIZE.getSize(),
                                MONSTER_VEL.getVelocity(), new ImageIcon(MONSTER_IMG.getImageUrl()).getImage(), true, true, this);
                        boardElements.add(intelligentMonster);
                        monsters.add(intelligentMonster);
                        break;
                    case '1':
                        player1 = new Player(x, y, PLAYER_WIDTH.getSize(), PLAYER_HEIGHT.getSize(), PLAYER_VEL.getVelocity(),
                                new ImageIcon(PLAYER1_IMG.getImageUrl()).getImage(), true, true, "Player1", this, null);
                        boardElements.add(player1);
                        break;
                    case '2':
                        player2 = new Player(x, y, PLAYER_WIDTH.getSize(), PLAYER_HEIGHT.getSize(), PLAYER_VEL.getVelocity(),
                                new ImageIcon(PLAYER2_IMG.getImageUrl()).getImage(), true, true, "Player2", this, null);
                        boardElements.add(player2);
                        break;
                }
                col++;
            }
            row++;
        }
        br.close();
    }
    public void movePlayer1(Direction d) {
        player1.move(d);
    }
    public void movePlayer1(Direction d, double velocity) {
        player1.move(d,velocity);
    }
    public void movePlayer2(Direction d) {
        player2.move(d);
    }
    public void movePlayer2(Direction d, double velocity) {
        player2.move(d,velocity);
    }
    public void moveMonsters() {
        for(Monster monster : monsters) {
            if(monster.isAlive()) {
                monster.move();
            }
        }
    }

    public void player1PlantsBomb() {
        this.player1.plantBomb();
    }

    public void player2PlantsBomb() {
        this.player2.plantBomb();
    }

    public Player getWinner(){
        return null;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }

    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    public ArrayList<Bonus> getBonuses() {
        return bonuses;
    }

    public ArrayList<Bomb> getBombs() {
        return bombs;
    }
    public ArrayList<Entity> getEntities() {
        return boardElements;
    }

    public void addBomb(Bomb bomb) {
        bombs.add(bomb);
    }

    public void addEntity(Entity entity) {
        boardElements.add(entity);
    }

    public void putBonusesInBoxes() {
        Random random = new Random();
        int numberOfBonuses = boxes.size() / 3;
        Collections.shuffle(boxes);
        ArrayList<Box> boxesWithBonuses = new ArrayList<>(boxes.subList(0, numberOfBonuses));
        for(Box box : boxesWithBonuses) {
            Bonus bonus;
            if(random.nextDouble() < 0.5) {
                bonus = new BiggerRangeBonus(box.getX(), box.getY(), BONUS_SIZE.getSize(), BONUS_SIZE.getSize(), BONUS_VEL.getVelocity(), new ImageIcon(POSITIVE_BONUS_IMG.getImageUrl()).getImage(), false, false, null);
            } else {
                 bonus = new MaxBombsBonus(box.getX(), box.getY(), BONUS_SIZE.getSize(), BONUS_SIZE.getSize(), BONUS_VEL.getVelocity(), new ImageIcon(POSITIVE_BONUS_IMG.getImageUrl()).getImage(), false, false, null);
            }
            box.setBonus(bonus);
            boardElements.add(bonus);
            bonuses.add(bonus);
        }
    }

    public void removeRemovableEntities() {
        ArrayList<Entity> removables = new ArrayList<>();
        for(Entity entity : boardElements) {
            if(entity.isRemovable()) removables.add(entity);
        }
        boardElements.removeAll(removables);
    }


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


}
