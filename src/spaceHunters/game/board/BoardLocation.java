/**
 * 
 */
package spaceHunters.game.board;

/**
 * @author Nathan
 * @version Apr 6, 2013
 */
public class BoardLocation {
	private int x, y;
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	private int distanceGone;
	private int aiRanking;
	private int value;
	private BoardLocation parent;

	public BoardLocation(int x, int y) {
		this.x = x;
		this.y = y;
		distanceGone = 0;
		aiRanking = 0;
	}

	/**
	 * @param x
	 * @param y
	 * @param object
	 */
	public BoardLocation(int x, int y, int distanceGone) {
		this.x = x;
		this.y = y;
		this.distanceGone = distanceGone;
		aiRanking = 0;
	}
	
	/**
	 * @param x
	 * @param y
	 * @param object
	 */
	public BoardLocation(int x, int y, int distanceGone, int estToGo, BoardLocation parent) {
		this.x = x;
		this.y = y;
		this.value = distanceGone + estToGo;
		this.distanceGone = distanceGone;
		this.parent = parent;
		aiRanking = 0;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	
	public boolean isSame(BoardLocation other) {
		return other.getX() == x && other.getY() == y;
	}
	
	public int getGone() {
		return distanceGone;
	}
	
	/**
	 * @return the aiRanking
	 */
	public int getAiRanking() {
		return aiRanking;
	}

	/**
	 * @param aiRanking the aiRanking to set
	 */
	public void setAiRanking(int aiRanking) {
		this.aiRanking = aiRanking;
	}

	/**
	 * @return
	 */
	public int getVal() {
		return value;
	}

	/**
	 * @return
	 */
	public BoardLocation getParent() {
		return parent;
	}
}
