import java.util.ArrayList;

public class Base {

    public final Coordinate position;
    private int savedPeople = 0;
    private ArrayList<Robot> robotsFire;
    private ArrayList<Fire> knownFires;
    private ArrayList<Robot> robotsRescuit;


    public Base(int nbRobots) {
        this.robotsRescuit = new ArrayList<>();
        this.robotsFire = new ArrayList<>();
        this.knownFires = new ArrayList<>();
        this.position = MainAlgorythm.grid.getBaseCoord();
        for (int i = 0; i < nbRobots; i++) {
            Robot newRobot = new Robot("e" + i, position);
            this.robotsFire.add(newRobot);
            MainAlgorythm.grid.getCoordinates()[position.getX()][position.getY()].addRobot(newRobot.getName());
        }
    }


    public void update() {
        for (Robot robotsFire : robotsFire) {
            if (this.position.compareTo(robotsFire.getPosition()) && robotsFire.hasNewFireInfo()) {
                ArrayList<Fire> robotFire = robotsFire.getKnowFire();
                for (Fire fire : robotFire) {
                    addFire(fire);
                }
                sortFires();
                robotsFire.setKnowFire(knownFires);
                if (!knownFires.isEmpty()) knownFires.removeFirst();
                robotsFire.setBattery(3);
                robotsFire.resetNewFireInfo(); // Reset the flag after sharing the info
            }
            robotsFire.update(this.position, MainAlgorythm.grid);
        }
        System.out.println("Base: " + savedPeople + " people saved");
        System.out.println("Base: " + knownFires + " fires known");
    }


    public void addFire(Fire fire) {
        if (!knownFires.contains(fire)) knownFires.add(fire);
    }

    public void removeFire(Fire fire) {

    }


    /**
     * on trie par distance: les feux les plus proches de la base
     *  seront eteints en premier
     */
    private void sortFires() {
        this.knownFires.sort((Fire f1, Fire f2) -> {
            int distanceA = MainAlgorythm.grid.getDistance(position, f1.getPosition());
            int distanceB = MainAlgorythm.grid.getDistance(position, f2.getPosition());
            return Integer.compare(distanceB, distanceA);
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


    public int getSavedPeople() {
        return savedPeople;
    }

    public void addSavedPeople(){
        this.savedPeople++;
    }
    


}
