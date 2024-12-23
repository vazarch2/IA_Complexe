import java.util.ArrayList;

public class Robot {

    Coordinate goal;
    private int battery = 30;
    private int water = 20;
    private Coordinate position;
    private String name;
    private ArrayList<Coordinate> route;
    private ArrayList<Fire> knowFire;

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
            if (voisin.isFire() && water > 0) {
                grid.unsetFireOnCoordinate(voisin.getX(), voisin.getY());
                water--;
                fire = true;
            } else if (voisin.isFire())
                newFire.add(new Fire(voisin));
        }
        if (baseCoordinate.compareTo(this.position)) {
            if (knowFire.isEmpty()) {
                int x = (int) (Math.random() * grid.getCoordinates().length);
                int y = (int) (Math.random() * grid.getCoordinates().length);
                goal = grid.getCoordinates()[x][y];
            }
            route = grid.getPath(position, goal);
        } else {
            if (route.isEmpty() || (battery == grid.getDistance(position, baseCoordinate)) || (water == 0)) {
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
        knowFire.addAll(newFire);
        if (fire) return;
        Coordinate newPosition = route.removeFirst();
        //System.out.println(name + ":" + newPosition + "," + route);
        grid.moveRobot(name, position, newPosition);
        position = newPosition;
        battery--;
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


    public ArrayList<Fire> getKnowFire() {
        return knowFire;
    }

    public void setKnowFire(ArrayList<Fire> knowFire) {
        this.knowFire = knowFire;
    }


}
