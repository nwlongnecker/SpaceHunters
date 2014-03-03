/**
 * 
 */
package spaceHunters.units.skills;

/**
 * @author Nathan
 * @version Apr 6, 2013
 */
public class Skill {

	int exp;
	int level;
	public static final int maxExp=100;
	
	public Skill(int lv) {
		level=lv;
		exp=0;
		
	}
	public void gainExp(int Gain, double affinity) {
		exp = (int)(Gain * 100/(100.0+Math.pow(level, 2)) * affinity);
		while(exp>=maxExp) {
			goToLevel(level+1);
			exp-=maxExp;
		}
	}
	
	private void goToLevel(int lv) {
		level=lv;
	}
	
	public int getLevel() {
		return level;
	}
	public int getExp() {
		return exp;
	}
}

