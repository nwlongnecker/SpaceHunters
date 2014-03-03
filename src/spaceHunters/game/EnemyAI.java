/**
 * 
 */
package spaceHunters.game;

import java.util.ArrayList;

import spaceHunters.game.board.Board;
import spaceHunters.game.board.BoardAlgorithmsHelper;
import spaceHunters.game.board.BoardLocation;
import spaceHunters.units.Unit;

/**
 * @author Nathan
 * @version Apr 9, 2013
 */
public class EnemyAI {

	/**
	 * @param board
	 * @param gameState
	 */
	public static void takeTurn(Board board, GameState gameState) {
		//Find the enemies on the battlefield
		ArrayList<Unit> enemies = new ArrayList<Unit>();
		ArrayList<BoardLocation> enemyLocations = new ArrayList<BoardLocation>();
		for(int c = 0; c < gameState.getUnits().length; c++) {
			for(int r = 0; r < gameState.getUnits()[0].length; r++) {
				if(gameState.getUnits()[c][r] != null && !gameState.getUnits()[c][r].isPlayer()) {
					enemies.add(gameState.getUnits()[c][r]);
					enemyLocations.add(new BoardLocation(c, r));
				}
			}
		}
		//For each enemy, do this...
		for(int i = 0; i < enemies.size(); i++) {
			doMove(board, gameState, enemyLocations.get(i));
			doAttack(board, gameState, enemyLocations.get(i));
			enemies.get(i).newTurn();
		}
	}

	/**
	 * @param board
	 * @param gameState
	 * @param x
	 * @param y
	 */
	private static void doAttack(Board board, GameState gameState, BoardLocation loc) {
		ArrayList<BoardLocation> possibleAttackableLocs = BoardAlgorithmsHelper.getAttackableLocs(loc.getX(), loc.getY(), gameState.getUnits()[loc.getX()][loc.getY()].getRange(), gameState, board);
		
		if(possibleAttackableLocs.size() == 0)
			return;
		
		for(int i = 0; i < possibleAttackableLocs.size(); i++) {
			Unit currentUnit = gameState.getUnits()[possibleAttackableLocs.get(i).getX()][possibleAttackableLocs.get(i).getY()];
			if(currentUnit != null) {
				int priority = currentUnit.getAiPriority();
				int value = ((currentUnit.getHealth()-gameState.getUnits()[loc.getX()][loc.getY()].getDamage())/priority) + currentUnit.getHealth();
				possibleAttackableLocs.get(i).setAiRanking(value);
			}
			else {
				possibleAttackableLocs.remove(i);
				i--;
			}
		}
		
		if(possibleAttackableLocs.size() == 0)
			return;
		
		int indexOfBestAttackLoc = 0;
		for(int i = 0; i < possibleAttackableLocs.size(); i++) {
			if(possibleAttackableLocs.get(i).getAiRanking() < possibleAttackableLocs.get(indexOfBestAttackLoc).getAiRanking()) {
				indexOfBestAttackLoc = i;
			}
		}
		AttackRecord record = new AttackRecord();
		gameState.getUnits()[loc.getX()][loc.getY()].attack(gameState.getUnits()[possibleAttackableLocs.get(indexOfBestAttackLoc).getX()][possibleAttackableLocs.get(indexOfBestAttackLoc).getY()], record, board.getTerrainModifier(possibleAttackableLocs.get(indexOfBestAttackLoc).getX(), possibleAttackableLocs.get(indexOfBestAttackLoc).getY()));
		gameState.addMessage(new Message(record.getMessageString(), record.getMessageColor(), possibleAttackableLocs.get(indexOfBestAttackLoc).getX(), possibleAttackableLocs.get(indexOfBestAttackLoc).getY(), true));
		//Check if the attack killed the unit
		if(gameState.getUnits()[possibleAttackableLocs.get(indexOfBestAttackLoc).getX()][possibleAttackableLocs.get(indexOfBestAttackLoc).getY()].isDead()) {
			gameState.getUnits()[possibleAttackableLocs.get(indexOfBestAttackLoc).getX()][possibleAttackableLocs.get(indexOfBestAttackLoc).getY()] = null;
		}
	}

	/**
	 * @param board
	 * @param gameState
	 * @param x
	 * @param y
	 */
	private static void doMove(Board board, GameState gameState, BoardLocation loc) {
		ArrayList<BoardLocation> possibleMoveLocs = BoardAlgorithmsHelper.getPossibleMoveLocs(loc.getX(), loc.getY(), gameState.getUnits()[loc.getX()][loc.getY()].getMoveableDistance(), gameState, board);
		
		if(possibleMoveLocs.size() == 0) {
			return;
		}
		
		for(int i = 0; i < possibleMoveLocs.size(); i++) {
			int locationValue = 0;
			//Add for adjacent rocks and allies
			if(possibleMoveLocs.get(i).getX()+1 < board.getWidth()) {
				if(!board.isPassable(possibleMoveLocs.get(i).getX()+1, possibleMoveLocs.get(i).getY())) {
					locationValue += 15;
				}
				if(gameState.getUnits()[possibleMoveLocs.get(i).getX()+1][possibleMoveLocs.get(i).getY()] != null && !gameState.getUnits()[possibleMoveLocs.get(i).getX()+1][possibleMoveLocs.get(i).getY()].isPlayer()) {
					locationValue += 20;
				}
			}
			if(0 <= possibleMoveLocs.get(i).getX()-1) {
				if(!board.isPassable(possibleMoveLocs.get(i).getX()-1,possibleMoveLocs.get(i).getY())) {
					locationValue += 15;
				}
				if(gameState.getUnits()[possibleMoveLocs.get(i).getX()-1][possibleMoveLocs.get(i).getY()] != null && !gameState.getUnits()[possibleMoveLocs.get(i).getX()-1][possibleMoveLocs.get(i).getY()].isPlayer()) {
					locationValue += 20;
				}
			}
			if(possibleMoveLocs.get(i).getY()+1 < board.getHeight()) {
				if(!board.isPassable(possibleMoveLocs.get(i).getX(),possibleMoveLocs.get(i).getY()+1)) {
					locationValue += 15;
				}
				if(gameState.getUnits()[possibleMoveLocs.get(i).getX()][possibleMoveLocs.get(i).getY()+1] != null && !gameState.getUnits()[possibleMoveLocs.get(i).getX()][possibleMoveLocs.get(i).getY()+1].isPlayer()) {
					locationValue += 20;
				}
			}
			if(0 <= possibleMoveLocs.get(i).getY()-1) {
				if(!board.isPassable(possibleMoveLocs.get(i).getX(),possibleMoveLocs.get(i).getY()-1)) {
					locationValue += 15;
				}
				if(gameState.getUnits()[possibleMoveLocs.get(i).getX()][possibleMoveLocs.get(i).getY()-1] != null && !gameState.getUnits()[possibleMoveLocs.get(i).getX()][possibleMoveLocs.get(i).getY()-1].isPlayer()) {
					locationValue += 20;
				}
			}
			int terrainModifier = board.getTerrainModifier(possibleMoveLocs.get(i).getX(), possibleMoveLocs.get(i).getY());
			locationValue += terrainModifier / 10;
			
			//add for enemy in range
			ArrayList<BoardLocation> attackableLocs = BoardAlgorithmsHelper.getPossibleAttackableLocs(gameState.getUnits()[loc.getX()][loc.getY()], possibleMoveLocs.get(i).getX(), possibleMoveLocs.get(i).getY(), gameState.getUnits()[loc.getX()][loc.getY()].getRange(), gameState, board);
			boolean isEnemyInRange = false;
			for(int j = 0; j < attackableLocs.size(); j++) {
				if(gameState.getUnits()[attackableLocs.get(j).getX()][attackableLocs.get(j).getY()] != null && gameState.getUnits()[attackableLocs.get(j).getX()][attackableLocs.get(j).getY()].isPlayer()) {
					isEnemyInRange = true;
				}
			}
			if(isEnemyInRange) {
				locationValue += 100;
			}
			
			possibleMoveLocs.get(i).setAiRanking(locationValue);
		}
		
		int indexOfBestMoveLoc = 0;
		for(int i = 0; i < possibleMoveLocs.size(); i++) {
			if(possibleMoveLocs.get(i).getAiRanking() >= possibleMoveLocs.get(indexOfBestMoveLoc).getAiRanking()) {
				indexOfBestMoveLoc = i;
			}
		}
		Unit moveUnit = gameState.getUnits()[loc.getX()][loc.getY()];
		gameState.getUnits()[loc.getX()][loc.getY()] = null;
    	gameState.getUnits()[possibleMoveLocs.get(indexOfBestMoveLoc).getX()][possibleMoveLocs.get(indexOfBestMoveLoc).getY()] = moveUnit;
    	moveUnit.addMoveLocs(BoardAlgorithmsHelper.findPathFromAToB(board, possibleMoveLocs.get(indexOfBestMoveLoc).getX(), possibleMoveLocs.get(indexOfBestMoveLoc).getY(),loc.getX(), loc.getY(), gameState, gameState.getUnits()[possibleMoveLocs.get(indexOfBestMoveLoc).getX()][possibleMoveLocs.get(indexOfBestMoveLoc).getY()].isPlayer()));
    	loc.setX(possibleMoveLocs.get(indexOfBestMoveLoc).getX());
    	loc.setY(possibleMoveLocs.get(indexOfBestMoveLoc).getY());
	}
}
