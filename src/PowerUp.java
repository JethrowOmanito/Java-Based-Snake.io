import java.awt.*;

public class PowerUp {
    private int x;
    private int y;
    private int type;

    public PowerUp(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }

    public void draw(Graphics g) {
        switch (type) {
            case 1:
                g.setColor(Color.RED);
                g.fillRect(x, y, 10, 10);
                break;
            case 2:
                g.setColor(Color.GREEN);
                g.fillRect(x, y, 10, 10);
                break;
            case 3:
                g.setColor(Color.BLUE);
                g.fillRect(x, y, 10, 10);
                break;
        }
    }

    // Return the rectangle representing the power-up's collision boundary
    public Rectangle getPowerUpRect() {
        return new Rectangle(x, y, 10, 10);
    }

	public void handleCollision(Snake snake) {
		// TODO Auto-generated method stub
		
	}
}
