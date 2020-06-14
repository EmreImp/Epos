package org.aurora.epos.test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.aurora.epos.utils.PerlinNoise;

public class PerlinFrameTest extends JFrame {

	private static final long serialVersionUID = 1421432824068017310L;
	BufferedImage image;
	float[][]noise = new float[0][0];

	public PerlinFrameTest() {
        initUI();
        generateLanscape();
        paintLandscape();
    }
	
    private void initUI() {
        setTitle("Perlin Test Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Add components here...
        image = new BufferedImage(300,200, BufferedImage.TYPE_INT_ARGB);
        //add(new Surface());
        setSize(300,200);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	PerlinFrameTest perlinTest = new PerlinFrameTest();
            	perlinTest.setVisible(true);
            }
        });
    }
    
    void generateLanscape() {
    	PerlinNoise pg = new PerlinNoise(0, 0);
    	int seed=new Random().nextInt();
		noise = pg.GeneratePerlinNoise(pg.GenerateWhiteNoise(300, 300, seed), 5);    
	}
    
    void paintLandscape() {
    	Color col=null;
    	for (int y=0; y<200; y++)
    		for (int x=0; x<300; x++) {
    			int value=(int)(noise[x][y]*10.0);
    			switch (value) {
    			case 0:
    				col=new Color(0,0,0);
    				break;
    			case 1:
    				col=new Color(0,0,50);
    				break;
    			case 2:
    				col=new Color(0,0,100);
    				break;
    			case 3:
    				col=new Color(0,0,150);
    				break;
    			case 4:
    				col=new Color(0,0,200);
    				break;
    			case 5:
    				col=new Color(0,200,0);
    				break;
    			case 6:
    				col=new Color(0,150,0);
    				break;
    			case 7:
    				col=new Color(100,100,0);
    				break;
    			case 8:
    				col=new Color(100,50,0);
    				break;
    			case 9:
    				col=new Color(200,200,200);
    				break;
    			}
    			image.setRGB(x, y, col.getRGB());
    		}
    	this.repaint();
    }

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(image, 0, 0, 300, 200, 0, 0, 300, 200, null);
	}
    
    
}
