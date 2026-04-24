package griffith;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import audio.GameAudio;
import game.GamePanel;
import game.MyLevelPanel;
import menu.DifficultyPanel;
import menu.NewMainMenuPanel;

public class Main {

	private JFrame frame; // main window
	private GamePanel gamePanel; // game screen
	private MyLevelPanel myLevelPanel; // medium mode level (SUSAN OGOZI)
	private NewMainMenuPanel newmainmenuPanel; // menu screen new (susan ogozi)
	private DifficultyPanel difficultyPanel;
	private GameAudio audio;
	private String currentDifficulty;

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

		/*
		// create the menu and game screens
		menuPanel = new MenuPanel(this);
		gamePanel = new GamePanel(this);

		// show the menu first when program starts
		showMenu(false);

		frame.setVisible(true);
		//
		audio = new GameAudio();
		audio.play("background");
		*/

		newmainmenuPanel = new NewMainMenuPanel(this);
		difficultyPanel = new DifficultyPanel(this);
		audio = new GameAudio();

		showMainMenu();

		frame.setVisible(true);
		audio.play("background");
	}

	/*
	// switches to the menu screen
	public void showMenu(boolean isGameOver) {
		// remove game panel if it's there
		frame.remove(gamePanel);
		frame.add(menuPanel);
		
		//Update restart music when showing menu(Susan Ogozi)
		if (audio != null) {
			audio.stop("gamePlay");
			audio.play("background");
		}
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
	*/

	/*
	// switches to the game screen and starts the game loop
	public void startGame() {
		frame.getContentPane().removeAll();

		// create a fresh game panel each time
		gamePanel = new GamePanel(this);
		
		frame.add(gamePanel);
		frame.revalidate();
		frame.repaint();
		
		//Update switch to game music
		if (audio != null) {
			audio.stop("background");
			audio.play("gamePlay");
		}
		
		gamePanel.requestFocusInWindow();
		//start the game loop (GamePanel handles its own thread via Runnable)
		gamePanel.startGame();
	}
	*/

	/*
	// restart is the same as starting a new game
	public void restartGame() {
		startGame();
	}
	*/

	public void showMainMenu() {
		frame.getContentPane().removeAll();
		frame.add(newmainmenuPanel);
		
		if (audio != null) {
			audio.stop("gamePlay");
			audio.play("background");
		}
		
		frame.revalidate();
		frame.repaint();
		newmainmenuPanel.requestFocusInWindow();
	}

	public void showDifficultyMenu() {
		frame.getContentPane().removeAll();
		frame.add(difficultyPanel);
		
		frame.revalidate();
		frame.repaint();
		difficultyPanel.requestFocusInWindow();
	}

	public void startGame(String difficulty) {
		this.currentDifficulty = difficulty;
		frame.getContentPane().removeAll();

		if (difficulty.equals("medium")) {
			myLevelPanel = new MyLevelPanel();
			myLevelPanel.setMainFrame(this);
			frame.add(myLevelPanel);
			frame.revalidate();
			frame.repaint();
			myLevelPanel.requestFocusInWindow();
			myLevelPanel.startGame();
		} else {
			gamePanel = new GamePanel(this, difficulty);
			frame.add(gamePanel);
			frame.revalidate();
			frame.repaint();
			gamePanel.requestFocusInWindow();
			gamePanel.startGame();
		}
		
		if (audio != null) {
			audio.stop("background");
			audio.play("gamePlay");
		}
	}

	public void showGameOver() {
		if (audio != null) {
			audio.stop("gamePlay");
		}
		showMainMenu();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Main());
	}
}