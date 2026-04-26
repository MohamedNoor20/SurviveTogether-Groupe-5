package griffith;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import audio.GameAudio;
import game.GamePanel;
import game.MyLevelPanel;
import menu.DifficultyPanel;
import menu.MenuPanel;

public class Main {

	private JFrame frame; // main window
	private GamePanel gamePanel; // game screen
	private MyLevelPanel myLevelPanel; // medium mode level (SUSAN OGOZI)
	private MenuPanel menuPanel; // menu screen new (susan ogozi)
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

		menuPanel = new MenuPanel(this);
		difficultyPanel = new DifficultyPanel(this);
		audio = new GameAudio();

		showMainMenu();

		frame.setVisible(true);
		audio.play("background");
	}

	public void showMainMenu() {
		frame.getContentPane().removeAll();
		frame.add(menuPanel);

		if (audio != null) {
			audio.stop("gamePlay");
			audio.play("background");
		}

		frame.revalidate();
		frame.repaint();
		menuPanel.requestFocusInWindow();
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
			myLevelPanel = new MyLevelPanel(this);
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
	// added menu button method and it is inside the game 
	public JButton createMenuButton(int baseX, int baseY, int baseW, int baseH) {

		double s = scale;

		JButton btn = new JButton("MENU");
		btn.setBackground(Color.BLACK);
		btn.setForeground(Color.WHITE);

		btn.setBounds((int) (baseX * s), (int) (baseY * s), (int) (baseW * s), (int) (baseH * s));

		btn.setFocusPainted(false);
		btn.setContentAreaFilled(true);
		btn.setOpaque(true);
		btn.setBorderPainted(true);

		btn.addActionListener(e -> showMainMenu());

		return btn;
	}
}