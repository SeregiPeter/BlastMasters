package view.ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Represents a panel for selecting maps.
 * The MapSelectorPanel class provides a panel with a map selector combo box
 * and a preview of the selected map.
 */
public class MapSelectorPanel extends JPanel {
    private JLabel mapPreviewLabel;
    private JComboBox<String> mapComboBox;
    private final Image[] mapImages;
    private final String[] mapNames;

    /**
     * Constructs a MapSelectorPanel with the given map names, map images, and an action listener.
     *
     * @param mapNames       an array of map names to display in the combo box
     * @param mapImages      an array of map preview images corresponding to the map names
     * @param actionListener the ActionListener to handle events when selecting a map
     */
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

    /**
     * Gets the index of the currently selected map in the combo box.
     *
     * @return the index of the currently selected map
     */
    public int getCurrentMapIndex() {
        return mapComboBox.getSelectedIndex();
    }

    /**
     * Sets the preview image of the selected map.
     *
     * @param image the image to be displayed as the map preview
     */
    public void setMapPreview(Image image) {
        mapPreviewLabel.setIcon(new ImageIcon(image));
    }
}

