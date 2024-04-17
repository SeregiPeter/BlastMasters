package view.ui;

import model.board.Board;
import view.state.GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import static model.board.Size.BOARD_HEIGHT;

public class StartGame extends JFrame {

    private GameEngine gameEngine;
    private PlayerCustomizationPanel playerPanel1;
    private PlayerCustomizationPanel playerPanel2;
    private MapSelectorPanel mapSelectorPanel;
    private ButtonPanel buttonPanel;
    private Image backgroundImage;
    private Image[] mapImages;
    private String[] mapNames = {"Map 1", "Map 2", "Map 3"};
    private int currentMapIndex = 0;

    public StartGame() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Blast Masters");
        setSize(1500, 807);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            backgroundImage = ImageIO.read(new File("src/resources/assets/menu/bomberblur.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        initializeMapImages();

        JPanel panelMain = createMainPanel();

        setContentPane(panelMain);

        addStartButtonActionListener();

        addExitButtonActionListener();
    }

    private void initializeMapImages() {
        mapImages = new Image[mapNames.length];
        for (int i = 0; i < mapNames.length; i++) {
            try {
                mapImages[i] = ImageIO.read(new File("src/resources/assets/menu/map" + (i + 1) + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private JPanel createMainPanel() {
        JPanel panelMain = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelMain.add(createPlayersPanel(), BorderLayout.EAST);
        panelMain.add(createMapSelectorPanel(), BorderLayout.CENTER);
        panelMain.add(createStartPanel(), BorderLayout.WEST);
        return panelMain;
    }

    private JPanel createPlayersPanel() {
        JPanel playersPanel = new JPanel(new GridLayout(1, 2));
        playersPanel.add(createPlayerPanel1());
        playersPanel.add(createPlayerPanel2());
        playersPanel.setOpaque(false);
        return playersPanel;
    }

    private PlayerCustomizationPanel createPlayerPanel1() {
        playerPanel1 = new PlayerCustomizationPanel();
        playerPanel1.setPlayerName("Player 1");
        playerPanel1.setControls("WASDRT");
        return playerPanel1;
    }

    private PlayerCustomizationPanel createPlayerPanel2() {
        playerPanel2 = new PlayerCustomizationPanel();
        playerPanel2.setPlayerName("Player 2");
        playerPanel2.setControls("↑←↓→OP");
        try {
            ImageIcon bombermanIcon2 = new ImageIcon(ImageIO.read(new File("src/resources/assets/menu/bomberman2.png")));
            Image image = bombermanIcon2.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(image);
            playerPanel2.setPlayerImage(scaledIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return playerPanel2;
    }

    private MapSelectorPanel createMapSelectorPanel() {
        mapSelectorPanel = new MapSelectorPanel(mapNames, mapImages, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentMapIndex = mapSelectorPanel.getCurrentMapIndex();
                Image selectedMapImage = mapImages[currentMapIndex];
                mapSelectorPanel.setMapPreview(selectedMapImage);
            }
        });
        return mapSelectorPanel;
    }

    private ButtonPanel createStartPanel() {
        buttonPanel = new ButtonPanel();
        return buttonPanel;
    }

    private void addStartButtonActionListener() {
        buttonPanel.addStartButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int roundsToWin = buttonPanel.getRoundsToWin();
                int selectedMapIndex = mapSelectorPanel.getCurrentMapIndex();

                String mapFilePath = "src/resources/maps/map" + (selectedMapIndex + 1) + ".txt";

                try {
                    Board board = new Board(BOARD_HEIGHT.getSize(), mapFilePath, selectedMapIndex);
                    gameEngine = new GameEngine(board);

                    getContentPane().removeAll();
                    getContentPane().add(gameEngine);
                    gameEngine.requestFocusInWindow();

                    revalidate();
                    repaint();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error loading map file.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void addExitButtonActionListener() {
        buttonPanel.addExitButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                System.exit(0);
            }
        });
    }
}

