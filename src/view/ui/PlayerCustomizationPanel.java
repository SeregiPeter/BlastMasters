package view.ui;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Represents a panel for player customization.
 * The PlayerCustomizationPanel class provides a panel where players can customize their character's name
 * and control keys.
 */
public class PlayerCustomizationPanel extends JPanel implements KeyListener {
    private JTextField playerNameField;
    private JLabel characterPreviewLabel;
    private JTextField controlWTextField;
    private JTextField controlATextField;
    private JTextField controlSTextField;
    private JTextField controlDTextField;
    private JTextField controlBoxTextField;
    private JTextField controlWallTextField;
    private JButton editButton;
    private JButton saveButton;
    private String initialPlayerName;
    private String initialControls;

    Color lighterBlue = new Color(51, 206, 250);
    private Border editableBorder = BorderFactory.createLineBorder(lighterBlue, 2);

    /**
     * Constructs a new PlayerCustomizationPanel with text fields and buttons for customization.
     * Initializes the panel with default player name and control keys.
     */
    public PlayerCustomizationPanel() {

        UIManager.put("Panel.font", new Font("Trebuchet MS", Font.BOLD, 22));
        UIManager.put("Label.font", new Font("Trebuchet MS", Font.BOLD, 22));
        UIManager.put("TextField.font", new Font("Trebuchet MS", Font.BOLD, 22));
        UIManager.put("Button.font", new Font("Trebuchet MS", Font.BOLD, 22));


        setLayout(new GridLayout(7, 1));
        setPreferredSize(new Dimension(150,1000));
        setOpaque(false);

        playerNameField = new JTextField("Player 1");
        playerNameField.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        playerNameField.setHorizontalAlignment(JTextField.CENTER);
        playerNameField.setOpaque(false);
        playerNameField.setEditable(false);


        characterPreviewLabel = new JLabel();
        characterPreviewLabel.setOpaque(false);

        characterPreviewLabel.setHorizontalAlignment(JTextField.CENTER);


        JPanel moveControlsPanel = new JPanel(new GridLayout(2, 3));
        moveControlsPanel.setOpaque(false);


        controlWTextField = new JTextField("W");
        controlWTextField.setEditable(false);
        controlWTextField.setHorizontalAlignment(JTextField.CENTER);
        controlWTextField.setBorder(null);

        controlATextField = new JTextField("A");
        controlATextField.setEditable(false);
        controlATextField.setHorizontalAlignment(JTextField.CENTER);
        controlATextField.setBorder(null);

        controlSTextField = new JTextField("S");
        controlSTextField.setEditable(false);
        controlSTextField.setHorizontalAlignment(JTextField.CENTER);
        controlSTextField.setBorder(null);

        controlDTextField = new JTextField("D");
        controlDTextField.setEditable(false);
        controlDTextField.setHorizontalAlignment(JTextField.CENTER);
        controlDTextField.setBorder(null);

        JPanel additionalControlsPanel = new JPanel(new GridLayout(2, 2));
        additionalControlsPanel.setOpaque(false);
        controlBoxTextField = new JTextField("R");
        controlBoxTextField.setEditable(false);
        controlBoxTextField.setHorizontalAlignment(JTextField.CENTER);
        controlBoxTextField.setBorder(null);

        JLabel boxLabel = new JLabel("Box:");
        boxLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 24));
        boxLabel.setHorizontalAlignment(JLabel.RIGHT);

        controlWallTextField = new JTextField("T");
        controlWallTextField.setEditable(false);
        controlWallTextField.setHorizontalAlignment(JTextField.CENTER);
        controlWallTextField.setBorder(null);

        controlWTextField.addKeyListener(this);
        controlATextField.addKeyListener(this);
        controlSTextField.addKeyListener(this);
        controlDTextField.addKeyListener(this);
        controlBoxTextField.addKeyListener(this);
        controlWallTextField.addKeyListener(this);

        JLabel bombLabel = new JLabel("Bomb:");
        bombLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 24));
        bombLabel.setHorizontalAlignment(JLabel.RIGHT);

        JPanel controlButtonsPanel = new JPanel(new FlowLayout());
        controlButtonsPanel.setOpaque(false);
        editButton = new JButton("Edit");
        saveButton = new JButton("Save");

        Color lighterBlue = new Color(51, 206, 250);

        editButton.setBackground(lighterBlue);
        editButton.setForeground(Color.white);
        saveButton.setBackground(lighterBlue);
        saveButton.setForeground(Color.white);
        saveButton.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
        editButton.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
        editButton.setPreferredSize(new Dimension(70, 50));
        saveButton.setPreferredSize(new Dimension(70, 50));

        controlButtonsPanel.add(editButton);
        controlButtonsPanel.add(saveButton);
        controlButtonsPanel.setSize(getWidth(),getHeight());

        moveControlsPanel.add(new JLabel());
        moveControlsPanel.add(controlWTextField);
        moveControlsPanel.add(new JLabel());
        moveControlsPanel.add(controlATextField);
        moveControlsPanel.add(controlSTextField);
        moveControlsPanel.add(controlDTextField);

        additionalControlsPanel.add(boxLabel);
        additionalControlsPanel.add(controlBoxTextField);
        additionalControlsPanel.add(bombLabel);
        additionalControlsPanel.add(controlWallTextField);


        add(playerNameField);
        add(characterPreviewLabel);
        add(moveControlsPanel);
        add(additionalControlsPanel);
        add(controlButtonsPanel);

        try {
            ImageIcon bombermanIcon = new ImageIcon(ImageIO.read(new File("src/resources/assets/menu/bomberman1.png")));
            Image image = bombermanIcon.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(image);
            characterPreviewLabel.setIcon(scaledIcon);

            int imageWidth = scaledIcon.getIconWidth();
            int imageHeight = scaledIcon.getIconHeight();
            characterPreviewLabel.setPreferredSize(new Dimension(imageWidth, imageHeight));

        } catch (IOException e) {
            e.printStackTrace();
        }

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playerNameField.isEditable()) {
                    playerNameField.setEditable(false);
                    controlWTextField.setEditable(false);
                    controlATextField.setEditable(false);
                    controlSTextField.setEditable(false);
                    controlDTextField.setEditable(false);
                    controlBoxTextField.setEditable(false);
                    controlWallTextField.setEditable(false);

                    playerNameField.setBorder(null);
                    controlWTextField.setBorder(null);
                    controlATextField.setBorder(null);
                    controlSTextField.setBorder(null);
                    controlDTextField.setBorder(null);
                    controlBoxTextField.setBorder(null);
                    controlWallTextField.setBorder(null);


                    if (!playerNameField.getText().equals(initialPlayerName)) {
                        playerNameField.setText(initialPlayerName);
                    }
                    if (!getControls().equals(initialControls)) {
                        setControls(initialControls);
                    }

                    editButton.setText("Edit");
                } else {

                    initialPlayerName = playerNameField.getText();
                    initialControls = getControls();

                    playerNameField.setEditable(true);
                    controlWTextField.setEditable(true);
                    controlATextField.setEditable(true);
                    controlSTextField.setEditable(true);
                    controlDTextField.setEditable(true);
                    controlBoxTextField.setEditable(true);
                    controlWallTextField.setEditable(true);

                    playerNameField.setBorder(editableBorder);
                    controlWTextField.setBorder(editableBorder);
                    controlATextField.setBorder(editableBorder);
                    controlSTextField.setBorder(editableBorder);
                    controlDTextField.setBorder(editableBorder);
                    controlBoxTextField.setBorder(editableBorder);
                    controlWallTextField.setBorder(editableBorder);


                }
            }
        });


        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                playerNameField.setEditable(false);
                controlWTextField.setEditable(false);
                controlATextField.setEditable(false);
                controlSTextField.setEditable(false);
                controlDTextField.setEditable(false);
                controlBoxTextField.setEditable(false);
                controlWallTextField.setEditable(false);

                playerNameField.setBorder(null);
                controlWTextField.setBorder(null);
                controlATextField.setBorder(null);
                controlSTextField.setBorder(null);
                controlDTextField.setBorder(null);
                controlBoxTextField.setBorder(null);
                controlWallTextField.setBorder(null);
            }
        });
    }

    /**
     * Retrieves the customized control keys as a concatenated string.
     *
     * @return A string representing the customized control keys.
     */
    public String getControls() {
        return controlWTextField.getText() + controlATextField.getText() +
                controlSTextField.getText() + controlDTextField.getText() + controlBoxTextField.getText() +
                controlWallTextField.getText();

    }

    /**
     * Sets the player's name in the text field.
     *
     * @param name The name to set for the player.
     */
    public void setPlayerName(String name) {
        playerNameField.setText(name);
    }

    /**
     * Sets the player's character preview image.
     *
     * @param imageIcon The ImageIcon to set as the character preview.
     */
    public void setPlayerImage(ImageIcon imageIcon) {
        characterPreviewLabel.setIcon(imageIcon);

        int imageWidth = imageIcon.getIconWidth();
        int imageHeight = imageIcon.getIconHeight();
        characterPreviewLabel.setPreferredSize(new Dimension(imageWidth, imageHeight));
    }

    /**
     * Sets the customized control keys for the player.
     *
     * @param controls A string representing the customized control keys.
     */
    public void setControls(String controls) {
        controlWTextField.setText(controls.substring(0, 1));
        controlATextField.setText(controls.substring(1, 2));
        controlSTextField.setText(controls.substring(2, 3));
        controlDTextField.setText(controls.substring(3, 4));
        controlBoxTextField.setText(controls.substring(4, 5));
        controlWallTextField.setText(controls.substring(5));
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        JTextField source = (JTextField) e.getSource();

        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
            source.setText(KeyEvent.getKeyText(keyCode));

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
