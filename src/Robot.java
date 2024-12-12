import java.util.ArrayList;
import java.util.List;

public class Robot {

	private int battery = 30;
	private int water = 20;
	private Coordinate position;
	private String name;
	private ArrayList<Action> actionSequence;
	private int timeBeforeFullBattery = 20;
	
	public Robot(String robotName, Coordinate position) {
		this.name = robotName;
		this.position = position;
		this.actionSequence = new ArrayList<Action>();
	}
	
	/**
	 * 
	 * @param fire
	 * @return le nombre de deciseconde que le robot aura enlevé au feu une fois son parcours terminé
	 */
	public int CreateActionSequence(Fire fire) {
		Coordinate destination = fire.getPosition();
		int fireToDecrement = fire.getFireQuantity();
		Coordinate[] path = Grid.getInstance().getPath(this.position, destination);
		int pathLength = path.length;
		int effectiveDecrementedFire = Math.min(this.battery - pathLength * 2, fireToDecrement);
		if(effectiveDecrementedFire <= 0){
			return 0;
		}
		int actionSequenceLenght = pathLength * 2 + effectiveDecrementedFire;
		for(int i = 0; i<actionSequenceLenght -1; i++){
			if(i<pathLength) {
				this.actionSequence.add(new Action("move", path[i]));//aller
			} else if(i < pathLength + effectiveDecrementedFire) {
				this.actionSequence.add(new Action("putOut", destination));//eteindre
			} else {
				this.actionSequence.add(new Action("move", path[actionSequenceLenght - i -2]));//retour
			}
		}
		this.actionSequence.addLast(new Action("move", Base.getInstance().getPosition()));//retour
		return effectiveDecrementedFire;
	}

	public boolean next() {
		// System.out.println(this.name + " : b" + this.battery + " x" + this.position.x + " y" + this.position.y + " al"+ this.actionSequence.length);
		if(!this.actionSequence.isEmpty()) {
			//reduit la batterie
			this.battery--;
			// unshift l'action
			Action action = this.actionSequence.removeFirst();
			/*
			Action action = this.actionSequence[0];
			Action[] newActionSequence = new Action[this.actionSequence.length -1];
            System.arraycopy(this.actionSequence, 1, newActionSequence, 0, newActionSequence.length);
			this.actionSequence = newActionSequence;
			*/
			Grid grid = Grid.getInstance();
			Base base = Base.getInstance();
			//sauve les personnes si il y a le feu
			Coordinate actualPosition = grid.getCoordinates()[this.position.getX()][this.position.getY()];
			if (!actualPosition.isBase() && actualPosition.isFire() && actualPosition.getTimeBeforeDead()>0){
				base.savePeople(actualPosition.getNbPeople());
				actualPosition.savePeople();
			}
			// regarde les voisins
			Coordinate[] neighbors = grid.getNeighbors(this.position);
			for (int i = 0; i < neighbors.length; i++) {
				if(neighbors[i].isFire()) {
					base.addFire(WildFires.getInstance().getFire(neighbors[i]));
				}
			}
			//execution de l'action
			if(action.getTypeAction().equals("move")) {
				grid.moveRobot(this.name,  this.position,  action.getCoordinate());
				this.position = action.getCoordinate();
			}else {
				WildFires.getInstance().decrementFireAt(action.getCoordinate());
			}
			if(!this.actionSequence.isEmpty()) {
				//le robot est de retour
				this.timeBeforeFullBattery = 20;
				return true;
			}
		}else {
			//le robot est dans la base
			if(this.battery != 30) {
				this.timeBeforeFullBattery--;
				if(this.timeBeforeFullBattery == 0){
					this.battery = 30;
				}
			}
			if(this.water != 20) {
				this.water++;
			}
		}
		return false;
	}

	public void setActionSequence(Action[] actionSequence) {
		this.actionSequence.addAll(List.of(actionSequence));
	}

	public int getBattery() {
		return battery;
	}

	public void setBattery(int battery) {
		this.battery = battery;
	}

	public int getWater() {
		return water;
	}

	public void setWater(int water) {
		this.water = water;
	}

	public Coordinate getPosition() {
		return position;
	}

	public void setPosition(Coordinate position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Action> getActionSequence() {
		return actionSequence;
	}

	public void setActionSequence(ArrayList<Action> actionSequence) {
		this.actionSequence = actionSequence;
	}

	public int getTimeBeforeFullBattery() {
		return timeBeforeFullBattery;
	}

	public void setTimeBeforeFullBattery(int timeBeforeFullBattery) {
		this.timeBeforeFullBattery = timeBeforeFullBattery;
	}
}
