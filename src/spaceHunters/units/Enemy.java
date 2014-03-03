/**
 * 
 */
package spaceHunters.units;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import spaceHunters.items.Suit;
import spaceHunters.items.Weapon;

/**
 * @author Nathan
 * @version Apr 6, 2013
 */
public class Enemy extends Unit {
	private Image image;

	/**
	 * @param moveDist
	 * @param blasterlv
	 * @param swordlv
	 * @param repairlv
	 * @param dodgelv
	 * @param psioniclv
	 * @param blasterA
	 * @param swordA
	 * @param repairA
	 * @param dodgeA
	 * @param psionicA
	 */
	public Enemy(String name, int moveDist, int blasterlv, int swordlv, int repairlv,
			int dodgelv, int psioniclv, Weapon weapon, Suit suit, String sprite) {
		super(name, moveDist, blasterlv, swordlv, repairlv, dodgelv, psioniclv, 0,
				0, 0, 0, 0, weapon, suit);
		image = Toolkit.getDefaultToolkit().getImage(getClass().getResource(sprite));
	}
	
	public void paint(Graphics2D g, int disX, int disY, int xloc, int yloc) {

		super.paint(g, disX, 6+disY, xloc, yloc, image);
		super.paint(g, disX, disY, xloc, yloc);
	}
}
