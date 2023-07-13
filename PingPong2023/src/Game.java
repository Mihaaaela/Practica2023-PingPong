
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferStrategy;


public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public final static int WIDTH = 1000;
	public final static int HEIGHT = WIDTH * 9 / 16; // 16:9 aspect ratio

	public boolean running = false; // true if the game is running
	private Thread gameThread; // thread where the game is updated AND drawn (single thread game)

	
	private Ball ball;


	private Paddle leftPaddle;
	private Paddle rightPaddle;

	private MainMenu menu; // Main Menu object

	/**
	 * constructor
	 */
	public Game() {

		canvasSetup();

		new Window("Ping Pong 2023", this);

		initialise();

		this.addKeyListener(new KeyInput(leftPaddle, rightPaddle));
		this.addMouseListener(menu);
		this.addMouseMotionListener(menu);
		this.setFocusable(true);

	}

	/**
	 * initialize all our game objects
	 */
	private void initialise() {
		// Initialize Ball object
		ball = new Ball();

		// Initialize paddle objects
		leftPaddle = new Paddle(Color.yellow, true);
		rightPaddle = new Paddle(Color.CYAN, false);

		// initialize main menu
		menu = new MainMenu(this);
	}

	
	private void canvasSetup() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
	}

	
	public void run() {

		this.requestFocus();

		// game timer
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				update();
				delta--;
				draw();
				frames++;
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}

		stop();
	}

	/**
	 * start the thread and the game
	 */
	public synchronized void start() {
		gameThread = new Thread(this);
		
		gameThread.start(); // start thread
		running = true;
	}

	/**
	 * Stop the thread and the game
	 */
	public void stop() {
		try {
			gameThread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * draw the back and all the objects
	 */
	public void draw() {

		BufferStrategy buffer = this.getBufferStrategy(); 

		if (buffer == null) { 
			this.createBufferStrategy(3); 
			return;
		}

		Graphics g = buffer.getDrawGraphics(); 
		
		

		// draw background
		drawBackground(g);

		// draw main menu contents
		if (menu.active)
			menu.draw(g);

		// draw ball
		ball.draw(g);

		// draw paddles (score will be drawn with them)
		leftPaddle.draw(g);
		rightPaddle.draw(g);

	
		g.dispose();
		buffer.show(); 

	}

	
	private void drawBackground(Graphics g) {
		// black background
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// Dotted line in the middle
		g.setColor(Color.black);
		Graphics2D g2d = (Graphics2D) g; 
										
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 10 }, 0);
		g2d.setStroke(dashed);
		g.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
	}

	/**
	 * update settings and move all objects
	 */
	public void update() {

		if (!menu.active) {
			// update ball (movements)
			ball.update(leftPaddle, rightPaddle);

			// update paddles (movements)
			leftPaddle.update(ball);
			rightPaddle.update(ball);
		}
	}

	
	public static int ensureRange(int value, int min, int max) {
		return Math.min(Math.max(value, min), max);
	}

	
	public static int sign(double d) {
		if (d <= 0)
			return -1;

		return 1;
	}

	
	public static void main(String[] args) {
		new Game();
	}

}
