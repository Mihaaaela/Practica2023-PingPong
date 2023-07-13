import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Paddle {

    private int x, y; // positions
    private int vel = 0; // speed and direction of paddle
    private int speed = 10; // speed of the paddle movement
    private int width = 22, height = 85; // dimensions
    private int score = 0; // score for the player
    private Color color; // color of the paddle
    private boolean left; // true if it's the left paddle
    private boolean gameOver = false; // tracks if the game is over

    Paddle(Color c, boolean left) {
        color = c;
        this.left = left;

        if (left)
            x = 0;
        else
            x = Game.WIDTH - width;

        y = Game.HEIGHT / 2 - height / 2;
    }

    /**
     * Add a point to the player and check if the game is over
     */
    public void addPoint() {
        score++;

        if (score == 2) {
            gameOver = true;
            stop();

        }
    }


    public void draw(Graphics g) {
        // Draw paddle
        g.setColor(color);
        g.fillRect(x, y, width, height);

        // Draw score or winning message
        if (gameOver) {
            String winMessage = "Player wins the game!";
            Font font = new Font("Roboto", Font.PLAIN, 50);
            int messageWidth = g.getFontMetrics(font).stringWidth(winMessage);
            int messageX = Game.WIDTH / 2 - messageWidth / 2;
            g.setFont(font);
            g.drawString(winMessage, messageX, 50);
        } else {
            String scoreText = Integer.toString(score);
            Font font = new Font("Roboto", Font.PLAIN, 50);
            int padding = 25;
            int sx;

            if (left) {
                int strWidth = g.getFontMetrics(font).stringWidth(scoreText);
                sx = Game.WIDTH / 2 - padding - strWidth;
            } else {
                sx = Game.WIDTH / 2 + padding;
            }

            g.setFont(font);
            g.drawString(scoreText, sx, 50);
            
            
        }
    }

    /**
     * Update position and collision tests
     *
     *  b - the ball
     */
    public void update(Ball b) {
        if (!gameOver) {
            // Update position
            y = Game.ensureRange(y + vel, 0, Game.HEIGHT - height);

            // Collisions
            int ballX = b.getX();
            int ballY = b.getY();

            if (left) {
                if (ballX <= width + x && ballY + Ball.SIZE >= y && ballY <= y + height)
                    b.changeXDir();
            } else {
                if (ballX + Ball.SIZE >= x && ballY + Ball.SIZE >= y && ballY <= y + height)
                    b.changeXDir();
            }
        }
    }

    /**
     * Switch the direction
     *
     *  direction - -1 for up and 1 for down
     */
    public void switchDirections(int direction) {
        vel = speed * direction;
    }

    /**
     * Stop moving the paddle
     */
    public void stop() {
        vel = 0;
    }
}
