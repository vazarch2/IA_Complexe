import java.util.ArrayList;

public class Coordinate {

    private final ArrayList<String> robots;
    private int x;
    private int y;
    private int secheresse = 1;
    private boolean isBase = false;
    private boolean isFire = false;
    private int nbPeople;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        robots = new ArrayList<String>();
        this.nbPeople = (int) Math.floor(Math.random() * 10);
    }

    public void addRobot(String robotName) {
        robots.add(robotName);
    }

    public void removeRobot(String robotName) {
        robots.remove(robotName);
    }

    public String toString() {
        return "[" + x + ":" + y + "]";
    }

    public void addPeople(int nbPeople) {
        this.nbPeople += nbPeople;
    }

    public ArrayList<String> getRobots() {
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

    public boolean compareTo(Coordinate coordinate) {
        return this.x == coordinate.getX() && this.y == coordinate.getY();
    }

    public int getSecheresse() {
        return secheresse;
    }

    public void setSecheresse(int secheresse) {
        this.secheresse = secheresse;
    }
}
