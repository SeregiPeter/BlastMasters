package model.board;

import model.board.element.Entity;
import model.board.element.character.Monster;
import model.board.element.character.Player;
import model.board.element.deposable.Bomb;
import model.board.element.deposable.Box;
import model.board.element.field.Wall;
import model.board.element.powerup.Bonus;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static model.board.Size.*;

public class Board {
    private List<Entity> boardEntities;
    private int boardSize;
    private int playerSize;
    private int monsterSize;
    private int wallSize;
    private int boxSize;
    private int bonusSize;
    private Player player1;
    private Player player2;
    private ArrayList<Entity> entities;
    private ArrayList<Monster> monsters;
    private ArrayList<Wall> walls;
    private ArrayList<Box> boxes;
    private ArrayList<Bonus> bonuses;
    private ArrayList<Bomb> bombs;
    private Map<Entity,String> images;

    public Board(int boardSize, int playerSize, int monsterSize, int wallSize, int boxSize, int bonusSize, String path) throws IOException {

    }

    public void initialize(String path) throws IOException {

    }
    public void movePlayer(int playerNumber,Direction d){}
    public void moveMonsters(){}
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
        return entities;
    }
}
