import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeGame {
    private static final int BOARD_WIDTH = 800;
    private static final int BOARD_HEIGHT = 600;
    private static final int FOOD_SIZE = 10;
    private static final int POWERUP_SIZE = 10;

    private Snake snake;
    private Food food;
    private List<PowerUp> powerUps;
    private Random random;
    private Canvas canvas;

    private JFrame frame; // Reference to the game frame

    public SnakeGame() {
        random = new Random();
        snake = new Snake(BOARD_WIDTH / 2, BOARD_HEIGHT / 2, BOARD_WIDTH, BOARD_HEIGHT, FOOD_SIZE);
        food = new Food(random.nextInt(BOARD_WIDTH - FOOD_SIZE), random.nextInt(BOARD_HEIGHT - FOOD_SIZE), FOOD_SIZE, Color.RED);
        powerUps = new ArrayList<>();
        generatePowerUps();
    }

    public void start() {
        initializeGame();

        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                canvas.repaint();
            }
        });
        timer.start();
    }

    private void initializeGame() {
        frame = new JFrame("Snake Game");

        canvas = new Canvas(this); // Initialize the custom Canvas here
        canvas.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT)); // Set preferred size for canvas
        frame.add(canvas);

        // Set the background image
        try {
            BufferedImage backgroundImage = ImageIO.read(new File("C:\\Users\\Jethrow Cruz\\Downloads\\t1.jpg"));
            canvas.setBackgroundImage(backgroundImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack(); // Pack the frame to fit the canvas size
        frame.setLocationRelativeTo(null);

        // Show buttons before starting the game
        showMenu();

        frame.setVisible(true);

        canvas.setFocusable(true);
        canvas.requestFocusInWindow();
    }

    // Show buttons to choose play, choose snake, or exit
    private void showMenu() {
        String[] options = { "Play Game", "Choose Snake", "Exit" };
        int choice = JOptionPane.showOptionDialog(null, "Choose an option", "Snake Game", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                startGame();
                break;
            case 1:
                // Add functionality to choose a snake (if needed)
                showMenu();
                break;
            case 2:
                System.exit(0);
                break;
            default:
                showMenu();
                break;
        }
    }

    private void startGame() {
        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    snake.turnLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    snake.turnRight();
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    snake.turnUp();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    snake.turnDown();
                }
            }
        });

        canvas.requestFocusInWindow();
    }

    private void update() {
        snake.move();

        if (snake.collidesWithFood(food)) {
            snake.handleFoodCollision(food);
            food.respawn(random.nextInt(BOARD_WIDTH - FOOD_SIZE), random.nextInt(BOARD_HEIGHT - FOOD_SIZE));
        }

        for (int i = powerUps.size() - 1; i >= 0; i--) {
            PowerUp powerUp = powerUps.get(i);
            if (snake.collidesWithPowerUp(powerUp)) {
                snake.handlePowerUpCollision(powerUp);
                powerUps.remove(i);
            }
        }

        if (snake.collidesWithBoundary() || snake.collidesWithItself()) {
            gameOver();
        }
    }

    void draw(Graphics g) {
        // Draw the snakes and food
        g.setColor(Color.GREEN);
        for (Point segment : snake.getSegments()) {
            g.fillRect(segment.x, segment.y, FOOD_SIZE, FOOD_SIZE);
        }

        g.setColor(Color.RED);
        g.fillRect(food.getX(), food.getY(), FOOD_SIZE, FOOD_SIZE);

        for (PowerUp powerUp : powerUps) {
            powerUp.draw(g);
        }
    }

    private void generatePowerUps() {
        for (int i = 0; i < 5; i++) {
            int type = random.nextInt(3) + 1;
            int x = random.nextInt(BOARD_WIDTH - POWERUP_SIZE);
            int y = random.nextInt(BOARD_HEIGHT - POWERUP_SIZE);
            powerUps.add(new PowerUp(x, y, type));
        }
    }

    private void gameOver() {
        JOptionPane.showMessageDialog(null, "Game Over! Your score: " + snake.getLength(), "Game Over", JOptionPane.INFORMATION_MESSAGE);
        snake = new Snake(BOARD_WIDTH / 2, BOARD_HEIGHT / 2, BOARD_WIDTH, BOARD_HEIGHT, FOOD_SIZE);
        food.respawn(random.nextInt(BOARD_WIDTH - FOOD_SIZE), random.nextInt(BOARD_HEIGHT - FOOD_SIZE));
        powerUps.clear();
        generatePowerUps();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SnakeGame game = new SnakeGame();
            game.start();
        });
    }
}

class CustomCanvas extends JPanel {
    private BufferedImage backgroundImage;

    public void setBackgroundImage(BufferedImage image) {
        this.backgroundImage = image;
        repaint();
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
        SnakeGame snakeGame = new SnakeGame();
        snakeGame.draw(g);
        
    }
}
