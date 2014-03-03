package spaceHunters.units;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import spaceHunters.SpaceHuntersApplet;
import spaceHunters.game.AttackRecord;
import spaceHunters.game.board.BoardLocation;
import spaceHunters.items.Suit;
import spaceHunters.items.Weapon;
import spaceHunters.units.skills.Skill;


public abstract class Unit {
	
	private int moveDistance;
	private Weapon weapon;
	private Suit suit;
	private String name;
	
	private Skill blaster;
	private Skill sword;
	private Skill repair;
	private Skill dodge;
	private Skill psionic;
	private double blasterAffinity;
	private double swordAffinity;
	private double repairAffinity;
	private double dodgeAffinity;
	@SuppressWarnings("unused")
	private double psionicAffinity;
	
	private boolean usedAction;
	private int distanceMoved;
	protected ArrayList<BoardLocation> moveLocs;
	protected int moveCount;
	private int moveRate;

	public Unit(String n, int moveDist, int blasterlv, int swordlv, int repairlv, int dodgelv, int psioniclv,
			double blasterA, double swordA, double repairA, double dodgeA, double psionicA, Weapon weapon, Suit suit) {
		moveDistance=moveDist;
		blaster = new Skill(blasterlv);
		sword = new Skill(swordlv);
		repair = new Skill(repairlv);
		dodge = new Skill(dodgelv);
		psionic = new Skill(psioniclv);
		blasterAffinity=blasterA;
		swordAffinity=swordA;
		repairAffinity=repairA;
		dodgeAffinity=dodgeA;
		psionicAffinity=psionicA;
		name = n;
		this.suit = suit;
		this.weapon = weapon;
		moveLocs = new ArrayList<BoardLocation>();
		moveRate = 2;
	}
	public Weapon getWeapon() {
		return weapon;
	}
	public Suit getSuit() {
		return suit;
	}
	public String getName() {
		return name;
	}
	
	public void newTurn() {
		usedAction=false;
		distanceMoved=0;
	}
	public double getHealthPercent() {
		return 1.0*suit.getHealth()/suit.getMaxHealth();
	}
	public int getHealth() {
		return suit.getHealth();
	}
	public int getMaxHealth() {
		return suit.getMaxHealth();
	}
	public boolean usedAction() {
		return usedAction;
	}
	public void doNothing() {
		usedAction=true;
	}
	public double getDodgeChance() {
		return 20-(20-suit.getDodgeChance())*10/(10.0+dodge.getLevel());
	}
	public double getHitChance() {
		if(weapon.getRange() == 0) {
			return 100-(100-weapon.getAccuracy())*10/(10.0+sword.getLevel());
		} else {
			return 100-(100-weapon.getAccuracy())*10/(10.0+blaster.getLevel());
		}
	}
	public int getDamage() {
		if(weapon.getRange() == 0) {
			return weapon.getDamage()*(10+sword.getLevel())/10;
		} else {
			return weapon.getDamage()*(10+blaster.getLevel())/10;
		}
	}
	/**
	 * @return
	 */
	private int getDamageVariation() {
		return weapon.getDamageVariation();
	}
	public int getRepairAmount() {
		return 2+repair.getLevel();
	}
	public double getRepairSuccessChance() {
		return 100-(100-70)*10/(10.0+repair.getLevel());
	}
	
	public int getRange() {
		return weapon.getRange();
	}
	public int getMoveableDistance() {
		return moveDistance-distanceMoved;
	}
	public void addDistanceMoved(int moved) {
		distanceMoved += moved;
	}

	public void attack(Unit target, AttackRecord record, int terrainModifier) {
		if(target.receiveAttack(getDamage(), getDamageVariation(), getHitChance(), record, terrainModifier)) {
			if(weapon.getRange() == 0) {
				sword.gainExp(50, swordAffinity);
			} else {
				blaster.gainExp(50, blasterAffinity);
			}
		} else {
			if(weapon.getRange() == 0) {
				sword.gainExp(10, swordAffinity);
			} else {
				blaster.gainExp(10, blasterAffinity);
			}
		}
		usedAction=true;
	}
	
	public boolean receiveAttack(int damage, int damageVariation, double hitChance, AttackRecord record, int terrainModifier) {
		double rand = 100*Math.random();
		dodge.gainExp(20, dodgeAffinity);
		if(rand<hitChance && rand>getDodgeChance()) {
			double damageRand = (Math.random() - 0.5) * 2;
			int damageDealt = (int)((((damageRand * damageVariation) + damage) * (100 - terrainModifier))/ 100);
			record.setMessageString("HIT! " + damageDealt);
			record.setMessageColor(Color.red);
			suit.takeDamage(damageDealt);
			return true;
		} else {
			record.setMessageString("MISS!");
			record.setMessageColor(Color.blue);
			return false;
		}
	}
	
	public void repair(Unit target, AttackRecord record) {
		if(target.receiveRepair(getRepairAmount(), getRepairSuccessChance(), record)) {
			repair.gainExp(75, repairAffinity);
		}
		usedAction=true;
	}
	
	public boolean receiveRepair(int amount, double successChance, AttackRecord record) {
		double rand = 100*Math.random();
		if(rand<successChance) {
			if(suit.repairDamage(amount)) {
				record.setMessageString("REPAIR! "+amount);
				record.setMessageColor(Color.green);
			} else {
				record.setMessageString("FULL HEALTH!");
				record.setMessageColor(Color.green);
			}
			return true;
		} else {
			record.setMessageString("FAIL!");
			record.setMessageColor(Color.blue);
			return false;
		}
	}
	
	public boolean isDead() {
		if(suit.getHealth()>0) {
			return false;
		} else {
			return true;
		}
	}
	
	public Weapon equipW(Weapon w) {
		Weapon old = weapon;
		weapon = w;
		return old;
	}
	public Suit equipS(Suit s) {
		Suit old = suit;
		suit = s;
		return old;
	}

	public void paint(Graphics2D g, int disX, int disY, int xloc, int yloc) {
		Color c=g.getColor();
		g.setColor(Color.red);
		double percentHealth = (1.0*getHealth())/getMaxHealth();
		if(moveLocs.size() == 0) {
			g.fillRect(disX+xloc*SpaceHuntersApplet.squareSize+1, disY+yloc*SpaceHuntersApplet.squareSize, (int)((SpaceHuntersApplet.squareSize-2)*percentHealth), 5);
			if(usedAction()) {
				g.setColor(Color.cyan.darker());
				g.fillOval(disX + xloc * SpaceHuntersApplet.squareSize, disY + yloc*SpaceHuntersApplet.squareSize, 15, 15);
			}
		}
		else {
			if(moveLocs.get(0).getParent() != null) {
				if(moveLocs.get(0).getParent().getX() == moveLocs.get(0).getX()+1) {
					g.fillRect(moveRate*moveCount + disX + moveLocs.get(0).getX() * SpaceHuntersApplet.squareSize + 1, disY + moveLocs.get(0).getY()*SpaceHuntersApplet.squareSize, (int)((SpaceHuntersApplet.squareSize-2)*percentHealth), 5);
				}
				else if(moveLocs.get(0).getParent().getX() == moveLocs.get(0).getX()-1) {
					g.fillRect(disX + moveLocs.get(0).getX() * SpaceHuntersApplet.squareSize + 1 - moveRate*moveCount, disY + moveLocs.get(0).getY()*SpaceHuntersApplet.squareSize, (int)((SpaceHuntersApplet.squareSize-2)*percentHealth), 5);
				}
				else if(moveLocs.get(0).getParent().getY() == moveLocs.get(0).getY()+1) {
					g.fillRect(disX + moveLocs.get(0).getX() * SpaceHuntersApplet.squareSize + 1, disY + moveLocs.get(0).getY()*SpaceHuntersApplet.squareSize + moveRate*moveCount, (int)((SpaceHuntersApplet.squareSize-2)*percentHealth), 5);
				}
				else if(moveLocs.get(0).getParent().getY() == moveLocs.get(0).getY()-1) {
					g.fillRect(disX + moveLocs.get(0).getX() * SpaceHuntersApplet.squareSize + 1, disY + moveLocs.get(0).getY()*SpaceHuntersApplet.squareSize - moveRate*moveCount, (int)((SpaceHuntersApplet.squareSize-2)*percentHealth), 5);
				}
			}
			else {
				g.fillRect(disX+xloc*SpaceHuntersApplet.squareSize+1, disY+yloc*SpaceHuntersApplet.squareSize, (int)((SpaceHuntersApplet.squareSize-2)*percentHealth), 5);
			}
		}
		g.setColor(c);
	}
	/**
	 * @return the blaster
	 */
	public Skill getBlaster() {
		return blaster;
	}
	/**
	 * @return the sword
	 */
	public Skill getSword() {
		return sword;
	}
	/**
	 * @return the repair
	 */
	public Skill getRepair() {
		return repair;
	}
	/**
	 * @return the dodge
	 */
	public Skill getDodge() {
		return dodge;
	}
	/**
	 * @return the psionic
	 */
	public Skill getPsionic() {
		return psionic;
	}
	/**
	 * @return
	 */
	public boolean isPlayer() {
		return false;
	}
	/**
	 * @return the aiPriority
	 */
	public int getAiPriority() {
		return 0;
	}
	
	public void addMoveLocs(ArrayList<BoardLocation> locs) {
		moveLocs.addAll(locs);
	}

	/**
	 * 
	 */
	public void updateMovement() {
		if(moveLocs.size() > 0) {
			moveCount++;
			if(moveCount >= 25) {
				moveCount = moveCount%25;
				moveLocs.remove(0);
				if(moveLocs.size() != 0 && moveLocs.get(0).getParent() == null) {
					moveLocs.remove(0);
				}
			}
			SpaceHuntersApplet.isScreenDirty = true;
		}
		
	}
	/**
	 * @param g
	 * @param disX
	 * @param disY
	 * @param xloc
	 * @param yloc
	 * @param spaceman
	 */
	public void paint(Graphics2D g, int disX, int disY, int xloc, int yloc, Image sprite) {
		if(moveLocs.size() == 0) {
			g.drawImage(sprite, disX + xloc * SpaceHuntersApplet.squareSize, disY + yloc*SpaceHuntersApplet.squareSize, null);
		}
		else {
			if(moveLocs.get(0).getParent() != null) {
				if(moveLocs.get(0).getParent().getX() == moveLocs.get(0).getX()+1) {
					g.drawImage(sprite, moveRate*moveCount + disX + moveLocs.get(0).getX() * SpaceHuntersApplet.squareSize, disY + moveLocs.get(0).getY()*SpaceHuntersApplet.squareSize, null);
				}
				else if(moveLocs.get(0).getParent().getX() == moveLocs.get(0).getX()-1) {
					g.drawImage(sprite, disX + moveLocs.get(0).getX() * SpaceHuntersApplet.squareSize - moveRate*moveCount, disY + moveLocs.get(0).getY()*SpaceHuntersApplet.squareSize, null);
				}
				else if(moveLocs.get(0).getParent().getY() == moveLocs.get(0).getY()+1) {
					g.drawImage(sprite, disX + moveLocs.get(0).getX() * SpaceHuntersApplet.squareSize, disY + moveLocs.get(0).getY()*SpaceHuntersApplet.squareSize + moveRate*moveCount, null);
				}
				else if(moveLocs.get(0).getParent().getY() == moveLocs.get(0).getY()-1) {
					g.drawImage(sprite, disX + moveLocs.get(0).getX() * SpaceHuntersApplet.squareSize, disY + moveLocs.get(0).getY()*SpaceHuntersApplet.squareSize - moveRate*moveCount, null);
				}
			}
			else {
				g.drawImage(sprite, disX + xloc * SpaceHuntersApplet.squareSize, disY + yloc*SpaceHuntersApplet.squareSize, null);
			}
		}
	}
}
