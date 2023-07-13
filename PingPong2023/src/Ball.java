import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

public class Ball {

    public static final int SIZE = 32;

    private int x, y; // position of top left corner of square
    private int xVel, yVel; // either 1 or -1
    int speed = 6; // speed of the ball

    /**
     * constructor
     */
    public Ball() {
        reset();
    }

    /**
     * setup initial position and velocity
     */
    private void reset() {
        // initial position
        x = Game.WIDTH / 2 - SIZE / 2;
        y = Game.HEIGHT / 2 - SIZE / 2;

        // initial velocity
        xVel = Game.sign(Math.random() * 2.0 - 1);
        yVel = Game.sign(Math.random() * 2.0 - 1);
    }

    /**
     * Draw ball (a circle)
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.pink);
        g2d.fillOval(x, y, SIZE, SIZE);
    }

    /**
     * update position AND collision tests
     */
    public void update(Paddle lp, Paddle rp) {
        // update position
        x += xVel * speed;
        y += yVel * speed;

        // collisions

        // with ceiling and floor
        if (y + SIZE >= Game.HEIGHT || y <= 0)
            changeYDir();

        // with walls
        if (x + SIZE >= Game.WIDTH) { // right wall
            lp.addPoint();
            reset();
        }
        if (x <= 0) { // left wall
            rp.addPoint();
            reset();
        }
    }

    /**
     * Get the x position of the ball
     */
    public int getX() {
        return x;
    }

    /**
     * Get the y position of the ball
     */
    public int getY() {
        return y;
    }

    /**
     * Switch x direction. If the ball is going right, go left; otherwise, go right.
     */
    public void changeXDir() {
        xVel *= -1;
    }

    /**
     * Switch y direction. If the ball is going up, go down; otherwise, go up.
     */
    public void changeYDir() {
        yVel *= -1;
    }
}
