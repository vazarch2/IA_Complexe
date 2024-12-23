import java.util.ArrayList;

public class Base {

    public final Coordinate position;
    private ArrayList<Robot> robotsFire;
    private ArrayList<Fire> knownFires;
    private ArrayList<Robot> robotsRescuit;
    private int nbSavedPeople = 0;


    public Base(int nbRobots, int nbExplorationRobots) {
        this.robotsRescuit = new ArrayList<>();
        this.robotsFire = new ArrayList<>();
        this.knownFires = new ArrayList<>();
        this.position = MainAlgorythm.grid.getBaseCoord();
        for (int i = 0; i < nbRobots; i++) {
            Robot newRobot = new Robot("e" + i, position);
            this.robotsFire.add(newRobot);
            MainAlgorythm.grid.getCoordinates()[position.getX()][position.getY()].addRobot(newRobot.getName());
        }
        for (int i = 0; i < nbExplorationRobots; i++) {
            Robot newRobot = new Robot("r" + i, position);
            this.robotsRescuit.add(new Robot("r" + i, position));
            MainAlgorythm.grid.getCoordinates()[position.getX()][position.getY()].addRobot(newRobot.getName());
        }
    }

    public void update() {
        for (Robot robotsFire : robotsFire) {
            if (position.compareTo(robotsFire.getPosition())) {
                ArrayList<Fire> robotFire = robotsFire.getKnowFire();
                for (Fire fire : robotFire) {
                    addFire(fire);
                }
                sortFires();
                robotsFire.setKnowFire(knownFires);
            }
            robotsFire.setBattery(30);
            robotsFire.setWater(20);
            robotsFire.update(this.position, MainAlgorythm.grid);
        }
    }

    public void savePeople(int nbPeople) {
        this.nbSavedPeople += nbPeople;
    }

    public void addFire(Fire fire) {
        if (!knownFires.contains(fire)) knownFires.add(fire);
    }

    public void removeFire(Fire fire) {

    }


    /**
     * on trie par distance: les feux les plus loins seront eteints en premier ce qui permet d'explorer la zone en meme temps
     */
    private void sortFires() {
        this.knownFires.sort((Fire f1, Fire f2) -> {
            int distanceA = MainAlgorythm.grid.getDistance(position, f1.getPosition());
            int distanceB = MainAlgorythm.grid.getDistance(position, f2.getPosition());
            return Integer.compare(distanceA, distanceB);
        });
    }


    public Coordinate getPosition() {
        return position;
    }


    public ArrayList<Robot> getRobotsFire() {
        return robotsFire;
    }

    public void setRobotsFire(ArrayList<Robot> robotsFire) {
        this.robotsFire = robotsFire;
    }

    public ArrayList<Fire> getKnownFires() {
        return knownFires;
    }

    public void setKnownFires(ArrayList<Fire> knownFires) {
        this.knownFires = knownFires;
    }

    public ArrayList<Robot> getRobotsRescuit() {
        return robotsRescuit;
    }

    public void setRobotsRescuit(ArrayList<Robot> robotsRescuit) {
        this.robotsRescuit = robotsRescuit;
    }


    public int getNbSavedPeople() {
        return nbSavedPeople;
    }

    public void setNbSavedPeople(int nbSavedPeople) {
        this.nbSavedPeople = nbSavedPeople;
    }


}
