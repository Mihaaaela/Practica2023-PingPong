import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class KeyInput extends KeyAdapter {

	private Paddle lp; // left paddle
	private boolean lup = false; // lup = left up (up1 in video)
	private boolean ldown = false;
	private boolean lleft = false;
	private boolean lright = false;

	private Paddle rp; // right paddle
	private boolean rup = false;
	private boolean rdown = false;
	private boolean rleft = false;
	private boolean rright = false;

	/**
	 * constructor
	 * 
	 *  p1 - paddle 1
	 *  p2 - paddle 2
	 */
	public KeyInput(Paddle p1, Paddle p2) {

		lp = p1;
		rp = p2;

	}

	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_UP) {
			rp.switchDirections(-1);
			rup = true;
		}
		if (key == KeyEvent.VK_DOWN) {
			rp.switchDirections(1);
			rdown = true;
		}
		if (key == KeyEvent.VK_LEFT) {
		    rp.switchDirections(-1);
		    rleft = true;
		}

		
		
		
		
		if (key == KeyEvent.VK_W) {
			lp.switchDirections(-1);
			lup = true;
		}
		if (key == KeyEvent.VK_S) {
			lp.switchDirections(1);
			ldown = true;
		}

		// exit
		if (key == KeyEvent.VK_ESCAPE) {
			System.exit(1);
		}
	}

	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_UP) {
			
			rup = false;
		}
		if (key == KeyEvent.VK_DOWN) {
	;
			rdown = false;
		}
		if (key == KeyEvent.VK_LEFT) {
			
			rleft = false;
		}
		
		
		
		
		
		
		if (key == KeyEvent.VK_W) {
			
			lup = false;
		}
		if (key == KeyEvent.VK_S) {
			
			ldown = false;
			
		}

		// this is the magic that will stop the lag
		if (!lup && !ldown)
			lp.stop();
		if (!rup && !rdown)
			rp.stop();

	
	}

}