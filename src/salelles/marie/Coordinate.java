package salelles.marie;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Coordinate implements Cloneable, Comparable<Coordinate> {

    static private String separator = "(?<=\\D)(?=\\d)";
    private int x;
    private int y;

    Coordinate(String coordinate) {
		try {
			x = (int) coordinate.split(separator)[0].toUpperCase().charAt(0) - 64;
			y = Integer.parseInt(coordinate.split(separator)[1]);
		} catch(Exception e) {
			return;
		}
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isValid() {
        return this.getX() >= 1 && this.getX() <= 10 && this.getY() >= 1 && this.getY() <= 10;
    }

    static public int getDist(Coordinate start, Coordinate end) {
        return (int)Math.sqrt(Math.pow(end.getX() - start.getX(), 2) + Math.pow(end.getY() - start.getY(), 2)) + 1;
    }

    public boolean equals(Object other) {
        Coordinate o = (Coordinate) other;
        return this.getX() == o.getX() && this.getY() == o.getY();
    }

    public Coordinate clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch(CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }
        return (Coordinate) o;
    }

    static public List<Coordinate> getSpots(Coordinate start, Coordinate end) {
        List<Coordinate> list = new ArrayList<>();
        Coordinate coord = start.clone();
        list.add(coord.clone());
        if (start.getX() == end.getX()) {
            do {
                coord.setY(coord.getY() + 1);
                list.add(coord.clone());
            } while (!coord.equals(end));
        } else if (start.getY() == end.getY()) {
            do {
                coord.setX(coord.getX() + 1);
                list.add(coord.clone());
            } while (!coord.equals(end));
        }
        return list;
    }
    
    private String toFormattedString() {
        return (char)(x+64) + "" + String.format("%02d", y);
    }
    
    @Override
    public int compareTo(Coordinate o) {
        return this.toFormattedString().compareTo(o.toFormattedString());
    }

    
    static public Coordinate giveCoordinate() {
		String [] arrLetter = {"A", "B", "C", "D", "E", "F","G","H", "I"};
		Random random= new Random();
		int intLetter = random.nextInt(arrLetter.length);
		String letter = arrLetter[intLetter];
		int [] arrIndice = {1, 2, 3, 4, 5, 6, 7, 8, 9};
		int intInd = random.nextInt(arrIndice.length);
		int indice = arrIndice[intInd];
		String coord = letter + indice;
		Coordinate givCoord = new Coordinate (coord); 
		return givCoord;
	}
	
    
    public ArrayList<Coordinate> CoordNeighbors(int dist){
    	//letter
    	int letterSt = this.getX() + 64;
		char letterS = (char)letterSt;
		int indLetter = letterS + dist; // change letter bottom
		int indLetter2 = letterS - dist; // change letter up
		char letterEnd = (char)indLetter;
		char letterEnd2 = (char)indLetter2;
		
		int indiceS = this.getY();
		int indiceEnd = indiceS + dist; //change indice right
		int indiceEnd2 = indiceS - dist; //change indice left
		if (indiceEnd2 < 0) indiceEnd2 =0; //if coord negatif => 0 because - diseppear with split coord
		
		/*neightbors coordonnees */
		Coordinate choiceE1 = new Coordinate (Character.toString(letterEnd)+ indiceS);;
		Coordinate choiceE2 = new Coordinate (Character.toString(letterS) + indiceEnd);
		Coordinate choiceE3 = new Coordinate (Character.toString(letterS) + indiceEnd2);
		Coordinate choiceE4 = new Coordinate (Character.toString(letterEnd2)+ indiceS);
		
		 ArrayList<Coordinate>listCoord = new  ArrayList<Coordinate>();
		 if (choiceE1.isValid()) listCoord.add(choiceE1);
		 if (choiceE2.isValid()) listCoord.add(choiceE2);
		 if (choiceE3.isValid()) listCoord.add(choiceE3);
		 if (choiceE4.isValid()) listCoord.add(choiceE4);
		 
		 return listCoord;
    }

    public ArrayList<Coordinate> CoordLine(){

    	int letterSt = this.getX() + 64;
		char letterS = (char)letterSt;
		
		Coordinate choiceE; 
		
		ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
		
		for (int i =0 ; i<10; i++) {
			choiceE = new Coordinate (Character.toString(letterS)+(i+1)); 
			coords.add (choiceE);
		}
		return coords; 
    }

    public ArrayList<Coordinate> CoordCol(){
    	int indiceS = this.getY();
		
		Coordinate choiceE; 
		
		ArrayList<Coordinate> coords= new ArrayList<Coordinate>();
		
		String [] arrLetter = {"A", "B", "C", "D", "E", "F","G","H","I","J"};
		
		for (int i =0 ; i<10; i++) {
			choiceE = new Coordinate (arrLetter[i]+indiceS); 
			coords.add(choiceE);
		}
		return coords; 
    }
    
    
    @Override
    public String toString() {
        return (char)(x+64) + "" + y;
    }
}
