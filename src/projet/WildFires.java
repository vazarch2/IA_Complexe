package projet;

import java.util.ArrayList;
import java.util.Arrays;

public class WildFires {

    private static WildFires INSTANCE = null;

    private int fireQuantity;
    private int timeBeforePropagation;

    private ArrayList<Fire> fires;
    private int timeBeforeNewFire;
    private int nbFiresPutedOut = 0;

    public WildFires(int nbExistingFires, int fireQuantity, int timeBeforePropagation) {
        // Initialisation des feux
        fires = new ArrayList<Fire>();
        for(int i = 0; i < nbExistingFires; i++) {
            this.newFire();
        }
        this.timeBeforeNewFire = 10;
        this.fireQuantity = fireQuantity;
        this.timeBeforePropagation = timeBeforePropagation;
    }

    public static WildFires getInstance(int nbExistingFires, int fireQuantity, int timeBeforePropagation) {
        if (INSTANCE == null){
            INSTANCE = new WildFires(nbExistingFires, fireQuantity, timeBeforePropagation);
        }
        return INSTANCE;
    }

    public static WildFires getInstance() {
        if (INSTANCE == null) {
            getInstance(10, 10, 50);
        }
        return INSTANCE;
    }

    public void next() {
        // Appelle les next des feux, modifie éventuellement this.fires et Grid
        for (Fire fire : this.fires) {
            fire.next();
        }

        // Gestion du temps avant l'apparition d'un nouveau feu
        if (this.timeBeforeNewFire > 0) {
            this.timeBeforeNewFire--;
        } else {
            this.newFire();
            this.timeBeforeNewFire = 10; // Réinitialisation du compte à rebours
        }
    }

    public void newFire() {

        // Utiliser Grid pour déterminer une position pour le nouveau feu
        int x = (int) (Math.random() * 11);
        int y = (int) (Math.random() * 11);
        Coordinate firePosition = Grid.getInstance().coordinates[x][y];

        // Vérifier si la position n'est pas déjà occupée par un feu ou une base
        if (!firePosition.isFire && !firePosition.isBase) {
            // Créer un nouveau feu et l'ajouter à la liste des feux
            fires.add(new Fire(firePosition, this.fireQuantity, this.timeBeforePropagation));
            firePosition.isFire = true;
        } else {
            // Choisir une nouvelle position si la première est occupée par
            this.newFire();
        }
    }

    public Fire newVirtualFire(Coordinate coordinate) {
        // Créer un nouveau feu virtuel
        return new Fire(coordinate, this.fireQuantity, this.timeBeforePropagation);
    }

    public static WildFires getINSTANCE() {
        return INSTANCE;
    }

    public static void setINSTANCE(WildFires INSTANCE) {
        WildFires.INSTANCE = INSTANCE;
    }

    public int getFireQuantity() {
        return fireQuantity;
    }

    public void setFireQuantity(int fireQuantity) {
        this.fireQuantity = fireQuantity;
    }

    public int getTimeBeforePropagation() {
        return timeBeforePropagation;
    }

    public void setTimeBeforePropagation(int timeBeforePropagation) {
        this.timeBeforePropagation = timeBeforePropagation;
    }

    public ArrayList<Fire> getFires() {
        return fires;
    }

    public void setFires(ArrayList<Fire> fires) {
        this.fires = fires;
    }

    public int getTimeBeforeNewFire() {
        return timeBeforeNewFire;
    }

    public void setTimeBeforeNewFire(int timeBeforeNewFire) {
        this.timeBeforeNewFire = timeBeforeNewFire;
    }

    public int getNbFiresPutedOut() {
        return nbFiresPutedOut;
    }

    public void setNbFiresPutedOut(int nbFiresPutedOut) {
        this.nbFiresPutedOut = nbFiresPutedOut;
    }
}
    