package projet;

public class Fire {

    private Coordinate position;
    private int fireQuantity; // le nombre de deci-seconde qu'il faut pour éteindre le feu
    private int timeBeforePropagation;
    private int nbPeople;
    private int maxFireQuantity;
    private int maxTimeBeforePropagation;

    public Fire(Coordinate position, int fireQuantity, int timeBeforePropagation) {
        this.position = position;
        this.nbPeople = position.getNbPeople();
        this.fireQuantity = fireQuantity;
        this.timeBeforePropagation = timeBeforePropagation;
        this.maxFireQuantity = fireQuantity;
        this.maxTimeBeforePropagation = timeBeforePropagation;
    }

    public void next() {
        // Logique pour le déroulement du feu à chaque itération (à faire)
        if (this.fireQuantity > 0) {
            // Éventuellement propagation
            if (this.timeBeforePropagation == 0) {
                this.propagation();
                this.timeBeforePropagation = 50; // Exemple de délai avant la prochaine propagation
            } else {
                this.timeBeforePropagation--;
            }
            this.killPeople();
        }
    }

    public void propagation() {

    	Coordinate[] neighbors = Grid.getInstance().getNeighbors(position);

        for (Coordinate neighbor : neighbors) {
            int probability = (int) Math.floor(Math.random() * 100);
            if (probability < 33) {
                neighbor.setFire(true);
                WildFires.getInstance().addFire(new Fire(neighbor, this.maxFireQuantity, this.maxTimeBeforePropagation));
            }
        }
    }

    public void virtualDecrementation(int fireQuantity) {
        this.fireQuantity-= fireQuantity;
    }

     public void decrementation() {
        this.fireQuantity--;
        if(this.fireQuantity <= 0){
            this.position.setFire(false);
        }
    }

    public Coordinate getPosition() {
        return position;
    }

    public void killPeople() {
        Grid.getInstance().getCoordinates()[position.getX()][position.getY()].decrementTimeBeforeDead();
    }


    public void setPosition(Coordinate position) {
        this.position = position;
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

    public int getNbPeople() {
        return nbPeople;
    }

    public void setNbPeople(int nbPeople) {
        this.nbPeople = nbPeople;
    }

    public int getMaxFireQuantity() {
        return maxFireQuantity;
    }

    public void setMaxFireQuantity(int maxFireQuantity) {
        this.maxFireQuantity = maxFireQuantity;
    }

    public int getMaxTimeBeforePropagation() {
        return maxTimeBeforePropagation;
    }

    public void setMaxTimeBeforePropagation(int maxTimeBeforePropagation) {
        this.maxTimeBeforePropagation = maxTimeBeforePropagation;
    }
}