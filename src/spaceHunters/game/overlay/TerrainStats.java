/**
 * 
 */
package spaceHunters.game.overlay;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import spaceHunters.SpaceHuntersApplet;
import spaceHunters.game.board.TerrainType;

/**
 * @author Nathan
 * @version Apr 6, 2013
 */
public class TerrainStats {
	private int statsLocX, statsLocY;
	private int statsWidth, statsHeight;
	private boolean showStats;
	
	public TerrainStats() {
		statsLocX = SpaceHuntersApplet.winX - 131; statsLocY = 10;
		statsWidth = 121; statsHeight = 44;
	}
	/**
	 * @param g
	 */
	public void paint(Graphics2D g, TerrainType terrainSelected) {
		if(showStats) {
			Color c=g.getColor();
			g.setColor(Color.white);
			g.fillRect(statsLocX, statsLocY, statsWidth, statsHeight);
			g.setColor(Color.black);
			g.setFont(SpaceHuntersApplet.standardBoldFont);
			if(terrainSelected == TerrainType.CAVE_FLOOR) {
				g.drawString("Cave Floor", statsLocX+10, statsLocY+20);
				g.drawString("0% defense", statsLocX+10, statsLocY+35);
			}
			else if(terrainSelected == TerrainType.ROCKS) {
				g.drawString("Rocks", statsLocX+10, statsLocY+20);
				g.drawString("Impassable", statsLocX+10, statsLocY+35);
			}
			else if(terrainSelected == TerrainType.ROUGH) {
				g.drawString("Rough", statsLocX+10, statsLocY+20);
				g.drawString("30% defense", statsLocX+10, statsLocY+35);
			}
			g.setFont(SpaceHuntersApplet.standardFont);
			g.setColor(c);
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		int y = e.getY();
    	int x = e.getX();
    	boolean previousShowStats = showStats;
		if(statsLocX < x && x < statsLocX + statsWidth && statsLocY < y && y < statsLocY + statsHeight) {
			showStats = false;
		} else {
			showStats = true;
		}
		if(showStats != previousShowStats) {
			SpaceHuntersApplet.isScreenDirty = true;
		}
	}
}
