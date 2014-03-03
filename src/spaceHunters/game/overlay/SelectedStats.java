/**
 * 
 */
package spaceHunters.game.overlay;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import spaceHunters.SpaceHuntersApplet;
import spaceHunters.units.Unit;

/**
 * @author Nathan
 * @version Apr 6, 2013
 */
public class SelectedStats {

	private boolean showInfo;
	private int infoLocX, infoLocY;
	private int infoWidth, infoHeight;
	
	public SelectedStats() {
		showInfo=true;
		infoLocX=10; infoLocY=SpaceHuntersApplet.winY-120;
		infoWidth=255; infoHeight=110;
	}
	/**
	 * @param g
	 * @param selected
	 */
	public void paint(Graphics2D g, Unit selected) {
		if(showInfo && selected != null) {
			Color c=g.getColor();
			g.setColor(Color.white);
			g.fillRect(infoLocX, infoLocY, infoWidth, infoHeight);
			g.setColor(Color.red);
			double percentHealth = (1.0*selected.getHealth())/selected.getMaxHealth();
			g.fillRect(infoLocX, infoLocY, (int)(infoWidth*percentHealth), 10);
			
			g.setColor(Color.black);
			g.setFont(SpaceHuntersApplet.standardBoldFont);
			g.drawString(""+selected.getName(), infoLocX+10, infoLocY+25);
			g.setFont(SpaceHuntersApplet.standardFont);
			
			g.drawString("Damage: "+selected.getDamage() , infoLocX+10, infoLocY+40);
			g.drawString("Hit Chance: "+(int)selected.getHitChance() , infoLocX+10, infoLocY+55);
			g.drawString("Health: "+selected.getHealth() , infoLocX+10, infoLocY+70);
			g.drawString(selected.getWeapon().getName() , infoLocX+10, infoLocY+85);
			g.drawString(selected.getSuit().getName(), infoLocX+10, infoLocY+100);
			
			g.setFont(SpaceHuntersApplet.standardBoldFont);
			g.drawString("Skills" , infoLocX+130, infoLocY+25);
			g.setFont(SpaceHuntersApplet.standardFont);
			
			g.drawString("Blasters: "+selected.getBlaster().getLevel() , infoLocX+130, infoLocY+40);
			g.drawString("Melee: "+selected.getSword().getLevel() , infoLocX+130, infoLocY+55);
			g.drawString("Repair: 	"+selected.getRepair().getLevel() , infoLocX+130, infoLocY+70);
			g.drawString("Dodge: "+selected.getDodge().getLevel() , infoLocX+130, infoLocY+85);
			g.drawString("Psionic: "+selected.getPsionic().getLevel(), infoLocX+130, infoLocY+100);
			g.setColor(c);
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		int y = e.getY();
    	int x = e.getX();
    	boolean previousShowInfo = showInfo;
		if(infoLocX < x && x < infoLocX + infoWidth && infoLocY < y && y < infoLocY + infoHeight) {
			showInfo = false;
		} else {
			showInfo = true;
		}
		if(showInfo != previousShowInfo) {
			SpaceHuntersApplet.isScreenDirty = true;
		}
	}

}
