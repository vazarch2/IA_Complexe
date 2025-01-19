import java.util.ArrayList;

public class Grid {


    private Coordinate baseCoord;
    private Coordinate[][] coordinates; //premier tableau = x, deuxieme tableau = y

    private int nbExtinguishedFires = 0;

    public Grid(int x, int y) {
        this.coordinates = new Coordinate[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Coordinate c = new Coordinate(i, j);
                if (j == Math.round((float) x / 2) && i == Math.round((float) y / 2)) {
                    c.setBase(true);
                    baseCoord = c;
                }
                this.coordinates[i][j] = c;
            }
        }
    }

    public int getDistance(Coordinate from, Coordinate to) {
        return Math.abs(from.getX() - to.getX()) + Math.abs(from.getY() - to.getY());
    }

    public ArrayList<Coordinate> getPath(Coordinate from, Coordinate to) {
        ArrayList<Coordinate> path = new ArrayList<>();
        int from_x = from.getX();
        int from_y = from.getY();
        int factorX = 0, factorY = 0;
        if (from_x > to.getX()) {
            factorX = -1;
        } else {
            factorX = 1;
        }
        if (from_y > to.getY()) {
            factorY = -1;
        } else {
            factorY = 1;
        }
        while (from_x != to.getX()) {
            if (from_x != to.getX()) from_x += factorX;
            path.add(new Coordinate(from_x, from_y));
        }
        while (from_y != to.getY()) {
            if (from_y != to.getY()) from_y += factorY;
            path.add(new Coordinate(from_x, from_y));
        }
        path.add(to);
        return path;
    }

    public void setFireToCoordinate(int x, int y) {
        this.coordinates[x][y].setFire(true);
    }

    public void unsetFireOnCoordinate(int x, int y) {
        this.coordinates[x][y].setFire(false);
        this.coordinates[x][y].setSecheresse(0);
        nbExtinguishedFires++;

    }

    public void killPeople(int x, int y) {
        this.coordinates[x][y].setNbPeople(0);
    }

    public void moveRobot(String robotName, Coordinate from, Coordinate to) {
        this.coordinates[from.getX()][from.getY()].removeRobot(robotName);
        this.coordinates[to.getX()][to.getY()].addRobot(robotName);
    }

    public ArrayList<Coordinate> getNeighbors(Coordinate coordinate) {
        ArrayList<Coordinate> neighbors = new ArrayList<>();
        int x = coordinate.getX();
        int y = coordinate.getY();
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                try {
                    if (!coordinates[i][j].compareTo(coordinate)) neighbors.add(coordinates[i][j]);
                } catch (Exception e) {
                }
            }
        }
        return neighbors;
    }


    public Coordinate[][] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinate[][] coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinate getBaseCoord() {
        return baseCoord;
    }

    public int getNbExtinguishedFires() {
        return nbExtinguishedFires;
    }

}
