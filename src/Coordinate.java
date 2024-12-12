public class Coordinate {

	private int x;
	private int y;

	private boolean isBase = false;
	private boolean isFire = false;
	private int nbPeople;
	private int timeBeforeDead = 50;

	private String[] robots = new String[0];

	public Coordinate(int x, int y) {
		if (x < 0 || x > 11 || y < 0 || y > 11) {
			throw new IllegalArgumentException("les coordonées doivent être entre 0 et 11");
		}
		this.x = x;
		this.y = y;
		this.nbPeople = (int) Math.floor(Math.random() * 10);
	}

	public boolean sameCoordinate(Coordinate c) {
		if (this.x == c.x && this.y == c.y) {
			return true;
		}
		return false;
	}

	public void addRobot(String robotName) {
		String[] newArray = new String[this.robots.length + 1];
        System.arraycopy(this.robots, 0, newArray, 0, this.robots.length);
		newArray[newArray.length - 1] = robotName;
		this.robots = newArray;
	}

	public void removeRobot(String robotName) {
		String[] newArray = new String[this.robots.length - 1];
		int cpt = 0;
		for (int i = 0; i < this.robots.length; i++) {
			if (this.robots[i] != robotName) {
				newArray[cpt] = this.robots[i];
				cpt++;
			}
		}
		this.robots = newArray;
	}

	public void decrementTimeBeforeDead() {
		this.timeBeforeDead--;
	}

	public void savePeople() {
		this.nbPeople = 0;
		this.timeBeforeDead = -1;
	}

	public void addPeople(int nbPeople) {
		this.nbPeople += nbPeople;
	}

	public String getRobots(){
		String robots = "";
		for (String robot : this.robots) {
			robots += robot + " ";
		}
		return robots;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isBase() {
		return isBase;
	}

	public void setBase(boolean base) {
		isBase = base;
	}

	public boolean isFire() {
		return isFire;
	}

	public void setFire(boolean fire) {
		isFire = fire;
	}

	public int getNbPeople() {
		return nbPeople;
	}

	public void setNbPeople(int nbPeople) {
		this.nbPeople = nbPeople;
	}

	public int getTimeBeforeDead() {
		return timeBeforeDead;
	}

	public void setTimeBeforeDead(int timeBeforeDead) {
		this.timeBeforeDead = timeBeforeDead;
	}

	public void setRobots(String[] robots) {
		this.robots = robots;
	}
}
