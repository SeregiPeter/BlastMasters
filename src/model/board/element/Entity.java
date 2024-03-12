package model.board.element;

import java.awt.*;

public abstract class Entity {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int velocity;
    protected Image image;
    protected boolean alive;
    protected boolean visible;

    public Entity(int x, int y, int width, int height, int velocity, Image image, boolean alive, boolean visible) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocity = velocity;
        this.image = image;
        this.alive = alive;
        this.visible = visible;
    }

    public boolean collides(Entity other) {
        Rectangle rect = new Rectangle(x, y, width, height);
        Rectangle otherRect = new Rectangle(other.x, other.y, other.width, other.height);
        return rect.intersects(otherRect);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
