package view;

import model.board.Board;
import model.board.Direction;
import model.board.element.Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static model.board.Image.BACKGROUND_IMG;

public class GameWindow extends JPanel {

    private Board board;
    private Timer frametimer;
    private Image background;
    public GameWindow(){
        super();
        background=new ImageIcon(String.valueOf(BACKGROUND_IMG)).getImage();
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
    }
    public void handleKeyPresses() {
        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "pressed left");
        this.getActionMap().put("pressed left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                board.getPlayer2().move(Direction.LEFT);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "pressed right");
        this.getActionMap().put("pressed right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                board.getPlayer2().move(Direction.RIGHT);;
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "pressed up");
        this.getActionMap().put("pressed up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                board.getPlayer2().move(Direction.UP);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "pressed down");
        this.getActionMap().put("pressed down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                board.getPlayer2().move(Direction.DOWN);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("A"), "pressed a");
        this.getActionMap().put("pressed a", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                board.getPlayer1().move(Direction.LEFT);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("D"), "pressed d");
        this.getActionMap().put("pressed d", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                board.getPlayer1().move(Direction.RIGHT);;
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("W"), "pressed w");
        this.getActionMap().put("pressed w", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                board.getPlayer1().move(Direction.UP);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("S"), "pressed s");
        this.getActionMap().put("pressed s", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                board.getPlayer1().move(Direction.DOWN);
            }
        });

    }
    class FrameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            repaint();
        }
    }
}



