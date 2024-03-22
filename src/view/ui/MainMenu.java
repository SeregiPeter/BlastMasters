package view.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MainMenu extends JFrame {
    private JButton btnExit;
    private JPanel panelMain;
    private JButton btnNewGame;
    private JLabel titleImageLabel;
    private Image backgroundImage;

    public MainMenu() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageIcon titleIcon = new ImageIcon("src/resources/assets/menu/blastmasterstitle.png");
        titleImageLabel = new JLabel(titleIcon);
        titleImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleImageLabel.setVerticalAlignment(SwingConstants.CENTER);

        btnNewGame = createButton("New Game");
        btnExit = createButton("Exit game");

        customizeButton(btnNewGame);
        customizeButton(btnExit);

        try {
            backgroundImage = ImageIO.read(new File("src/resources/assets/menu/bomberblur.png"));
            panelMain = createImagePanel();
        } catch (IOException e) {
            e.printStackTrace();
        }

        configureMainPanel();
        configureButtonPanel();

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartGame startGame = new StartGame();
                startGame.setVisible(true);
                dispose();
            }
        });
    }

    private JButton createButton(String text) {
        return new JButton(text);
    }

    private void customizeButton(JButton button) {
        button.setPreferredSize(new Dimension(200, 50));
        button.setBackground(new Color(51, 206, 250));
        button.setForeground(Color.white);
        button.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
    }

    private JPanel createImagePanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
    }

    private void configureMainPanel() {
        panelMain.setLayout(new BorderLayout());
        panelMain.add(Box.createVerticalStrut(50), BorderLayout.NORTH);
        panelMain.add(titleImageLabel, BorderLayout.CENTER);
    }

    private void configureButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 90, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.add(btnNewGame);
        buttonPanel.add(btnExit);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 90, 0));
        panelMain.add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.setContentPane(mainMenu.panelMain);
        mainMenu.setTitle("Blast Masters");
        mainMenu.setSize(1500, 751);
        mainMenu.setVisible(true);
        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

