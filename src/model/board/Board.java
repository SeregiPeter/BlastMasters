package model.board;

import model.board.element.Entity;
import model.board.element.character.Monster;
import model.board.element.character.Player;
import model.board.element.deposable.Bomb;
import model.board.element.deposable.Box;
import model.board.element.field.Wall;
import model.board.element.powerup.Bonus;

import java.util.ArrayList;
import java.util.Map;

public class Board {
    private int broardSize;
    private int playerSize;
    private int monsterSize;
    private int wallSize;
    private int boxSize;
    private int bonusSize;
    private Player player1;
    private Player player2;
    private ArrayList<Monster> monsters;
    private ArrayList<Wall> walls;
    private ArrayList<Box> boxes;
    private ArrayList<Bonus> bonuses;
    private ArrayList<Bomb> bombs;
    private Map<Entity,String> images;

    public Board() {
    }
    public void initialize(String Path){}
    public void movePlayer(int playerNumber,Direction d){}
    public void moveMonsters(){}
    public Player getWinner(){
        return null;
    }
}
