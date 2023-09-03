import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

class Canvas extends JPanel {
    private BufferedImage backgroundImage;
    private SnakeGame snakeGame; // Add a reference to the SnakeGame instance

    public void setBackgroundImage(BufferedImage image) {
        this.backgroundImage = image;
        repaint();
    }

    // Add a constructor that accepts a SnakeGame instance
    public Canvas(SnakeGame snakeGame) {
        this.snakeGame = snakeGame;
    }

    public void drawBackground(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this);
        } else {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);

        // Use the reference to the actual SnakeGame instance to call draw
        snakeGame.draw(g);
    }
}
