import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    private int x;
    private int y;
    private int dx;
    private int dy;
    private int length;
    private List<Point> segments;
    private int boardWidth;
    private int boardHeight;
    private int foodSize;

    public Snake(int x, int y, int boardWidth, int boardHeight, int foodSize) {
        this.x = x;
        this.y = y;
        this.dx = 0;
        this.dy = 0;
        this.length = 1;
        this.segments = new ArrayList<>();
        this.segments.add(new Point(x, y));
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.foodSize = foodSize;
    }

    public void turnLeft() {
        dx = -1;
        dy = 0;
    }

    public void turnRight() {
        dx = 1;
        dy = 0;
    }

    public void turnUp() {
        dx = 0;
        dy = -1;
    }

    public void turnDown() {
        dx = 0;
        dy = 1;
    }

    public void move() {
        x += dx * foodSize;
        y += dy * foodSize;

        // Check for collision with the boundaries of the game board
        if (x < 0) {
            x = boardWidth - foodSize;
        } else if (x >= boardWidth) {
            x = 0;
        }

        if (y < 0) {
            y = boardHeight - foodSize;
        } else if (y >= boardHeight) {
            y = 0;
        }

        segments.add(0, new Point(x, y));

        // Remove the last segment if not growing
        if (segments.size() > length) {
            segments.remove(segments.size() - 1);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLength() {
        return length;
    }

    public void grow() {
        length++;
    }

    public List<Point> getSegments() {
        return segments;
    }

    public boolean collidesWithBoundary() {
        return x < 0 || x >= boardWidth || y < 0 || y >= boardHeight;
    }

    public boolean collidesWithItself() {
        for (int i = 1; i < segments.size(); i++) {
            Point segment = segments.get(i);
            if (segment.x == x && segment.y == y) {
                return true;
            }
        }
        return false;
    }

    public boolean collidesWithFood(Food food) {
        Point head = segments.get(0);
        Rectangle headRect = new Rectangle(head.x, head.y, foodSize, foodSize);
        return headRect.intersects(food.getFoodRect());
    }

    public boolean collidesWithPowerUp(PowerUp powerUp) {
        Point head = segments.get(0);
        Rectangle headRect = new Rectangle(head.x, head.y, foodSize, foodSize);
        return headRect.intersects(powerUp.getPowerUpRect());
    }

    public void handleFoodCollision(Food food) {
        if (collidesWithFood(food)) {
            grow();
            food.respawn(boardWidth - foodSize, boardHeight - foodSize);
        }
    }

    public void handlePowerUpCollision(PowerUp powerUp) {
        if (collidesWithPowerUp(powerUp)) {
            // Apply power-up effect based on the power-up type (implement this as needed)
            powerUp.handleCollision(this);
        }
    }
}
