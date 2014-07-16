package org.aurora.isotest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

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
	

	private Thread animator;           // the thread that performs the animation
	/** used to stop the animation thread */
	private volatile boolean running = false; 
	private volatile boolean isPaused = false;
	/** used at game termination */
	private volatile boolean gameOver = false;
	private int score = 0;
	
	
	private BufferedImage helpIm;
	
	// for displaying messages
	private Font msgsFont;
	private FontMetrics metrics;
	
	// off-screen rendering
	private Graphics dbg; 
	private Image dbImage = null;
	// light blue for the background
	private static final Color lightBlue = new Color(0.17f, 0.87f, 1.0f);


	// game entities
	private WorldDisplay world;
	//private PlayerSprite player;
	//private AlienSprite aliens[]; 
	

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

	    // create the world, the player, and aliens
		createWorld(imsLoader); 

	    // prepare title/help screen
	    helpIm = imsLoader.getImage("title");
	    showHelp = true;    // show at start-up
	    isPaused = true;

	    // set up message showing stuff
	    msgsFont = new Font("SansSerif", Font.BOLD, 24);
	    metrics = this.getFontMetrics(msgsFont);
		
	}

	  private void createWorld(ImagesLoader imsLoader)
	  // create the game world, the player, and aliens
	  {
	    world = new WorldDisplay(imsLoader, this);  // game world, a WorldDisplay object
	    /*
	    player = new PlayerSprite(7,12, PWIDTH, PHEIGHT, clipsLoader, imsLoader, 
	                                       world, this);  // start on tile (7,12)

	    aliens = new AlienSprite[4];

	    aliens[0] = new AlienAStarSprite(10, 11, PWIDTH, PHEIGHT, imsLoader, world);
	    aliens[1] = new AlienQuadSprite(6, 21, PWIDTH, PHEIGHT, imsLoader, world);
	    aliens[2] = new AlienQuadSprite(14, 20, PWIDTH, PHEIGHT, imsLoader, world);
	    aliens[3] = new AlienAStarSprite(34, 34, PWIDTH, PHEIGHT, imsLoader, world);
	       // use 2 AStar and 2 quad alien sprites
	       // the 4th alien is positioned at an illegal tile location (34,34)

	    world.addSprites(player, aliens);  // tell the world about the sprites
	    */
	  }  // end of createWorld()
	
	  public void addNotify()
	  // wait for the JPanel to be added to the JFrame before starting
	  { 
		  super.addNotify();   // creates the peer
		  startGame();         // start the thread
	  }
	
	  private void startGame()
	  // initialise and start the thread
	  {
		  if (animator == null || !running) {
			  animator = new Thread(this);
			  animator.start();
		  }
	  } // end of startGame()

	  // ------------- game life cycle methods ------------
	  // called by the JFrame's window listener methods

	  public void resumeGame()
	  // called when the JFrame is activated / deiconified
	  {
		  if (!showHelp)
			  isPaused = false;
	  }

	  public void pauseGame()
	  // called when the JFrame is deactivated / iconified
	  {
		  isPaused = true;
	  }

	  public void stopGame()
	  // called when the JFrame is closing
	  {
		  running = false;
	  }

	  // ----------------------------------------------	  
	  
	  
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
			gameUpdate();
			gameRender();
			paintScreen();

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
				gameUpdate(); // update state but don't render
				skips++;
			}
		}
		System.exit(0); // so window disappears
	}

	  private void gameUpdate() 
	  { 
	    if (!isPaused && !gameOver) {/*
	      for(int i=0; i < aliens.length; i++)
	        aliens[i].update();    // update all the aliens*/
	    } 
	  }  // end of gameUpdate()
	
	  
	
	  private void paintScreen()
	  // use active rendering to put the buffered image on-screen
	  { 
	    Graphics g;
	    try {
	      g = this.getGraphics();
	      if ((g != null) && (dbImage != null))
	        g.drawImage(dbImage, 0, 0, null);
	      // Sync the display on some systems.
	      // (on Linux, this fixes event queue problems)
	      Toolkit.getDefaultToolkit().sync();
	      g.dispose();
	    }
	    catch (Exception e)
	    { System.out.println("Graphics context error: " + e);  }
	  } // end of paintScreen()

	private void gameRender()
	  {
	    if (dbImage == null){
	      dbImage = createImage(PWIDTH, PHEIGHT);
	      if (dbImage == null) {
	        System.out.println("dbImage is null");
	        return;
	      }
	      else
	        dbg = dbImage.getGraphics();
	    }

	    // a light blue background
	    dbg.setColor(lightBlue);
	    dbg.fillRect(0, 0, PWIDTH, PHEIGHT);

	    // draw the game elements: order is important
	    world.draw(dbg);  
	    /* WorldDisplay draws the game world: the tile floor, blocks, 
	       pickups, and the sprites. */

	    reportStats(dbg);

	    if (gameOver)
	      gameOverMessage(dbg);

	    if (showHelp)    // draw the help at the very front (if switched on)
	      dbg.drawImage(helpIm, (PWIDTH-helpIm.getWidth())/2, 
	                          (PHEIGHT-helpIm.getHeight())/2, null);
	  }  // end of gameRender()

	  private void gameOverMessage(Graphics g)
	  // Center the game-over message in the panel.
	  {
	    String msg = "Game Over. Your score: " + score;

		int x = (PWIDTH - metrics.stringWidth(msg))/2; 
		int y = (PHEIGHT - metrics.getHeight())/2;
		g.setColor(Color.red);
	    g.setFont(msgsFont);
		g.drawString(msg, x, y);
	    g.setColor(Color.black);
	  }  // end of gameOverMessage()

	 
	  private void reportStats(Graphics g)
	  // Report time spent playing, the number of hits, pickups left
	  {
	    if (!gameOver)    // stop incrementing the timer once the game is over
	      timeSpentInGame = 
	          (int) ((System.currentTimeMillis() - gameStartTime)/1000000000L);  // ns --> secs
		g.setColor(Color.red);
	    g.setFont(msgsFont);
		g.drawString("Time: " + timeSpentInGame + " secs", 15, 25);
	    //g.drawString( player.getHitStatus(), 15, 50);     // ask the player
	    g.drawString( world.getPickupsStatus(), 15, 75);  // ask WorldDisplay
		g.setColor(Color.black);
	  }  // end of reportStats()
	 

	// handles termination, help, and game-play keys
	private void processKey(KeyEvent e) {
		int keyCode = e.getKeyCode();
		System.out.println("Key press:"+keyCode);

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
	    // game-play keys
		/*if (!isPaused && !gameOver) {
			// move the player based on the numpad key pressed
			if (keyCode == KeyEvent.VK_NUMPAD7)
				player.move(TiledSprite.NW); // move north west
			else if (keyCode == KeyEvent.VK_NUMPAD9)
				player.move(TiledSprite.NE); // north east
			else if (keyCode == KeyEvent.VK_NUMPAD3)
				player.move(TiledSprite.SE); // south east
			else if (keyCode == KeyEvent.VK_NUMPAD1)
				player.move(TiledSprite.SW); // south west
			else if (keyCode == KeyEvent.VK_NUMPAD5)
				player.standStill(); // stand still
			else if (keyCode == KeyEvent.VK_NUMPAD2)
				player.tryPickup(); // try to pick up from this tile
		}*/

	} // end of processKey()

}
