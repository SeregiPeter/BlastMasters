package view.state;

import model.board.Board;
import model.board.Direction;
import model.board.element.Entity;
import model.board.element.character.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static model.board.Direction.*;
import static model.board.Direction.UP;
import static model.board.Image.*;
import static view.state.GameState.PAUSED;

/**
 * The graphical engine for the game, responsible for rendering the game board and handling user input.
 */
public class GameEngine extends JPanel {
    private boolean paused;
    private Board board;
    private Timer frametimer;
    private Image background;
    private Map<Direction,Boolean> Player1Movement;
    private Map<Direction,Boolean> Player2Movement;

    /**
     * Constructs a GameEngine with the specified game board.
     *
     * @param board the game board
     */
    public GameEngine(Board board){
        super();
        paused=false;
        Player1Movement= new HashMap<>();
        Player2Movement= new HashMap<>();
        this.board=board;
        background=getBackgroundImage(board.getSelectedMapIndex()).getImage();
        handleKeyPresses();
        frametimer = new javax.swing.Timer(10, new FrameListener());
        frametimer.start();
    }

    private ImageIcon getBackgroundImage(int mapIndex) {
        return switch (mapIndex) {
            case 1 -> new ImageIcon(BACKGROUND_IMG_MAP2.getImageUrl());
            case 2 -> new ImageIcon(BACKGROUND_IMG_MAP3.getImageUrl());
            default ->
                    new ImageIcon(BACKGROUND_IMG_MAP1.getImageUrl());
        };
    }

    /**
     * Paints the game components including the background, entities, and players.
     *
     * @param grphcs the graphics context
     */
    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.drawImage(background, 0, 0, 1520, 747, null);
        ArrayList<Entity> entities = new ArrayList<>(board.getEntities());
        for (Entity entity : entities) {
            entity.draw(grphcs);
        }
        board.getPlayer1().draw(grphcs);
        board.getPlayer2().draw(grphcs);
    }

    /**
     * Handles key presses for player movement and actions.
     */
    public void handleKeyPresses() {
        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "pressed left");
        this.getActionMap().put("pressed left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player1Movement.put(LEFT,true);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "released left");
        this.getActionMap().put("released left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player1Movement.put(LEFT,false);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "pressed right");
        this.getActionMap().put("pressed right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player1Movement.put(RIGHT,true);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "released right");
        this.getActionMap().put("released right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player1Movement.put(RIGHT,false);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "pressed up");
        this.getActionMap().put("pressed up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player1Movement.put(UP,true);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "released up");
        this.getActionMap().put("released up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player1Movement.put(UP,false);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "pressed down");
        this.getActionMap().put("pressed down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player1Movement.put(DOWN,true);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "released down");
        this.getActionMap().put("released down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player1Movement.put(DOWN,false);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("A"), "pressed a");
        this.getActionMap().put("pressed a", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player2Movement.put(LEFT,true);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "released a");
        this.getActionMap().put("released a", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player2Movement.put(LEFT,false);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("D"), "pressed d");
        this.getActionMap().put("pressed d", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player2Movement.put(RIGHT,true);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "released d");
        this.getActionMap().put("released d", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player2Movement.put(RIGHT,false);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("W"), "pressed w");
        this.getActionMap().put("pressed w", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player2Movement.put(UP,true);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "released w");
        this.getActionMap().put("released w", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player2Movement.put(UP,false);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("S"), "pressed s");
        this.getActionMap().put("pressed s", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player2Movement.put(DOWN,true);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "released s");
        this.getActionMap().put("released s", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player2Movement.put(DOWN,false);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_T, 0, true), "pressed t");
        this.getActionMap().put("pressed t", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                board.player2PlantsBomb();
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0, true), "pressed p");
        this.getActionMap().put("pressed p", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                board.player1PlantsBomb();
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "pressed esc");
        this.getActionMap().put("pressed esc", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (paused){
                    paused=false;
                }else{
                    paused=true;
                }
            }
        });

    }

    /**
     * ActionListener for updating the game state and rendering the frame.
     */
    class FrameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {

            repaint();
            board.statusCheck();
            if(paused){
                handleGameState(PAUSED);
                return;
            }
            handleGameState(board.getGameState());
        }
    }

    /**
     * Handles the movement of a player based on the key input.
     *
     * @param player the player to move
     * @param playerMovement the map of player movement directions
     */
    private void handlePlayerMovement(Player player, Map<Direction, Boolean> playerMovement) {
        int movesAtTheSameTime=0;
        ArrayList<Direction> moves=new ArrayList<>();
        for (Direction d:playerMovement.keySet()) {
            if(playerMovement.get(d)){
                movesAtTheSameTime++;
                moves.add(d);
            }
        }
        for (Direction d: moves) {
            if(movesAtTheSameTime>1){
                if (player.equals(board.getPlayer1())){         //this is a very ugly 'if' this will have to be fixed somehow

                    board.movePlayer1(d,player.getVelocity()/2);             //Math.sqrt(Math.pow(PLAYER_VEL.getVelocity(),2)/2) if fractional number allowed
                }else {
                    board.movePlayer2(d,player.getVelocity()/2);
                }
            }else{
                if (player.equals(board.getPlayer1())){         //this is a very ugly 'if' this will have to be fixed somehow

                    board.movePlayer1(d);
                }else {
                    board.movePlayer2(d);
                }
            }
        }
    }

    /**
     * Handles the game state transitions and actions based on the current state.
     *
     * @param state the current game state
     */
    private void handleGameState(GameState state){
        switch (state){
            case DRAW :
                board.removeRemovableEntities();
                System.out.println(state);
                restart();
                break;
            case PAUSED:

                break;

            case PLAYER1_WON, PLAYER2_WON:
                board.removeRemovableEntities();
                System.out.println(state);
                board.roundEnd();
                restart();
                break;
            case BOTH_ALIVE :
                board.removeRemovableEntities();
                handlePlayerMovement(board.getPlayer1(),Player1Movement);
                handlePlayerMovement(board.getPlayer2(),Player2Movement);
                board.moveMonsters();
                break;
            case PLAYER2_FINAL_WIN:
                System.out.println("bent van player2 nyert");
                restart(); //ideiglenes meghívás a győzelmi ablak megjelenés helyett
                break;
            case PLAYER1_FINAL_WIN:
                System.out.println("bent van player1 nyert");
                restart(); //ideiglenes meghívás a győzelmi ablak megjelenés helyett
                break;
            default:
        }

    }

    /**
     * Restarts the game by resetting the board and entities.
     */
    private void restart() {
        board.reset();
        System.out.println("bent van");
    }

}



