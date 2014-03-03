/**
 * 
 */
package spaceHunters.items;

/**
 * @author Nathan
 * @version Apr 6, 2013
 */
public class Suit {
	private String name;
	private int health;
	private int maxHealth;
	private int dodgeChance;

	/**
	 * @param i
	 * @param j
	 * @param string
	 */
	public Suit(int health, int dodgeChance, String name) {
		this.health = health;
		this.maxHealth = health;
		this.dodgeChance = dodgeChance;
		this.name = name;
	}

	/**
	 * @return
	 */
	public int getMaxHealth() {
		return maxHealth;
	}

	/**
	 * @return
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * @return
	 */
	public int getDodgeChance() {
		return dodgeChance;
	}

	/**
	 * @param damage
	 */
	public void takeDamage(int damage) {
		health -= damage;
		if(health < 0) {
			health = 0;
		}
	}

	/**
	 * @param amount
	 * @return
	 */
	public boolean repairDamage(int amount) {
		health += amount;
		if(health > maxHealth) {
			health = maxHealth;
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

}
