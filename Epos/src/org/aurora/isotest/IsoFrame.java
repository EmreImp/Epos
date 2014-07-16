package org.aurora.isotest;

import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class IsoFrame  extends JFrame implements WindowListener {

	  private static int DEFAULT_FPS = 30;      // 40 is too fast! 

	  private IsoPanel ip; // where the game is drawn
	  //private MidisLoader midisLoader;


	  public IsoFrame(long period)
	  { super("IsoFrame");

	    Container c = getContentPane();    // default BorderLayout used
	    ip = new IsoPanel(this, period);
	    c.add(ip, "Center");

	    addWindowListener( this );
	    pack();
	    setResizable(false);
	    setVisible(true);
	  }  // end of JumpingJack() constructor


	  // ----------------- window listener methods -------------
	  public void windowActivated(WindowEvent e) 
	  { 
		  ip.resumeGame();  
	  }
	  public void windowDeactivated(WindowEvent e) 
	  { 
		  ip.pauseGame();  
	  }
	  public void windowDeiconified(WindowEvent e) 
	  {  
		  ip.resumeGame();  
	  }
	  public void windowIconified(WindowEvent e) 
	  {  
		  ip.pauseGame(); 
	  }
	  public void windowClosing(WindowEvent e)
	  {  
		  ip.stopGame();  
	  }
	  public void windowClosed(WindowEvent e) {}
	  public void windowOpened(WindowEvent e) {}

	  // ----------------------------------------------------

	  public static void main(String args[])
	  { 
	    long period = (long) 1000.0/DEFAULT_FPS;
	    new IsoFrame(period*1000000L);    // ms --> nanosecs 
	  }
	
	
}
