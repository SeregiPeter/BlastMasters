import model.board.Board;
import model.board.Direction;
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

public class DirectionTest {

    @Test
    public void TestDirection() {
        assertEquals(Direction.getOppositeDirection(Direction.LEFT),Direction.RIGHT);
    }

}