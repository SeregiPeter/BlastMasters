package view.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class HoverPanel extends JPanel {
    private JLabel scoreLabel;
    private JLabel winLabel;
    public HoverPanel(int score1,int score2,String winner) {
        setLayout(new BorderLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(500, 200));



        JLabel titleLabel = new JLabel("Score");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        winLabel=new JLabel();
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        winLabel.setHorizontalAlignment(JLabel.CENTER);




        scoreLabel = new JLabel(score1+":"+score2);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 100));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);

        add(titleLabel,BorderLayout.NORTH);
        add(scoreLabel,BorderLayout.CENTER);
        add(winLabel,BorderLayout.SOUTH);

    }
    public void setScore(int score1,int score2,String winner){

        scoreLabel.setText(score1+":"+score2);
        winLabel.setText(winner);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(Color.WHITE);
        int cornerRadius = 60;
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        try {
            Image leftImage = Toolkit.getDefaultToolkit().getImage("src/resources/assets/menu/bomberman2.png");
            Image rightImage = Toolkit.getDefaultToolkit().getImage("src/resources/assets/menu/bomberman1.png");

            int imageWidth = leftImage.getWidth(null);
            int imageHeight = leftImage.getHeight(null);

            rightImage = rightImage.getScaledInstance(imageWidth, imageHeight, Image.SCALE_FAST);

            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(leftImage, 0);
            tracker.addImage(rightImage, 1);
            tracker.waitForAll();


            int imageXOffset = 10;
            int imageYOffset = (getHeight() - imageHeight) / 2;

            g2d.drawImage(leftImage, imageXOffset, imageYOffset, null);

            g2d.drawImage(rightImage, getWidth() - imageXOffset - imageWidth, imageYOffset, this);
        } catch (Exception e) {
            System.out.println("Error loading image");
        }
    }
}
