/**
 * 
 */
package spaceHunters.items;

/**
 * @author Nathan
 * @version Apr 6, 2013
 */
public class Weapon {
	private String name;
	private int damage;
	private int range;
	private int accuracy;
	private int damageVariation;

	/**
	 * @param i
	 * @param j
	 * @param k
	 * @param string
	 */
	public Weapon(int damage, int damageVariation, int range, int accuracy, String name) {
		this.name = name;
		this.damage = damage;
		this.range = range;
		this.accuracy = accuracy;
		this.damageVariation = damageVariation;
	}

	/**
	 * @return
	 */
	public int getAccuracy() {
		return accuracy;
	}

	/**
	 * @return
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * @return
	 */
	public int getRange() {
		return range;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public int getDamageVariation() {
		return damageVariation;
	}

}
