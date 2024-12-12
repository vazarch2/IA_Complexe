import java.util.Timer;
import java.util.TimerTask;

public class MainAlgorythm {

    private static Base base;
    private static WildFires wildfires;
    private static Interface view;

    private static final int MAXTIME = 60000;
    private static int remainingTime = 60000;
    private static final int timeToSleep = 100;

    private static int nbExplorationRobots = 3;
    private static int nbRobots = 7;
    private static int nbExistingFires = 10;

    private static int maxFireQuantity = 10;
    private static int maxTimeBeforePropagation = 50;

    public static void doTasks() {
        base.next();
        wildfires.next();
        view.update();
    }

    public static void main(String[] args) {
        base = Base.getInstance(nbRobots, nbExplorationRobots);
        wildfires = WildFires.getInstance(nbExistingFires, maxFireQuantity, maxTimeBeforePropagation);
        view = new Interface();
        view.setVisible(true);

        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                doTasks();
                remainingTime -= timeToSleep;

                if (remainingTime <= 0 || wildfires.getFires().isEmpty() || wildfires.getFires().size() == 120) {
                    timer.cancel(); // Stop the timer when the condition is met
                    if (remainingTime <= 0 || wildfires.getFires().size() == 120) {
                        int nbPeopleSaved = base.getNbSavedPeople();
                        System.out.println("Perdu, il reste " + wildfires.getFires().size() + " feux, vous avez sauvé "
                                + nbPeopleSaved + " personnes");
                        System.out.println("Nombre de personnes non sauvées : " + base.getUnsavedPeopleCount());
                        System.out.println("Nombre de feux eteints : " + wildfires.getNbFiresPutedOut());
                    } else {
                        double time = (MAXTIME - remainingTime) / 1000;
                        System.out.print("Feux éteints en :" + time + "s");
                        System.out.println("Nombre de personnes non sauvées : " + base.getUnsavedPeopleCount());
                        System.out.println("Nombre de feux eteints : " + wildfires.getNbFiresPutedOut());
                    }
                }
            }
        };

        timer.scheduleAtFixedRate(task, 1000, timeToSleep);
    }
}