package view.state;

import control.Settings;
import model.board.Board;
import model.board.Direction;
import model.board.element.Entity;
import model.board.element.character.Monster;
import model.board.element.character.Player;
import view.ui.HoverPanel;

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
import static view.state.GameState.*;

/**
 * The graphical engine for the game, responsible for rendering the game board and handling user input.
 */
public class GameEngine extends JPanel {
    private boolean paused;
    private Board board;
    private Settings settings;
    private Timer frametimer;
    private Image background;
    private Map<Direction,Boolean> Player1Movement;
    private Map<Direction,Boolean> Player2Movement;
    private JButton nextRoundButton;
    private JButton mainMenuButton;

    private HoverPanel hoverPanel;
    /**
     * Constructs a GameEngine with the specified game board.
     *
     * @param board the game board
     */
    public GameEngine(Board board, Settings settings,JButton mainMenuButton) {
        super();

        paused=false;
        Player1Movement= new HashMap<>();
        Player2Movement= new HashMap<>();
        this.mainMenuButton=mainMenuButton;;
        this.board=board;
        this.settings=settings;
        background=getBackgroundImage(board.getSelectedMapIndex()).getImage();
        frametimer = new javax.swing.Timer(10, new FrameListener());
        frametimer.start();

        add(mainMenuButton);
        handleKeyPresses();
        initializeHover();
        initializeNextRoundButton();
    }



    private void initializeNextRoundButton() {
        nextRoundButton = new JButton("Next Round");
        nextRoundButton.setBackground(new Color(51, 206, 250));
        nextRoundButton.setForeground(Color.white);
        nextRoundButton.setPreferredSize(new Dimension(300,50));
        nextRoundButton.setVisible(false);
        nextRoundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean newNewRound=false;
                if(nextRoundButton.getText().equals("New Round")) newNewRound=true;
                setHoverPanelInvisible();
                restart(newNewRound);
            }
        });
        add(nextRoundButton);
    }

    private void initializeHover() {
        hoverPanel = new HoverPanel(board.getPlayer1().getPoints(),board.getPlayer1().getPoints(),"");
        hoverPanel.setVisible(false);
        add(hoverPanel);
    }

    private ImageIcon getBackgroundImage(int mapIndex) {
        return switch (mapIndex) {
            case 1 -> new ImageIcon(BACKGROUND_IMG_MAP2.getImageUrl());
            case 2 -> new ImageIcon(BACKGROUND_IMG_MAP3.getImageUrl());
            default -> new ImageIcon(BACKGROUND_IMG_MAP1.getImageUrl());
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
        ArrayList<Monster> monsters = new ArrayList<>(board.getMonsters());
        for (Monster monster : monsters) {
            monster.draw(grphcs);
        }
        if (hoverPanel.isVisible()) {

            Graphics2D g2d = (Graphics2D) grphcs;
            float hoverPanelOpacity = 0.7f;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, hoverPanelOpacity));
            g2d.fillRect(0, 0, getWidth(), getHeight());
            int hoverPanelX = (getWidth() - hoverPanel.getWidth()) / 2;
            int hoverPanelY = (getHeight() - hoverPanel.getHeight()) / 2;

            hoverPanel.setBounds(hoverPanelX, hoverPanelY, hoverPanel.getWidth(), hoverPanel.getHeight());

            if(board.getGameState()==PLAYER1_FINAL_WIN ||board.getGameState()==PLAYER2_FINAL_WIN) {
                nextRoundButton.setBounds(hoverPanelX + hoverPanel.getWidth() / 2 , hoverPanelY + hoverPanel.getHeight(), nextRoundButton.getWidth(), nextRoundButton.getHeight());
                mainMenuButton.setBounds(hoverPanelX + hoverPanel.getWidth() / 2 - mainMenuButton.getWidth() , hoverPanelY + hoverPanel.getHeight(), nextRoundButton.getWidth(), nextRoundButton.getHeight());
            }else{
                nextRoundButton.setBounds(hoverPanelX + hoverPanel.getWidth() / 2 - nextRoundButton.getWidth() / 2, hoverPanelY + hoverPanel.getHeight(), nextRoundButton.getWidth(), nextRoundButton.getHeight());
                mainMenuButton.setBounds(hoverPanelX + hoverPanel.getWidth() / 2 - mainMenuButton.getWidth() / 2, hoverPanelY + hoverPanel.getHeight(), mainMenuButton.getWidth(), mainMenuButton.getHeight());
            }
            float nextButtonOpacity = 1f;
            AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, nextButtonOpacity);
            g2d.setComposite(alphaComposite);
        }

    }

    /**
     * Handles key presses for player movement and actions.
     */
    public void handleKeyPresses() {

        int[] events=settings.getKeyBindings();

        this.getInputMap().put(KeyStroke.getKeyStroke(events[7], 0), "pressed left");
        this.getActionMap().put("pressed left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player1Movement.put(LEFT, true);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(events[7], 0, true), "released left");
        this.getActionMap().put("released left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player1Movement.put(LEFT, false);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(events[9], 0), "pressed right");
        this.getActionMap().put("pressed right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player1Movement.put(RIGHT, true);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(events[9], 0, true), "released right");
        this.getActionMap().put("released right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player1Movement.put(RIGHT, false);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(events[6], 0), "pressed up");
        this.getActionMap().put("pressed up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player1Movement.put(UP, true);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(events[6], 0, true), "released up");
        this.getActionMap().put("released up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player1Movement.put(UP, false);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(events[8], 0), "pressed down");
        this.getActionMap().put("pressed down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player1Movement.put(DOWN, true);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(events[8], 0, true), "released down");
        this.getActionMap().put("released down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player1Movement.put(DOWN, false);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(events[1], 0), "pressed a");
        this.getActionMap().put("pressed a", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player2Movement.put(LEFT, true);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(events[1], 0, true), "released a");
        this.getActionMap().put("released a", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player2Movement.put(LEFT, false);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(events[3], 0), "pressed d");
        this.getActionMap().put("pressed d", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player2Movement.put(RIGHT, true);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(events[3], 0, true), "released d");
        this.getActionMap().put("released d", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player2Movement.put(RIGHT, false);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(events[0], 0), "pressed w");
        this.getActionMap().put("pressed w", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player2Movement.put(UP, true);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(events[0], 0, true), "released w");
        this.getActionMap().put("released w", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player2Movement.put(UP, false);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(events[2], 0), "pressed s");
        this.getActionMap().put("pressed s", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player2Movement.put(DOWN, true);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(events[2], 0, true), "released s");
        this.getActionMap().put("released s", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Player2Movement.put(DOWN, false);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(events[5], 0, true), "pressed t");
        this.getActionMap().put("pressed t", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                board.player2PlantsBomb();
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke(events[4], 0, true), "pressed y");
        this.getActionMap().put("pressed y", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                board.player2PlantsBox();
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke(events[10], 0, true), "pressed u");
        this.getActionMap().put("pressed u", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                board.player1PlantsBox();
            }
        });


        this.getInputMap().put(KeyStroke.getKeyStroke(events[11], 0, true), "pressed p");
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
                if(board.getGameState()!=BOTH_ALIVE) return;
                if (paused) {
                    paused = false;
                    setHoverPanelInvisible();
                } else {
                    paused = true;
                    setHoverPanelVisible("", true,false);
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
            if (paused) {
                handleGameState(PAUSED);
                return;
            }
            handleGameState(board.getGameState());
        }
    }

    /**
     * Handles the movement of a player based on the key input.
     *
     * @param player         the player to move
     * @param playerMovement the map of player movement directions
     */
    private void handlePlayerMovement(Player player, Map<Direction, Boolean> playerMovement) {
        ArrayList<Direction> moves = new ArrayList<>();
        for (Direction d : playerMovement.keySet()) {
            if (playerMovement.get(d)) {
                moves.add(d);
            }
        }
        if (!moves.isEmpty()) {
            player.move(moves.get(0));
        }
    }

    /**
     * Handles the game state transitions and actions based on the current state.
     *
     * @param state the current game state
     */
    private void handleGameState(GameState state) {
        switch (state) {
            case DRAW:
                board.removeRemovableEntities();
                setHoverPanelVisible("Draw", false,true);
                break;
            case PAUSED:

                break;

            case PLAYER1_WON:
                board.removeRemovableEntities();
                setHoverPanelVisible(settings.getPlayer1Name()+" won the round",false,true);

                break;
            case PLAYER2_WON:
                board.removeRemovableEntities();
                setHoverPanelVisible(settings.getPlayer2Name()+" won the round",false,true);
                break;
            case BOTH_ALIVE:
                board.removeRemovableEntities();
                handlePlayerMovement(board.getPlayer1(), Player1Movement);
                handlePlayerMovement(board.getPlayer2(), Player2Movement);
                board.moveMonsters();
                break;
            case PLAYER2_FINAL_WIN:
                setHoverPanelVisible(settings.getPlayer2Name()+" won the game", true,true);

                break;
            case PLAYER1_FINAL_WIN:
                setHoverPanelVisible(settings.getPlayer1Name()+" won the game", true,true);

                break;
            default:
        }

    }

    /**
     * Restarts the game by resetting the board and entities.
     */
    private void restart(boolean newNewRound) {
        board.reset(newNewRound);
    }
    private void setHoverPanelVisible(String stateLabel, boolean showMainMenuButton, boolean showNextRoundButton){
        hoverPanel.setScore(board.getPlayer2().getPoints(),board.getPlayer1().getPoints(),stateLabel);
        hoverPanel.setVisible(true);
        nextRoundButton.setText("Next Round");

        if(showNextRoundButton)nextRoundButton.setVisible(true);
        if(showMainMenuButton) mainMenuButton.setVisible(true);

        if(showMainMenuButton&&showNextRoundButton){
            int width=hoverPanel.getWidth()/2 -40;
            int height=nextRoundButton.getHeight();
            nextRoundButton.setPreferredSize(new Dimension(width,height));
            mainMenuButton.setPreferredSize(new Dimension(width,height));
            nextRoundButton.setText("New Round");
        }
    }
    private void setHoverPanelInvisible() {
        hoverPanel.setVisible(false);
        nextRoundButton.setVisible(false);
        mainMenuButton.setVisible(false);
    }



}



