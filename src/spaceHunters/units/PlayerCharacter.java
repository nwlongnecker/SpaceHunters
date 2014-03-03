package spaceHunters.units;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import spaceHunters.items.Suit;
import spaceHunters.items.Weapon;


public class PlayerCharacter extends Unit {
	private Image Spaceman;
	private int aiPriority;

	public PlayerCharacter(String n, int moveDist, int blasterlv, int swordlv, int repairlv, int dodgelv, int psioniclv,
			double blasterA, double swordA, double repairA, double dodgeA, double psionicA, int aiPriority, Weapon weapon, Suit suit, String sprite) {
		super(n, moveDist, blasterlv, swordlv, repairlv, dodgelv, psioniclv,
				blasterA, swordA, repairA, dodgeA, psionicA, weapon, suit);
		Spaceman = Toolkit.getDefaultToolkit().getImage(getClass().getResource(sprite));
		this.aiPriority = aiPriority;
	}
	
	public boolean isCharacter() { return true;	}
	
	public void paint(Graphics2D g, int disX, int disY, int xloc, int yloc) {
		super.paint(g, disX, disY, xloc, yloc, Spaceman);
		super.paint(g, disX, disY, xloc, yloc);
	}

	/* (non-Javadoc)
	 * @see spaceHunters.units.Unit#isPlayer()
	 */
	@Override
	public boolean isPlayer() {
		return true;
	}
	
	/**
	 * @return the aiPriority
	 */
	public int getAiPriority() {
		return aiPriority;
	}
}