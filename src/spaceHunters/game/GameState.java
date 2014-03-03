/**
 * 
 */
package spaceHunters.game;

import java.util.ArrayList;

import spaceHunters.SpaceHuntersApplet;
import spaceHunters.game.board.BoardLocation;
import spaceHunters.game.board.TerrainType;
import spaceHunters.units.Unit;

/**
 * @author Nathan Longnecker
 * @version Apr 6, 2013
 */
public class GameState {
	private Unit selected;
	private TerrainType terrain;
	private int x, y;
	private Unit[][] units;

	/**
	 * @return the units
	 */
	public Unit[][] getUnits() {
		return units;
	}

	/**
	 * @param units the units to set
	 */
	public void setUnits(Unit[][] units) {
		this.units = units;
	}

	private ArrayList<BoardLocation> enemyAttackableLocs;
	private ArrayList<BoardLocation> attackableLocs;
	private ArrayList<BoardLocation> repairableLocs;
	private ArrayList<BoardLocation> moveableLocs;
	private ArrayList<Message> messages;
	
	
	public GameState() {
		x = 0;
		y = 0;

		enemyAttackableLocs = new ArrayList<BoardLocation>();
		attackableLocs = new ArrayList<BoardLocation>();
		repairableLocs = new ArrayList<BoardLocation>();
		moveableLocs = new ArrayList<BoardLocation>();
    	messages = new ArrayList<Message>();
	}
	
	/**
	 * @return the enemyAttackableLocs
	 */
	public ArrayList<BoardLocation> getEnemyAttackableLocs() {
		return enemyAttackableLocs;
	}

	/**
	 * @param enemyAttackableLocs the enemyAttackableLocs to set
	 */
	public void setEnemyAttackableLocs(ArrayList<BoardLocation> enemyAttackableLocs) {
		this.enemyAttackableLocs = enemyAttackableLocs;
	}

	/**
	 * @return the attackableLocs
	 */
	public ArrayList<BoardLocation> getAttackableLocs() {
		return attackableLocs;
	}

	/**
	 * @param attackableLocs the attackableLocs to set
	 */
	public void setAttackableLocs(ArrayList<BoardLocation> attackableLocs) {
		this.attackableLocs = attackableLocs;
	}

	/**
	 * @return the repairableLocs
	 */
	public ArrayList<BoardLocation> getRepairableLocs() {
		return repairableLocs;
	}

	/**
	 * @param repairableLocs the repairableLocs to set
	 */
	public void setRepairableLocs(ArrayList<BoardLocation> repairableLocs) {
		this.repairableLocs = repairableLocs;
	}

	/**
	 * @return the moveableLocs
	 */
	public ArrayList<BoardLocation> getMoveableLocs() {
		return moveableLocs;
	}

	/**
	 * @param moveableLocs the moveableLocs to set
	 */
	public void setMoveableLocs(ArrayList<BoardLocation> moveableLocs) {
		this.moveableLocs = moveableLocs;
	}

	public void setSelected(Unit select) {
		selected = select;
	}
	public void setSelectedTerrain(TerrainType select) {
		terrain = select;
	}

	/**
	 * @return
	 */
	public Unit getSelected() {
		return selected;
	}
	
	public TerrainType getTerrain() {
		return terrain;
	}

	/**
	 * @param c
	 */
	public void setSelectedX(int x) {
		this.x = x;
	}

	/**
	 * @return the x
	 */
	public int getSelectedX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getSelectedY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setSelectedY(int y) {
		this.y = y;
	}

	/**
	 * 
	 */
	public void resetLocs() {
		enemyAttackableLocs.clear();
		repairableLocs.clear();
		attackableLocs.clear();
		moveableLocs.clear();
	}

	/**
	 * @return Returns whether the player is currently doing a menu action
	 */
	public boolean isDoingAction() {
		return moveableLocs.size() != 0 || attackableLocs.size() != 0 || repairableLocs.size() != 0;
	}

	/**
	 * @param boardLocation
	 * @return
	 */
	public boolean isTargetSquare(BoardLocation boardLocation) {
		boolean ret = false;
		//Check Movable Locs
		for(int i=0; i < moveableLocs.size() ; i++){
			if(boardLocation.isSame(moveableLocs.get(i))) {
				ret = true;
			}
		}
		//Check Attackable Locs
		for(int i=0; i < attackableLocs.size() ; i++){
			if(boardLocation.isSame(attackableLocs.get(i))) {
				ret = true;
			}
		}
		//Check Repairable Locs
		for(int i=0; i < repairableLocs.size() ; i++){
			if(boardLocation.isSame(repairableLocs.get(i))) {
				ret = true;
			}
		}
		return ret;
	}

	/**
	 * @return the messages
	 */
	public ArrayList<Message> getMessages() {
		return messages;
	}

	/**
	 * @param messages the messages to set
	 */
	public void addMessage(Message message) {
		this.messages.add(message);
	}
	
	/**
	 * Performs any time-based updates
	 */
	public void updateGameState() {
		for(int i = 0; i < messages.size(); i++) {
			messages.get(i).countDown();
			if(messages.get(i).isDead()) {
				messages.remove(i);
				i--;
				SpaceHuntersApplet.isScreenDirty = true;
			}
		}
	}
}
