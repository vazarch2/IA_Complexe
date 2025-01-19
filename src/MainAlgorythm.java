import java.util.Scanner;

public class MainAlgorythm {

    private static final int MAXTIME = 120000;
    private static final int timeToSleep = 100;
    
    private static int nbRobotsRescuit = 7;
    private static int nbExistingFires = 2;
    private static int initialPeople = 0;
    public static Grid grid;
    private static SwingInterface view;
    private static WildFires wildfires;
    private static Base base;
    private static int remainingTime = 120000;

    public static WildFires getWildfires() {
        return wildfires;
    }

    public static Base getBase(){
        return base;
    }

    public static void doTasks() {
        
        int count = 0;

        while (true) {
     
            
            
            view.updateGrid();
            if (count == 0){
                wildfires.update();
            }

            

            if (grid.getNbFire() == 0) return;
            if (grid.getNbPeopleWithoutBase() == 0) return;

            view.updateGrid();
            remainingTime -= timeToSleep;
            base.update();
            view.updateGrid();
            System.out.println("Remaining time: " + remainingTime);
            if (remainingTime == 0) return;

            try {
                Thread.sleep(timeToSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
          
            if (count == 4){
                count = 0;
            } else{
                count++;
            }
          
        }

    }

    public static void main(String[] args) {
        System.out.println("entrez le nombre de feux");
        Scanner sc = new Scanner(System.in);
        while (!sc.hasNextInt()) {
            System.out.println("Entrez un nombre valide de feux!");
            sc.next();
        }
        nbExistingFires = sc.nextInt();

        System.out.println("Entrez le nombre de robots:");
        while (!sc.hasNextInt()) {
            System.out.println("Entrez un nombre valide de robots!");
            sc.next();
        }
        nbRobotsRescuit = sc.nextInt();
        sc.close();
        grid = new Grid(21, 21);
        initialPeople = grid.getNbPeople();
        wildfires = new WildFires(nbExistingFires);
        base = new Base(nbRobotsRescuit);
        view = new SwingInterface(grid);
    
        // Run doTasks in a separate thread
        new Thread(() -> {
            doTasks();
            System.out.println("Nombre de personnes sauvées : " + grid.getNbPeople());

            view.showEndScreen(grid.getNbExtinguishedFires(), grid.getNbPeople() + base.getSavedPeople(), initialPeople, MAXTIME-remainingTime);
        }).start();

        
    }
}