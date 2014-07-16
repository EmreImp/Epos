package org.aurora.isotest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class IsoPanel extends JPanel implements Runnable {

	private static final int PWIDTH = 800; // size of panel
	private static final int PHEIGHT = 400;
	private static final String IMS_INFO = "imsInfo.txt";

	/** Number of frames with a delay of 0 ms before the animation thread yields
	to other running threads. */
	private static final int NO_DELAYS_PER_YIELD = 16;

	/** no. of frames that can be skipped in any one animation loop
     i.e the games state is updated but not rendered */
	private static final int MAX_FRAME_SKIPS = 5;


	private IsoFrame topFrame;
	/** period between drawing in _nanosecs_ */
	private long period;
	private boolean showHelp;
	
	private long gameStartTime;   // when the game started
	private int timeSpentInGame;
	

	/** used to stop the animation thread */
	private volatile boolean running = false; 
	private volatile boolean isPaused = false;

	public IsoPanel(IsoFrame jj, long period) {
		topFrame = jj;
		this.period = period;
		setDoubleBuffered(false);
		setBackground(Color.black);
		setPreferredSize(new Dimension(PWIDTH, PHEIGHT));

		setFocusable(true);
		requestFocus(); // the JPanel now has focus, so receives key events

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				processKey(e);
			}
		});

		// initialise the loaders
		ImagesLoader imsLoader = new ImagesLoader(IMS_INFO);
		// clipsLoader = new ClipsLoader(SNDS_FILE);

	}

	@Override
	public void run()
	/* The frames of the animation are drawn inside the while loop. */
	{
		long beforeTime, afterTime, timeDiff, sleepTime;
		long overSleepTime = 0L;
		int noDelays = 0;
		long excess = 0L;

		gameStartTime = System.currentTimeMillis();
		beforeTime = gameStartTime;

		running = true;

		while (running) {
			//gameUpdate();
			//gameRender();
			//paintScreen();

			afterTime = System.currentTimeMillis();
			timeDiff = afterTime - beforeTime;
			sleepTime = (period - timeDiff) - overSleepTime;

			if (sleepTime > 0) { // some time left in this cycle
				try {
					Thread.sleep(sleepTime / 1000000L); // nano -> ms
				} catch (InterruptedException ex) {
				}
				overSleepTime = (System.currentTimeMillis() - afterTime) - sleepTime;
			} else { // sleepTime <= 0; the frame took longer than the period
				excess -= sleepTime; // store excess time value
				overSleepTime = 0L;

				if (++noDelays >= NO_DELAYS_PER_YIELD) {
					Thread.yield(); // give another thread a chance to run
					noDelays = 0;
				}
			}

			beforeTime = System.currentTimeMillis();

			/*
			 * If frame animation is taking too long, update the game state
			 * without rendering it, to get the updates/sec nearer to the
			 * required FPS.
			 */
			int skips = 0;
			while ((excess > period) && (skips < MAX_FRAME_SKIPS)) {
				excess -= period;
				//gameUpdate(); // update state but don't render
				skips++;
			}
		}
		System.exit(0); // so window disappears
	}

	public void resumeGame()
	// called when the JFrame is activated / deiconified
	{
		if (!showHelp) // CHANGED
			isPaused = false;
	}

	// called when the JFrame is deactivated / iconified
	public void pauseGame() {
		isPaused = true;
	}

	// called when the JFrame is closing
	public void stopGame() {
		running = false;
	}

	// handles termination, help, and game-play keys
	private void processKey(KeyEvent e) {
		int keyCode = e.getKeyCode();

		// termination keys
		// listen for esc, q, end, ctrl-c on the canvas to
		// allow a convenient exit from the full screen configuration
		if ((keyCode == KeyEvent.VK_ESCAPE) || (keyCode == KeyEvent.VK_Q)
				|| (keyCode == KeyEvent.VK_END)
				|| ((keyCode == KeyEvent.VK_C) && e.isControlDown()))
			running = false;

		// help controls
		if (keyCode == KeyEvent.VK_H) {
			if (showHelp) { // help being shown
				showHelp = false; // switch off
				isPaused = false;
			} else { // help not being shown
				showHelp = true; // show it
				isPaused = true; // isPaused may already be true
			}
		}

		/*
		 * // game-play keys if (!isPaused && !gameOver) { // move the player
		 * based on the numpad key pressed if (keyCode == KeyEvent.VK_NUMPAD7)
		 * player.move(TiledSprite.NW); // move north west else if (keyCode ==
		 * KeyEvent.VK_NUMPAD9) player.move(TiledSprite.NE); // north east else
		 * if (keyCode == KeyEvent.VK_NUMPAD3) player.move(TiledSprite.SE); //
		 * south east else if (keyCode == KeyEvent.VK_NUMPAD1)
		 * player.move(TiledSprite.SW); // south west else if (keyCode ==
		 * KeyEvent.VK_NUMPAD5) player.standStill(); // stand still else if
		 * (keyCode == KeyEvent.VK_NUMPAD2) player.tryPickup(); // try to pick
		 * up from this tile }
		 */} // end of processKey()

}
