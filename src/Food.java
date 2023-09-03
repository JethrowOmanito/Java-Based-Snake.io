import java.awt.*;

public class Food {
    private int x;
    private int y;
    private int size;
    private Color color;
    public static final int FOOD_SIZE = 10;

    public Food(int x, int y, int size, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    // Check if the snake's head intersects with the food
    public boolean isEaten(Snake snake) {
        Point head = snake.getSegments().get(0);
        Rectangle headRect = new Rectangle(head.x, head.y, size, size);
        return headRect.intersects(getFoodRect());
    }

    // Return the rectangle representing the food's collision boundary
    public Rectangle getFoodRect() {
        return new Rectangle(x, y, size, size);
    }

    // Respawn the food at the given location
    public void respawn(int newX, int newY) {
        x = newX;
        y = newY;
    }
}
