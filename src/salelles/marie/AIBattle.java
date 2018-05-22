package salelles.marie;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Arrays;

public class AIBattle extends Player {

		private int level;
		private List<Boolean> touched; 
		private int touchedSize;//size list touched wen ship is sunk
		
		AIBattle(String name, int level) {
			super(name);
			this.level=level;
			this.touched= new  ArrayList<Boolean>();
			this.touchedSize=0;
		}
		 
		
		public Coordinate[] placedShip(int size) {
			Coordinate start = Coordinate.giveCoordinate();
			ArrayList<Coordinate> arrCoord = start.CoordNeighbors(size-1);
			Coordinate coordEnd;
			do {
				Random random= new Random();
				int coordChoice = random.nextInt(arrCoord.size());
				coordEnd = arrCoord.get(coordChoice);
			}while(!coordEnd.isValid());
			return new Coordinate[] {start, coordEnd};	
		}
		
		
		public void initialize () {
			this.touchedSize=this.touchedSize + touched.size();
			this.touched= new  ArrayList<Boolean>();
		}
		
		public void addTouched (boolean ajout) {
			touched.add(ajout);
		}
		
		
		public int getLevel() {
			return level;
		}


		public void setLevel(int level) {
			this.level = level;
		}


		public Coordinate shoot(){
			
			Coordinate coordChose;
			if(level ==1) {
				coordChose = Coordinate.giveCoordinate();
				
				return coordChose;
				
			}else if (level == 2) {
				do {
					coordChose = Coordinate.giveCoordinate();
				}while(shots.contains(coordChose));
				
				return coordChose;	
				
			}else {
				int indLastTouch = -1; 
				int indLastLastTouch = -2;
				boolean testAllTouch = false;
				boolean testAllTouchN = false;
				Random random= new Random();
				
				//search last and last last touched
				for (int i =0 ; i<touched.size(); i++){ 
					if (touched.get(i)== true){
						indLastLastTouch = indLastTouch;
						indLastTouch = i;
					}
				}
				
				ArrayList<Coordinate> coordN = new ArrayList<Coordinate> ();
				Coordinate lastTouched;
				
				if ((indLastTouch) <=touched.size() && indLastTouch >= 0){ //if touched one time
					
					
					if (indLastLastTouch >= 0){ //if touch second time
						//same letter
						if (shots.get(indLastTouch+this.touchedSize).getX() ==  shots.get(indLastLastTouch+this.touchedSize).getX() && ! testAllTouch) { 
							
							
							lastTouched = shots.get(indLastTouch+this.touchedSize);
							coordN = lastTouched.CoordLine(); //tab with coords same letter
							do {
								int intInd = random.nextInt(coordN.size());
								coordChose = coordN.get(intInd); //choose coords
								if (shots.containsAll(coordN))  testAllTouch= true; //if we have touch all the cases in coordN
							}while ((shots.contains(coordChose) || lastTouched.getX()!=coordChose.getX() || !coordChose.isValid()) && !testAllTouch && coordChose.getY()>=lastTouched.getY()+4 && coordChose.getY()<=lastTouched.getY()-4);
							
							
							if(!testAllTouch) {
								return coordChose;
							}
						
						//same indice
						}else if (shots.get(indLastTouch+this.touchedSize).getY() ==  shots.get(indLastLastTouch+this.touchedSize).getY() && ! testAllTouch){
							
							lastTouched = shots.get(indLastTouch+this.touchedSize);
							
							coordN = lastTouched.CoordCol(); //tab with coord same indice
							do {
								int intInd = random.nextInt(coordN.size());
								coordChose =coordN.get(intInd); //choose coords
								if (shots.containsAll(coordN))  testAllTouch= true; //if we have touch all the cases in coordN
							}while ((shots.contains(coordChose)  || lastTouched.getY()!=coordChose.getY() || !coordChose.isValid()) && !testAllTouch && coordChose.getX()>=lastTouched.getX()+4 && coordChose.getX()<=lastTouched.getX()-4 ); 
							
	
							if(!testAllTouch) {
								return coordChose;
							}
							 
						}
					}
					
						lastTouched = shots.get(indLastTouch+this.touchedSize);
	
						coordN = lastTouched.CoordNeighbors(1); //tab with neightbors coords
	
						do {
							int intInd = random.nextInt(coordN.size());
							coordChose =coordN.get(intInd); //choose coords
							if (shots.containsAll(coordN))  testAllTouchN= true;  //if we have touch all the cases in coordN
						}while (shots.contains(coordChose) && !testAllTouchN);
	
	
						if(!testAllTouchN) {
							return coordChose;
						}
					}
				 // if don t touched anything
					
				do {
					coordChose = Coordinate.giveCoordinate();
				}while(shots.contains(coordChose));

				return coordChose;
			}
		}
}

