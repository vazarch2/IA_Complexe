public class Grid {

	public static Grid INSTANCE = new Grid();

	
	private Coordinate[][] coordinates; //premier tableau = x, deuxieme tableau = y
	
	private Grid() {
		this.coordinates = new Coordinate[11][11];
		for(int i=0; i < 11; i++) {
			for (int j=0; j<11; j++) {
				Coordinate c =  new Coordinate(i, j);
				if(j==5 && i==5) {
					c.setBase(true);
				}
				this.coordinates[i][j] = c;
			}
		}
	}
	
	public static Grid getInstance() {
		return INSTANCE;
	}
	
	public Coordinate[] getPath(Coordinate from, Coordinate to) {
		int pathLength = Grid.getDistance(from, to);
		Coordinate[] path = new Coordinate[pathLength];
		int factorX = 0;
		if(from.getX() < to.getX()) {
			factorX = 1;
		}else if(from.getX() > to.getX()) {
			factorX = -1;
		}
		int cpt = 0;
		int column = from.getX();
		while(column != to.getX()) {
			column = column + factorX;
			path[cpt] = this.coordinates[column][from.getY()];
			cpt++;
		}
		int factorY = 0;
		if(from.getY() < to.getY()) {
			factorY = 1;
		}else if(from.getY() > to.getY()) {
			factorY = -1;
		}
		int row = from.getY();
		while(row != to.getY()) {
			row = row + factorY;
			path[cpt] = this.coordinates[to.getX()][row];
			cpt++;
		}
		return path;
	}
	
	public void setFireToCoordinate(int x, int y) {
		this.coordinates[x][y].setFire(true);;
	}
	
	public void unsetFireOnCoordinate(int x, int y) {
		this.coordinates[x][y].setFire(false);;
	}
	
	public void moveRobot(String robotName, Coordinate from, Coordinate to) {
		this.coordinates[from.getX()][from.getY()].removeRobot(robotName);
		this.coordinates[to.getX()][to.getY()].addRobot(robotName);
	}
	
	
	public Coordinate[] getNeighbors(Coordinate coordinate) { 
		Coordinate[] neighbors;
		int x = coordinate.getX();
		int y = coordinate.getY();
		if(x == 0) {
			if(y == 0) {
				neighbors= new Coordinate[3];
				neighbors[0] = this.coordinates[1][0];
				neighbors[1] = this.coordinates[1][1];
				neighbors[2] = this.coordinates[0][1];
			}else if(y == 10) {
				neighbors= new Coordinate[3];
				neighbors[0] = this.coordinates[1][9];
				neighbors[1] = this.coordinates[1][9];
				neighbors[2] = this.coordinates[0][9];
			}else {
				neighbors= new Coordinate[5];
				neighbors[0] = this.coordinates[0][y - 1];
				neighbors[1] = this.coordinates[1][y - 1];
				neighbors[2] = this.coordinates[1][y];
				neighbors[3] = this.coordinates[1][y + 1];
				neighbors[4] = this.coordinates[0][y + 1];
			}
		} else if (x == 10) {
			if(y == 0) {
				neighbors= new Coordinate[3];
				neighbors[0] = this.coordinates[9][0];
				neighbors[1] = this.coordinates[9][1];
				neighbors[2] = this.coordinates[9][1];
			} else if (y == 10) {
				neighbors= new Coordinate[3];
				neighbors[0] = this.coordinates[10][9];
				neighbors[1] = this.coordinates[9][9];
				neighbors[2] = this.coordinates[9][10];
			}else {
				neighbors= new Coordinate[5];
				neighbors[0] = this.coordinates[10][y - 1];
				neighbors[1] = this.coordinates[9][y - 1];
				neighbors[2] = this.coordinates[9][y];
				neighbors[3] = this.coordinates[9][y + 1];
				neighbors[4] = this.coordinates[10][y + 1];
			}
		}else {
			if(y == 0) {
				neighbors= new Coordinate[5];
				neighbors[0] = this.coordinates[x - 1][0];
				neighbors[1] = this.coordinates[x - 1][1];
				neighbors[2] = this.coordinates[x][1];
				neighbors[3] = this.coordinates[x + 1][1];
				neighbors[4] = this.coordinates[x + 1][0];
			} else if(y == 10) {
				neighbors= new Coordinate[5];
				neighbors[0] = this.coordinates[x - 1][10];
				neighbors[1] = this.coordinates[x - 1][9];
				neighbors[2] = this.coordinates[x][9];
				neighbors[3] = this.coordinates[x + 1][9];
				neighbors[4] = this.coordinates[x + 1][10];
			}else {
				neighbors = new Coordinate[8];
				neighbors[0] = this.coordinates[x - 1][y - 1];
				neighbors[1] = this.coordinates[x - 1][y];
				neighbors[2] = this.coordinates[x - 1][y + 1];
				neighbors[3] = this.coordinates[x][y - 1];
				neighbors[4] = this.coordinates[x][y + 1];
				neighbors[5] = this.coordinates[x + 1][y - 1];
				neighbors[6] = this.coordinates[x + 1][y];
				neighbors[7] = this.coordinates[x + 1][y + 1];
			}
		}
		return neighbors;
	}
	
	public static int getDistance(Coordinate from, Coordinate to) {
		return Math.abs(from.getX() - to.getX()) + Math.abs(from.getY() - to.getY());
	}

	public static Grid getINSTANCE() {
		return INSTANCE;
	}

	public static void setINSTANCE(Grid INSTANCE) {
		Grid.INSTANCE = INSTANCE;
	}

	public Coordinate[][] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinate[][] coordinates) {
		this.coordinates = coordinates;
	}

}
