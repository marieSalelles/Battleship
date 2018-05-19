package salelles.marie;


import java.util.ArrayList;
import java.util.List;

public class Game {

    private Player currentPlayer, otherPlayer, firstPlayer;
    private int level, level2; 

    Game(String name1, String name2) {
        currentPlayer = new Player(name1);
        otherPlayer = new Player(name2); //mettre AI
        firstPlayer= currentPlayer;
    }
    
    Game(String name1, String name2, int level) {
        currentPlayer = new Player(name1);
        otherPlayer = new AIBattle(name2,level); //mettre AI
        firstPlayer= currentPlayer;
        this.level=level;
    }
    
    public Game(String name1, String name2, int level,int level2) {
        currentPlayer = new AIBattle(name1,level);
        otherPlayer = new AIBattle(name2,level2); //mettre AI
        firstPlayer= currentPlayer;
        this.level=level;
        this.level2=level2;
    }

    public Player getFirstPlayer() {
		return firstPlayer;
	}

	public void setFirstPlayer(Player firstPlayer) {
		this.firstPlayer = firstPlayer;
	}

	public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getOtherPlayer() {
        return otherPlayer;
    }

    public boolean isDone() {
        return currentPlayer.hasLost() || otherPlayer.hasLost();
    }

    public Player winner() {
        return currentPlayer.hasLost() ? otherPlayer : currentPlayer;
    }

    public void nextTurn() {
        Player temp = currentPlayer;
        currentPlayer = otherPlayer;
        otherPlayer = temp;
    }

    private boolean isOverlapping(Coordinate start, Coordinate end) {
        boolean result = false;
        List<Coordinate> placedSpots = new ArrayList<>();

        for (Ship ship : currentPlayer.getShips()) {
            placedSpots.addAll(ship.getSpots());
        }

        for (Coordinate coord : Coordinate.getSpots(start, end)) {
            if (placedSpots.contains(coord)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean isValid(Coordinate start, Coordinate end) {
        return start.isValid() && end.isValid() && !isOverlapping(start, end) && (start.getX() == end.getX() || start.getY() == end.getY());
    }

    public void replay() {
    	if (getCurrentPlayer()==getFirstPlayer()) {
    		if (level==0) {
    			otherPlayer = new Player (firstPlayer.getName());
	    		currentPlayer= new Player (otherPlayer.getName());
	    		firstPlayer = currentPlayer;
    		}
    		else if (level > 0 && level2 == 0) {
    			currentPlayer= new AIBattle (otherPlayer.getName(),level);
    			otherPlayer = new Player (firstPlayer.getName());
    			firstPlayer = currentPlayer;
    		
    		}else if (level > 0 && level2 > 0) {
        			currentPlayer= new AIBattle (otherPlayer.getName(),level);
        			otherPlayer = new AIBattle (firstPlayer.getName(),level2);
        			firstPlayer = currentPlayer;
    		}	
    	}
    	else if (getOtherPlayer()==getFirstPlayer()){
    		if (level ==0) {
	    		currentPlayer= new Player (currentPlayer.getName());
	    		otherPlayer = new Player (firstPlayer.getName());
	    		firstPlayer = currentPlayer;
    		}
    		else if (level2 ==0) {
    				currentPlayer= new Player (currentPlayer.getName());
    				otherPlayer = new AIBattle (firstPlayer.getName(),level);
    				firstPlayer = currentPlayer;
    				
    		}else if (level > 0 && level2 > 0) {
        			currentPlayer= new AIBattle (currentPlayer.getName(),level);
        			otherPlayer = new AIBattle (firstPlayer.getName(),level2);
        			firstPlayer = currentPlayer;
        	
    		}
    		
    	}
    }
}
