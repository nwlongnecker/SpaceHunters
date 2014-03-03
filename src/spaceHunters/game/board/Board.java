/**
 * 
 */
package spaceHunters.game.board;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import spaceHunters.SpaceHuntersApplet;
import spaceHunters.game.AttackRecord;
import spaceHunters.game.GameState;
import spaceHunters.game.Message;
import spaceHunters.items.beamSwords.Claws;
import spaceHunters.items.blasters.Lightning;
import spaceHunters.items.suits.HedgehogSpines;
import spaceHunters.items.suits.ZergCarapace;
import spaceHunters.units.Enemy;
import spaceHunters.units.PlayerCharacter;
import spaceHunters.units.Unit;

/**
 * @author Nathan Longnecker
 * @version Apr 5, 2013
 */
public class Board {

	private int height;
	private int width;
	private TerrainType[][] board;
	private GameState gameState;
	private Image rough;
	private Image caveFloor;
	private Image rocks;
	
	public Board(GameState gameState) {
		height=25;
		width=25;
		rough = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Rough_Cave_Floor.jpg"));
		caveFloor = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Cave_Floor.jpg"));
		rocks = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Rocks.jpg"));
		board = new TerrainType[width][height];
		for(int j=0;j<height;j++) {
        	for(int i=0;i<width;i++) {
        		double rand = Math.random();
        		if(rand >.8) {
        			board[i][j] = TerrainType.ROCKS;
        		} 
        		else if(rand > .6) {
        			board[i][j] = TerrainType.ROUGH;
        		}
        		else {
        			board[i][j] = TerrainType.CAVE_FLOOR;
        		}
        	}
		}
		Unit[][] units = new Unit[width][height];
		for(int j=0;j<height;j++) {
        	for(int i=0;i<width;i++) {
        		units[i][j]=null;
        	}
		}
		this.gameState = gameState;
		gameState.setUnits(units);
	}
	
	public void placeCharacters(ArrayList<PlayerCharacter> characters) {
		Unit[][] units = gameState.getUnits();
		units[3][5] = characters.get(0);
		units[4][5] = characters.get(1);
		units[4][4] = characters.get(2);
		units[3][6] = characters.get(3);
		units[5][3] = characters.get(4);
		units[4][3] = characters.get(5);
		board[3][5] = TerrainType.CAVE_FLOOR;
		board[4][5] = TerrainType.CAVE_FLOOR;
		board[4][4] = TerrainType.CAVE_FLOOR;
		board[3][6] = TerrainType.CAVE_FLOOR;
		board[5][3] = TerrainType.CAVE_FLOOR;
		board[4][3] = TerrainType.CAVE_FLOOR;
		
		for(int i=0; i<10; i++) {
			int xspot = (int)(12+10*Math.random());
			int yspot = (int)(12+10*Math.random());
			double rand = Math.random();
			if(rand < 0.4) {
				units[xspot][yspot] = new Enemy("Zergling", 7, 0, 4, 0, 0, 0, new Claws(), new ZergCarapace(), "/images/zergling.png");
			}
			else if(rand < 0.8) {
				units[xspot][yspot] = new Enemy("Hedgehog", 5, 0, 2, 0, 0, 0, new Claws(), new HedgehogSpines(), "/images/Hedgehog2.png");
			}
			else {
				units[xspot][yspot] = new Enemy("Pikachu", 6, 5, 0, 0, 0, 0, new Lightning(), new ZergCarapace(), "/images/Pikachu.png");
			}
			board[xspot][yspot] = TerrainType.CAVE_FLOOR;
		}

    	gameState.setSelectedTerrain(board[3][5]);
		gameState.setUnits(units);
	}

	/**
	 * @return Returns the width of the board in pixels
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return Returns the height of the board in pixels
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param g
	 */
	public void paint(Graphics2D g, int disX, int disY) {
		Color c=g.getColor();
		//Draw the board
		for(int j=0;j<height;j++) {
        	for(int i=0;i<width;i++) {
        		if(board[i][j] == TerrainType.ROCKS) {
        			//g.setColor(Color.black);
        			g.drawImage(rocks, disX + i * SpaceHuntersApplet.squareSize, disY + j * SpaceHuntersApplet.squareSize, null);
        			//g.fillRect(disX+i*SpaceHuntersApplet.squareSize, disY+j*SpaceHuntersApplet.squareSize, SpaceHuntersApplet.squareSize, SpaceHuntersApplet.squareSize);
        		} else if(board[i][j] == TerrainType.CAVE_FLOOR) {
        			g.drawImage(caveFloor, disX + i * SpaceHuntersApplet.squareSize, disY + j * SpaceHuntersApplet.squareSize, null);
        		} else if(board[i][j] == TerrainType.ROUGH) {
        			g.drawImage(rough, disX + i * SpaceHuntersApplet.squareSize, disY + j * SpaceHuntersApplet.squareSize, null);
        		}
        		
        	}
		}
		
		//HIGHLIGHT MOVEABLE LOCS
		for(int i=0; i < gameState.getMoveableLocs().size() ; i++) {
			g.setColor(new Color(Color.blue.getRed(),Color.blue.getGreen(),Color.blue.getBlue(),100));
			g.fillRect(disX+gameState.getMoveableLocs().get(i).getX()*SpaceHuntersApplet.squareSize, disY+gameState.getMoveableLocs().get(i).getY()*SpaceHuntersApplet.squareSize, SpaceHuntersApplet.squareSize, SpaceHuntersApplet.squareSize);
		}
		
		//HIGHLIGHT ATTACKABLE LOCS
		for(int i=0; i < gameState.getAttackableLocs().size() ; i++) {
			g.setColor(new Color(Color.red.darker().getRed(),Color.red.darker().getGreen(),Color.red.darker().getBlue(),100));
			g.fillRect(disX+gameState.getAttackableLocs().get(i).getX()*SpaceHuntersApplet.squareSize, disY+gameState.getAttackableLocs().get(i).getY()*SpaceHuntersApplet.squareSize, SpaceHuntersApplet.squareSize, SpaceHuntersApplet.squareSize);
		}
		
		//HIGHLIGHT REPAIRABLE LOCS
		for(int i=0; i < gameState.getRepairableLocs().size() ; i++) {
			g.setColor(new Color(Color.green.getRed(),Color.green.getGreen(),Color.green.getBlue(),100));
			g.fillRect(disX+gameState.getRepairableLocs().get(i).getX()*SpaceHuntersApplet.squareSize, disY+gameState.getRepairableLocs().get(i).getY()*SpaceHuntersApplet.squareSize, SpaceHuntersApplet.squareSize, SpaceHuntersApplet.squareSize);
		}
		
		//HIGHLIGHT ENEMYATTACKABLE LOCS
		for(int i=0; i < gameState.getEnemyAttackableLocs().size() ; i++) {
			g.setColor(new Color(Color.red.darker().getRed(),Color.red.darker().getGreen(),Color.red.darker().getBlue(),100));
			g.fillRect(disX+gameState.getEnemyAttackableLocs().get(i).getX()*SpaceHuntersApplet.squareSize, disY+gameState.getEnemyAttackableLocs().get(i).getY()*SpaceHuntersApplet.squareSize, SpaceHuntersApplet.squareSize, SpaceHuntersApplet.squareSize);
		}
		
		//Highlight the selected loc
		g.setColor(new Color(Color.yellow.getRed(),Color.yellow.getGreen(),Color.yellow.getBlue(),100));
		g.fillRect(disX+gameState.getSelectedX()*SpaceHuntersApplet.squareSize, disY+gameState.getSelectedY()*SpaceHuntersApplet.squareSize, SpaceHuntersApplet.squareSize, SpaceHuntersApplet.squareSize);

		//Draw the units
		for(int j=0;j<height;j++) {
        	for(int i=0;i<width;i++) {
        		if(gameState.getUnits()[i][j] != null) {
        			gameState.getUnits()[i][j].paint(g, disX, disY, i, j);
        		}
        	}
		}
		g.setColor(c);
	}

	/**
	 * @param e
	 * @throws Exception 
	 */
	public void mousePressed(MouseEvent e, int disX, int disY) {
    	int x=e.getX();
		int y=e.getY();
        int c=(x-disX)/SpaceHuntersApplet.squareSize;
		int r=(y-disY)/SpaceHuntersApplet.squareSize;
        if(c>=width || r>=height ) {
        	c=0;
        	r=0;
        }
        if(gameState.isDoingAction() && gameState.isTargetSquare(new BoardLocation(c, r))) {
        	//Perform Attack
        	if(gameState.getAttackableLocs().size() != 0 && gameState.getUnits()[c][r] != null) {
        		AttackRecord record = new AttackRecord();
        		gameState.getUnits()[gameState.getSelectedX()][gameState.getSelectedY()].attack(gameState.getUnits()[c][r], record, getTerrainModifier(c,r));
        		gameState.addMessage(new Message(record.getMessageString(), record.getMessageColor(), c, r, true));
        		//Check if the attack killed the unit
        		if(gameState.getUnits()[c][r].isDead()) {
        			gameState.getUnits()[c][r] = null;
        		}
        	}
        	//Perform Move
        	else if(gameState.getMoveableLocs().size() != 0) {
        		gameState.getUnits()[gameState.getSelectedX()][gameState.getSelectedY()] = null;
            	gameState.getUnits()[c][r] = gameState.getSelected();
            	int moveDistance = BoardAlgorithmsHelper.getMoveDistanceBetween(this, c, r, gameState.getSelectedX(), gameState.getSelectedY(), gameState, gameState.getUnits()[c][r].isPlayer());
            	gameState.getSelected().addDistanceMoved(moveDistance);
            	gameState.getSelected().addMoveLocs(BoardAlgorithmsHelper.findPathFromAToB(this, c, r, gameState.getSelectedX(), gameState.getSelectedY(), gameState, gameState.getUnits()[c][r].isPlayer()));
        	}
        	//Perform Repair
        	else if(gameState.getRepairableLocs().size() != 0 && gameState.getUnits()[c][r] != null) {
        		AttackRecord record = new AttackRecord();
        		gameState.getUnits()[gameState.getSelectedX()][gameState.getSelectedY()].repair(gameState.getUnits()[c][r], record);
        		gameState.addMessage(new Message(record.getMessageString(), record.getMessageColor(), c, r, true));
        	}
            gameState.resetLocs();
        }
        else {
            gameState.resetLocs();
	        gameState.setSelected(gameState.getUnits()[c][r]);
	        gameState.setSelectedTerrain(board[c][r]);
	        if(gameState.getSelected() != null && !gameState.getSelected().isPlayer()) {
	        	gameState.setEnemyAttackableLocs(BoardAlgorithmsHelper.getPossibleMoveLocs(c, r, gameState.getSelected().getRange() + gameState.getSelected().getMoveableDistance(), gameState, this));
	        }
        }
        gameState.setSelectedX(c);
        gameState.setSelectedY(r);
        gameState.setSelected(gameState.getUnits()[c][r]);
        SpaceHuntersApplet.isScreenDirty = true;
	}
	

	/**
	 * @param i
	 * @param y
	 * @return
	 */
	public boolean isPassable(int x, int y) {
		return board[x][y] != TerrainType.ROCKS;
	}

	/**
	 * @param i
	 * @param y
	 * @return
	 */
	public int movecost(int c, int r) {
		int cost;
		switch(board[c][r]) {
		case ROUGH:
			cost = 2;
			break;
		case CAVE_FLOOR:
			cost = 1;
			break;
		default:
			cost = 0;
			break;
		}
		return cost;
	}

	/**
	 * @param x
	 * @param y
	 * @return
	 */
	public int getTerrainModifier(int c, int r) {
		int modifier;
		switch(board[c][r]) {
		case ROUGH:
			modifier = 30;
			break;
		case CAVE_FLOOR:
			modifier = 0;
			break;
		default:
			modifier = 0;
			break;
		}
		return modifier;
	}

	/**
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {
		//Draw the units
		for(int j=0;j<height;j++) {
        	for(int i=0;i<width;i++) {
        		if(gameState.getUnits()[i][j] != null) {
        			gameState.getUnits()[i][j].updateMovement();
        		}
        	}
		}
	}

}
