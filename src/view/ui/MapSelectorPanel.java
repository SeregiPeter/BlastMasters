package view.ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MapSelectorPanel extends JPanel {
    private JLabel mapPreviewLabel;
    private JComboBox<String> mapComboBox;
    private Image[] mapImages;
    private String[] mapNames;

    public MapSelectorPanel(String[] mapNames, Image[] mapImages, ActionListener actionListener) {
        this.mapNames = mapNames;
        this.mapImages = mapImages;

        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        setOpaque(false);

        JLabel mapLabel = new JLabel("Map selector");
        mapLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 25));

        mapPreviewLabel = new JLabel(new ImageIcon(mapImages[0]));
        mapPreviewLabel.setPreferredSize(new Dimension(200, 200));

        mapComboBox = new JComboBox<>(mapNames);
        mapComboBox.addActionListener(actionListener);

        add(mapLabel);
        add(mapPreviewLabel);
        add(mapComboBox);
    }

    public int getCurrentMapIndex() {
        return mapComboBox.getSelectedIndex();
    }

    public void setMapPreview(Image image) {
        mapPreviewLabel.setIcon(new ImageIcon(image));
    }
}

