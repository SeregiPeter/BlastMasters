package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PauseButton extends JButton {

    private JFrame frame;
    private TimeCounter timeCounter;
    private boolean paused;

    public PauseButton(JFrame frame, TimeCounter timeCounter) {
        super("Pause");
        this.frame = frame;
        this.timeCounter = timeCounter;
        this.paused = false;
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (paused) {
                    resumeGame();
                } else {
                    pauseGame();
                }
            }
        });
    }

    private void pauseGame() {
        timeCounter.pause();
        paused = true;
        setLabel("Resume");
        frame.setEnabled(false);
    }

    private void resumeGame() {
        timeCounter.resume();
        paused = false;
        setLabel("Pause");
        frame.setEnabled(true);
    }
}
