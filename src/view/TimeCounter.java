package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimeCounter extends JPanel {
    private JLabel timeLabel;
    private Timer timer;
    private int seconds;

    public TimeCounter() {
        timeLabel = new JLabel("0:00");
        timeLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 24));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(timeLabel);

        seconds = 0;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
                updateTimeLabel();
            }
        });
        timer.start();
    }

    private void updateTimeLabel() {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        String formattedTime = String.format("%d:%02d", minutes, remainingSeconds);
        timeLabel.setText(formattedTime);
    }

    public void pause() {
        timer.stop(); // Stop the timer when the game is paused
    }

    public void resume() {
        timer.start(); // Resume the timer when the game is resumed
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Time Counter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new TimeCounter());
        frame.pack();
        frame.setVisible(true);
    }
}
