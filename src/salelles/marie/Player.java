package salelles.marie;


import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.Arrays;


public class Player {
    private String name;
    protected List<Ship> ships;
    protected List<Coordinate> shots;

    Player(String name) {
    	this(name, new ArrayList<Ship>());
    }
    
   Player(String name, List<Ship> ships){
    	this.name = name;
    	this.ships = ships;
        this.shots = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    
    public List<Coordinate> getShots() {
        return shots;
    }

    
    public boolean[] shootAt(Coordinate coord){
      	ListIterator<Ship> s = ships.listIterator();
      	boolean hit = false;
      	boolean sunk = false;
     	while (!hit && s.hasNext()) {
     		Ship ship = s.next();
      		hit = shootAtHit(ship, coord);
      		if(hit){ sunk= shootAtSunk(ship);}
      	}
      return new boolean[] {hit, sunk};	
      }
      
      public boolean shootAtHit(Ship ship, Coordinate coord){
    	  boolean hit;
    	  hit = ship.isHit(coord);
      return hit;
      }
      
     
      public boolean shootAtSunk (Ship ship){
    	  boolean sank;
    	  sank = ship.isDestroyed();
      	return sank;
      }

    public void addShot(Coordinate coord) {
        shots.add(coord);
    }

    public void addShip(Ship ship) {
        ships.add(ship);
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(List<Ship> ships) {
		this.ships = ships;
	}

	public boolean canShootAt(Coordinate coord) {
        return !shots.contains(coord);
    }

    public boolean hasLost() {
        for (Ship ship : ships) {
            if (!ship.isDestroyed()) {
                return false;
            }
        }
        return true;
    }
    
    public Coordinate[] askShipCoords(Scanner sc, String name, int size) {
        Coordinate start;
        Coordinate end;
        int s;

        if (this.getShips().size() > 0) {
            System.out.println("Placed ships coordinates:");
            for (Ship ship : this.getShips()) {
                System.out.println(ship);
            }
        }
        System.out.println(this.getName() + " enter " + name + " start coordinates. size : " + size);
        start = new Coordinate(sc.nextLine());
        System.out.println(this.getName() + " enter " + name + " end coordinates. size : " + size);
        end = new Coordinate(sc.nextLine());
        s = Coordinate.getDist(start, end);
        Coordinate result[] = {start, end};
        Arrays.sort(result);
        return result;
    }

    public  Coordinate askShootCoords(Scanner sc) {
        Coordinate coord;

        do {
        	 System.out.println("\nOu tirer ?");
            coord = new Coordinate(sc.nextLine());
        } while (!coord.isValid());
        return coord;
    }
}
