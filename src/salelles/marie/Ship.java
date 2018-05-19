package salelles.marie;

import java.util.List;

public class Ship {
    private String name;
    private Coordinate startCoord, endCoord;
    private int size, touched;

    static private String separator = "(?<=\\D)(?=\\d)";

    Ship(String name, Coordinate startCoord, Coordinate endCoord) {
        this.name = name;
        touched = 0;
        if ( (startCoord.getX() == endCoord.getX() && endCoord.getY() < endCoord.getY() ) || ( startCoord.getY() == endCoord.getY() && endCoord.getX() < startCoord.getX() ) ) {
            this.startCoord = endCoord;
            this.endCoord = startCoord;
        } else {
            this.startCoord = startCoord;
            this.endCoord = endCoord;
        }
        size = Coordinate.getDist(startCoord, endCoord);
    }

    public int getSize() {
        return size;
    }

    public int getTouched() {
		return touched;
	}

	public List<Coordinate> getSpots() {
        return Coordinate.getSpots(startCoord, endCoord);
    }

    public boolean isHit(Coordinate missileCoord) {
        if (startCoord.getX() <= missileCoord.getX() && missileCoord.getX() <= endCoord.getX() && startCoord.getY() <= missileCoord.getY() && missileCoord.getY() <= endCoord.getY()) {
            touched++;
            return true;
        }
        return false;
    }

    public boolean isDestroyed() {
        return touched == size;
    }

    public String toString() {
        return String.format("Ship [name : %s, start : %s, end : %s, size : %d]", name, startCoord, endCoord, size);
    }
}
