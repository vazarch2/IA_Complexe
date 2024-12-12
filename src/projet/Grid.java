package projet;

public class Grid {

	public static Grid INSTANCE = new Grid();

	
	private Coordinate[][] coordinates; //premier tableau = x, deuxieme tableau = y
	
	private Grid() {
		this.coordinates = new Coordinate[11][11];
		for(int i=0; i < 11; i++) {
			for (int j=0; j<11; j++) {
				Coordinate c =  new Coordinate(i, j);
				if(j==5 && i==5) {
					c.isBase = true;
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
		if(from.x < to.x) {
			factorX = 1;
		}else if(from.x > to.x) {
			factorX = -1;
		}
		int cpt = 0;
		int column = from.x;
		while(column != to.x) {
			column = column + factorX;
			path[cpt] = this.coordinates[column][from.y];
			cpt++;
		}
		int factorY = 0;
		if(from.y < to.y) {
			factorY = 1;
		}else if(from.y > to.y) {
			factorY = -1;
		}
		int row = from.y;
		while(row != to.y) {
			row = row + factorY;
			path[cpt] = this.coordinates[to.x][row];
			cpt++;
		}
		return path;
	}
	
	public void setFireToCoordinate(int x, int y) {
		this.coordinates[x][y].isFire = true;
	}
	
	public void unsetFireOnCoordinate(int x, int y) {
		this.coordinates[x][y].isFire = false;
	}
	
	public void moveRobot(String robotName, Coordinate from, Coordinate to) {
		this.coordinates[from.x][from.y].removeRobot(robotName);
		this.coordinates[to.x][to.y].addRobot(robotName);
	}
	
	
	public Coordinate[] getNeighbors(Coordinate coordinate) { 
		Coordinate[] neighbors;
		int x = coordinate.x;
		int y = coordinate.y;
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
		return Math.abs(from.x - to.x) + Math.abs(from.y - to.y);
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
