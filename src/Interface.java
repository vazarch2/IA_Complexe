public class Interface {

    /**
     * Create the frame.
     */
    public Interface() {
        this.TerminalInterface(MainAlgorythm.grid);
    }

    public void update() {
        this.TerminalInterface(MainAlgorythm.grid);
    }

    public void TerminalInterface(Grid grid) {
        System.out.print("\033\143");
        System.out.println();
        Coordinate coordinate;
        for (int i = 0; i < grid.getCoordinates().length; i++) {
            for (int j = 0; j < grid.getCoordinates().length; j++) {
                coordinate = grid.getCoordinates()[i][j];
                if (!coordinate.getRobots().isEmpty()) System.out.print("R" + coordinate.getRobots());
                if (coordinate.isBase()) {
                    System.out.print('B');
                } else if (coordinate.isFire()) {
                    System.out.print('F');
                } else {
                    System.out.print(' ');
                }
                System.out.print(' ');
            }
            System.out.println();
        }
    }


}