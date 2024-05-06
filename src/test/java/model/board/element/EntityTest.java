package model.board.element;

import model.board.Direction;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.awt.Point;

public class EntityTest {
    private Entity entity;

    @Before
    public void setUp() {
        entity = new Entity(100, 100, 50, 50, 2.0, null, true, true) {};
    }

    @Test
    public void testConstructorAndProperties() {
        assertEquals(100.0, entity.getX(), 0.001);
        assertEquals(100.0, entity.getY(), 0.001);
        assertEquals(50, entity.getWidth());
        assertEquals(50, entity.getHeight());
        assertEquals(2.0, entity.getVelocity(), 0.001);
        assertTrue(entity.isAlive());
        assertTrue(entity.isVisible());
    }

    @Test
    public void testMoveTowardsDirection() {
        entity.moveTowardsDirection(Direction.UP);
        assertEquals(98.0, entity.getY(), 0.001);
        entity.moveTowardsDirection(Direction.DOWN);
        assertEquals(100.0, entity.getY(), 0.001);
        entity.moveTowardsDirection(Direction.LEFT);
        assertEquals(98.0, entity.getX(), 0.001);
        entity.moveTowardsDirection(Direction.RIGHT);
        assertEquals(100.0, entity.getX(), 0.001);
    }

    @Test
    public void testCollisionDetection() {
        Entity other = new Entity(100, 100, 50, 50, 0, null, true, true) {};
        assertTrue(entity.collides(other));
        other.setY(151);
        assertFalse(entity.collides(other));
    }

    @Test
    public void testVisibilityAndStateChanges() {
        entity.setVisible(false);
        assertFalse(entity.isVisible());
        entity.setAlive(false);
        assertFalse(entity.isAlive());
        entity.setRemovable(true);
        assertTrue(entity.isRemovable());
    }

    @Test
    public void testGetCenterCoordinate() {
        assertEquals(new Point(125, 125), entity.getCenterCoordinate());
    }

    @Test
    public void testGetRowAndColumn() {
        entity.setX(120);
        entity.setY(180);
        assertEquals(2, entity.getColumn());
        assertEquals(3, entity.getRow());
    }

}