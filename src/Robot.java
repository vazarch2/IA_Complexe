import java.util.ArrayList;

public class Robot {

    Coordinate goal;
    private int battery = 3;
    private Coordinate position;
    private String name;
    private ArrayList<Coordinate> route;
    private ArrayList<Fire> knowFire;
    private boolean saving = false;
    private boolean hasNewFireInfo = false;

    public Robot(String robotName, Coordinate position) {
        this.name = robotName;
        this.position = position;
        route = new ArrayList<>();
        knowFire = new ArrayList<>();
    }

    public void update(Coordinate baseCoordinate, Grid grid) {
        System.out.print(name + ":");
        ArrayList<Coordinate> voisins = grid.getNeighbors(position);
        ArrayList<Fire> newFire = new ArrayList<>();
        boolean fire = false;
        for (Coordinate voisin : voisins) {
            if(voisin.isFire()){
                newFire.add(new Fire(voisin));
                if( battery > 0){
                    grid.unsetFireOnCoordinate(voisin.getX(), voisin.getY(),true);
                    battery--;
                    System.out.println("battery:" + battery);
                    fire = true;
                    newFire.remove(new Fire(voisin));
                }
                
            }

        }

        if (grid.getCoordinate(position.getX(), position.getY()).getNbPeople() > 0 && !saving) {
            grid.getCoordinate(position.getX(), position.getY()).setNbPeople(0);
            battery--;
            saving = true;
        }


        if (!newFire.isEmpty()) {
            knowFire.addAll(newFire);
            hasNewFireInfo = true; // Set the flag to true if new fire info is found
        }
        
        if (baseCoordinate.compareTo(this.position)) {
            if (knowFire.isEmpty()) {

                int x = (int) (Math.random() * grid.getCoordinates().length);
                int y = (int) (Math.random() * grid.getCoordinates().length);
                goal = grid.getCoordinates()[x][y];

            } else goal = knowFire.removeFirst().getPosition();
            
            if (saving){
                saving = false;
                MainAlgorythm.getBase().addSavedPeople();
            }

            route = grid.getPath(position, goal);

        } else {
            if (route.isEmpty() || battery == 0 || saving) { 
                goal = baseCoordinate;
                route = grid.getPath(position, goal);
            } else {
                if (position.compareTo(goal)) {
                    if (!knowFire.isEmpty()) goal = knowFire.removeFirst().getPosition();
                    else goal = baseCoordinate;
                    route = grid.getPath(position, goal);
                }
            }
        }
        
        
        if (fire) return;

        if (!route.isEmpty()) {
            Coordinate newPosition = route.removeFirst();
            //System.out.println(name + ":" + newPosition.isFire() + "," + route);
            grid.moveRobot(name, this.position, newPosition);
            this.position = newPosition;
        }

        
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
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


    public ArrayList<Fire> getKnowFire() {
        return knowFire;
    }

    public void setKnowFire(ArrayList<Fire> knowFire) {
        this.knowFire = knowFire;
    }

    public boolean hasNewFireInfo() {
        return hasNewFireInfo;
    }

    public void resetNewFireInfo() {
        hasNewFireInfo = false;
    }
}
