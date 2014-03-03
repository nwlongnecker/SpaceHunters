/**
 * 
 */
package spaceHunters;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

/**
 * @author Nathan Longnecker
 * @version Apr 5, 2013
 */
public interface SpaceHunterState {;
	public void mouseClicked(MouseEvent arg0);
	public void mouseEntered(MouseEvent arg0);
	public void mouseExited(MouseEvent arg0);
	public void mousePressed(MouseEvent arg0);
	public void mouseReleased(MouseEvent arg0);
	public void mouseDragged(MouseEvent e);
	public void mouseMoved(MouseEvent e);
	public void actionPerformed(ActionEvent e);
	public void paint(Graphics2D g);
}
