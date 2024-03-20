package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class StartPanel extends JPanel {
    private JLabel titleLabel;
    private JButton startButton;
    private JButton exitButton;
    private JSpinner roundSpinner;

    public StartPanel() {
        setLayout(new GridLayout(4, 1));
        setOpaque(false);

        initializeTitleLabel();

        initializeStartButton();

        initializeExitButton();

        initializeRoundPanel();
    }

    private void initializeTitleLabel() {
        try {
            Image titleImage = ImageIO.read(new File("blastmasterstitlesmall.png"));
            titleLabel = new JLabel(new ImageIcon(titleImage));
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            add(titleLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeStartButton() {
        startButton = createButton("Start");
        add(startButton);
    }

    private void initializeExitButton() {
        exitButton = createButton("Exit");
        add(exitButton);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
        button.setBackground(new Color(51, 206, 250));
        button.setForeground(Color.white);
        return button;
    }

    private void initializeRoundPanel() {
        JPanel roundPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        roundPanel.setOpaque(false);
        JLabel roundsLabel = createLabel("First to");
        roundSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 10, 1));
        roundSpinner.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
        roundPanel.add(roundsLabel);
        roundPanel.add(roundSpinner);
        roundPanel.add(createLabel("wins"));
        add(roundPanel);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
        return label;
    }

    public void addStartButtonActionListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    public void addExitButtonActionListener(ActionListener listener) {
        exitButton.addActionListener(listener);
    }

    public int getRoundsToWin() {
        return (int) roundSpinner.getValue();
    }
}
