package projet;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JTextPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.BoxLayout;

public class Interface extends JFrame {

	private JPanel contentPane;

	private JPanel gridPanel;


	private Grid grid = Grid.getInstance();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface frame = new Interface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void update() {
		this.grid = Grid.getInstance();
		EventQueue.invokeLater(() -> {
			this.gridPanel.removeAll();
			this.createGridPanel(gridPanel);
			this.gridPanel.repaint(); //redessiner la grille
			this.gridPanel.revalidate();
		});
	}

	/**
	 * Create the frame.
	 */
	public Interface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1600, 1000);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(4, 5));

		JPanel header = new JPanel();
		contentPane.add(header, BorderLayout.NORTH);

		gridPanel = new JPanel();
		contentPane.add(gridPanel, BorderLayout.CENTER);
		gridPanel.setLayout(new GridLayout(11, 11, 0, 0));

		this.createGridPanel(gridPanel);
	}

	public void createGridPanel(JPanel gridPanel) {

		Coordinate coordinate;

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				coordinate = this.grid.coordinates[i][j];
				JPanel coordinatexij = new JPanel();
				if (coordinate.isBase) {
					coordinatexij.setBackground(new Color(200, 200, 0));
				} else if (coordinate.isFire) {
					coordinatexij.setBackground(new Color(255, 0, 0));
				} else {
					coordinatexij.setBackground(new Color(0, 200, 0));
				}
				gridPanel.add(coordinatexij);
				JTextPane robotsNamesxij = new JTextPane();
				robotsNamesxij.setText("R: " + coordinate.getRobots());
				coordinatexij.add(robotsNamesxij);
				JTextPane nbPeoplexij = new JTextPane();
				nbPeoplexij.setText("nbH: " + coordinate.nbPeople);
				coordinatexij.add(nbPeoplexij);
			}
		}
	}

	@Override
	public JPanel getContentPane() {
		return contentPane;
	}

	public void setContentPane(JPanel contentPane) {
		this.contentPane = contentPane;
	}

	public JPanel getGridPanel() {
		return gridPanel;
	}

	public void setGridPanel(JPanel gridPanel) {
		this.gridPanel = gridPanel;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}
}