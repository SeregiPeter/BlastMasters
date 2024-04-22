import model.board.Board;
import model.board.Image;
import model.board.Size;
import model.board.element.character.Player;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static model.board.Image.PLAYER1_IMMORTAL_IMG;
import static model.board.Size.*;
import static model.board.Velocity.PLAYER_VEL;
import static org.junit.Assert.assertEquals;

public class PlayerTest {

    private Player player;

    @Before
    public void setUp() throws IOException {
        String mapFilePath = "src/main/resources/maps/map" + (0 + 1) + ".txt";

        Board board = new  Board(BOARD_WIDTH.getSize(), mapFilePath, 0, 1);
        List<String> player1ImageUrls = Image.PLAYER1_IMG.getImageUrls();
        List<java.awt.Image> player1Images = new ArrayList<>();
        List<String> player1ImmortalImgUrls = PLAYER1_IMMORTAL_IMG.getImageUrls();
        List<java.awt.Image> player1ImmortalImages = new ArrayList<>();
        for (String url : player1ImageUrls) {
            player1Images.add(new ImageIcon(url).getImage());
        }
        for (String url : player1ImmortalImgUrls) {
            player1ImmortalImages.add(new ImageIcon(url).getImage());
        }
        player = new Player(0, 0, PLAYER_WIDTH.getSize(), PLAYER_HEIGHT.getSize(), PLAYER_VEL.getVelocity(),
                player1Images, player1ImmortalImages,true, true, "Player1", board, null);
    }

    @Test
    public void testPlayerInitialization() {
        // Test player name initialization
        assertEquals("Player name match", "", player.getName());

        // You can add more assertions to test other properties as needed
    }

    @Test
    public void testIncrementPoints() {
        // Test initial points
        assertEquals("Initial points should be 0", 0, player.getPoints());

        // Increment points
        player.incrementPoints();
        assertEquals("Points should be incremented to 1", 1, player.getPoints());

        // You can add more assertions to test other scenarios
    }

    @Test
    public void testPlantBomb() {
        assertEquals("Initial number of placeable bombs should be 1", 1, player.getNumberOfPlaceableBombs());

        player.plantBomb();


        assertEquals("Number of placeable bombs should decrease after planting", 0, player.getNumberOfPlaceableBombs());
    }

}