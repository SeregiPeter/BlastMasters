package view;

import model.board.Board;
import model.board.Direction;
import model.board.Size;
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
import static model.board.Image.BACKGROUND_IMG;
import static model.board.Image.PLAYER2_IMG;
import static model.board.Velocity.PLAYER_VEL;

public class GameWindow extends JPanel {

    private Board board;
    private Timer frametimer;
    private Image background;
    private Map<Direction,Boolean> Player1Movement;
    private Map<Direction,Boolean> Player2Movement;
    public GameWindow(Board board){
        super();
        Player1Movement= new HashMap<>();//Map.of("UP",false,"DOWN",false,"LEFT",false,"RIGHT",false);
        Player2Movement= new HashMap<>();//Map.of("UP",false,"DOWN",false,"LEFT",false,"RIGHT",false);
        this.board=board;
        background=new ImageIcon(BACKGROUND_IMG.getImageUrl()).getImage();
        handleKeyPresses();
        frametimer = new javax.swing.Timer(10, new FrameListener());
        frametimer.start();

    }
    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.drawImage(background, 0, 0, 800, 600, null);
        for (Entity entity:board.getEntities()) {
            entity.draw(grphcs);
        }
        board.getPlayer1().draw(grphcs);
        board.getPlayer2().draw(grphcs);
    }
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

    }
    class FrameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            handlePlayerMovement(board.getPlayer1(),Player1Movement);
            handlePlayerMovement(board.getPlayer2(),Player2Movement);
            board.moveMonsters();
            repaint();
        }
    }

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

                    board.movePlayer1(d,1);                      //Math.sqrt(Math.pow(PLAYER_VEL.getVelocity(),2)/2) if fractional number allowed
                }else {
                    board.movePlayer2(d,1);
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

}



