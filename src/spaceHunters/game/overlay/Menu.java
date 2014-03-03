/**
 * 
 */
package spaceHunters.game.overlay;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import spaceHunters.SpaceHuntersApplet;
import spaceHunters.game.GameState;
import spaceHunters.game.Message;
import spaceHunters.game.board.Board;
import spaceHunters.game.board.BoardAlgorithmsHelper;

/**
 * @author Nathan
 * @version Apr 6, 2013
 */
public class Menu {
	private int guiLocX, guiLocY;
	private int guiWidth, guiHeight;
	private int lastxdrag;
	private int lastydrag;
	private GameState gameState;
	
	public Menu(GameState gameState) {
		guiLocX = 10; guiLocY = 10;
		guiWidth = 81; guiHeight = 181;
		lastxdrag = 0; lastydrag = 0;
		this.gameState = gameState;
	}
	/**
	 * @param g
	 */
	public void paint(Graphics2D g) {
		if(gameState.getSelected() != null && gameState.getSelected().isPlayer()) {
			Color c=g.getColor();
			g.setColor(Color.white);
			g.fillRect(guiLocX, guiLocY, guiWidth, guiHeight);
			g.setColor(Color.lightGray);
			g.fillRect(guiLocX+5, guiLocY+5, 70, 30);
			g.fillRect(guiLocX+5, guiLocY+40, 70, 30);
			g.fillRect(guiLocX+5, guiLocY+75, 70, 30);
			g.fillRect(guiLocX+5, guiLocY+110, 70, 30);
			g.fillRect(guiLocX+5, guiLocY+145, 70, 30);
			g.setColor(Color.black);
			g.setFont(SpaceHuntersApplet.standardBoldFont);
			g.drawString("Attack", guiLocX+12, guiLocY+25);
			g.drawString("Move", guiLocX+12, guiLocY+60);
			g.drawString("Repair", guiLocX+12, guiLocY+95);
			g.drawString("Equip", guiLocX+12, guiLocY+130);
			g.drawString("Stay", guiLocX+12, guiLocY+165);
			g.setFont(SpaceHuntersApplet.standardFont);
			g.setColor(c);
		}
	}

	public void mouseDragged(MouseEvent e) {
		if(gameState.getSelected() != null && gameState.getSelected().isPlayer()) {
			int y = e.getY();
	    	int x = e.getX();
	    	
	    	if(guiLocX < x && x < guiLocX+guiWidth && guiLocY < y && y < guiLocY+guiHeight) {
	    		if(lastxdrag!=0 || lastydrag!=0) {
	    			guiLocX=x-lastxdrag+guiLocX;
		    		guiLocY=y-lastydrag+guiLocY;
		    		SpaceHuntersApplet.isScreenDirty = true;
		    	}
	    		lastxdrag=x;
	    		lastydrag=y;
	    	}
		}
	}
	
	public boolean mousePressed(MouseEvent e, Board board) {
    	boolean ret = false;
		if(gameState.getSelected() != null && gameState.getSelected().isPlayer()) {
			lastxdrag = 0;
			lastydrag = 0;
			int y = e.getY();
	    	int x = e.getX();
	    	if(guiLocX < x && x < guiLocX+guiWidth && guiLocY < y && y < guiLocY+guiHeight) {
	    		ret = true;
	    		
	    		//GUI BUTTONS
	        	if(guiLocX+5 < x && x < guiLocX+75 && guiLocY+5 < y && y < guiLocY+35) {
	        		//ATTACK
	        		boolean deselect = false;
					if(gameState.getAttackableLocs().size() != 0) {
						deselect = true;
					}
	        		gameState.resetLocs();
	        		if(gameState.getSelected().usedAction()) {
	        			gameState.addMessage(new Message(gameState.getSelected().getName()+" has already moved this turn", Color.red, 2, 1, false));
	           		} else {
	           			if(!deselect) {
	           				gameState.setAttackableLocs(BoardAlgorithmsHelper.getAttackableLocs(gameState.getSelectedX(), gameState.getSelectedY(), gameState.getSelected().getRange(), gameState, board));
	           			}
	           		}
	        	} else if(guiLocX+5 < x && x < guiLocX+75 && guiLocY+40 < y && y < guiLocY+70) {
	        		//MOVE
	        		boolean deselect = false;
					if(gameState.getMoveableLocs().size() != 0) {
						deselect = true;
					}
	        		gameState.resetLocs();
	        		if(gameState.getSelected().usedAction() || gameState.getSelected().getMoveableDistance() == 0) {
	        			gameState.addMessage(new Message(gameState.getSelected().getName()+" cannot move any further", Color.red, 2, 1, false));
	        		} else {
	        			if(!deselect) {
	        				gameState.setMoveableLocs(BoardAlgorithmsHelper.getPossibleMoveLocs(gameState.getSelectedX(), gameState.getSelectedY(), gameState.getSelected().getMoveableDistance(), gameState, board));
	        			}
	        		}
				} else if(guiLocX+5 < x && x < guiLocX+75 && guiLocY+75 < y && y < guiLocY+105) {
					//REPAIR
					boolean deselect = false;
					if(gameState.getRepairableLocs().size() != 0) {
						deselect = true;
					}
					gameState.resetLocs();
	        		if(gameState.getSelected().usedAction()) {
	        			gameState.addMessage(new Message(gameState.getSelected().getName()+" has already moved this turn", Color.red, 2, 1, false));
	        		} else {
	        			if(!deselect) {
	           				gameState.setRepairableLocs(BoardAlgorithmsHelper.getRepairableLocs(gameState.getSelectedX(), gameState.getSelectedY(), 1, gameState, board));
	        			}
	        		}
				} else if(guiLocX+5 < x && x < guiLocX+75 && guiLocY+110 < y && y < guiLocY+140) {
					//EQUIP
					gameState.resetLocs();
					if(gameState.getSelected().usedAction()) {
						gameState.addMessage(new Message(gameState.getSelected().getName()+" has already moved this turn", Color.red, 2, 1, false));
					} else {
						//((Character)selected).doNothing();
					}
				} else if(guiLocX+5 < x && x < guiLocX+75 && guiLocY+145 < y && y < guiLocY+175) {
					//STAY
					gameState.resetLocs();
					if(gameState.getSelected().usedAction()) {
						gameState.addMessage(new Message(gameState.getSelected().getName()+" has already moved this turn", Color.red, 2, 1, false));
					} else {
						gameState.getSelected().doNothing();
					}
				}
	            SpaceHuntersApplet.isScreenDirty = true;
	    	}
		}
    	return ret;
	}
}
