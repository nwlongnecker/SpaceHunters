/**
 * 
 */
package spaceHunters.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import spaceHunters.SpaceHunterState;
import spaceHunters.SpaceHuntersApplet;
import spaceHunters.game.board.Board;
import spaceHunters.game.overlay.Menu;
import spaceHunters.game.overlay.SelectedStats;
import spaceHunters.game.overlay.TerrainStats;
import spaceHunters.items.beamSwords.LaserDrill;
import spaceHunters.items.beamSwords.Rapier;
import spaceHunters.items.beamSwords.Roll;
import spaceHunters.items.blasters.BasicBlaster;
import spaceHunters.items.blasters.DuctTapeSniper;
import spaceHunters.items.blasters.Hamster;
import spaceHunters.items.blasters.Nunchucks;
import spaceHunters.items.suits.Armor;
import spaceHunters.items.suits.BasicSuit;
import spaceHunters.items.suits.HamsterSuit;
import spaceHunters.items.suits.HedgehogSpines;
import spaceHunters.items.suits.NinjaSuit;
import spaceHunters.units.PlayerCharacter;

/**
 * @author Nathan Longnecker
 * @version Apr 5, 2013
 */
public class Game implements SpaceHunterState {
	
	private Board board;
	private GameState gameState;
	private Menu menu;
	private TerrainStats terrainStats;
	private SelectedStats selectedStats;
	private ArrayList<PlayerCharacter> characters;
	private int panRate = 4;
	private boolean panLeft, panRight, panUp, panDown;
	private int disX, disY;
	
	public Game() {
    	disX = 0;
    	disY = 0;
    	gameState = new GameState();
    	board = new Board(gameState);
    	menu = new Menu(gameState);
    	terrainStats = new TerrainStats();
    	selectedStats = new SelectedStats();
    	characters = new ArrayList<PlayerCharacter>();
    	/* Player Name, Move distance, Blaster level, Sword level, Repair level, Dodge level, Psionic level
    	 * Blaster affinity, Sword affinity, Repair affinity, Dodge affinity, Psionic affinity, AI attack priority, Weapon, Suit */
    	characters.add(new PlayerCharacter("Sonic", 10, 1, 1, 1, 0, 0, 1.2, 1.2, 1.2, 1.2, 1.2, 1, new Roll(), new HedgehogSpines(), "/images/sonic.png"));
    	characters.add(new PlayerCharacter("Furry", 6, 0, 2, 0, 0, 0, 1, 1.5, 1, 1, 1, 2, new Hamster(), new HamsterSuit(), "/images/hamster.png"));
    	characters.add(new PlayerCharacter("Sniper", 6, 2, 0, 0, 0, 0, 1.5, 1, 1, 1, 1, 3, new DuctTapeSniper(), new BasicSuit(), "/images/Spaceman1.png"));
    	characters.add(new PlayerCharacter("Wannabe Ninja", 6, 0, 1, 1, 0, 1, 1, 1.2, 1.35, 1, 1.2, 4, new Nunchucks(), new NinjaSuit(), "/images/Ninja_Sprite.png"));
    	characters.add(new PlayerCharacter("Medic", 6, 0, 0, 2, 0, 0, 1, 1, 1.5, 1, 1, 5, new LaserDrill(), new BasicSuit(), "/images/medic.png"));
    	characters.add(new PlayerCharacter("Erika", 6, 0, 0, 0, 1, 2, 1, 1, 1, 1, 1.5, 6, new Rapier(), new Armor(), "/images/Erika.png"));
    	gameState.setSelected(characters.get(0));
    	gameState.setSelectedX(3);
    	gameState.setSelectedY(5);
    	board.placeCharacters(characters);
	}

	public void paint(Graphics2D g) {
		board.paint(g, disX, disY);
		menu.paint(g);
		terrainStats.paint(g, gameState.getTerrain());
		selectedStats.paint(g, gameState.getSelected());
		for(int i = 0; i < gameState.getMessages().size(); i++) {
			gameState.getMessages().get(i).drawMessage(g, disX, disY);
		}
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		//If the click was not on the menu, click on the board
		if(!menu.mousePressed(e, board)) {
			board.mousePressed(e, disX, disY);
		}
		checkTurnOver();
		checkGameOver();
	}

	/**
	 * 
	 */
	private void checkGameOver() {
		boolean areEnemiesAlive = false;
		boolean arePlayersAlive = false;
		for(int j=0;j<board.getHeight();j++) {
        	for(int i=0;i<board.getWidth();i++) {
        		if(gameState.getUnits()[i][j] != null) {
        			if(gameState.getUnits()[i][j].isPlayer()) {
        				arePlayersAlive = true;
        			}
        			else {
        				areEnemiesAlive = true;
        			}
        		}
        	}
		}
		if(!areEnemiesAlive) {
			gameState.addMessage(new Message("VICTORY", Color.orange, 2, 1, false));
		}
		if(!arePlayersAlive) {
			gameState.addMessage(new Message("YOU LOSE", Color.orange, 2, 1, false));
		}
	}

	/**
	 * 
	 */
	private void checkTurnOver() {
		boolean isTurnOver = true;
		for(int i = 0; i < characters.size(); i++) {
			if(!characters.get(i).usedAction() && !characters.get(i).isDead()) {
				isTurnOver = false;
			}
		}
		if(isTurnOver) {
			endTurn();
		}
	}

	/**
	 * 
	 */
	private void endTurn() {
		gameState.addMessage(new Message("Enemy Turn", Color.red, 2, 1, false));
		EnemyAI.takeTurn(board, gameState);
		for(int i = 0; i < characters.size(); i++) {
			characters.get(i).newTurn();
		}
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseDragged(MouseEvent e) {
		menu.mouseDragged(e);
	}

	public void mouseMoved(MouseEvent e) {
		terrainStats.mouseMoved(e);
		selectedStats.mouseMoved(e);
		int y = e.getY();
    	int x = e.getX();
		if(x < 25 && 0 > disX) {
			panLeft = true;
		} else {
			panLeft = false;
		}
		if(x > SpaceHuntersApplet.winX - 25 && disX - SpaceHuntersApplet.winX > -1 * board.getWidth() * SpaceHuntersApplet.squareSize) {
			panRight = true;
		} else {
			panRight = false;
		}
		if(y < 25 && 0 > disY) {
			panUp = true;
		} else {
			panUp = false;
		}
		if(y > SpaceHuntersApplet.winY - 25 && disY-SpaceHuntersApplet.winY > -1 * board.getHeight() * SpaceHuntersApplet.squareSize) {
			panDown = true;
		} else {
			panDown = false;
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		gameState.updateGameState();
		board.actionPerformed(e);
		if(panLeft) {
			if(0 > disX)
				disX=disX+panRate;
			else
				panLeft = false;
		}
		if(panRight) {
			if(disX-SpaceHuntersApplet.winX > -1*board.getWidth()*SpaceHuntersApplet.squareSize)
				disX=disX-panRate;
			else
				panRight = false;
		}
		if(panUp) {
			if(0 > disY)
				disY=disY+panRate;
			else
				panUp = false;
		}
		if(panDown) {
			if(disY-SpaceHuntersApplet.winY > -1*board.getHeight()*SpaceHuntersApplet.squareSize)
				disY=disY-panRate;
			else
				panDown = false;
		}
		if(panLeft || panRight || panUp || panDown) {
			SpaceHuntersApplet.isScreenDirty = true;
		}
	}
}
