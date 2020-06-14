package org.aurora.epos.test;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class GameCore extends JPanel implements ActionListener 
{

	private static final long serialVersionUID = 1L;
	private static final int PWIDTH = 800;
	private static final int PHEIGHT = 600;
	private boolean isRunning;


	public GameCore()
	{
		setBackground(Color.black);
		setPreferredSize( new Dimension(PWIDTH, PHEIGHT) );
	}


	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.white);
		g.fillRect (0, 0, PWIDTH, PHEIGHT);
	} 


	public static void main(String args[])
	{

		GameCore ttPanel = new GameCore();

		// create a JFrame to hold the timer test JPanel
		JFrame app = new JFrame("Game Core");
		app.getContentPane().add(ttPanel, BorderLayout.CENTER);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		app.pack();
		app.setResizable(false);  
		app.setVisible(true);
	} // end of main()


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}


}
