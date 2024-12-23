public class MainAlgorythm {

    private static final int MAXTIME = 60000;
    private static final int timeToSleep = 100;
    private static final int nbExplorationRobots = 3;
    private static final int nbRobotsRescuit = 7;
    private static final int nbExistingFires = 1;
    public static Grid grid;
    private static Interface view;
    private static WildFires wildfires;
    private static Base base;
    private static int remainingTime = 60000;

    public static void doTasks() {
        while (true) {
            wildfires.update();
            if (wildfires.getFires().isEmpty()) return;
            base.update();
            view.update();
            remainingTime -= timeToSleep;
            System.out.println("Remaining time: " + remainingTime);
            if (remainingTime == 0) return;
        }

    }

    public static void main(String[] args) {
        grid = new Grid(11, 11);
        wildfires = new WildFires(nbExistingFires);
        base = new Base(nbRobotsRescuit, nbExplorationRobots);
        view = new Interface();
        doTasks();
        System.out.println("Nombre de personnes sauvées : " + base.getNbSavedPeople());
        System.exit(200);

    }
}