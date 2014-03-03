/**
 * 
 */
package start;

import spaceHunters.SpaceHuntersApplet;

/**
 * @author Nathan
 * @version Apr 11, 2013
 */
public class StartClass {
	public static void main(String [] args)
	{
		int winX=1000;
		int winY=650;
		// create an object of type CaesarCode which is the main applet class
		SpaceHuntersApplet applet = new SpaceHuntersApplet();
		applet.init(winX, winY);   // invoke the applet's init() method
		applet.start();  // starts the applet
		
		// Create a window (JFrame) and make applet the content pane.
		javax.swing.JFrame window = new javax.swing.JFrame("SpaceHunters");
		window.setContentPane(applet);
		window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		window.setSize(winX+14, winY+38);              // Resize
		window.setVisible(true);    // Make the window visible.
	}
}