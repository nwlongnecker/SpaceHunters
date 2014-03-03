/**
 * 
 */
package spaceHunters.game.board;

import java.util.ArrayList;

import spaceHunters.game.GameState;
import spaceHunters.units.Unit;

/**
 * This class contains algorithms that the board uses to help with unit movement
 * @author Nathan Longnecker
 * @version Apr 6, 2013
 */
public class BoardAlgorithmsHelper {

	/**
	 * Gets a list of locations that a piece may move to
	 * @param xStart The x location of the piece
	 * @param yStart The y location of the piece
	 * @param moveDistance The distance that the piece may move
	 * @param gameState The current GameState object
	 * @return Returns a list of locations that the piece may move to
	 */
	public static ArrayList<BoardLocation> getPossibleMoveLocs(int xStart, int yStart, int moveDistance, GameState gameState, Board board) {
		ArrayList<BoardLocation> possibleLocs = new ArrayList<BoardLocation>();
		ArrayList<BoardLocation> checkLocs = new ArrayList<BoardLocation>();
		checkLocs.add(new BoardLocation(xStart,yStart));
		BoardLocation current;
		boolean alreadyChecked;
		while(checkLocs.size()>0) {
			current = checkLocs.remove(0);
			alreadyChecked=false;
			for(int i=0;i<possibleLocs.size();i++) {
				if(possibleLocs.get(i).isSame(current))
					alreadyChecked=true;
			}
			if(!alreadyChecked) {
				possibleLocs.add(current);
				if(current.getGone() < moveDistance) {
					//Check each direction around the current piece
					if(current.getX()+1<board.getWidth() && board.isPassable(current.getX()+1,current.getY())) {
						if(gameState.getUnits()[current.getX()+1][current.getY()] == null || areAllies(gameState.getUnits()[xStart][yStart], gameState.getUnits()[current.getX()+1][current.getY()])) {
							checkLocs.add(new BoardLocation(current.getX()+1,current.getY(), current.getGone()+board.movecost(current.getX()+1,current.getY())));
						}
					}
					if(0 <= current.getX()-1 && board.isPassable(current.getX()-1,current.getY())) {
						if(gameState.getUnits()[current.getX()-1][current.getY()] == null || areAllies(gameState.getUnits()[xStart][yStart], gameState.getUnits()[current.getX()-1][current.getY()])) {
							checkLocs.add(new BoardLocation(current.getX()-1,current.getY(), current.getGone()+board.movecost(current.getX()-1,current.getY())));
						}
					}
					if(current.getY()+1<board.getHeight() && board.isPassable(current.getX(),current.getY()+1)) {
						if(gameState.getUnits()[current.getX()][current.getY()+1] == null || areAllies(gameState.getUnits()[xStart][yStart], gameState.getUnits()[current.getX()][current.getY()+1])) {
							checkLocs.add(new BoardLocation(current.getX(),current.getY()+1, current.getGone()+board.movecost(current.getX(),current.getY()+1)));
						}
					}
					if(0 <= current.getY()-1 && board.isPassable(current.getX(),current.getY()-1)) {
						if(gameState.getUnits()[current.getX()][current.getY()-1] == null || areAllies(gameState.getUnits()[xStart][yStart], gameState.getUnits()[current.getX()][current.getY()-1])) {
							checkLocs.add(new BoardLocation(current.getX(),current.getY()-1, current.getGone()+board.movecost(current.getX(),current.getY()-1)));
						}
					}
				}
			}
		}
		//Remove locations that have occupants
		for(int i = 0; i < possibleLocs.size(); i++) {
			if(gameState.getUnits()[possibleLocs.get(i).getX()][possibleLocs.get(i).getY()] != null) {
				possibleLocs.remove(i);
				i--;
			}
		}
		possibleLocs.add(new BoardLocation(xStart,yStart));
		return possibleLocs;
	}
	
	/**
	 * Gets a list of locations that a piece is close enough to attack
	 * @param xStart The x location of the piece
	 * @param yStart The y location of the piece
	 * @param moveDistance The attack range of the piece
	 * @param gameState The current GameState object
	 * @return Returns a list of locations that the piece may attack
	 */
	public static ArrayList<BoardLocation> getAttackableLocs(int xStart, int yStart, int range, GameState gameState, Board board) {
		ArrayList<BoardLocation> possibleLocs = new ArrayList<BoardLocation>();
		ArrayList<BoardLocation> checkLocs = new ArrayList<BoardLocation>();
		checkLocs.add(new BoardLocation(xStart,yStart));
		BoardLocation current;
		boolean alreadyChecked;
		while(checkLocs.size()>0) {
			current = checkLocs.remove(0);
			alreadyChecked=false;
			for(int i=0;i<possibleLocs.size();i++) {
				if(possibleLocs.get(i).isSame(current))
					alreadyChecked=true;
			}
			if(!alreadyChecked) {
				possibleLocs.add(current);
				if(current.getGone() < range) {
					//Check each direction around the current piece
					if(current.getX()+1<board.getWidth() && board.isPassable(current.getX()+1,current.getY())) {
						checkLocs.add(new BoardLocation(current.getX()+1,current.getY(), current.getGone()+1));
					}
					if(0 <= current.getX()-1 && board.isPassable(current.getX()-1,current.getY())) {
						checkLocs.add(new BoardLocation(current.getX()-1,current.getY(), current.getGone()+1));
					}
					if(current.getY()+1<board.getHeight() && board.isPassable(current.getX(),current.getY()+1)) {
						checkLocs.add(new BoardLocation(current.getX(),current.getY()+1, current.getGone()+1));
					}
					if(0 <= current.getY()-1 && board.isPassable(current.getX(),current.getY()-1)) {
						checkLocs.add(new BoardLocation(current.getX(),current.getY()-1, current.getGone()+1));
					}
				}
			}
		}
		//Remove locations that have occupants
		for(int i = 0; i < possibleLocs.size(); i++) {
			if(gameState.getUnits()[possibleLocs.get(i).getX()][possibleLocs.get(i).getY()] != null && areAllies(gameState.getUnits()[xStart][yStart], gameState.getUnits()[possibleLocs.get(i).getX()][possibleLocs.get(i).getY()])) {
				possibleLocs.remove(i);
				i--;
			}
		}
		return possibleLocs;
	}

	/**
	 * Gets a list of locations that a piece is close enough to repair
	 * @param xStart The x location of the piece
	 * @param yStart The y location of the piece
	 * @param moveDistance The repair range of the piece
	 * @param gameState The current GameState object
	 * @return Returns a list of locations that the piece may repair
	 */
	public static ArrayList<BoardLocation> getRepairableLocs(int xStart, int yStart, int range, GameState gameState, Board board) {
		ArrayList<BoardLocation> possibleLocs = new ArrayList<BoardLocation>();
		ArrayList<BoardLocation> checkLocs = new ArrayList<BoardLocation>();
		checkLocs.add(new BoardLocation(xStart,yStart));
		BoardLocation current;
		boolean alreadyChecked;
		while(checkLocs.size()>0) {
			current = checkLocs.remove(0);
			alreadyChecked=false;
			for(int i=0;i<possibleLocs.size();i++) {
				if(possibleLocs.get(i).isSame(current))
					alreadyChecked=true;
			}
			if(!alreadyChecked) {
				possibleLocs.add(current);
				if(current.getGone() < range) {
					//Check each direction around the current piece
					if(current.getX()+1<board.getWidth() && board.isPassable(current.getX()+1,current.getY())) {
						checkLocs.add(new BoardLocation(current.getX()+1,current.getY(), current.getGone()+1));
					}
					if(0 <= current.getX()-1 && board.isPassable(current.getX()-1,current.getY())) {
						checkLocs.add(new BoardLocation(current.getX()-1,current.getY(), current.getGone()+1));
					}
					if(current.getY()+1<board.getHeight() && board.isPassable(current.getX(),current.getY()+1)) {
						checkLocs.add(new BoardLocation(current.getX(),current.getY()+1, current.getGone()+1));
					}
					if(0 <= current.getY()-1 && board.isPassable(current.getX(),current.getY()-1)) {
						checkLocs.add(new BoardLocation(current.getX(),current.getY()-1, current.getGone()+1));
					}
				}
			}
		}
		//Remove locations that have occupants
		for(int i = 0; i < possibleLocs.size(); i++) {
			if(gameState.getUnits()[possibleLocs.get(i).getX()][possibleLocs.get(i).getY()] != null && !areAllies(gameState.getUnits()[xStart][yStart], gameState.getUnits()[possibleLocs.get(i).getX()][possibleLocs.get(i).getY()])) {
				possibleLocs.remove(i);
				i--;
			}
		}
		return possibleLocs;
	}
	
	//Checks if two units are allied
	private static boolean areAllies(Unit first, Unit second) {
		boolean ret = false;
		if(first.isPlayer() && second.isPlayer()) {
			ret = true;
		}
		else if(!first.isPlayer() && !second.isPlayer()) {
			ret = true;
		}
		return ret;
	}

	/**
	 * @param unit
	 * @param x
	 * @param y
	 * @param range
	 * @param gameState
	 * @return
	 */
	public static ArrayList<BoardLocation> getPossibleAttackableLocs(Unit unit, int xStart, int yStart, int range, GameState gameState, Board board) {
		ArrayList<BoardLocation> possibleLocs = new ArrayList<BoardLocation>();
		ArrayList<BoardLocation> checkLocs = new ArrayList<BoardLocation>();
		checkLocs.add(new BoardLocation(xStart,yStart));
		BoardLocation current;
		boolean alreadyChecked;
		while(checkLocs.size()>0) {
			current = checkLocs.remove(0);
			alreadyChecked=false;
			for(int i=0;i<possibleLocs.size();i++) {
				if(possibleLocs.get(i).isSame(current))
					alreadyChecked=true;
			}
			if(!alreadyChecked) {
				possibleLocs.add(current);
				if(current.getGone() < range) {
					//Check each direction around the current piece
					if(current.getX()+1<board.getWidth() && board.isPassable(current.getX()+1,current.getY())) {
						checkLocs.add(new BoardLocation(current.getX()+1,current.getY(), current.getGone()+board.movecost(current.getX()+1,current.getY())));
					}
					if(0 <= current.getX()-1 && board.isPassable(current.getX()-1,current.getY())) {
						checkLocs.add(new BoardLocation(current.getX()-1,current.getY(), current.getGone()+board.movecost(current.getX()-1,current.getY())));
					}
					if(current.getY()+1<board.getHeight() && board.isPassable(current.getX(),current.getY()+1)) {
						checkLocs.add(new BoardLocation(current.getX(),current.getY()+1, current.getGone()+board.movecost(current.getX(),current.getY()+1)));
					}
					if(0 <= current.getY()-1 && board.isPassable(current.getX(),current.getY()-1)) {
						checkLocs.add(new BoardLocation(current.getX(),current.getY()-1, current.getGone()+board.movecost(current.getX(),current.getY()-1)));
					}
				}
			}
		}
		//Remove locations that have occupants
		for(int i = 0; i < possibleLocs.size(); i++) {
			if(gameState.getUnits()[possibleLocs.get(i).getX()][possibleLocs.get(i).getY()] != null && areAllies(unit, gameState.getUnits()[possibleLocs.get(i).getX()][possibleLocs.get(i).getY()])) {
				possibleLocs.remove(i);
				i--;
			}
		}
		return possibleLocs;
	}

	/**
	 * @param board
	 * @param c
	 * @param r
	 * @param selectedX
	 * @param selectedY
	 * @return
	 */
	public static int getMoveDistanceBetween(Board board, int startX, int startY, int finishX, int finishY, GameState gameState, boolean aIsPlayer) {
		ArrayList<BoardLocation> path = findPathFromAToB(board, startX, startY, finishX, finishY, gameState, aIsPlayer);
		int pathDistance = -1;
		if(path != null && path.size() > 0) {
			pathDistance = path.get(0).getGone();
		}
		return pathDistance;
	}

    enum FoundPath { NO_PATH, STILL_LOOKING, FOUND_PATH; }
	/**
	 * Will return null if there is no path
	 * @param board
	 * @param startX
	 * @param startY
	 * @param finishX
	 * @param finishY
	 * @return Returns a list of board locations from the start position to the end position (inclusive)
	 */
	public static ArrayList<BoardLocation> findPathFromAToB(Board board, int startX, int startY, int finishX, int finishY, GameState gameState, boolean aIsPlayer) {
		ArrayList<BoardLocation> open, closed;
	    ArrayList<BoardLocation> path = new ArrayList<BoardLocation>();
	    
        open = new ArrayList<BoardLocation>();
        closed = new ArrayList<BoardLocation>();
        path= new ArrayList<BoardLocation>();
        
	    FoundPath foundPath = FoundPath.STILL_LOOKING;
	    
	    open.add(new BoardLocation(startX, startY, 0, Math.abs(startX-finishX)+Math.abs(startY-finishY), null));
	    
    	boolean tileClosed = false;
    	while(foundPath == FoundPath.STILL_LOOKING) {
    		//Get the closest estimated location
    		BoardLocation lowestVal = open.get(0);
    		for(int i=0; i < open.size();i++) {
    			if(open.get(i).getVal() < lowestVal.getVal()) {
    				lowestVal=open.get(i);
    			}
    		}
    		//If the closest is the target location, then return
    		if(lowestVal.getX() == finishX && lowestVal.getY() == finishY) {
    			foundPath= FoundPath.FOUND_PATH;
    			BoardLocation currentTile = lowestVal;
    	    	while(currentTile.getParent() != null) {
    	    		path.add(currentTile);
    	    		currentTile=currentTile.getParent();
    	    	}
    	    	path.add(currentTile);
    	    	return path;
    		}

    		//Add the closest to the closed list
    		closed.add(lowestVal);
    		open.remove(lowestVal);
			
    		if(lowestVal.getX()+1<board.getWidth()) {
	    		for(int i=0; i<closed.size();i++) {
	    			if(closed.get(i).getX()==lowestVal.getX()+1 && closed.get(i).getY()==lowestVal.getY()) {
	    				tileClosed=true;
	    			}
	    		}
	    		if(board.isPassable(lowestVal.getX()+1,lowestVal.getY()) && !tileClosed) {
	    			if(gameState.getUnits()[lowestVal.getX()+1][lowestVal.getY()] == null || aIsPlayer == gameState.getUnits()[lowestVal.getX()+1][lowestVal.getY()].isPlayer()) {
	    				open.add(new BoardLocation(lowestVal.getX()+1, lowestVal.getY(), lowestVal.getGone()+board.movecost(lowestVal.getX(), lowestVal.getY()),
		    					Math.abs(lowestVal.getX()+1-finishX)+Math.abs(lowestVal.getY()-finishY),
		    					lowestVal));
	    			}
	    		}
	    		tileClosed=false;
	    	}
    		//////////////////////////////////////////////////////////////////////////////////
    		if(lowestVal.getX()-1>=0) {
	    		for(int i=0; i<closed.size();i++) {
	    			if(closed.get(i).getX()==lowestVal.getX()-1 && closed.get(i).getY()==lowestVal.getY()) {
	    				tileClosed=true;
	    			}
	    		}
	    		if(board.isPassable(lowestVal.getX()-1,lowestVal.getY()) && !tileClosed) {
	    			if(gameState.getUnits()[lowestVal.getX()-1][lowestVal.getY()] == null || aIsPlayer == gameState.getUnits()[lowestVal.getX()-1][lowestVal.getY()].isPlayer()) {
		    			open.add(new BoardLocation(lowestVal.getX()-1, lowestVal.getY(), lowestVal.getGone()+board.movecost(lowestVal.getX(), lowestVal.getY()),
		    					Math.abs(lowestVal.getX()-1-finishX)+Math.abs(lowestVal.getY()-finishY),
		    					lowestVal));
	    			}
	    		}
	    		tileClosed=false;
    		}
    		//////////////////////////////////////////////////////////////////////////////////
    		if(lowestVal.getY()+1<board.getHeight()) {
	    		for(int i=0; i<closed.size();i++) {
	    			if(closed.get(i).getX()==lowestVal.getX() && closed.get(i).getY()==lowestVal.getY()+1){
	    				tileClosed=true;
	    			}
	    		}
	    		if(board.isPassable(lowestVal.getX(),lowestVal.getY()+1) && !tileClosed) {
	    			if(gameState.getUnits()[lowestVal.getX()][lowestVal.getY()+1] == null || aIsPlayer == gameState.getUnits()[lowestVal.getX()][lowestVal.getY()+1].isPlayer()) {
		    			open.add(new BoardLocation(lowestVal.getX(), lowestVal.getY()+1,lowestVal.getGone()+board.movecost(lowestVal.getX(), lowestVal.getY()),
		    					Math.abs(lowestVal.getX()-finishX)+Math.abs(lowestVal.getY()+1-finishY),
		    					lowestVal));
	    			}
	    		}
	    		tileClosed=false;
    		}
    		//////////////////////////////////////////////////////////////////////////////////
    		if(lowestVal.getY()-1>=0) {
	    		for(int i=0; i<closed.size();i++) {
	    			if(closed.get(i).getX()==lowestVal.getX() && closed.get(i).getY()==lowestVal.getY()-1){
	    				tileClosed=true;
	    			}
	    		}
	    		if(board.isPassable(lowestVal.getX(),lowestVal.getY()-1) && !tileClosed){
	    			if(gameState.getUnits()[lowestVal.getX()][lowestVal.getY()-1] == null || aIsPlayer == gameState.getUnits()[lowestVal.getX()][lowestVal.getY()-1].isPlayer()) {
		    			open.add(new BoardLocation(lowestVal.getX(), lowestVal.getY()-1, lowestVal.getGone()+board.movecost(lowestVal.getX(), lowestVal.getY()),
		    					Math.abs(lowestVal.getX()-finishX)+Math.abs(lowestVal.getY()-1-finishY),
		    					lowestVal));
	    			}
	    		}
	    		tileClosed=false;
    		}
    		if(open.isEmpty()) {
    			foundPath= FoundPath.NO_PATH;
    		}
    	}
    	return null;
    }
}
