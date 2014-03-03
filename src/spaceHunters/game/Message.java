/**
 * 
 */
package spaceHunters.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import spaceHunters.SpaceHuntersApplet;

/**
 * @author Nathan Longnecker
 * @version Apr 6, 2013
 */

public class Message {

	private int length;
	private String message;
	private int x, y;
	private Color color;
	boolean isDispDependent;
	
	public Message(String m, Color c, int xloc, int yloc, boolean isLocDep){
		//There are 60 events per second: length/60 = Sec
		length=100;
		message=m;
		color=c;
		x=xloc*SpaceHuntersApplet.squareSize;
		y=yloc*SpaceHuntersApplet.squareSize;
		isDispDependent=isLocDep;
	}
	
	public void countDown() {
		length--;
	}
	
	public boolean isDead() {
		if(length>0) {
			return false;
		} else {
			return true;
		}
	}

	public void drawMessage(Graphics2D g, int disX, int disY) {
		Color c=g.getColor();
		g.setColor(color);
		if(isDispDependent){
			g.drawString(message, disX+x, disY+y);
		} else {
			g.setFont(new Font("Verdana", Font.BOLD, 26));
			g.drawString(message, x, y);
			g.setFont(SpaceHuntersApplet.standardFont);
		}
		g.setColor(c);
	}
}
