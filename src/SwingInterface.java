import javax.swing.*;
import java.awt.*;

public class SwingInterface extends JFrame {

    private final Grid grid;
    private final JPanel gridPanel;
    private final JLabel[][] cellLabels;

    public SwingInterface(Grid grid) {
        this.grid = grid;

        // Initialize JFrame
        setTitle("Wildfire Simulation Grid");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);

        // Get grid dimensions
        int rows = grid.getCoordinates().length;
        int cols = grid.getCoordinates()[0].length;

        // Set layout and initialize grid panel
        gridPanel = new JPanel(new GridLayout(rows, cols));
        cellLabels = new JLabel[rows][cols];

        // Create cells for the grid
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JLabel cell = new JLabel("", SwingConstants.CENTER);
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cell.setOpaque(true);
                cellLabels[i][j] = cell;
                gridPanel.add(cell);
            }
        }

        // Add grid panel to frame
        add(gridPanel);

        // Initial update and make frame visible
        updateGrid();
        setVisible(true);
    }

    public void updateGrid() {
        Coordinate[][] coordinates = grid.getCoordinates();

        for (int i = 0; i < coordinates.length; i++) {
            for (int j = 0; j < coordinates[i].length; j++) {
                Coordinate coord = coordinates[i][j];
                JLabel cell = cellLabels[i][j];

                if (coord.isBase()) {
                    cell.setBackground(Color.BLUE);
                    cell.setText("Base");
                } else if (coord.isFire()) {
                    cell.setBackground(Color.RED);
                    cell.setText("Fire");
                } else if (!coord.getRobots().isEmpty()) {
                    cell.setBackground(Color.GREEN);
                    cell.setText(coord.getRobots().toString());
                } else if (coord.getNbPeople() > 0) {
                    cell.setBackground(Color.YELLOW);
                    cell.setText("People: " + coord.getNbPeople());
                } else if (coord.getSecheresse() == 0) {
                    cell.setBackground(Color.GRAY);
                    cell.setText("X");
                } else {
                    cell.setBackground(Color.WHITE);
                    cell.setText("");
                }
            }
        }

        // Refresh UI
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    public void showEndScreen(int extinguishedFires, int savedPeople, int initialPeople, int time) {
        // Clear the frame
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        // Create end screen components
        JLabel endLabel = new JLabel("Simulation Complete", SwingConstants.CENTER);
        endLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel statsLabel = new JLabel(
                String.format("Extinguished Fires: %d, Saved People: %d / %d, Total time : %d", extinguishedFires, savedPeople, initialPeople, time),
                SwingConstants.CENTER
        );
        statsLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        // Add components to frame
        add(endLabel, BorderLayout.NORTH);
        add(statsLabel, BorderLayout.CENTER);

        // Refresh UI
        revalidate();
        repaint();
    }

    
    
}