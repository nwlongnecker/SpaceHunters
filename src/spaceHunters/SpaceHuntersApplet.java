/**
 * 
 */
package spaceHunters;

import java.applet.Applet;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.Timer;

import spaceHunters.game.Game;

/**
 * @author Nathan Longnecker
 * @version Apr 5, 2013
 */
public class SpaceHuntersApplet extends Applet implements MouseListener, MouseMotionListener, ActionListener {
	
	public static Font standardFont;
	public static Font standardBoldFont;
	public static int winX;
	public static int winY;
	private SpaceHunterState currentState;
	public static int squareSize = 50;
	private Timer t;
	public static boolean isScreenDirty;
	
	public void init(int winX, int winY) {
		SpaceHuntersApplet.winX = winX;
		SpaceHuntersApplet.winY = winY;
		String standardFontName = "Verdana";
		standardFont= new Font(standardFontName, Font.PLAIN, 14);
		standardBoldFont= new Font(standardFontName, Font.BOLD, 14);
		
		//resize(winX+1, winY+1);
		addMouseListener(this);
		addMouseMotionListener(this);
		currentState = new Game();
    	t = new Timer(17, this);
    	t.start();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		currentState.mouseClicked(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {	}
	@Override
	public void mouseExited(MouseEvent e) {	}
	@Override
	public void mousePressed(MouseEvent e) {	
		currentState.mousePressed(e);
	}
	@Override
	public void mouseReleased(MouseEvent e) {	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		currentState.mouseDragged(e);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		currentState.mouseMoved(e);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		currentState.actionPerformed(e);
		if(isScreenDirty) {
			repaint();
			isScreenDirty = false;
		}
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		currentState.paint(g2);
	}
	
	//Double buffering:
    public void update (Graphics g)
    {
        if (dbImage == null)
        {
            dbImage = createImage (this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics ();
        }
        // clear screen in background
        dbg.setColor (getBackground ());
        dbg.fillRect (0, 0, this.getSize().width, this.getSize().height);
        // draw elements in background
        dbg.setColor (getForeground());
        paint (dbg);
        // draw image on the screen
        g.drawImage (dbImage, 0, 0, this);
    }
    private Image dbImage;
    private Graphics dbg;
    //END DoubleBuffering.	
}
