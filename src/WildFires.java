import java.util.ArrayList;

public class WildFires {


    private ArrayList<Fire> fires;


    public WildFires(int nbExistingFires) {
        // Initialisation des feux
        fires = new ArrayList<Fire>();
        for (int i = 0; i < nbExistingFires; i++) {
            this.firstFire();
        }
    }

    public void update() {
        ArrayList<Fire> newFire = new ArrayList<>();
        ArrayList<Fire> oldFire = new ArrayList<>();
        for (int i = 0; i < fires.size(); i++) {
            Fire actualFire = fires.get(i);
            Coordinate position = actualFire.getPosition();
            int secheresse = position.getSecheresse();
            position.setSecheresse(secheresse <<= 1);

            for (Coordinate voisin : MainAlgorythm.grid.getNeighbors(position)) {
                voisin.setSecheresse(voisin.getSecheresse() << 1);
                int aleatoire = (int) (Math.random() * 512 + 1);
                if (voisin.getSecheresse() > aleatoire) {
                    newFire.add(new Fire(voisin));
                    MainAlgorythm.grid.setFireToCoordinate(voisin.getX(), voisin.getY());
                    MainAlgorythm.grid.killPeople(voisin.getX(), voisin.getY());
                }
            }
            if (secheresse > 2048 || secheresse == 0){
                oldFire.add(actualFire);
                MainAlgorythm.grid.unsetFireOnCoordinate(position.getX(), position.getY(),false);
                //System.out.println("Fire at " + position + " has been extinguished");
                continue;
            } 
        }
        fires.removeAll(oldFire);
        fires.addAll(newFire);
    }

    public void firstFire() {
        // Utiliser Grid pour déterminer une position pour le nouveau feu
        int x = (int) (Math.random() * MainAlgorythm.grid.getCoordinates().length);
        int y = (int) (Math.random() * MainAlgorythm.grid.getCoordinates().length);
        Coordinate firePosition = MainAlgorythm.grid.getCoordinates()[x][y];
        while (!firePosition.isBase() && !firePosition.isFire()) {
            x = (int) (Math.random() * MainAlgorythm.grid.getCoordinates().length);
            y = (int) (Math.random() * MainAlgorythm.grid.getCoordinates().length);
            firePosition = MainAlgorythm.grid.getCoordinates()[x][y];
            fires.add(new Fire(firePosition));
            firePosition.setFire(true);
        }
    }


    public ArrayList<Fire> getFires() {
        return fires;
    }

    public void setFires(ArrayList<Fire> fires) {
        this.fires = fires;
    }

    public void removeFire(Fire fire) {
        for (int i = 0; i < fires.size(); i++) {
            if (fires.get(i).getPosition().compareTo(fire.getPosition())) {
                fires.remove(i);
                break;
            }
        }
    }


}
    