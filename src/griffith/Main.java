package griffith;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

	private JFrame frame; // main window
	private GamePanel gamePanel; // game screen
	private MenuPanel menuPanel; // menu screen

	public double scale = 1.0;
	private final int BaseSize = 768;

	public Main() {
		frame = new JFrame("Survive Together");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		int usableWidth = screen.width;
		int usableHeight = screen.height;

		int size = Math.min(usableWidth, usableHeight);
		size = Math.min(size, BaseSize);

		scale = (double) size / BaseSize;

		int finalSize = (int) (BaseSize * scale);

		frame.setSize(finalSize, finalSize);
		frame.setLocationRelativeTo(null);

		// create the menu and game screens
		menuPanel = new MenuPanel(this);
		gamePanel = new GamePanel(this);

		// show the menu first when program starts
		showMenu(false);

		frame.setVisible(true);
	}

	// switches to the menu screen
	public void showMenu(boolean isGameOver) {
		// remove game panel if it's there
		frame.remove(gamePanel);
		frame.add(menuPanel);

		if (isGameOver) {
			menuPanel.showGameOverMenu();
		} else {
			menuPanel.showFullMenu();
		}

		frame.pack();
		frame.revalidate();
		frame.repaint();
		menuPanel.requestFocusInWindow();
	}

	// switches to the game screen and starts the game loop
	public void startGame() {
		frame.getContentPane().removeAll();

		// create a fresh game panel each time
		gamePanel = new GamePanel(this);
		
		frame.add(gamePanel);
		frame.revalidate();
		frame.repaint();
		
		gamePanel.requestFocusInWindow();
		//start the game loop (GamePanel handles its own thread via Runnable)
		gamePanel.startGame();
	}

	// restart is the same as starting a new game
	public void restartGame() {
		startGame();
	}

	public static void main(String[] args) {
		// run on the Swing event thread
		SwingUtilities.invokeLater(() -> new Main());
	}
}