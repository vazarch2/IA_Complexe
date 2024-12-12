package projet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Base {
	
	private static Base instance = null;
	
	private final Coordinate position = new Coordinate(5,5);

	private ArrayList<Robot> robots;
	private ArrayList<Robot> storedRobots;
	private ArrayList<Fire> knownFires;
	private ArrayList<Robot> robotsExploration;
	private String[] robotsExplorationSides = new String[]{"center", "left", "right"};
	private int nbSavedPeople = 0;


	private Base(int nbRobots, int nbExplorationRobots) {
		this.robotsExploration = new ArrayList<Robot>();
		this.robots = new ArrayList<Robot>();
		this.knownFires = new ArrayList<Fire>();
		this.storedRobots = new ArrayList<Robot>();

		Grid grid = Grid.getInstance();
		for (int i = 0; i < nbExplorationRobots; i++){

			this.robotsExploration.add(new Robot("e" + i, position));
			grid.coordinates[position.x][position.y].addRobot(this.robotsExploration.get(i).name);
		}
		for (int i = 0; i < nbRobots - nbExplorationRobots ; i++){
			this.robots.add(new Robot("r" + i, position));
			this.storedRobots.add(this.robots.get(i));
			grid.coordinates[position.x][position.y].addRobot(this.robots.get(i).name);
		}
		this.storedRobots.addAll(this.robots);
	}

	public static Base getInstance(int nbRobots, int nbExplorationRobots) {
		if (instance == null) {
			instance = new Base(nbRobots, nbExplorationRobots);
		}
		return instance;
	}

	public static Base getInstance() {
		if(instance == null){
			getInstance(7, 3);
		}
		return instance;
	}


	public void next() {
		// envoi des robots d'exploration
		if(!this.robotsExploration.isEmpty() && robotsExploration.getFirst().battery == 30) {
			this.turnExplorationRobotsSides();
			for(int i = 0; i<this.robotsExploration.size(); i++) {
				this.robotsExploration.get(i).setActionSequence(this.getActionSequenceForExplore(this.robotsExplorationSides[i]));
			}
		}
		// envoi des robots
		if(this.knownFires.size() > 1) {
			this.sortFires();
		}
		if(this.storedRobots.size() > 1) {
			this.sortStoredRobots();
		}
		for(int i = 0; i < this.knownFires.size(); i++) {
			for(int j = 0; j < this.storedRobots.size(); j++) {
				if(this.knownFires.get(i).fireQuantity > 0) {
					int effectiveDecrementedFire = this.storedRobots.get(j).CreateActionSequence(this.knownFires.get(i));
					this.knownFires.get(i).virtualDecrementation(effectiveDecrementedFire);
					if(effectiveDecrementedFire > 0){
						this.storedRobots.remove(j);
					}
					if(this.knownFires.get(i).fireQuantity == 0) {
						this.knownFires.remove(i);
						break;
					}
				}
			}
		}
		// next des robots
		for(int i = 0; i<this.robots.size(); i++) {
			boolean isBackHome = this.robots.get(i).next();
			if(isBackHome) {
				this.storedRobots.add(this.robots.get(i));
			}
		}
		for(int i = 0; i<this.robotsExploration.size(); i++) {
			this.robotsExploration.get(i).next();
		}
	}

	public void savePeople(int nbPeople){
		Grid.getInstance().coordinates[position.x][position.y].addPeople(nbPeople);;
		this.nbSavedPeople += nbPeople;
	}

	public void addFire(Fire fire) {
		for(int i = 0; i<this.knownFires.size(); i++) {
			if (this.knownFires.get(i).position.sameCoordinate(fire.position)) {
				return;
			}
		}
		this.knownFires.addLast(WildFires.getInstance().newVirtualFire(fire.position));
	}

	/**
	 * on trie par distance: les feux les plus loins seront eteints en premier ce qui permet d'explorer la zone en meme temps
	 */
	private void sortFires() {
		this.knownFires.sort((Fire f1, Fire f2) -> {
			int distanceA = Grid.getDistance(position, f1.position);
			int distanceB = Grid.getDistance(position, f2.position);
			return Integer.compare(distanceA, distanceB);
		});
	}

	public int getUnsavedPeopleCount() {
	    int count = 0;
	    Coordinate[][] coordinates = Grid.getInstance().coordinates;

	    for (int i = 0; i < coordinates.length; i++) {
	        for (int j = 0; j < coordinates[i].length; j++) {
	            Coordinate currentCoordinate = coordinates[i][j];

	            // Vérifier si la case contient des personnes non sauvées
	            if (!currentCoordinate.isBase && currentCoordinate.nbPeople > 0 && currentCoordinate.TimeBeforeDead <= 0) {
	                count += currentCoordinate.nbPeople;
	            }
	        }
	    }

	    return count;
	}



	private void sortStoredRobots() {
		storedRobots.sort(Comparator.comparingInt((Robot r) -> r.battery));
	}

	private void turnExplorationRobotsSides() {
		String temp = this.robotsExplorationSides[0];
		this.robotsExplorationSides[0] = this.robotsExplorationSides[1];
		this.robotsExplorationSides[1] = this.robotsExplorationSides[2];
		this.robotsExplorationSides[2] = temp;
	}
	
	private Action[] getActionSequenceForExplore(String side) {
		Action[] actionSequence = new Action[24];
		Coordinate lastPosition = position;
		if(side.equals("center")) {
			lastPosition = new Coordinate(lastPosition.x, lastPosition.y+1);
			actionSequence[0] = new Action("move", lastPosition);
			lastPosition = new Coordinate(lastPosition.x, lastPosition.y+1);
			actionSequence[1] = new Action("move", lastPosition);

			for(int i=2; i<5; i++) {
				lastPosition = new Coordinate(lastPosition.x+1, lastPosition.y);
				actionSequence[i] = new Action("move", lastPosition);
			}
			for(int i=5; i<9; i++) {
				lastPosition = new Coordinate(lastPosition.x, lastPosition.y-1);
				actionSequence[i] = new Action("move", lastPosition);
			}
			for(int i=9; i<15; i++) {
				lastPosition = new Coordinate(lastPosition.x-1, lastPosition.y);
				actionSequence[i] = new Action("move", lastPosition);
			}
			for(int i=15; i<19; i++) {
				lastPosition = new Coordinate(lastPosition.x, lastPosition.y+1);
				actionSequence[i] = new Action("move", lastPosition);
			}
			for(int i=19; i<22; i++) {
				lastPosition = new Coordinate(lastPosition.x+1, lastPosition.y);
				actionSequence[i] = new Action("move", lastPosition);
			}
			actionSequence[22] = new Action("move", new Coordinate(lastPosition.x, lastPosition.y-1));
			actionSequence[23] = new Action("move", position);
		}else{
			int factor = 1;
			if(side.equals("left")) {
				factor = -1;
			}
			for(int i=0; i<4; i++) {
				lastPosition = new Coordinate(lastPosition.x, lastPosition.y + 1);
				actionSequence[i] = new Action("move", lastPosition);
			}
			for(int i = 4; i<8; i++) {
				lastPosition = new Coordinate(lastPosition.x + factor, lastPosition.y);
				actionSequence[i] = new Action("move", lastPosition);
			}
			for(int i = 8; i<16; i++) {
				lastPosition = new Coordinate(lastPosition.x, lastPosition.y - 1);
				actionSequence[i] = new Action("move", lastPosition);
			}
			for(int i = 16; i<20; i++) {
				lastPosition = new Coordinate(lastPosition.x - factor, lastPosition.y);
				actionSequence[i] = new Action("move", lastPosition);
			}
			for(int i=20; i<24; i++) {
				lastPosition = new Coordinate(lastPosition.x, lastPosition.y + 1);
				actionSequence[i] = new Action("move", lastPosition);
			}
		}
		return actionSequence;
	}



	public static void setInstance(Base instance) {
		Base.instance = instance;
	}

	public Coordinate getPosition() {
		return position;
	}

	public ArrayList<Robot> getRobots() {
		return robots;
	}

	public void setRobots(ArrayList<Robot> robots) {
		this.robots = robots;
	}

	public ArrayList<Robot> getStoredRobots() {
		return storedRobots;
	}

	public void setStoredRobots(ArrayList<Robot> storedRobots) {
		this.storedRobots = storedRobots;
	}

	public ArrayList<Fire> getKnownFires() {
		return knownFires;
	}

	public void setKnownFires(ArrayList<Fire> knownFires) {
		this.knownFires = knownFires;
	}

	public ArrayList<Robot> getRobotsExploration() {
		return robotsExploration;
	}

	public void setRobotsExploration(ArrayList<Robot> robotsExploration) {
		this.robotsExploration = robotsExploration;
	}

	public String[] getRobotsExplorationSides() {
		return robotsExplorationSides;
	}

	public void setRobotsExplorationSides(String[] robotsExplorationSides) {
		this.robotsExplorationSides = robotsExplorationSides;
	}

	public int getNbSavedPeople() {
		return nbSavedPeople;
	}

	public void setNbSavedPeople(int nbSavedPeople) {
		this.nbSavedPeople = nbSavedPeople;
	}
}
