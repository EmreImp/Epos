package org.aurora.eposSINGLE;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.aurora.epos.utils.DungeonBuilder;


public class EposMain extends JPanel {

	
	private static final int DEFAULT_COMPLEXITY = 10;
	private static final int PWIDTH = 800;
	private static final int PHEIGHT = 600;
	DungeonBuilder _builder=new DungeonBuilder();

	public static void main(String[] args) {
	     int complexity = DEFAULT_COMPLEXITY;
	     if (args.length != 0)
	       complexity = Integer.parseInt(args[0]);

	     EposMain thePanel = new EposMain(10);

	     // create a JFrame to hold the timer test JPanel
	     JFrame app = new JFrame("Epic Procedures");
	     app.getContentPane().add(thePanel, BorderLayout.CENTER);
	     app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     app.pack();
	     app.setResizable(false);  
	     app.setVisible(true);
	}
	
	  public EposMain(int p)
	  {
	    setBackground(Color.white);
	    setPreferredSize( new Dimension(PWIDTH, PHEIGHT) );
	  } 

	   public void paintComponent(Graphics g)
	   {
	     super.paintComponent(g);

	     // clear the background
	     g.setColor(Color.white);
	     g.fillRect (0, 0, PWIDTH, PHEIGHT);
	    // report average FPS
		 g.setColor(Color.black);
		 g.drawString("Preparing for something big", 10, 25);

	   }


}
